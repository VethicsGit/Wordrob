
package com.pro.wardrobe.ApiResponse.PriceRangeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("min_max_price_range")
    @Expose
    private MinMaxPriceRange minMaxPriceRange;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MinMaxPriceRange getMinMaxPriceRange() {
        return minMaxPriceRange;
    }

    public void setMinMaxPriceRange(MinMaxPriceRange minMaxPriceRange) {
        this.minMaxPriceRange = minMaxPriceRange;
    }

}
