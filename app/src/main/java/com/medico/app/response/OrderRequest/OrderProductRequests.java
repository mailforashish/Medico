package com.medico.app.response.OrderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProductRequests {
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("order_details_json")
    @Expose
    private OrderDetailsJson orderDetailsJson;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public OrderDetailsJson getOrderDetailsJson() {
        return orderDetailsJson;
    }

    public void setOrderDetailsJson(OrderDetailsJson orderDetailsJson) {
        this.orderDetailsJson = orderDetailsJson;
    }

}

