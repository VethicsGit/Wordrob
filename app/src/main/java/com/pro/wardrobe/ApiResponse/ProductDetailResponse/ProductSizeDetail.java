
package com.pro.wardrobe.ApiResponse.ProductDetailResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductSizeDetail {

    @SerializedName("product_size_id")
    @Expose
    private String productSizeId;
    @SerializedName("size_id")
    @Expose
    private String sizeId;
    @SerializedName("size")
    @Expose
    private String size;

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
