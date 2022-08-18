package com.medico.app.response.OrderResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DrugData implements Serializable {
    @SerializedName("awb")
    @Expose
    private Object awb;
    @SerializedName("drug")
    @Expose
    private List<DrugList> drug = null;

    public Object getAwb() {
        return awb;
    }

    public void setAwb(Object awb) {
        this.awb = awb;
    }

    public List<DrugList> getDrug() {
        return drug;
    }

    public void setDrug(List<DrugList> drug) {
        this.drug = drug;
    }
}
