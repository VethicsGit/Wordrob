
package com.pro.wardrobe.ApiResponse.CartListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Color {

    @SerializedName("product_color_id")
    @Expose
    private String productColorId;
    @SerializedName("color_id")
    @Expose
    private String colorId;
    @SerializedName("color_name")
    @Expose
    private String colorName;
    @SerializedName("color_code")
    @Expose
    private String colorCode;

    public String getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(String productColorId) {
        this.productColorId = productColorId;
    }

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

}
