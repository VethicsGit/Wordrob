
package com.pro.wardrobe.ApiResponse.CartListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartList {

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_size_id")
    @Expose
    private String productSizeId;
    @SerializedName("product_color_id")
    @Expose
    private String productColorId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("hips")
    @Expose
    private String hips;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image_name")
    @Expose
    private String imageName;
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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("is_fav")
    @Expose
    private String isFav;
    @SerializedName("size")
    @Expose
    private List<Size> size = null;
    @SerializedName("color")
    @Expose
    private List<Color> color = null;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(String productColorId) {
        this.productColorId = productColorId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHips() {
        return hips;
    }

    public void setHips(String hips) {
        this.hips = hips;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

}
