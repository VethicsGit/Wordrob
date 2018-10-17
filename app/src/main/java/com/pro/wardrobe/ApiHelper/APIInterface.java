package com.pro.wardrobe.ApiHelper;

import com.pro.wardrobe.ApiResponse.AddbannerResponse.Addbanner;
import com.pro.wardrobe.ApiResponse.CateListResponse.CatelistResponse;
import com.pro.wardrobe.ApiResponse.ChangePassResponse.ChangePassResponse;
import com.pro.wardrobe.ApiResponse.ContactUsResponse.ContactUsResponse;
import com.pro.wardrobe.ApiResponse.CountryResponse.CountryResponse;
import com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Designercategory;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.Designerimages;
import com.pro.wardrobe.ApiResponse.ForgotPassResponse.ForgotPassResponse;
import com.pro.wardrobe.ApiResponse.LoginResponse.LoginResponse;
import com.pro.wardrobe.ApiResponse.PrivacyPolicyResponse.PrivacyPolicyResponse;
import com.pro.wardrobe.ApiResponse.ProfileResponse.ProfileResponse;
import com.pro.wardrobe.ApiResponse.Signup_Response.SignupResponse;
import com.pro.wardrobe.ApiResponse.TermsResponse.TermsResponse;
import com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.UpdateDeviceTokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("signin")
    Call<LoginResponse> signin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("Signup")
    Call<SignupResponse> Signup(@Field("name") String name, @Field("email") String email, @Field("password") String password);



    @FormUrlEncoded
    @POST("change_password")
    Call<ChangePassResponse> ChangePass(@Field("user_id") String user_id, @Field("password") String password, @Header("Authorization")String token);

    @FormUrlEncoded
    @POST("update_device_token")
    Call<UpdateDeviceTokenResponse> UpdateDeviceToken(@Field("user_id") String user_id, @Field("device_type") String device_type , @Field("device_token") String device_token,@Field("device_id") String device_id , @Header("Authorization")String token);

    @FormUrlEncoded
    @POST("logout")
    Call<UpdateDeviceTokenResponse> Logout(@Field("user_id") String user_id,  @Field("device_token") String device_token, @Header("Authorization")String token);

    @FormUrlEncoded
    @POST("user_profile")
    Call<ProfileResponse> user_profile(@Field("user_id") String user_id, @Header("Authorization")String token);

    @FormUrlEncoded
    @POST("country_list")
    Call<CountryResponse> country_list(@Field("user_id") String user_id, @Header("Authorization")String token);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgotPassResponse> ForgotPass(@Field("email") String email);

    @POST("Terms_and_condition")
    Call<TermsResponse> Terms_and_condition();

    @POST("privacy_policy")
    Call<PrivacyPolicyResponse> privacy_policy();

    @FormUrlEncoded
    @POST("contact_us")
    Call<ContactUsResponse> ContactUs(@Field("from_name")String from_name,@Field("from_email") String from_email,@Field("message") String message,@Field("phone_number") String phone_number );

    @FormUrlEncoded
    @POST("update_user_profile")
    Call<ProfileResponse> update_user_profile(
            @Header("Authorization")String token,
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("countries_id") String countries_id ,
            @Field("region") String region ,
            @Field("phone_num") String phone_num

    );
    @FormUrlEncoded
    @POST("update_user_profile")
    Call<ProfileResponse> update_user_profile_statusnot(
            @Header("Authorization")String token,
            @Field("user_id") String user_id,
            @Field("notification_status") String notification_status

    );
    @FormUrlEncoded
    @POST("update_user_profile")
    Call<ProfileResponse> update_user_profile_promotionnot(
            @Header("Authorization")String token,
            @Field("user_id") String user_id,
            @Field("promotion_notification") String promotion_notification

    );
    @FormUrlEncoded
    @POST("category_list")
    Call<CatelistResponse> category_list(
            @Field("user_id") String user_id,
            @Header("Authorization")String token


    );


    @FormUrlEncoded
    @POST("update_user_profile")
    Call<ProfileResponse> update_user_profile_instocknot(
            @Header("Authorization")String token,
            @Field("user_id") String user_id,
            @Field("promotion_notification") String promotion_notification

    );

    @FormUrlEncoded
    @POST("app_banner")
    Call<Addbanner> add_banner(
            @Field("user_id") String user_id,
            @Header("Authorization")String token


    );
    @FormUrlEncoded
    @POST("vendor_list")
    Call<Designerimages> design_list(
            @Field("user_id") String user_id,
            @Field("offset") String offset,
//            @Field(("search_string"))String search_string,
            @Field("product_type_id")String product_type_id,
             @Header("Authorization")String token

    );
    @FormUrlEncoded
    @POST("product_type_list")
    Call<Designercategory>design_category(
            @Field("user_id") String user_id,
            @Header("Authorization")String token

    );

}
