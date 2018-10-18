
package com.pro.wardrobe.ApiResponse.FavoriteProductListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fav_product_list")
    @Expose
    private List<FavProductList> favProductList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FavProductList> getFavProductList() {
        return favProductList;
    }

    public void setFavProductList(List<FavProductList> favProductList) {
        this.favProductList = favProductList;
    }

}
