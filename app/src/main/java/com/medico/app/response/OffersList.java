package com.medico.app.response;

public class OffersList {
    private  String offers;
    private String offers_code;

    public OffersList(String offers, String offers_code) {
        this.offers = offers;
        this.offers_code = offers_code;
    }

    public String getOffers() {
        return offers;
    }

    public String getOffers_code() {
        return offers_code;
    }
}
