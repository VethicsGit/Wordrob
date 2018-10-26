
package com.pro.wardrobe.ApiResponse.ProductRatingListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("product_rating_list")
    @Expose
    private List<ProductRatingList> productRatingList = null;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductRatingList> getProductRatingList() {
        return productRatingList;
    }

    public void setProductRatingList(List<ProductRatingList> productRatingList) {
        this.productRatingList = productRatingList;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
