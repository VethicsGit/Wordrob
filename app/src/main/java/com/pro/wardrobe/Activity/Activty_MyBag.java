package com.pro.wardrobe.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.wardrobe.Adapter.mybag_list_Adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CartListResponse.CartList;
import com.pro.wardrobe.ApiResponse.CartListResponse.ResponseCartList;
import com.pro.wardrobe.R;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activty_MyBag extends AppCompatActivity {

    ImageView mybag_back;
    Button mybag_checkout;
    EditText mybag_promo_edit;
    RecyclerView mybag_recycler;
    TextView mybag_totalprice;
    TextView cartemptytext;
    NestedScrollView cartrootlayout;

Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty__my_bag);

        c=this;
        TextView mybag_title = findViewById(R.id.mybag_title);
        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        mybag_title.setTypeface(facebold);

        mybag_back = findViewById(R.id.mybag_back);
        mybag_totalprice= findViewById(R.id.mybag_totalprice);
        cartemptytext= findViewById(R.id.cartemptytext);
        mybag_recycler = findViewById(R.id.mybag_recycler);
        mybag_checkout = findViewById(R.id.mybag_checkout);
        mybag_promo_edit = findViewById(R.id.mybag_promo_edit);
        cartrootlayout= findViewById(R.id.cartrootlayout);
        mybag_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mybag_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Shipping.class);
                startActivity(i);
            }
        });
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mybag_recycler.setLayoutManager(manager);


        mybag_recycler.setNestedScrollingEnabled(false);
        mybag_recycler.setHasFixedSize(true);

myBagApiCall();

    }

    public void myBagApiCall() throws NumberFormatException{
        final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
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
                    if (response1.getStatus().equals("true")) {
                        cartemptytext.setVisibility(View.GONE);
                        cartrootlayout.setVisibility(View.VISIBLE);
                        List<CartList> cartList = response1.getCartList();
                        Toast.makeText(Activty_MyBag.this, "cart list size "+cartList.size(), Toast.LENGTH_SHORT).show();

                        if (cartList.size()>0) {
                            cartemptytext.setVisibility(View.GONE);
cartrootlayout.setVisibility(View.VISIBLE);
                            mybag_list_Adapter mybag_list_adapter = new mybag_list_Adapter(getApplicationContext(), cartList, mybag_recycler, mybag_totalprice);
                            mybag_recycler.setAdapter(mybag_list_adapter);
                        }else {
                            cartemptytext.setVisibility(View.VISIBLE);
                            cartrootlayout.setVisibility(View.GONE);
                        }
                    } else {
                        cartemptytext.setVisibility(View.VISIBLE);
                        cartrootlayout.setVisibility(View.GONE);
                        Toast.makeText(Activty_MyBag.this, "Oops, No result found..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) throws NumberFormatException {
                Toast.makeText(Activty_MyBag.this, "error ", Toast.LENGTH_SHORT).show();
                Log.e("BagError", t.getMessage());
            }
        });
    }


}
