package com.medico.app.response.OrderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderProductRequests {
    @SerializedName("drugs")
    @Expose
    private List<Drug> drugs = null;
    @SerializedName("is_checkout")
    @Expose
    private Integer isCheckout;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("perception")
    @Expose
    private String perception;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public Integer getIsCheckout() {
        return isCheckout;
    }

    public void setIsCheckout(Integer isCheckout) {
        this.isCheckout = isCheckout;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getPerception() {
        return perception;
    }

    public void setPerception(String perception) {
        this.perception = perception;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

}