package com.medico.app.retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import com.medico.app.dialog.MyProgressDialog;
import com.medico.app.dialog.NoInternetDialog;
import com.medico.app.response.AddEdit.AddEditAddressResponse;
import com.medico.app.response.OrderProduct.CreateOrderResponse;
import com.medico.app.response.OrderProduct.CreateOrderResponseUpi;
import com.medico.app.response.OrderRequest.OrderProductRequests;
import com.medico.app.response.OrderResponse.OrderListResponse;
import com.medico.app.response.Payment.ReportResponse;
import com.medico.app.response.Addcart.AddCartResponse;
import com.medico.app.response.Addcart.RemoveCartResponse;
import com.medico.app.response.Address.AddressResponse;
import com.medico.app.response.Banner.BannerResponse;
import com.medico.app.response.Cartlist.CartResponse;
import com.medico.app.response.OTp.OTPResponse;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.response.LoginResponse;
import com.medico.app.response.stateList.StateResponse;
import com.medico.app.utils.Constant;
import com.medico.app.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {
    private Context mContext;
    private MyProgressDialog dialog;
    private ApiResponseInterface mApiResponseInterface;
    private ApiInterface apiService;
    String authToken;

    public ApiManager(Context context, ApiResponseInterface apiResponseInterface) {
        this.mContext = context;
        this.mApiResponseInterface = apiResponseInterface;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        dialog = new MyProgressDialog(mContext);
        authToken = Constant.BEARER + new SessionManager(context).getUserToken();

    }

    public ApiManager(Context context) {
        this.mContext = context;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        dialog = new MyProgressDialog(mContext);
        authToken = Constant.BEARER + new SessionManager(context).getUserToken();
    }

    public void login(String username) {
        Call<LoginResponse> call = apiService.loginUserMobile(username);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("loginRequest", call.request().toString());
                Log.e("loginResponce", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSuccess()) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.LOGIN);
                    } else {
                        mApiResponseInterface.isError(response.body().getMessage());
                    }
                } else if (response.code() == 401) {
                    //Log.e("errorResponce", response.body().getError());
                    Toast.makeText(mContext, "Invalid User", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mContext, response.body().getError(), Toast.LENGTH_SHORT).show();
                }
                mApiResponseInterface.isError(response.raw().message());
                //closeDialog();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //closeDialog();
                Log.e("loginResponceError", t.getMessage());
            }
        });
    }


    public void loginOTP(String username, String password) {
        //showDialog();
        Call<OTPResponse> call = apiService.loginUserMobileOtp(username, password);
        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                Log.e("loginOtpResponse", call.request().toString());
                Log.e("loginOtp", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSuccess()) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.OTP_LOGIN);
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        mApiResponseInterface.isError(response.body().getMessage());
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 422) {
                    //Log.e("errorResponce", response.body().getMessage());
                    Toast.makeText(mContext, "Invalid Otp", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mContext, response.body().getError(), Toast.LENGTH_SHORT).show();

                }
                mApiResponseInterface.isError(response.raw().message());
                //closeDialog();
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                //closeDialog();
                Log.e("loginResponceError", t.getMessage());
            }
        });
    }


    public void getStateList() {
        Call<StateResponse> call = apiService.getStateData(authToken, "application/json");
        Log.e("authToken", authToken);
        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                Log.e("stateList", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.STATE_LIST);
                    }
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                Log.e("stateListErr", t.getMessage());
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBannerList() {
        Call<BannerResponse> call = apiService.getBannerData(authToken, "application/json");
        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                Log.e("BannerList", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.BANNER_LIST);
                    }
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Log.e("BannerListErr", t.getMessage());
                Toast.makeText(mContext, "Banner Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getProductList(String pageNumber) {
        Call<ProductListResponse> call = apiService.getProductData(authToken, "application/json", pageNumber, "16");
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                Log.e("ProductList", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResult().getData() != null) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.PRODUCT_LIST);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Log.e("ProductListErr", t.getMessage());
                Toast.makeText(mContext, "Product Error", Toast.LENGTH_LONG).show();
                new NoInternetDialog(mContext);
            }
        });
    }

    public void getProductListNextPage(String pageNumber) {
        Call<ProductListResponse> call = apiService.getProductData(authToken, "application/json", pageNumber, "16");
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                Log.e("ProductListNext", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResult().getData() != null) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.PRODUCT_LIST_NEXT_PAGE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                Log.e("ProductListNextErr", t.getMessage());
                Toast.makeText(mContext, "ProductListNext Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addCart(String product_id, String quantity) {
        Call<AddCartResponse> call = apiService.addCartData(authToken, "application/json", product_id, quantity);
        call.enqueue(new Callback<AddCartResponse>() {
            @Override
            public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                //Log.e("AddCartResponse", call.request().toString());
                Log.e("CartFunction", "Add Cart Response " + new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSuccess()) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.ADD_CART);
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        mApiResponseInterface.isError(response.body().getMessage());
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartResponse> call, Throwable t) {
                //closeDialog();
                Log.e("CartFunction", "Add Cart Error " + t.getMessage());
            }
        });
    }


    public void deleteCart(String product_id) {
        Call<RemoveCartResponse> call = apiService.deleteCartData(authToken, "application/json", product_id);
        call.enqueue(new Callback<RemoveCartResponse>() {
            @Override
            public void onResponse(Call<RemoveCartResponse> call, Response<RemoveCartResponse> response) {
                Log.e("CartFunction", "Cart Item Remove Response " + new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), Constant.REMOVE_CART);
                    Toast.makeText(mContext, "Remove Item ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveCartResponse> call, Throwable t) {
                Log.e("CartFunction", "Cart Item Remove Error" + t.getMessage());
                Toast.makeText(mContext, "Item D Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void changeQuantity(String product_id, String quantity) {
        Call<Object> call = apiService.changeCartQuantity(authToken, "application/json", product_id, quantity);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("CartFunction", "Cart Item Update Response " + new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseInterface.isSuccess(response.body(), Constant.CHANGE_QUANTITY);
                    Toast.makeText(mContext, "UpDate Qty ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("CartFunction", "Cart Uty Error " + t.getMessage());
                Toast.makeText(mContext, "Qty  Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCartList() {
        Call<CartResponse> call = apiService.getCartData(authToken, "application/json");
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                Log.e("CartFunction", "Cart Item List Response " + new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.CART_LIST);
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.e("CartFunction", "Cart List Error " + t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAddressList() {
        Call<AddressResponse> call = apiService.getAddressData(authToken, "application/json");
        Log.e("authToken", authToken);
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                Log.e("addressList", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.ADDRESS_LIST);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Log.e("addressListErr", t.getMessage());
                Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addAddress(String name, String mobile, String address1, String address2,
                           String state, String district, String pincode, String landmark, String type) {
        Call<AddEditAddressResponse> call = apiService.addAddressData(authToken, "application/json", name, mobile, address1, address2, state, district, pincode, landmark, type);
        call.enqueue(new Callback<AddEditAddressResponse>() {
            @Override
            public void onResponse(Call<AddEditAddressResponse> call, Response<AddEditAddressResponse> response) {
                Log.e("addAddress", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        mApiResponseInterface.isSuccess(response.body(), Constant.ADD_ADDRESS);
                    } else {
                        mApiResponseInterface.isError(response.body().getMessage());
                    }
                } else if (response.code() == 422) {
                    Toast.makeText(mContext, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddEditAddressResponse> call, Throwable t) {
                Toast.makeText(mContext, "Not Added", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void editAddress(String address_id, String name, String mobile, String address1, String address2,
                            String state, String district, String pincode, String landmark, String type) {
        showDialog();
        Call<AddEditAddressResponse> call = apiService.editAddressData(authToken, "application/json", address_id, name, mobile, address1, address2, state, district, pincode, landmark, type);
        call.enqueue(new Callback<AddEditAddressResponse>() {
            @Override
            public void onResponse(Call<AddEditAddressResponse> call, Response<AddEditAddressResponse> response) {
                Log.e("EditAddress", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        mApiResponseInterface.isSuccess(response.body(), Constant.EDIT_ADDRESS);

                    } else {
                        mApiResponseInterface.isError(response.body().getMessage());
                    }
                }
                closeDialog();
            }

            @Override
            public void onFailure(Call<AddEditAddressResponse> call, Throwable t) {
                Toast.makeText(mContext, "Not Edited", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteAddress(String address_id) {

        Call<Object> call = apiService.deleteAddressData(authToken, "application/json", address_id);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("DeleteAddress", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    // if (response.body().getSuccess()) {
                    mApiResponseInterface.isSuccess(response.body(), Constant.DELETE_ADDRESS);
                    Toast.makeText(mContext, "Deleted ", Toast.LENGTH_LONG).show();
                    // } else {
                    // mApiResponseInterface.isError(response.body().getMessage());
                    // }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("DeleteAddress", "" + t.getMessage());
                Toast.makeText(mContext, "Deleted Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void searchProduct(String keyword, String pageNumber) {
     /* Log.e("keyword", keyword);
        Log.e("pageNumber", pageNumber);
        Log.e("authToken", authToken);*/
        Call<ProductListResponse> call = apiService.getSearchProduct(keyword, pageNumber);
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                //   if (response.isSuccessful() && response.body() != null) {
                Log.e("filterTest", new Gson().toJson(response.body()));
                mApiResponseInterface.isSuccess(response.body(), Constant.SEARCH_PRODUCT);
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                //   Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createOrder(OrderProductRequests orderProductRequests) {
        Call<CreateOrderResponse> call = apiService.OrderProduct(authToken, "application/json", orderProductRequests);
        Log.e("orderLog", "request = " + call.request().toString());
        call.enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                //Log.e("createOrderDetail", new Gson().toJson(response.body().getResult()));
                Log.e("orderLog", "responce = " + new Gson().toJson(response.body()));
                if (response.body().getSuccess()) {
                    mApiResponseInterface.isSuccess(response.body(), Constant.CREATE_ORDER);
                } else {
                    Toast.makeText(mContext, new Gson().toJson(response.body().getError()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                Log.e("orderLog", "apierror = " + t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createOrderUpi(OrderProductRequests orderProductRequests) {
        Call<CreateOrderResponseUpi> call = apiService.OrderProductUpi(authToken, "application/json", orderProductRequests);
        Log.e("orderUpiLog", "request = " + call.request().toString());
        call.enqueue(new Callback<CreateOrderResponseUpi>() {
            @Override
            public void onResponse(Call<CreateOrderResponseUpi> call, Response<CreateOrderResponseUpi> response) {
                Log.e("orderUpiLog", "responce = " + new Gson().toJson(response.body()));
                if (response.body().getSuccess()) {
                    mApiResponseInterface.isSuccess(response.body(), Constant.CREATE_ORDER_UPI);
                } else {
                    Toast.makeText(mContext, new Gson().toJson(response.body().getError()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponseUpi> call, Throwable t) {
                Log.e("orderUpiLog", "apierror = " + t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verifyPayment(String transaction_id, String orderId) {
        showDialog();
        Call<ReportResponse> call = apiService.verifyPayment(authToken, "application/json", transaction_id, orderId);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                Log.e("verifyPayment", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSuccess()) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.VERIFY_PAYMENT);
                    } else {
                        mApiResponseInterface.isError(response.body().getError());
                    }
                }
                closeDialog();
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                closeDialog();
                Log.e("errorPayment", " ", t);
                //Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getOrderList() {
        Call<OrderListResponse> call = apiService.getOrderData(authToken, "application/json");
        call.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                Log.e("OrderList", "Order List Response " + new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResult() != null) {
                        mApiResponseInterface.isSuccess(response.body(), Constant.ORDER_LIST);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                Log.e("OrderList", "OrderList List Error " + t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

   /* public void cashFreePayment(CashFreePaymentRequest cashFreePaymentRequest) {
        Call<Object> call = apiService.cashFreePayment(authToken, "application/json", cashFreePaymentRequest);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("cashFreePaymentResponce", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("cashFreePaymentError", t.getMessage());
            }
        });
    }*/


    public void showDialog() {
        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
