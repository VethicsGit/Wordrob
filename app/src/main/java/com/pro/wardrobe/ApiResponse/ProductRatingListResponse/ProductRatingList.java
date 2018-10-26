
package com.pro.wardrobe.ApiResponse.ProductRatingListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRatingList {

    @SerializedName("rating_review_id")
    @Expose
    private String ratingReviewId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("rating_point")
    @Expose
    private String ratingPoint;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("total_like_count")
    @Expose
    private String totalLikeCount;
    @SerializedName("total_unlike_count")
    @Expose
    private String totalUnlikeCount;
    @SerializedName("my_like_unlike")
    @Expose
    private List<MyLikeUnlike> myLikeUnlike = null;

    public String getRatingReviewId() {
        return ratingReviewId;
    }

    public void setRatingReviewId(String ratingReviewId) {
        this.ratingReviewId = ratingReviewId;
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

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(String ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalLikeCount() {
        return totalLikeCount;
    }

    public void setTotalLikeCount(String totalLikeCount) {
        this.totalLikeCount = totalLikeCount;
    }

    public String getTotalUnlikeCount() {
        return totalUnlikeCount;
    }

    public void setTotalUnlikeCount(String totalUnlikeCount) {
        this.totalUnlikeCount = totalUnlikeCount;
    }

    public List<MyLikeUnlike> getMyLikeUnlike() {
        return myLikeUnlike;
    }

    public void setMyLikeUnlike(List<MyLikeUnlike> myLikeUnlike) {
        this.myLikeUnlike = myLikeUnlike;
    }

}
