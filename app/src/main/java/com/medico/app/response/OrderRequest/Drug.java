package com.medico.app.response.OrderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Drug implements Serializable {
    @SerializedName("drug_id")
    @Expose
    private String drugId;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}

