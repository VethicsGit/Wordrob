
package com.pro.wardrobe.ApiResponse.ColorListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("color_list")
    @Expose
    private List<ColorList> colorList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ColorList> getColorList() {
        return colorList;
    }

    public void setColorList(List<ColorList> colorList) {
        this.colorList = colorList;
    }

}
