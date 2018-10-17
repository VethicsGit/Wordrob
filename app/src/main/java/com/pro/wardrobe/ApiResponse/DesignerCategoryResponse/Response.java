
package com.pro.wardrobe.ApiResponse.DesignerCategoryResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("product_type_list")
    @Expose
    private List<ProductTypeList> productTypeList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductTypeList> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<ProductTypeList> productTypeList) {
        this.productTypeList = productTypeList;
    }

}
