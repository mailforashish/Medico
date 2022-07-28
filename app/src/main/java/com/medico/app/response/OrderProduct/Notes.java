package com.medico.app.response.OrderProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notes {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("merchant_order_id")
    @Expose
    private String merchant_order_id;

    public String getMerchant_order_id() {
        return merchant_order_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
