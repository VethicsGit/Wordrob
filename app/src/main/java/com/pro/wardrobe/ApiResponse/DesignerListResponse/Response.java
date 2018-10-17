
package com.pro.wardrobe.ApiResponse.DesignerListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendor_list")
    @Expose
    private List<VendorList> vendorList = null;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VendorList> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<VendorList> vendorList) {
        this.vendorList = vendorList;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
