
package com.pro.wardrobe.ApiResponse.SizeListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("size_list")
    @Expose
    private List<SizeList> sizeList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SizeList> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<SizeList> sizeList) {
        this.sizeList = sizeList;
    }

}
