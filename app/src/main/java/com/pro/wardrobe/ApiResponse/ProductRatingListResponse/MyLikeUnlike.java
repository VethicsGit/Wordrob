
package com.pro.wardrobe.ApiResponse.ProductRatingListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyLikeUnlike {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rating_review_id")
    @Expose
    private String ratingReviewId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("like_unlike_status")
    @Expose
    private String likeUnlikeStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLikeUnlikeStatus() {
        return likeUnlikeStatus;
    }

    public void setLikeUnlikeStatus(String likeUnlikeStatus) {
        this.likeUnlikeStatus = likeUnlikeStatus;
    }

}
