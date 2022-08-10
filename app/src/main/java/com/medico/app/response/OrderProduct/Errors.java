package com.medico.app.response.OrderProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {
    @SerializedName("23")
    @Expose
    private String _23;

    public String get23() {
        return _23;
    }

    public void set23(String _23) {
        this._23 = _23;
    }
}
