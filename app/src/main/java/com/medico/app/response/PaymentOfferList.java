package com.medico.app.response;

public class PaymentOfferList {
    private Integer image_payment_icon;
    private String payment_modeTitle;
    private String payment_modeDescription;

    public PaymentOfferList(Integer image_payment_icon, String payment_modeTitle, String payment_modeDescription) {
        this.image_payment_icon = image_payment_icon;
        this.payment_modeTitle = payment_modeTitle;
        this.payment_modeDescription = payment_modeDescription;
    }

    public Integer getImage_payment_icon() {
        return image_payment_icon;
    }

    public void setImage_payment_icon(Integer image_payment_icon) {
        this.image_payment_icon = image_payment_icon;
    }

    public String getPayment_modeTitle() {
        return payment_modeTitle;
    }

    public void setPayment_modeTitle(String payment_modeTitle) {
        this.payment_modeTitle = payment_modeTitle;
    }

    public String getPayment_modeDescription() {
        return payment_modeDescription;
    }

    public void setPayment_modeDescription(String payment_modeDescription) {
        this.payment_modeDescription = payment_modeDescription;
    }
}
