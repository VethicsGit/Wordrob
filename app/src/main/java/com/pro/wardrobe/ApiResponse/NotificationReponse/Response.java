
package com.pro.wardrobe.ApiResponse.NotificationReponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("activity_list")
    @Expose
    private List<ActivityList> activityList = null;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ActivityList> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityList> activityList) {
        this.activityList = activityList;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
