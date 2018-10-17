
package com.pro.wardrobe.ApiResponse.CountryResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("country_list")
    @Expose
    private List<CountryList> countryList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CountryList> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryList> countryList) {
        this.countryList = countryList;
    }

}
