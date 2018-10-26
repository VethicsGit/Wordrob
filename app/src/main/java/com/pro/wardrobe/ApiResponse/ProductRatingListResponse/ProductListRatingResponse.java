
package com.pro.wardrobe.ApiResponse.ProductRatingListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductListRatingResponse {

    @SerializedName("response")
    @Expose
    private List<Response> response = null;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

}
