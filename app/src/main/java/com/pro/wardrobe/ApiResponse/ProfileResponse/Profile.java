
package com.pro.wardrobe.ApiResponse.ProfileResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("notification_status")
    @Expose
    private String notificationStatus;
    @SerializedName("promotion_notification")
    @Expose
    private String promotionNotification;
    @SerializedName("instock_notification")
    @Expose
    private String instockNotification;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_num")
    @Expose
    private String phoneNum;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("countries_id")
    @Expose
    private String countriesId;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("password_updated")
    @Expose
    private String passwordUpdated;
    @SerializedName("member_since")
    @Expose
    private String memberSince;
    @SerializedName("email_verified")
    @Expose
    private String emailVerified;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("profile_pic_thumb")
    @Expose
    private String profilePicThumb;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getPromotionNotification() {
        return promotionNotification;
    }

    public void setPromotionNotification(String promotionNotification) {
        this.promotionNotification = promotionNotification;
    }

    public String getInstockNotification() {
        return instockNotification;
    }

    public void setInstockNotification(String instockNotification) {
        this.instockNotification = instockNotification;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountriesId() {
        return countriesId;
    }

    public void setCountriesId(String countriesId) {
        this.countriesId = countriesId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPasswordUpdated() {
        return passwordUpdated;
    }

    public void setPasswordUpdated(String passwordUpdated) {
        this.passwordUpdated = passwordUpdated;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProfilePicThumb() {
        return profilePicThumb;
    }

    public void setProfilePicThumb(String profilePicThumb) {
        this.profilePicThumb = profilePicThumb;
    }

}
