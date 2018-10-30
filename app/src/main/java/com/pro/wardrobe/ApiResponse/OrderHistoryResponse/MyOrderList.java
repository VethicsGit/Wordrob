
package com.pro.wardrobe.ApiResponse.OrderHistoryResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrderList {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("braintree_transaction_id")
    @Expose
    private String braintreeTransactionId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("shipping_amount")
    @Expose
    private String shippingAmount;
    @SerializedName("discount_code")
    @Expose
    private String discountCode;
    @SerializedName("discount_percentage")
    @Expose
    private String discountPercentage;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("payment_method_string")
    @Expose
    private String paymentMethodString;
    @SerializedName("wardrobe_order_id")
    @Expose
    private String wardrobeOrderId;
    @SerializedName("order_details")
    @Expose
    private List<OrderDetail> orderDetails = null;
    @SerializedName("billing_address")
    @Expose
    private List<BillingAddress> billingAddress = null;
    @SerializedName("shipping_address")
    @Expose
    private List<ShippingAddress> shippingAddress = null;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBraintreeTransactionId() {
        return braintreeTransactionId;
    }

    public void setBraintreeTransactionId(String braintreeTransactionId) {
        this.braintreeTransactionId = braintreeTransactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentMethodString() {
        return paymentMethodString;
    }

    public void setPaymentMethodString(String paymentMethodString) {
        this.paymentMethodString = paymentMethodString;
    }

    public String getWardrobeOrderId() {
        return wardrobeOrderId;
    }

    public void setWardrobeOrderId(String wardrobeOrderId) {
        this.wardrobeOrderId = wardrobeOrderId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<BillingAddress> getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(List<BillingAddress> billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<ShippingAddress> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<ShippingAddress> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
