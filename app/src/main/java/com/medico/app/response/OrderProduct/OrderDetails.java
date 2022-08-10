package com.medico.app.response.OrderProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetails {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("cart_id")
    @Expose
    private Integer cartId;
    @SerializedName("user_id")
    @Expose
    private UserId userId;
    @SerializedName("is_fullfill_one_store")
    @Expose
    private Integer isFullfillOneStore;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("perception_by")
    @Expose
    private Object perceptionBy;
    @SerializedName("is_perception_required")
    @Expose
    private Integer isPerceptionRequired;
    @SerializedName("perception_url")
    @Expose
    private Object perceptionUrl;
    @SerializedName("coupon_code")
    @Expose
    private Object couponCode;
    @SerializedName("coupon_discount")
    @Expose
    private Integer couponDiscount;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("igst_percentage")
    @Expose
    private Object igstPercentage;
    @SerializedName("igst_value")
    @Expose
    private Integer igstValue;
    @SerializedName("delivery_charges")
    @Expose
    private Object deliveryCharges;
    @SerializedName("portal_charges")
    @Expose
    private Object portalCharges;
    @SerializedName("other_charges")
    @Expose
    private Object otherCharges;
    @SerializedName("cut_amount")
    @Expose
    private Integer cutAmount;
    @SerializedName("wallet")
    @Expose
    private Object wallet;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("drugs")
    @Expose
    private List<Drug> drugs = null;
    @SerializedName("address")
    @Expose
    private List<AddressOrder> address = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public Integer getIsFullfillOneStore() {
        return isFullfillOneStore;
    }

    public void setIsFullfillOneStore(Integer isFullfillOneStore) {
        this.isFullfillOneStore = isFullfillOneStore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getPerceptionBy() {
        return perceptionBy;
    }

    public void setPerceptionBy(Object perceptionBy) {
        this.perceptionBy = perceptionBy;
    }

    public Integer getIsPerceptionRequired() {
        return isPerceptionRequired;
    }

    public void setIsPerceptionRequired(Integer isPerceptionRequired) {
        this.isPerceptionRequired = isPerceptionRequired;
    }

    public Object getPerceptionUrl() {
        return perceptionUrl;
    }

    public void setPerceptionUrl(Object perceptionUrl) {
        this.perceptionUrl = perceptionUrl;
    }

    public Object getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Object couponCode) {
        this.couponCode = couponCode;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Object getIgstPercentage() {
        return igstPercentage;
    }

    public void setIgstPercentage(Object igstPercentage) {
        this.igstPercentage = igstPercentage;
    }

    public Integer getIgstValue() {
        return igstValue;
    }

    public void setIgstValue(Integer igstValue) {
        this.igstValue = igstValue;
    }

    public Object getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Object deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Object getPortalCharges() {
        return portalCharges;
    }

    public void setPortalCharges(Object portalCharges) {
        this.portalCharges = portalCharges;
    }

    public Object getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Object otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Integer getCutAmount() {
        return cutAmount;
    }

    public void setCutAmount(Integer cutAmount) {
        this.cutAmount = cutAmount;
    }

    public Object getWallet() {
        return wallet;
    }

    public void setWallet(Object wallet) {
        this.wallet = wallet;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public List<AddressOrder> getAddress() {
        return address;
    }

    public void setAddress(List<AddressOrder> address) {
        this.address = address;
    }

}
