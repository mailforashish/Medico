package com.medico.app.response.ProductDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.medico.app.response.ProductList.ProductListResponse;

import java.util.List;

public class ProductResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("drug_id")
    @Expose
    private String drugId;
    @SerializedName("drug_name")
    @Expose
    private String drugName;
    @SerializedName("item_image")
    @Expose
    private Object itemImage;
    @SerializedName("drug_type")
    @Expose
    private String drugType;
    @SerializedName("scientific_name")
    @Expose
    private String scientificName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("hsn")
    @Expose
    private String hsn;
    @SerializedName("igst")
    @Expose
    private Integer igst;
    @SerializedName("is_text_include")
    @Expose
    private Integer isTextInclude;
    @SerializedName("schedule")
    @Expose
    private Integer schedule;
    @SerializedName("category_id")
    @Expose
    private ProductCategoryId categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private Integer subCategoryId;
    @SerializedName("manufactur")
    @Expose
    private String manufactur;
    @SerializedName("packing_type")
    @Expose
    private String packingType;
    @SerializedName("packaging")
    @Expose
    private Integer packaging;
    @SerializedName("uom")
    @Expose
    private String uom;
    @SerializedName("unit_price")
    @Expose
    private Double unitPrice;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("store_temperature")
    @Expose
    private String storeTemperature;
    @SerializedName("dengerous_level")
    @Expose
    private String dengerousLevel;
    @SerializedName("storage_location")
    @Expose
    private String storageLocation;
    @SerializedName("uses")
    @Expose
    private String uses;
    @SerializedName("side_effect")
    @Expose
    private String sideEffect;
    @SerializedName("driving")
    @Expose
    private String driving;
    @SerializedName("kidney")
    @Expose
    private String kidney;
    @SerializedName("liver")
    @Expose
    private String liver;
    @SerializedName("pregnancy")
    @Expose
    private String pregnancy;
    @SerializedName("breast_feeding")
    @Expose
    private String breastFeeding;
    @SerializedName("alcohol")
    @Expose
    private String alcohol;
    @SerializedName("benefits")
    @Expose
    private String benefits;
    @SerializedName("how_to_use")
    @Expose
    private String howToUse;
    @SerializedName("how_works")
    @Expose
    private String howWorks;
    @SerializedName("quick_tips")
    @Expose
    private String quickTips;
    @SerializedName("is_perception")
    @Expose
    private Integer isPerception;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("is_cart")
    @Expose
    private Boolean isCart;
    @SerializedName("images")
    @Expose
    private List<ProductImages> images = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Object getItemImage() {
        return itemImage;
    }

    public void setItemImage(Object itemImage) {
        this.itemImage = itemImage;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public Integer getIgst() {
        return igst;
    }

    public void setIgst(Integer igst) {
        this.igst = igst;
    }

    public Integer getIsTextInclude() {
        return isTextInclude;
    }

    public void setIsTextInclude(Integer isTextInclude) {
        this.isTextInclude = isTextInclude;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public ProductCategoryId getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ProductCategoryId categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getManufactur() {
        return manufactur;
    }

    public void setManufactur(String manufactur) {
        this.manufactur = manufactur;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public Integer getPackaging() {
        return packaging;
    }

    public void setPackaging(Integer packaging) {
        this.packaging = packaging;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getStoreTemperature() {
        return storeTemperature;
    }

    public void setStoreTemperature(String storeTemperature) {
        this.storeTemperature = storeTemperature;
    }

    public String getDengerousLevel() {
        return dengerousLevel;
    }

    public void setDengerousLevel(String dengerousLevel) {
        this.dengerousLevel = dengerousLevel;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getDriving() {
        return driving;
    }

    public void setDriving(String driving) {
        this.driving = driving;
    }

    public String getKidney() {
        return kidney;
    }

    public void setKidney(String kidney) {
        this.kidney = kidney;
    }

    public String getLiver() {
        return liver;
    }

    public void setLiver(String liver) {
        this.liver = liver;
    }

    public String getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(String pregnancy) {
        this.pregnancy = pregnancy;
    }

    public String getBreastFeeding() {
        return breastFeeding;
    }

    public void setBreastFeeding(String breastFeeding) {
        this.breastFeeding = breastFeeding;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public String getHowWorks() {
        return howWorks;
    }

    public void setHowWorks(String howWorks) {
        this.howWorks = howWorks;
    }

    public String getQuickTips() {
        return quickTips;
    }

    public void setQuickTips(String quickTips) {
        this.quickTips = quickTips;
    }

    public Integer getIsPerception() {
        return isPerception;
    }

    public void setIsPerception(Integer isPerception) {
        this.isPerception = isPerception;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsCart() {
        return isCart;
    }

    public void setIsCart(Boolean isCart) {
        this.isCart = isCart;
    }

    public List<ProductImages> getImages() {
        return images;
    }

    public void setImages(List<ProductImages> images) {
        this.images = images;
    }


}
