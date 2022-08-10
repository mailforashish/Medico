package com.medico.app.response.OrderProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOrderResult {
    @SerializedName("order_details")
    @Expose
    private OrderDetails orderDetails;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

}

