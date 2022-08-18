package com.medico.app.response.ProductList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Result data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return data;
    }

    public void setResult(Result data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Result {
        @SerializedName("current_page")
        @Expose
        private Integer currentPage;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;
        @SerializedName("first_page_url")
        @Expose
        private String firstPageUrl;
        @SerializedName("from")
        @Expose
        private Integer from;
        @SerializedName("next_page_url")
        @Expose
        private String nextPageUrl;
        @SerializedName("path")
        @Expose
        private String path;
        @SerializedName("per_page")
        @Expose
        private Integer perPage;
        @SerializedName("prev_page_url")
        @Expose
        private String prevPageUrl;
        @SerializedName("to")
        @Expose
        private Integer to;

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public String getFirstPageUrl() {
            return firstPageUrl;
        }

        public void setFirstPageUrl(String firstPageUrl) {
            this.firstPageUrl = firstPageUrl;
        }

        public Integer getFrom() {
            return from;
        }

        public void setFrom(Integer from) {
            this.from = from;
        }

        public String getNextPageUrl() {
            return nextPageUrl;
        }

        public void setNextPageUrl(String nextPageUrl) {
            this.nextPageUrl = nextPageUrl;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getPerPage() {
            return perPage;
        }

        public void setPerPage(Integer perPage) {
            this.perPage = perPage;
        }

        public String getPrevPageUrl() {
            return prevPageUrl;
        }

        public void setPrevPageUrl(String prevPageUrl) {
            this.prevPageUrl = prevPageUrl;
        }

        public Integer getTo() {
            return to;
        }

        public void setTo(Integer to) {
            this.to = to;
        }

    }

    public static class Data {
        @SerializedName("drug_id")
        @Expose
        private String drugId;
        @SerializedName("drug_name")
        @Expose
        private String drugName;
        @SerializedName("drug_type")
        @Expose
        private String drugType;
        @SerializedName("item_image")
        @Expose
        private String itemImage;
        @SerializedName("scientific_name")
        @Expose
        private String scientificName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("schedule")
        @Expose
        private Integer schedule;

        @SerializedName("quantity")
        @Expose
        public Integer quantity;

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
        private Object uom;
        @SerializedName("unit_price")
        @Expose
        private String unitPrice;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("store_temperature")
        @Expose
        private String storeTemperature;
        @SerializedName("dengerous_level")
        @Expose
        private String dengerousLevel;
        @SerializedName("storage_location")
        @Expose
        private String storageLocation;
        @SerializedName("igst")
        @Expose
        private Integer igst;
        @SerializedName("is_cart")
        @Expose
        private Boolean isCart;
        @SerializedName("category_id")
        @Expose
        private CategoryId categoryId;
        @SerializedName("images")
        @Expose
        private List<Image> images = null;


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

        public String getDrugType() {
            return drugType;
        }

        public void setDrugType(String drugType) {
            this.drugType = drugType;
        }

        public String getItemImage() {
            return itemImage;
        }

        public void setItemImage(String itemImage) {
            this.itemImage = itemImage;
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

        public Object getUom() {
            return uom;
        }

        public void setUom(Object uom) {
            this.uom = uom;
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

        public Object getStoreTemperature() {
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

        public Integer getIgst() {
            return igst;
        }

        public void setIgst(Integer igst) {
            this.igst = igst;
        }

        public Boolean getIsCart() {
            return isCart;
        }

        public void setIsCart(Boolean isCart) {
            this.isCart = isCart;
        }
        public CategoryId getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(CategoryId categoryId) {
            this.categoryId = categoryId;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

    }

    public class Image {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("drug_id")
        @Expose
        private String drugId;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
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

        public String getDrugId() {
            return drugId;
        }

        public void setDrugId(String drugId) {
            this.drugId = drugId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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

    public class CategoryId {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("child")
        @Expose
        private List<Object> child = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Object> getChild() {
            return child;
        }

        public void setChild(List<Object> child) {
            this.child = child;
        }

    }


}
