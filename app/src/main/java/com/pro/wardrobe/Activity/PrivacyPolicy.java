package com.pro.wardrobe.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.PrivacyPolicyResponse.PrivacyPolicyResponse;
import com.pro.wardrobe.ApiResponse.TermsResponse.TermsResponse;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicy extends AppCompatActivity {

    WebView privacy_text;
    ImageView privacy_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        ((TextView)findViewById(R.id.privacy_title)).setTypeface(facebold);
        privacy_text=findViewById(R.id.privacy_text);
        privacy_back=findViewById(R.id.privacy_back);

//        privacy_text.setText(dummy);

        privacy_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<PrivacyPolicyResponse> call=apiInterface.privacy_policy();

        call.enqueue(new Callback<PrivacyPolicyResponse>() {
            @Override
            public void onResponse(Call<PrivacyPolicyResponse> call, Response<PrivacyPolicyResponse> response) {
                PrivacyPolicyResponse termsofuse=response.body();
                List<com.pro.wardrobe.ApiResponse.PrivacyPolicyResponse.Response> list=termsofuse.getResponse();

                for (int i = 0; i < list.size(); i++) {

                    com.pro.wardrobe.ApiResponse.PrivacyPolicyResponse.Response response1=list.get(i);
                    if (response1.getStatus().equals("true")){

                        String text = "<html><body style=\"text-align:justify;\">";
                        text += response1.getPrivacyPolicy();
                        text += "</body></html>";


                        privacy_text.loadData(text, "text/html", null);
                    }

                }

            }

            @Override
            public void onFailure(Call<PrivacyPolicyResponse> call, Throwable t) {

            }
        });

    }

    String dummy="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" + "\n" + "Safeguarding Client Information\n" + "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" + "\n" + "Third Party Sites Links\n" + "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.";
}
