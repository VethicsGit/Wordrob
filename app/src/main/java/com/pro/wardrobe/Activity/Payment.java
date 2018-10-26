package com.pro.wardrobe.Activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.wardrobe.Adapter.Payment_item_list_Adapter;
import com.pro.wardrobe.Adapter.mybag_list_Adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CartListResponse.CartList;
import com.pro.wardrobe.ApiResponse.CartListResponse.ResponseCartList;
import com.pro.wardrobe.ApiResponse.CoupanCodeValidResponse.CouponCode;
import com.pro.wardrobe.ApiResponse.CoupanCodeValidResponse.ResponseCoupanCodeValid;
import com.pro.wardrobe.ApiResponse.CreateOrderResponse.ResponseCreateOrder;
import com.pro.wardrobe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatActivity {

    ImageView payment_back;
    Button payment_btn_next;
    TextView payment_title;


    TextView payment_total;
    TextView payment_subtotal;

    RecyclerView payment_item_recycler;

    Button payment_promo_btn;
    EditText payment_promo_edit;

    SharedPreferences preferences;
    APIInterface apiInterface;

    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        c = this;
        preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        payment_back = findViewById(R.id.payment_back);
        payment_title = findViewById(R.id.payment_title);
        payment_total = findViewById(R.id.payment_total);
        payment_subtotal = findViewById(R.id.payment_subtotal);


        payment_promo_edit = findViewById(R.id.payment_promo_edit);
        payment_promo_btn = findViewById(R.id.payment_promo_btn);

       /* SharedPreferences billingPref = getSharedPreferences("billingadd", MODE_PRIVATE);
        final JSONArray billingary = new JSONArray();
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


        SharedPreferences shippingPref = getSharedPreferences("shippingadd", MODE_PRIVATE);
        final JSONArray shippingary = new JSONArray();
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


        shippingary.put(shippingObj);*/


        payment_promo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                Call<ResponseCoupanCodeValid> call = apiInterface.check_coupon_code_valid(preferences.getString("user_id", ""), payment_promo_edit.getText().toString(), preferences.getString("token", ""));
                call.enqueue(new Callback<ResponseCoupanCodeValid>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseCoupanCodeValid> call, @NonNull Response<ResponseCoupanCodeValid> response) {
                        mProgressDialog.dismiss();
                        ResponseCoupanCodeValid responseCoupanCodeValid = response.body();
                        List<com.pro.wardrobe.ApiResponse.CoupanCodeValidResponse.Response> resList = responseCoupanCodeValid.getResponse();
                        com.pro.wardrobe.ApiResponse.CoupanCodeValidResponse.Response res = resList.get(0);
                        if (res.getStatus().equals("true")) {
                            Toast.makeText(Payment.this, "coupan code applied", Toast.LENGTH_SHORT).show();
                            Float t = Float.parseFloat(payment_total.getText().toString());
                            CouponCode c = res.getCouponCode();
                            Float discount = t * Integer.parseInt(c.getPercentage()) / 100;
                            payment_total.setText(String.valueOf(t - discount));

                        } else {
                            payment_promo_edit.setText("");
                            payment_promo_edit.setError("Please enter valid coupan code");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCoupanCodeValid> call, Throwable t) {

                    }
                });

            }
        });

        myBagApiCall();
        payment_item_recycler = findViewById(R.id.payment_item_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        payment_item_recycler.setLayoutManager(manager);

        payment_item_recycler.setNestedScrollingEnabled(false);
        payment_item_recycler.setHasFixedSize(true);

        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Bold.ttf");
        payment_title.setTypeface(facebold);

        payment_btn_next = findViewById(R.id.payment_btn_next);
        payment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });

        payment_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Call<ResponseCreateOrder> call = apiInterface.create_order(
                        preferences.getString("user_id", ""),
shippingary.toString(),
                        billingary.toString(),
                        payment_subtotal.getText().toString(),
                        payment_total.getText().toString(),
                        payment_total.getText().toString(),
"1",
                        preferences.getString("token","")
                        );

                call.enqueue(new Callback<ResponseCreateOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseCreateOrder> call, @NonNull Response<ResponseCreateOrder> response) {
                        ResponseCreateOrder responseCreateOrder=response.body();
                        List<com.pro.wardrobe.ApiResponse.CreateOrderResponse.Response> resList=responseCreateOrder.getResponse();
                        com.pro.wardrobe.ApiResponse.CreateOrderResponse.Response res=resList.get(0);
                        if (res.getStatus().equals("true")){
*/
                Intent intent = new Intent(getApplicationContext(), Confirm.class);
                intent.putExtra("subtotal", payment_subtotal.getText().toString());
                intent.putExtra("total", payment_total.getText().toString());
                startActivity(intent);
                     /*   }else {
                            Toast.makeText(Payment.this, "Something went wrong..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCreateOrder> call, Throwable t) {

                    }
                });*/


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
                    Toast.makeText(Payment.this, "cart list size ", Toast.LENGTH_SHORT).show();
                    if (response1.getStatus().equals("true")) {
                        List<CartList> cartList = response1.getCartList();

                        Payment_item_list_Adapter payment_item_list_adapter = new Payment_item_list_Adapter(getApplicationContext(), cartList, payment_total, payment_subtotal);
                        payment_item_recycler.setAdapter(payment_item_list_adapter);
                    } else {
                        Toast.makeText(Payment.this, "Oops, No result found..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) throws NumberFormatException {
                Toast.makeText(Payment.this, "error ", Toast.LENGTH_SHORT).show();
                Log.e("BagError", t.getMessage());
            }
        });
    }
}
