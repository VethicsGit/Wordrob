package com.pro.wardrobe.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.widget.TextView;

import com.pro.wardrobe.Adapter.OfferZone_adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.OfferZoneResponse.OfferZoneList;
import com.pro.wardrobe.ApiResponse.OfferZoneResponse.OfferZoneResponse;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class OfferZone extends AppCompatActivity {




    RecyclerView offer;
    OfferZone_adapter offerZone_adapter;
    TextView title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_offerzone);


        offer=findViewById(R.id.offer);
        title=findViewById(R.id.title);



        title.setText("OFFERS ");


        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        offer.setLayoutManager(manager);



        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);

        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<OfferZoneResponse>call=apiInterface.offerZone_list(preferences.getString("user_id",""),preferences.getString("token",""));
        call.enqueue(new Callback<OfferZoneResponse>() {
            @Override
            public void onResponse(retrofit2.Call<OfferZoneResponse> call, Response<OfferZoneResponse> response) {

                Log.e("response","offer_zone"+response.body().toString());
                OfferZoneResponse offerZoneResponse=response.body();
                List<com.pro.wardrobe.ApiResponse.OfferZoneResponse.Response>responses=offerZoneResponse.getResponse();


                for (int i=0;i<responses.size();i++)
                {
                    com.pro.wardrobe.ApiResponse.OfferZoneResponse.Response response1=responses.get(i);
                        Log.e("messge","status"+response1.getStatus());
                    if (response1.getStatus().equals("true"))
                    {
                        List<OfferZoneList>offerZoneLists=response1.getOfferZoneList();

//                            for (int j=0;j<offerZoneLists.size();j++) {


                                OfferZone_adapter offerZone_adapter = new OfferZone_adapter(offerZoneLists, getApplicationContext());

                                offer.setAdapter(offerZone_adapter);
//                            }
                    }
                }



            }

            @Override
            public void onFailure(retrofit2.Call<OfferZoneResponse> call, Throwable t) {

            }
        });



    }
}
