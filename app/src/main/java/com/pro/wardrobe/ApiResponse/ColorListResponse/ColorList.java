
package com.pro.wardrobe.ApiResponse.ColorListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColorList {

    @SerializedName("color_id")
    @Expose
    private String colorId;
    @SerializedName("color_name")
    @Expose
    private String colorName;
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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
