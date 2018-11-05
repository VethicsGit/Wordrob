
package com.pro.wardrobe.ApiResponse.FAQResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("faq_list")
    @Expose
    private List<FaqList> faqList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FaqList> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<FaqList> faqList) {
        this.faqList = faqList;
    }

}
