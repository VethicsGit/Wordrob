
package com.pro.wardrobe.ApiResponse.OfferZoneResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferZoneList {

    @SerializedName("offer_zone_id")
    @Expose
    private String offerZoneId;
    @SerializedName("offer_zone_name")
    @Expose
    private String offerZoneName;
    @SerializedName("offer_zone_image")
    @Expose
    private String offerZoneImage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getOfferZoneId() {
        return offerZoneId;
    }

    public void setOfferZoneId(String offerZoneId) {
        this.offerZoneId = offerZoneId;
    }

    public String getOfferZoneName() {
        return offerZoneName;
    }

    public void setOfferZoneName(String offerZoneName) {
        this.offerZoneName = offerZoneName;
    }

    public String getOfferZoneImage() {
        return offerZoneImage;
    }

    public void setOfferZoneImage(String offerZoneImage) {
        this.offerZoneImage = offerZoneImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
