
package com.pro.wardrobe.ApiResponse.AddbannerResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("app_banner")
    @Expose
    private String appBanner;

    public String getAppBanner() {
        return appBanner;
    }

    public void setAppBanner(String appBanner) {
        this.appBanner = appBanner;
    }

}
