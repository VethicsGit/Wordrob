
package com.pro.wardrobe.ApiResponse.ProductDetailResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("delivery_instruction")
    @Expose
    private String delivery_instruction;
    @SerializedName("special_instruction")
    @Expose
    private String special_instruction;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("is_fav")
    @Expose
    private String isFav;
    @SerializedName("product_size_details")
    @Expose
    private List<ProductSizeDetail> productSizeDetails = null;
    @SerializedName("product_color_details")
    @Expose
    private List<ProductColorDetail> productColorDetails = null;
    @SerializedName("rating_count")
    @Expose
    private String ratingCount;
    @SerializedName("rating_avg")
    @Expose
    private String ratingAvg;
    @SerializedName("is_purchased")
    @Expose
    private String isPurchased;

    public String getDelivery_instruction() {
        return delivery_instruction;
    }

    public void setDelivery_instruction(String delivery_instruction) {
        this.delivery_instruction = delivery_instruction;
    }

    public String getSpecial_instruction() {
        return special_instruction;
    }

    public void setSpecial_instruction(String special_instruction) {
        this.special_instruction = special_instruction;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public List<ProductSizeDetail> getProductSizeDetails() {
        return productSizeDetails;
    }

    public void setProductSizeDetails(List<ProductSizeDetail> productSizeDetails) {
        this.productSizeDetails = productSizeDetails;
    }

    public List<ProductColorDetail> getProductColorDetails() {
        return productColorDetails;
    }

    public void setProductColorDetails(List<ProductColorDetail> productColorDetails) {
        this.productColorDetails = productColorDetails;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(String ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public String getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(String isPurchased) {
        this.isPurchased = isPurchased;
    }

}
