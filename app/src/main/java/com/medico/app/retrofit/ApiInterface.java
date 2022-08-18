package com.medico.app.retrofit;

import com.google.gson.JsonObject;
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
import com.medico.app.response.PinCode.PinCodeResponse;
import com.medico.app.response.ProductList.ProductListResponse;
import com.medico.app.response.LoginResponse;
import com.medico.app.response.stateList.StateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("mobile-login-otp")
    Call<LoginResponse> loginUserMobile(@Field("username") String username);

    @FormUrlEncoded
    @POST("mobile-login")
    Call<OTPResponse> loginUserMobileOtp(@Field("username") String username, @Field("password") String password);

    @GET("states")
    Call<StateResponse> getStateData(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("banner-list")
    Call<BannerResponse> getBannerData(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("products")
    Call<ProductListResponse> getProductData(@Header("Authorization") String token, @Header("Accept") String accept,
                                             @Query("page") String p, @Query("per_page") String lim);

    @FormUrlEncoded
    @POST("add-cart")
    Call<AddCartResponse> addCartData(@Header("Authorization") String token, @Header("Accept") String accept,
                                      @Field("product_id") String product_id, @Field("quantity") String quantity);

    @FormUrlEncoded
    @POST("remove-cart")
    Call<RemoveCartResponse> deleteCartData(@Header("Authorization") String token, @Header("Accept") String accept, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("change-quantity")
    Call<Object> changeCartQuantity(@Header("Authorization") String token, @Header("Accept") String accept,
                                    @Field("product_id") String product_id, @Field("quantity") String quantity);

    @GET("carts")
    Call<CartResponse> getCartData(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("address-list")
    Call<AddressResponse> getAddressData(@Header("Authorization") String token, @Header("Accept") String accept);

    @FormUrlEncoded
    @POST("add-edit-address")
    Call<AddEditAddressResponse> addAddressData(@Header("Authorization") String token, @Header("Accept") String accept,
                                                @Field("name") String name, @Field("mobile") String mobile, @Field("address1") String address1, @Field("address2") String address2,
                                                @Field("state") String state, @Field("district") String district, @Field("pincode") String pincode,
                                                @Field("landmark") String landmark, @Field("type") String type);

    @FormUrlEncoded
    @POST("add-edit-address")
    Call<AddEditAddressResponse> editAddressData(@Header("Authorization") String token, @Header("Accept") String accept, @Field("address_id") String address_id, @Field("name") String name,
                                                 @Field("mobile") String mobile, @Field("address1") String address1, @Field("address2") String address2,
                                                 @Field("state") String state, @Field("district") String district, @Field("pincode") String pincode,
                                                 @Field("landmark") String landmark, @Field("type") String type);

    @FormUrlEncoded
    @POST("delete-address")
    Call<Object> deleteAddressData(@Header("Authorization") String token, @Header("Accept") String accept, @Field("address_id") String address_id);

    @GET("products")
    Call<ProductListResponse> getSearchProduct(@Query("q") String p, @Query("per_page") String lim);

    @POST("place-order")
    Call<CreateOrderResponse> OrderProduct(@Header("Authorization") String token, @Header("Accept") String accept,
                                           @Body OrderProductRequests order_detail);

    @POST("place-order")
    Call<CreateOrderResponseUpi> OrderProductUpi(@Header("Authorization") String token, @Header("Accept") String accept,
                                                 @Body OrderProductRequests order_detail);


    @FormUrlEncoded
    @POST("validate-Order")
    Call<ReportResponse> verifyPayment(@Header("Authorization") String token, @Header("Accept") String accept, @Field("order_id") String order_id,
                                       @Field("payment_id") String payment_id, @Field("payment_mode") String payment_mode);

    @GET("orders")
    Call<OrderListResponse> getOrderData(@Header("Authorization") String token, @Header("Accept") String accept);

    @GET("{pincode}")
    Call<PinCodeResponse> getPinCode(@Path("pincode") String pin);

   /* @POST("cash-free-payment")
    Call<Object> cashFreePayment(
            @Header("Authorization") String token,
            @Header("Accept") String accept,
            @Body CashFreePaymentRequest cashFreePaymentRequest);*/

}