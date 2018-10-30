
package com.pro.wardrobe.ApiResponse.NotificationReponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityList {

    @SerializedName("activity_list_id")
    @Expose
    private String activityListId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("display_msg")
    @Expose
    private String displayMsg;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("read_status")
    @Expose
    private String readStatus;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getActivityListId() {
        return activityListId;
    }

    public void setActivityListId(String activityListId) {
        this.activityListId = activityListId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getDisplayMsg() {
        return displayMsg;
    }

    public void setDisplayMsg(String displayMsg) {
        this.displayMsg = displayMsg;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
