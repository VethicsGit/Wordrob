package com.pro.wardrobe.ApiHelper;

import com.pro.wardrobe.ApiResponse.AddToFavorite.AddToFavorite;
import com.pro.wardrobe.ApiResponse.AddbannerResponse.Addbanner;
import com.pro.wardrobe.ApiResponse.CateListResponse.CatelistResponse;
import com.pro.wardrobe.ApiResponse.ChangePassResponse.ChangePassResponse;
import com.pro.wardrobe.ApiResponse.ColorListResponse.ResponseColorList;
import com.pro.wardrobe.ApiResponse.ContactUsResponse.ContactUsResponse;
import com.pro.wardrobe.ApiResponse.CountryResponse.CountryResponse;
import com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Designercategory;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.Designerimages;
import com.pro.wardrobe.ApiResponse.FavoriteProductListResponse.FavoritieProductListResponse;
import com.pro.wardrobe.ApiResponse.ForgotPassResponse.ForgotPassResponse;
import com.pro.wardrobe.ApiResponse.LoginResponse.LoginResponse;
import com.pro.wardrobe.ApiResponse.OfferZoneResponse.OfferZoneResponse;
import com.pro.wardrobe.ApiResponse.PriceRangeResponse.ResponsePriceRange;
import com.pro.wardrobe.ApiResponse.PrivacyPolicyResponse.PrivacyPolicyResponse;
import com.pro.wardrobe.ApiResponse.ProductDetailResponse.ProductDetailResponse;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductListResponse;
import com.pro.wardrobe.ApiResponse.ProfileResponse.ProfileResponse;
import com.pro.wardrobe.ApiResponse.RemoveToFavorite.RemoveToFavorite;
import com.pro.wardrobe.ApiResponse.Signup_Response.SignupResponse;
import com.pro.wardrobe.ApiResponse.SizeListResponse.ResponseSizeList;
import com.pro.wardrobe.ApiResponse.SocialSiginResponse.ResponseSocialSignin;
import com.pro.wardrobe.ApiResponse.TermsResponse.TermsResponse;
import com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.UpdateDeviceTokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @FormUrlEncoded
    @POST("fav_product_list")
    Call<FavoritieProductListResponse>fav_product_list(
            @Field("user_id") String user_id,
            @Header("Authorization") String token
    );


    @FormUrlEncoded
    @POST("product_list")
    Call<ProductListResponse> product_list(
            @Field("user_id") String user_id,
            @Field("offset") String offset,
            @Field("category_id") String category_id,
            @Field("vendor_id") String vendor_id,
            @Field("offer_zone_id")String offer_zone_id,
            @Header("Authorization") String token);


    @FormUrlEncoded
    @POST("product_list")
    Call<ProductListResponse> product_list_sorting(
            @Field("user_id") String user_id,
            @Field("offset") String offset,
            @Field("category_id") String category_id,
            @Field("vendor_id") String vendor_id,
            @Field("sort_by") String sort_by,
            @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("product_list")
    Call<ProductListResponse> product_list_filter(
            @Field("user_id") String user_id,
            @Field("offset") String offset,
            @Field("category_id") String category_id,
            @Field("vendor_id") String vendor_id,
            @Field("min_price") String min_price,
            @Field("max_price") String max_price,
            @Field("color_ids") String color_ids,
            @Field("size_ids") String size_ids,
            @Header("Authorization") String token);


    @FormUrlEncoded
    @POST("product_details")
    Call<ProductDetailResponse> product_detail(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("color_list")
    Call<ResponseColorList> color_list(
            @Field("user_id") String user_id,
            @Header("Authorization") String Authorization
                      );


    @FormUrlEncoded
    @POST("size_list")
    Call<ResponseSizeList> size_list(
            @Field("user_id") String user_id,
            @Header("Authorization") String Authorization
                      );

    @FormUrlEncoded
    @POST("min_max_price_range")
    Call<ResponsePriceRange> min_max_price_range(
            @Field("user_id") String user_id,
            @Header("Authorization") String Authorization
                      );

    @FormUrlEncoded
    @POST("add_to_fav_product")
    Call<AddToFavorite>add_fav(
      @Field("user_id")String user_id,
      @Field("product_id")String product_id,
      @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("remove_from_fav_product")
    Call<RemoveToFavorite>remove_fav(
    @Field("user_id")String user_id,
    @Field("product_id")String product_id,
    @Header("Authorization") String token
            );


    @FormUrlEncoded
    @POST("social_signin")
    Call<ResponseSocialSignin>social_signin(
            @Field("media_type")String media_type,
            @Field("media_id")String media_id,
            @Field("name")String name,
            @Field("email")String email
    );

    @FormUrlEncoded
    @POST("check_social_id")
    Call<ResponseSocialSignin>check_social_id(
            @Field("media_type")String media_type,
            @Field("media_id")String media_id

    );


    @FormUrlEncoded
    @POST("offer_zone_list")
    Call<OfferZoneResponse>offerZone_list(
            @Field("user_id")String user_id,
            @Header("Authorization") String token

    );



}
