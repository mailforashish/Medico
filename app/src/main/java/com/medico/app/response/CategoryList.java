package com.medico.app.response;

public class CategoryList {
    private int category_image;
    String category;
    String discount;
    String price;
    private boolean isExpandable;

    public CategoryList() {

    }

    public CategoryList(int category_image, String category, String discount, String price) {
        this.category_image = category_image;
        this.category = category;
        this.discount = discount;
        this.price = price;
        isExpandable = false;
    }

    public int getCategory_image() {
        return category_image;
    }

    public void setCategory_image(int category_image) {
        this.category_image = category_image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public boolean isExpandable() {
        return isExpandable;
    }
}