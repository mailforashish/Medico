package com.medico.app.response.OrderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderListResponse implements Serializable {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private OrderListResult result = null;
    @SerializedName("error")
    @Expose
    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public OrderListResult getResult() {
        return result;
    }

    public void setResult(OrderListResult result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    //@SerializedName("success")
    //    @Expose
    //    private Boolean success;
    //    @SerializedName("data")
    //    @Expose
    //    private OrderListResult data;
    //    @SerializedName("message")
    //    @Expose
    //    private String message;
    //
    //    public Boolean getSuccess() {
    //        return success;
    //    }
    //
    //    public void setSuccess(Boolean success) {
    //        this.success = success;
    //    }
    //
    //    public OrderListResult getData() {
    //        return data;
    //    }
    //
    //    public void setData(OrderListResult data) {
    //        this.data = data;
    //    }
    //
    //    public String getMessage() {
    //        return message;
    //    }
    //
    //    public void setMessage(String message) {
    //        this.message = message;
    //    }
}

