package com.medico.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.medico.app.R;
import com.medico.app.adapter.CartAdapter;
import com.medico.app.adapter.MainAddressAdapter;
import com.medico.app.databinding.ActivityCartBinding;
import com.medico.app.dialog.AddressDialog;
import com.medico.app.interfaceClass.AddressAction;
import com.medico.app.interfaceClass.CartItemCount;
import com.medico.app.response.Addcart.RemoveCartResponse;
import com.medico.app.response.Address.AddressResponse;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.response.Cartlist.CartResponse;
import com.medico.app.response.Cartlist.CartResult;
import com.medico.app.retrofit.ApiManager;
import com.medico.app.retrofit.ApiResponseInterface;
import com.medico.app.utils.Constant;
import com.medico.app.utils.HideStatus;
import com.medico.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity implements ApiResponseInterface, CartItemCount, AddressAction {
    ActivityCartBinding binding;
    private CartAdapter cartAdapter;
    private MainAddressAdapter addressAdapter;
    private List<CartResult> cartList;
    private List<AddressResult> addressLists = new ArrayList<>();
    private SessionManager sessionManager;
    private LinearLayoutManager linearLayoutManager;
    private TextView tv_amount_input;
    private int SelectedPosition;
    ApiManager apiManager;
    HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        hideStatus = new HideStatus(getWindow(), true);
        binding.setClickListener(new EventHandler(this));
        tv_amount_input = findViewById(R.id.tv_amount_input);

        sessionManager = new SessionManager(this);
        apiManager = new ApiManager(this, this);
        apiManager.getAddressList();
        apiManager.getCartList();
        if (cartList != null) {
            Float sum = 0.f;
            for (int i = 0; i < cartList.size(); i++) {
                float actualPrice = Float.parseFloat(cartList.get(i).getProductId().getUnitPrice());
                float totalDiscount = (actualPrice * Float.parseFloat(cartList.get(i).getProductId().getDiscount())) / 100;
                float priceAfterDiscount = actualPrice - totalDiscount;
                sum = (sum + (priceAfterDiscount * cartList.get(i).getQuantity()));
            }

            tv_amount_input.setText(String.valueOf(String.format("%.2f", sum)));
            Log.e("Total Amount : INR", " finalamountactivity " + sum);
            binding.btnPay.setText("Proceed to Pay ₹ " + String.valueOf(String.format("%.2f", sum)));
        }

        linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rvCartItem.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        binding.rvAddress.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void getCartItem(boolean add, String action, String count, String product_id, String quantity) {
        if (add) {
            if (action.equals("remove")) {
                apiManager.deleteCart(product_id);
            } else if (action.equals("UpdateQuantity")) {
                apiManager.changeQuantity(product_id, quantity);
            }
        }
    }

    @Override
    public void isError(String errorCode) {

    }

    @Override
    public void isSuccess(Object response, int ServiceCode) {
        if (ServiceCode == Constant.ADDRESS_LIST) {
            AddressResponse rsp = (AddressResponse) response;
            try {
                if (rsp != null) {
                    addressLists = rsp.getData();
                    addressAdapter = new MainAddressAdapter(CartActivity.this, addressLists, "cart", SelectedPosition);
                    binding.rvAddress.setAdapter(addressAdapter);
                    addressAdapter.notifyDataSetChanged();
                    sessionManager.saveAddressInLocal("address", addressLists);
                }
            } catch (Exception e) {
            }
        }
        if (ServiceCode == Constant.CART_LIST) {
            CartResponse rsp = (CartResponse) response;
            if (rsp != null) {
                cartList = rsp.getData();
                cartAdapter = new CartAdapter(CartActivity.this, this, cartList, tv_amount_input, binding.btnPay);
                binding.rvCartItem.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                sessionManager.saveListInLocal("cart", cartList);
            }

        }
        if (ServiceCode == Constant.CHANGE_QUANTITY) {
            Object rsp = (Object) response;
        }
        if (ServiceCode == Constant.REMOVE_CART) {
            RemoveCartResponse rsp = (RemoveCartResponse) response;
        }

    }

    @Override
    public void addressChanges(String action, AddressResult addressResult, int pos) {
        if (action.equals("add")) {
            addressLists.add(addressResult);
        }
        addressAdapter.notifyDataSetChanged();
    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPage() {
            onBackPressed();
        }

        public void addAddress() {
            new AddressDialog(mContext, "add", CartActivity.this);
        }

        public void buttonPay() {
            if (cartList.size() >= 1 && addressLists.size() >= 1) {
                startActivity(new Intent(CartActivity.this, PaymentActivity.class)
                        .putExtra("pay_amount", binding.tvAmountInput.getText().toString()));
                cartAdapter.notifyDataSetChanged();
                finish();
            } else {
                Toast.makeText(CartActivity.this, "Please Add Item&Address", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CartActivity.this, MainActivity.class));
        finish();

        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}