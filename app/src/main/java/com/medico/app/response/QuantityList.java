package com.medico.app.response;

public class QuantityList {
    private String qty;
    private String check;


    public QuantityList(String qty) {
        this.qty = qty;
        this.check = check;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
