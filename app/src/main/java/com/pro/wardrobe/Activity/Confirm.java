package com.pro.wardrobe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Header;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.PostalAddress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.wardrobe.Adapter.Payment_item_list_Adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CLientTokenResponse.ResponseClientToken;
import com.pro.wardrobe.ApiResponse.CartListResponse.CartList;
import com.pro.wardrobe.ApiResponse.CartListResponse.ResponseCartList;
import com.pro.wardrobe.ApiResponse.CreateOrderResponse.ResponseCreateOrder;
import com.pro.wardrobe.R;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Confirm extends AppCompatActivity {

    Button confirm_btn_placeorder;
    ImageView confirm_back;
    TextView confirm_title;

    RecyclerView confirm_item_recycler;

    TextView confirm_total;
    TextView confirm_subtotal;
    TextView confirm_dummy;

    TextView confirm_billing_address;
    TextView confirm_shipping_address;
    SharedPreferences preferences;
    APIInterface apiInterface;


    JSONArray billingary;
    JSONArray shippingary;

    TextView confirm_payment_option;

    //    BraintreeFragment   mBraintreeFragment;
//Authorization mAuthorization;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        c = this;

        preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        confirm_total = findViewById(R.id.confirm_total);
        confirm_payment_option= findViewById(R.id.confirm_payment_option);
        confirm_subtotal = findViewById(R.id.confirm_subtotal);
        confirm_dummy = findViewById(R.id.confirm_dummy);
        confirm_billing_address = findViewById(R.id.confirm_billing_address);
        confirm_shipping_address = findViewById(R.id.confirm_shipping_address);
        SharedPreferences billingPref = getSharedPreferences("billingadd", MODE_PRIVATE);
        String billing_ad = billingPref.getString("firstname", "") + " " + billingPref.getString("lastname", "") + ",\n" +
                billingPref.getString("address", "") + ",\n" + billingPref.getString("city", "") + ", " + billingPref.getString("region", "") + ",\n" +
                billingPref.getString("country", "") + ",\n" + billingPref.getString("phone", "");
        confirm_billing_address.setText(billing_ad);


        SharedPreferences shippingPref = getSharedPreferences("shippingadd", MODE_PRIVATE);
        String shipping_ad = shippingPref.getString("firstname", "") + " " + shippingPref.getString("lastname", "") + ",\n" +
                shippingPref.getString("address", "") + ",\n" + shippingPref.getString("city", "") + ", " + shippingPref.getString("region", "") + ",\n" +
                shippingPref.getString("country", "") + ",\n" + shippingPref.getString("phone", "");
        confirm_shipping_address.setText(shipping_ad);


//        SharedPreferences billingPref = getSharedPreferences("billingadd", MODE_PRIVATE);
        billingary = new JSONArray();
        JSONObject billingObj = new JSONObject();

        try {
            billingObj.put("fname", billingPref.getString("firstname", ""));
            billingObj.put("lname", billingPref.getString("lastname", ""));
            billingObj.put("phone_number", billingPref.getString("phone", ""));
            billingObj.put("city", billingPref.getString("city", ""));
            billingObj.put("address", billingPref.getString("address", ""));
            billingObj.put("Countries_id", billingPref.getString("country_id", ""));
            billingObj.put("region", billingPref.getString("region", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        billingary.put(billingObj);


//        SharedPreferences shippingPref = getSharedPreferences("shippingadd", MODE_PRIVATE);
        shippingary = new JSONArray();
        JSONObject shippingObj = new JSONObject();

        try {
            shippingObj.put("fname", shippingPref.getString("firstname", ""));
            shippingObj.put("lname", shippingPref.getString("lastname", ""));
            shippingObj.put("phone_number", shippingPref.getString("phone", ""));
            shippingObj.put("city", shippingPref.getString("city", ""));
            shippingObj.put("address", shippingPref.getString("address", ""));
            shippingObj.put("Countries_id", shippingPref.getString("country_id", ""));
            shippingObj.put("region", shippingPref.getString("region", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        shippingary.put(shippingObj);

        confirm_btn_placeorder = findViewById(R.id.confirm_btn_placeorder);
        confirm_title = findViewById(R.id.confirm_title);
        confirm_item_recycler = findViewById(R.id.confirm_item_recycler);

        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        confirm_item_recycler.setLayoutManager(manager);
        confirm_item_recycler.setNestedScrollingEnabled(false);
        confirm_item_recycler.setHasFixedSize(true);

        myBagApiCall();

        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Bold.ttf");
        confirm_title.setTypeface(facebold);


        confirm_payment_option.setText(getIntent().getStringExtra("paymentoption"));

        confirm_back = findViewById(R.id.confirm_back);
        confirm_btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getIntent().getStringExtra("paymentoption").equals("Cash on Delivery")) {

//                    Toast.makeText(c, "if condition", Toast.LENGTH_SHORT).show();
                    Call<ResponseCreateOrder> call = apiInterface.create_order(
                            preferences.getString("user_id", ""),
                            shippingary.toString(),
                            billingary.toString(),
                            confirm_subtotal.getText().toString(),
                            confirm_total.getText().toString(),
                            confirm_total.getText().toString(),
                            "1",
                            preferences.getString("token", "")
                    );


                    Log.e("confirm ", preferences.getString("user_id", ""));
                    Log.e("confirm ", shippingary.toString());
                    Log.e("confirm ", billingary.toString());
                    Log.e("confirm ", confirm_subtotal.getText().toString());
                    Log.e("confirm ", confirm_total.getText().toString());
                    Log.e("confirm ", confirm_total.getText().toString());
                    Log.e("confirm ", "1");
                    Log.e("confirm ", preferences.getString("token", ""));

                    final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();
                    call.enqueue(new Callback<ResponseCreateOrder>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseCreateOrder> call, @NonNull Response<ResponseCreateOrder> response) {
                            mProgressDialog.dismiss();
                            ResponseCreateOrder responseCreateOrder = response.body();
                            List<com.pro.wardrobe.ApiResponse.CreateOrderResponse.Response> resList = responseCreateOrder.getResponse();
                            com.pro.wardrobe.ApiResponse.CreateOrderResponse.Response res = resList.get(0);
                            if (res.getStatus().equals("true")) {

                                OrderPlaced orderPlaced=new OrderPlaced(res.getOrderId());
//                                Toast.makeText(getApplicationContext(), "order id "+res.getOrderId(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), orderPlaced.getClass());
                                intent.putExtra("orderId", res.getOrderId());
                                startActivity(intent);
                            } else {
                                Toast.makeText(Confirm.this, "Something went wrong..!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseCreateOrder> call, Throwable t) {

                        }
                    });

                } else  if (getIntent().getStringExtra("paymentoption").equals("credit Card / Debit Card")) {

//                    Toast.makeText(c, "else condition", Toast.LENGTH_SHORT).show();

                    final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();
                    Call<ResponseClientToken> callClientToen = apiInterface.get_client_token(
                            preferences.getString("user_id", ""),
                            preferences.getString("token", "")
                    );
                    callClientToen.enqueue(new Callback<ResponseClientToken>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseClientToken> call, @NonNull Response<ResponseClientToken> response) {
                            mProgressDialog.dismiss();
                            ResponseClientToken responseClientToken = response.body();
                            List<com.pro.wardrobe.ApiResponse.CLientTokenResponse.Response> resList = responseClientToken.getResponse();
                            com.pro.wardrobe.ApiResponse.CLientTokenResponse.Response res = resList.get(0);
                            if (res.getStatus().equals("true")) {


                                DropInRequest dropInRequest = new DropInRequest()
                                        .clientToken(res.getClientToken());
                                startActivityForResult(dropInRequest.getIntent(getApplicationContext()), 100);
                            } else {
                                Toast.makeText(c, "Something went wrong..!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseClientToken> call, Throwable t) {

                        }
                    });


                }
            }
        });

        confirm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void myBagApiCall() throws NumberFormatException {
        final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        Call<ResponseCartList> call = apiInterface.cart_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ResponseCartList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCartList> call, @NonNull Response<ResponseCartList> response) {
                try {
                    mProgressDialog.dismiss();
                    Gson gson = new GsonBuilder().create();
                    String myCustomArray = gson.toJson(response).toString();
                    Log.e("BagResponse", myCustomArray);
                    ResponseCartList res = response.body();
                    List<com.pro.wardrobe.ApiResponse.CartListResponse.Response> resList = res.getResponse();
                    com.pro.wardrobe.ApiResponse.CartListResponse.Response response1 = resList.get(0);
//                    Toast.makeText(Confirm.this, "cart list size ", Toast.LENGTH_SHORT).show();
                    if (response1.getStatus().equals("true")) {
                        List<CartList> cartList = response1.getCartList();

                        Payment_item_list_Adapter payment_item_list_adapter = new Payment_item_list_Adapter(getApplicationContext(), cartList, confirm_dummy, confirm_dummy);
                        confirm_item_recycler.setAdapter(payment_item_list_adapter);

                        confirm_total.setText(getIntent().getStringExtra("total"));
                        confirm_subtotal.setText(getIntent().getStringExtra("subtotal"));
                    } else {
                        Toast.makeText(Confirm.this, "Oops, No result found..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) throws NumberFormatException {
//                Toast.makeText(Confirm.this, "error ", Toast.LENGTH_SHORT).show();
                Log.e("BagError", t.getMessage());
            }
        });
    }

    /*   @Override
       public void onConfigurationFetched(Configuration configuration) {

       }
   */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String nonStr = nonce.getNonce();

//                Toast.makeText(c, "nonce " + nonStr, Toast.LENGTH_SHORT).show();

                Call<ResponseCreateOrder> call = apiInterface.braintree_create_order(
                        preferences.getString("user_id", ""),
                        shippingary.toString(),
                        billingary.toString(),
                        confirm_subtotal.getText().toString(),
                        confirm_total.getText().toString(),
                        confirm_total.getText().toString(),
                        "2",
                        nonStr,
                        preferences.getString("token", "")
                );


                Log.e("confirm ", preferences.getString("user_id", ""));
                Log.e("confirm ", shippingary.toString());
                Log.e("confirm ", billingary.toString());
                Log.e("confirm ", confirm_subtotal.getText().toString());
                Log.e("confirm ", confirm_total.getText().toString());
                Log.e("confirm ", confirm_total.getText().toString());
                Log.e("confirm ", "2");
                Log.e("confirm ", preferences.getString("token", ""));

                final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                call.enqueue(new Callback<ResponseCreateOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseCreateOrder> call, @NonNull Response<ResponseCreateOrder> response) {
                        mProgressDialog.dismiss();
                        ResponseCreateOrder responseCreateOrder = response.body();
                        List<com.pro.wardrobe.ApiResponse.CreateOrderResponse.Response> resList = responseCreateOrder.getResponse();
                        com.pro.wardrobe.ApiResponse.CreateOrderResponse.Response res = resList.get(0);
                        if (res.getStatus().equals("true")) {

                            Intent intent = new Intent(getApplicationContext(), OrderPlaced.class);
                            intent.putExtra("orderId", res.getOrderId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(Confirm.this, "Something went wrong..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCreateOrder> call, Throwable t) {

                    }
                });


                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Toast.makeText(c, "User Cancelled process", Toast.LENGTH_SHORT).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("Payment_error", error.toString());
            }
        }
    }
}
