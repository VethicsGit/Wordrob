package com.pro.wardrobe.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pro.wardrobe.Adapter.Designers_Recyclarview_adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.Designerimages;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.VendorList;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class New extends AppCompatActivity {

    final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    RecyclerView designers_recyclarview;
    Designers_Recyclarview_adapter designers_recyclarview_adapter;

    String strid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_designer);



        designers_recyclarview = findViewById(R.id.designers_recyclarview);
        designers_recyclarview.setNestedScrollingEnabled(false);
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        designers_recyclarview.setLayoutManager(gridLayoutManager);




        Intent intent=getIntent();
        strid = intent.getStringExtra("product_type_id");




            final SharedPreferences preferences = getApplicationContext().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);

            Call<Designerimages>call1=apiInterface.design_list(preferences.getString("user_id",""),"0",strid,preferences.getString("token",""));
            call1.enqueue(new Callback<Designerimages>() {

                @Override
                public void onResponse(Call<Designerimages> call, Response<Designerimages> response) {

//                    Log.e("message","darshak"+intent.getStringExtra("product_type_id"));

                    Designerimages designerimages=response.body();
                    List<com.pro.wardrobe.ApiResponse.DesignerListResponse.Response> responses=designerimages.getResponse();

                    for (int i = 0;i<responses.size();i++) {

                        com.pro.wardrobe.ApiResponse.DesignerListResponse.Response response1 = responses.get(i);

                        if (response1.getStatus().equals("true")) {


                            List<VendorList> vendorLists = response1.getVendorList();

                            for (i = 0; i<vendorLists.size(); i++) {


                                designers_recyclarview_adapter = new Designers_Recyclarview_adapter(vendorLists, getApplicationContext());
                                designers_recyclarview.setAdapter(designers_recyclarview_adapter);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<Designerimages> call, Throwable t) {

                }
            });
        }


    }

