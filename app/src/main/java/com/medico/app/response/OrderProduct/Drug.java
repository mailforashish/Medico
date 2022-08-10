package com.medico.app.response.OrderProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drug {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("drug_id")
    @Expose
    private Integer drugId;
    @SerializedName("drug_name")
    @Expose
    private String drugName;
    @SerializedName("outlet_id")
    @Expose
    private String outletId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("awb_no")
    @Expose
    private Object awbNo;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("coupon_code")
    @Expose
    private Object couponCode;
    @SerializedName("coupon_discount")
    @Expose
    private Object couponDiscount;
    @SerializedName("igst_value")
    @Expose
    private Integer igstValue;
    @SerializedName("total")
    @Expose
    private Object total;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getAwbNo() {
        return awbNo;
    }

    public void setAwbNo(Object awbNo) {
        this.awbNo = awbNo;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Object getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Object couponCode) {
        this.couponCode = couponCode;
    }

    public Object getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(Object couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public Integer getIgstValue() {
        return igstValue;
    }

    public void setIgstValue(Integer igstValue) {
        this.igstValue = igstValue;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
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

}
