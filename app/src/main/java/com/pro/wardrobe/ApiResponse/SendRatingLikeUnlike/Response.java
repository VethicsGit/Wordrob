
package com.pro.wardrobe.ApiResponse.SendRatingLikeUnlike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response_msg")
    @Expose
    private String responseMsg;
    @SerializedName("like_unlike_status")
    @Expose
    private String likeUnlikeStatus;
    @SerializedName("rating_review_id")
    @Expose
    private String ratingReviewId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getLikeUnlikeStatus() {
        return likeUnlikeStatus;
    }

    public void setLikeUnlikeStatus(String likeUnlikeStatus) {
        this.likeUnlikeStatus = likeUnlikeStatus;
    }

    public String getRatingReviewId() {
        return ratingReviewId;
    }

    public void setRatingReviewId(String ratingReviewId) {
        this.ratingReviewId = ratingReviewId;
    }

}
