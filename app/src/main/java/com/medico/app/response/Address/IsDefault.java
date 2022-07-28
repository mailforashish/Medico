package com.medico.app.response.Address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsDefault {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("value")
    @Expose
    private Boolean value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}
