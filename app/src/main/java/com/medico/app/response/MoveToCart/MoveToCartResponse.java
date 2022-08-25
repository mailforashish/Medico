package com.medico.app.response.MoveToCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoveToCartResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private MoveToCartResult data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public MoveToCartResult getData() {
        return data;
    }

    public void setData(MoveToCartResult data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class MoveToCartResult {
    }

}

