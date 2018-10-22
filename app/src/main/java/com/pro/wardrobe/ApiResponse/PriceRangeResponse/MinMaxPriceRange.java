
package com.pro.wardrobe.ApiResponse.PriceRangeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MinMaxPriceRange {

    @SerializedName("min_price")
    @Expose
    private String minPrice;
    @SerializedName("max_price")
    @Expose
    private String maxPrice;

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

}
