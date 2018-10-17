
package com.pro.wardrobe.ApiResponse.DesignerListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorList {

    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("product_type_id")
    @Expose
    private String productTypeId;
    @SerializedName("product_type")
    @Expose
    private String productType;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

}
