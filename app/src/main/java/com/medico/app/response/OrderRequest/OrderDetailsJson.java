package com.medico.app.response.OrderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderDetailsJson implements Serializable {
    @SerializedName("order_item")
    @Expose
    private List<OrderItem> orderItem = null;
    @SerializedName("shipping_address")
    @Expose
    private ShippingAddress shippingAddress;

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
