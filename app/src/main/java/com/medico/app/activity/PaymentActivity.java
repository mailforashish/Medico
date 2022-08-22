package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medico.app.dialog.OrderCompletedDialog;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.response.Cart.CartList;
import com.medico.app.response.OrderProduct.CreateOrderResponse;
import com.medico.app.response.OrderProduct.CreateOrderResponseUpi;
import com.medico.app.response.OrderProduct.CreateOrderResult;
import com.medico.app.response.OrderRequest.Drug;
import com.medico.app.response.OrderRequest.OrderProductRequests;
import com.medico.app.response.Payment.ReportResponse;
import com.medico.app.R;
import com.medico.app.adapter.PaymentOffersAdapter;
import com.medico.app.databinding.ActivityPaymentBinding;
import com.medico.app.interfaceClass.PlaceOrder;
import com.medico.app.response.Cart.CartResult;
import com.medico.app.response.PaymentOfferList;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.medico.app.utils.SessionManager.LATITUDE;
import static com.medico.app.utils.SessionManager.LONGITUDE;

public class PaymentActivity extends AppCompatActivity implements ApiResponseInterface, PaymentResultListener, PlaceOrder {
    ActivityPaymentBinding binding;
    ApiManager apiManager;
    List<PaymentOfferList> list = new ArrayList<>();
    PaymentOffersAdapter paymentOffersAdapter;
    private String amount;
    final int UPI_PAYMENT = 0;
    SessionManager sessionManager;
    private int address_position;
    private List<AddressResult> addressLists = new ArrayList<>();
    private List<CartList> purchaseLists = new ArrayList<>();
    private String shippingCharge = "0";
    List<Drug> orderItem = new ArrayList<>();
    private String orderId;
    private String transactionId = "TID" + System.currentTimeMillis();
    private Double finalAmount;
    private String raz_payType, payment_Selector = "raz";
    HideStatus hideStatus;
    String latitude, longitude;
    String prescriptionImage;
    private List<ProductListResponse.Data> cartList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));

        Checkout.preload(getApplicationContext());
        amount = getIntent().getStringExtra("pay_amount");
        prescriptionImage = getIntent().getStringExtra("UploadImage");
        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        binding.tvTotalAmount.setText(amount);
        binding.rvPaymentOffer.setLayoutManager(new LinearLayoutManager(PaymentActivity.this, LinearLayoutManager.VERTICAL, false));
        paymentOffersAdapter = new PaymentOffersAdapter(PaymentActivity.this, list, this);
        binding.rvPaymentOffer.setAdapter(paymentOffersAdapter);
        addressLists = sessionManager.getAddressFromLocal("address");
        purchaseLists = sessionManager.getListFromLocal("cart");
        address_position = new SessionManager(this).getAddressPosition();

        HashMap<String, String> location = sessionManager.getSaveLocation();
        if (location.isEmpty()) {
        } else {
            latitude = location.get(LATITUDE);
            longitude = location.get(LONGITUDE);
        }
        setData();
    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPaymentPage() {
            onBackPressed();
        }
    }


    @Override
    public void getPlaceOrder(boolean select, String transactionType, String title) {
        if (title.equals("UPI")) {
            raz_payType = "upi";
            createOrder(transactionType);
        } else if (title.equals("Wallets")) {
            raz_payType = "wp";
            createOrder(transactionType);
        } else if (title.equals("Credit/Debit Card")) {
            raz_payType = "card";
            createOrder(transactionType);
        } else if (title.equals("Net Banking")) {
            raz_payType = "nb";
            createOrder(transactionType);
        } else if (title.equals("PhonePe")) {
            raz_payType = "pp";
            createOrder(transactionType);
        } else if (title.equals("Pay on Delivery")) {
            createOrder(transactionType);
        }

    }

    private void createOrder(String transactionType) {
        try {
            for (int i = 0; i < purchaseLists.size(); i++) {
                Drug drug = new Drug();
                drug.setDrugId(purchaseLists.get(i).getProductId().getDrugId());
                drug.setQuantity(String.valueOf(purchaseLists.get(i).getQuantity()));
                orderItem.add(drug);
            }
            OrderProductRequests orderProductRequests = new OrderProductRequests();
            orderProductRequests.setDrugs(orderItem);
            orderProductRequests.setIsCheckout(1);
            orderProductRequests.setPaymentMode(transactionType);
            orderProductRequests.setAddressId(addressLists.get(address_position).getId());
            orderProductRequests.setWallet("0");
            orderProductRequests.setCouponCode("");
            orderProductRequests.setPerception(prescriptionImage);
            orderProductRequests.setLat(latitude);
            orderProductRequests.setLon(longitude);
            Log.e("OrderItem", "" + new Gson().toJson(orderProductRequests));
            if (transactionType.equals("cod")) {
                //COD = 1//cod
                apiManager.createOrder(orderProductRequests);
            } else {
                //All Online = 2//razorpay
                apiManager.createOrderUpi(orderProductRequests);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.CREATE_ORDER) {
            CreateOrderResponse rsp = (CreateOrderResponse) response;
            if (rsp != null) {
                sessionManager.saveListInLocal("cart", cartList);
                new OrderCompletedDialog(this, "Medico" + System.currentTimeMillis(), amount);

            }
        }
        if (ServiceCode == Constant.CREATE_ORDER_UPI) {
            CreateOrderResponseUpi rsp = (CreateOrderResponseUpi) response;
            try {
                if (rsp != null) {
                    orderId = String.valueOf(rsp.getData().getOrderDetails().getId());
                    finalAmount = rsp.getData().getOrderDetails().getCutAmount();
                    if (rsp.getData().getOrderDetails().getId() != null) {
                        startRazorPayGateway(rsp.getData());
                        sessionManager.saveListInLocal("cart", cartList);
                    }
                }
            } catch (Exception e) {
            }
        }
        if (ServiceCode == Constant.VERIFY_PAYMENT) {
            ReportResponse rsp = (ReportResponse) response;
            if (rsp.getData() != null) {
                new OrderCompletedDialog(this, transactionId, String.valueOf(finalAmount));
            }
        }

    }

    public void startRazorPayGateway(CreateOrderResult orderData) {
        CreateOrderResult data = orderData;
        final Activity activity = this;
        final Checkout checkout = new Checkout();
        //checkout.setKeyID(data.getKey());
        checkout.setKeyID("rzp_live_eZnz4rVH8Xxrdv");
        //Log.e("razKey", data.getKey());

        try {
            JSONObject options = new JSONObject();
            // Notes Object
            JSONObject notes = new JSONObject();
            notes.put("amount", "" + data.getOrderDetails().getCutAmount());
            //notes.put("amount", "" + "100");
            notes.put("merchant_order_id", "merchant_order_" + data.getOrderDetails().getId());
            options.put("notes", notes);
            options.put("key", "rzp_live_eZnz4rVH8Xxrdv");
            //String amount = String.valueOf(data.getOrderDetails().getCutAmount() * 100);
            String amount = "100";
            options.put("amount", amount);
            options.put("name", "GO MEDICOS");
            options.put("description", "Order Created by " + data.getOrderDetails().getAddress().get(0).getName());
            options.put("image", "https://i.ibb.co/5c4GNMd/Asset-4100.png");
            options.put("color", R.color.color_secondary);
            options.put("id", data.getOrderDetails().getId());
            options.put("currency", "INR");
            if (raz_payType.equals("card")) {
                options.put("prefill.email", sessionManager.getMobile() + "@gmail.com");
                options.put("prefill.contact", sessionManager.getMobile());
                options.put("prefill.method", "card");
            } else if (raz_payType.equals("nb")) {
                options.put("prefill.email", sessionManager.getMobile() + "@gmail.com");
                options.put("prefill.contact", sessionManager.getMobile());
                options.put("prefill.method", "netbanking");
            } else if (raz_payType.equals("wp")) {
                options.put("prefill.email", sessionManager.getMobile() + "@gmail.com");
                options.put("prefill.contact", sessionManager.getMobile());
                options.put("prefill.method", "wallet");
            } else if (raz_payType.equals("gp")) {
                options.put("prefill.email", sessionManager.getMobile() + "@gmail.com");
                options.put("prefill.contact", sessionManager.getMobile());
                options.put("prefill.method", "upi");
            } else if (raz_payType.equals("pp")) {
                options.put("prefill.email", sessionManager.getMobile() + "@gmail.com");
                options.put("prefill.contact", sessionManager.getMobile());
                options.put("prefill.method", "upi");
            } else if (raz_payType.equals("upi")) {
                options.put("prefill.email", sessionManager.getMobile() + "@gmail.com");
                options.put("prefill.contact", sessionManager.getMobile());
                options.put("prefill.method", "upi");
            }
            //options.put("prefill.method", "upi");
            //checkSelectedPaymentMethod();
            checkout.open(activity, options);
            Log.e("Payment", "fill data " + options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Log.e("Payment", "razorpayPaymentID  " + razorpayPaymentID);
        Log.e("Payment", "orderId  " + orderId);
        try {
            apiManager.verifyPayment(orderId, razorpayPaymentID, "razorpay");
        } catch (Exception e) {
            Log.e("Payment", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Log.e("Payment", "Exception in onPaymentError " + +code + " " + response);
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Payment", "Exception in onPaymentError log" + e);
        }
    }

    private void checkSelectedPaymentMethod() {
        try {
            //String amount = String.valueOf(selectedPlan.getAmount());
            String amount = "1";
            String upiId = "yap107295@equitas";
            String name = new SessionManager(getApplicationContext()).getUserName();
            String note = "Medico !";
            String transactionId = "TID" + System.currentTimeMillis();
            String transactionRefId = "TID" + System.currentTimeMillis();
            payUsingUpi(amount, upiId, name, note, transactionRefId, transactionId);
        } catch (Exception e) {
        }
    }

    void payUsingUpi(String amount, String upiId, String name, String note, String transactionRefId, String transactionId) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("tr", transactionRefId)
                .appendQueryParameter("ti", transactionId)
                .appendQueryParameter("cu", "INR")
                .build();

        Log.e("UPI", "UPI_URI " + uri);
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        Log.e("UPI", "UPI_CHOOSER " + chooser);
        Log.e("UPI", "upiPayIntent " + upiPayIntent);
        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(PaymentActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("UPI", "UPI_RESULT_REQUEST_CODE " + requestCode);
        Log.e("UPI", "UPI_RESULT_RESULT_CODE " + resultCode);
        Log.e("UPI", "UPI_RESULT_DATA " + data);
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.e("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PaymentActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                //apiManager.verifyPayment(approvalRefNo, orderId);
                /*CashFreePaymentRequest cashFreePaymentRequest = new CashFreePaymentRequest(approvalRefNo, String.valueOf(new SessionManager(this).getUserId()));
                apiManager.cashFreePayment(cashFreePaymentRequest);*/
                //new PaymentCompletedDialog(this, approvalRefNo, order_id);

                Toast.makeText(PaymentActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "responseStr: " + approvalRefNo);

            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PaymentActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PaymentActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isError(String errorCode) {
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            @SuppressLint("MissingPermission")
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void setData() {
        PaymentOfferList list1 = new PaymentOfferList(R.drawable.ic_paytm, "Paytm Wallet | Postpaid", "Get 4000 cashback points on minimum transaction of Rs.999. Offer once per User.");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_amazon_pay_round, "Amazon Pay", "Get 4000 cashback points on minimum transaction of Rs.999. Offer once per User.");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_pay, "PhonePe", "Get 4000 cashback points on minimum transaction of Rs.999. Offer once per User.");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_mobikwik, "Mobikwik", "Get 4000 cashback points on minimum transaction of Rs.999. Offer once per User.");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_upi_sign, "UPI", "");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_wallet_pay, "Wallets", "");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_master_card, "Credit/Debit Card", "Get Flat Rs.250 cashback on aminimum transaction of Rs.2000 on ICICI Debit  Credit cards Get flat Rs.150 discount on a minimum transaction of Rs.1500 on Onecard");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_net_banking, "Net Banking", "We support over 100 banks");
        list.add(list1);
        list1 = new PaymentOfferList(R.drawable.ic_delivery, "Pay on Delivery", "Pay via Cash/UPI at the time of delivery");
        list.add(list1);
    }

}