package com.pro.wardrobe.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.Adapter.FAQAdapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.FAQResponse.ResponseFaq;
import com.pro.wardrobe.ApiResponse.SizeListResponse.ResponseSizeList;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQs extends AppCompatActivity {

    ImageView faq_back;
    RecyclerView faq_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Roboto_Regular.ttf");
        ((TextView) findViewById(R.id.faq_title)).setTypeface(facebold);


        faq_back = findViewById(R.id.faq_back);
        faq_recycler= findViewById(R.id.faq_recycler);
        LinearLayoutManager manager111=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        faq_recycler.setLayoutManager(manager111);
        faq_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<ResponseFaq> call = apiInterface.faq_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ResponseFaq>() {
            @Override
            public void onResponse(Call<ResponseFaq> call, Response<ResponseFaq> response) {
ResponseFaq responseFaq=response.body();
                List<com.pro.wardrobe.ApiResponse.FAQResponse.Response> reslist=responseFaq.getResponse();
                com.pro.wardrobe.ApiResponse.FAQResponse.Response res=reslist.get(0);
                if (res.getStatus().equals("true")){
                    FAQAdapter adapter=new FAQAdapter(getApplicationContext(),res.getFaqList());
                faq_recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseFaq> call, Throwable t) {

            }
        });
    }
}
