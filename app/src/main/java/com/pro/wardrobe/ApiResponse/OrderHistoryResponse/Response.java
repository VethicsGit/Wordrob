
package com.pro.wardrobe.ApiResponse.OrderHistoryResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("my_order_list")
    @Expose
    private List<MyOrderList> myOrderList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<MyOrderList> getMyOrderList() {
        return myOrderList;
    }

    public void setMyOrderList(List<MyOrderList> myOrderList) {
        this.myOrderList = myOrderList;
    }

}
