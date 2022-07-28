package com.medico.app.response.OrderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItem implements Serializable {
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("manufacture_name")
    @Expose
    private String manufactureName;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_after_price")
    @Expose
    private String discountAfterPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountAfterPrice() {
        return discountAfterPrice;
    }

    public void setDiscountAfterPrice(String discountAfterPrice) {
        this.discountAfterPrice = discountAfterPrice;
    }

}
