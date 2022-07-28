package com.medico.app.response.OrderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.medico.app.response.OrderRequest.OrderDetailsJson;

import java.io.Serializable;

public class OrderListResult implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("payment_type")
    @Expose
    private Integer paymentType;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("shipping_charge")
    @Expose
    private Integer shippingCharge;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("order_details_json")
    @Expose
    private OrderDetailsJson orderDetailsJson;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order_status")
    @Expose
    private Integer orderStatus;
    @SerializedName("transaction_post_detail")
    @Expose
    private String transactionPostDetail;
    @SerializedName("transaction_response_detail")
    @Expose
    private String transactionResponseDetail;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("delivered_date")
    @Expose
    private String deliveredDate;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Object getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(Integer shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public OrderDetailsJson  getOrderDetailsJson() {
        return orderDetailsJson;
    }

    public void setOrderDetailsJson(OrderDetailsJson  orderDetailsJson) {
        this.orderDetailsJson = orderDetailsJson;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getTransactionPostDetail() {
        return transactionPostDetail;
    }

    public void setTransactionPostDetail(String transactionPostDetail) {
        this.transactionPostDetail = transactionPostDetail;
    }

    public Object getTransactionResponseDetail() {
        return transactionResponseDetail;
    }

    public void setTransactionResponseDetail(String transactionResponseDetail) {
        this.transactionResponseDetail = transactionResponseDetail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
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
