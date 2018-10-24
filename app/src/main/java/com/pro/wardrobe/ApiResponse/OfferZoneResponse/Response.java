
package com.pro.wardrobe.ApiResponse.OfferZoneResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("offer_zone_list")
    @Expose
    private List<OfferZoneList> offerZoneList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OfferZoneList> getOfferZoneList() {
        return offerZoneList;
    }

    public void setOfferZoneList(List<OfferZoneList> offerZoneList) {
        this.offerZoneList = offerZoneList;
    }

}
