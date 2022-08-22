package com.medico.app.response.Cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private CartResult data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CartResult getData() {
        return data;
    }

    public void setData(CartResult data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
