
package com.pro.wardrobe.ApiResponse.CateByAlphabeticResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCatebyalphabetic {

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