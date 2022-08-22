package com.medico.app.response.Cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResult {

    @SerializedName("cart")
    @Expose
    private List<CartList> cart = null;
    @SerializedName("save-later")
    @Expose
    private List<SaveLaterList> saveLater = null;

    public List<CartList> getCart() {
        return cart;
    }

    public void setCart(List<CartList> cart) {
        this.cart = cart;
    }

    public List<SaveLaterList> getSaveLater() {
        return saveLater;
    }

    public void setSaveLater(List<SaveLaterList> saveLater) {
        this.saveLater = saveLater;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("product_id")
    @Expose
    private ProductId productId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}

