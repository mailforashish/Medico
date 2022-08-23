package com.medico.app.response.Cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductId {
    @SerializedName("drug_id")
    @Expose
    private String drugId;
    @SerializedName("drug_name")
    @Expose
    private String drugName;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("drug_type")
    @Expose
    private Object drugType;
    @SerializedName("scientific_name")
    @Expose
    private String scientificName;
    @SerializedName("igst")
    @Expose
    private Integer igst;
    @SerializedName("schedule")
    @Expose
    private Integer schedule;
    @SerializedName("manufactur")
    @Expose
    private String manufactur;
    @SerializedName("unit_price")
    @Expose
    private String unitPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("is_cart")
    @Expose
    private Boolean isCart;
    @SerializedName("images")
    @Expose
    private List<CartImage> images = null;

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Object getDrugType() {
        return drugType;
    }

    public void setDrugType(Object drugType) {
        this.drugType = drugType;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public Integer getIgst() {
        return igst;
    }

    public void setIgst(Integer igst) {
        this.igst = igst;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public String getManufactur() {
        return manufactur;
    }

    public void setManufactur(String manufactur) {
        this.manufactur = manufactur;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Boolean getIsCart() {
        return isCart;
    }

    public void setIsCart(Boolean isCart) {
        this.isCart = isCart;
    }

    public List<CartImage> getImages() {
        return images;
    }

    public void setImages(List<CartImage> images) {
        this.images = images;
    }
}
