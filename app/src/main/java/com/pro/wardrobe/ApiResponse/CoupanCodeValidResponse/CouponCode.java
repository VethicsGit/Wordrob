
package com.pro.wardrobe.ApiResponse.CoupanCodeValidResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponCode {

    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("description")
    @Expose
    private String description;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
