
package com.pro.wardrobe.ApiResponse.CartListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cart_list")
    @Expose
    private List<CartList> cartList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CartList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartList> cartList) {
        this.cartList = cartList;
    }

}
