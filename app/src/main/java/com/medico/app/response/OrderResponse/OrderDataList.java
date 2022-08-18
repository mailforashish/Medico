package com.medico.app.response.OrderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderDataList implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("perception_url")
    @Expose
    private String perceptionUrl;
    @SerializedName("coupon_discount")
    @Expose
    private Integer couponDiscount;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("igst_value")
    @Expose
    private Integer igstValue;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("wallet")
    @Expose
    private Double wallet;
    @SerializedName("cut_amount")
    @Expose
    private Double cutAmount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("drugs")
    @Expose
    private List<DrugData> drugs = null;
    @SerializedName("address")
    @Expose
    private List<AddressList> address = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerceptionUrl() {
        return perceptionUrl;
    }

    public void setPerceptionUrl(String perceptionUrl) {
        this.perceptionUrl = perceptionUrl;
    }

    public Integer getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(Integer couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getIgstValue() {
        return igstValue;
    }

    public void setIgstValue(Integer igstValue) {
        this.igstValue = igstValue;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public Double getCutAmount() {
        return cutAmount;
    }

    public void setCutAmount(Double cutAmount) {
        this.cutAmount = cutAmount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<DrugData> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugData> drugs) {
        this.drugs = drugs;
    }

    public List<AddressList> getAddress() {
        return address;
    }

    public void setAddress(List<AddressList> address) {
        this.address = address;
    }


}
