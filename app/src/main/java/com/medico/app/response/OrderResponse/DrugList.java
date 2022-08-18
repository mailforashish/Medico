package com.medico.app.response.OrderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugList implements Serializable {
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
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("discount")
    @Expose
    private String discount;
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

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
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

}

