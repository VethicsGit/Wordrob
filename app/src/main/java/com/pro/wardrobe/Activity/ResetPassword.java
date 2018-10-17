package com.pro.wardrobe.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.ChangePassResponse.ChangePassResponse;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    EditText resetpass_password, resetpass_confirm_password;
    Button resetpass_btn_save;
    ImageView resetpass_back;
    TextView reset_reset, reset_pass;
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
         apiInterface =APIClient.getClient().create(APIInterface.class);
        resetpass_password = findViewById(R.id.resetpass_password);
        resetpass_confirm_password = findViewById(R.id.resetpass_confirm_password);
        resetpass_btn_save = findViewById(R.id.resetpass_btn_save);
        resetpass_back = findViewById(R.id.resetpass_back);
        reset_reset = findViewById(R.id.reset_reset);
        reset_pass = findViewById(R.id.reset_pass);

        Typeface face = Typeface.createFromAsset(getAssets(), "Roboto_Regular.ttf");
        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Medium.ttf");
        reset_pass.setTypeface(face);
        reset_reset.setTypeface(facebold);

        resetpass_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("LoginStatus",MODE_PRIVATE);
                if (resetpass_password.getText().toString().equals("") && resetpass_confirm_password.getText().toString().equals("")) {
                    resetpass_password.setError("Please fill something");
                    resetpass_confirm_password.setError("Please fill something");
                } else if (resetpass_password.getText().toString().equals("")) {
                    resetpass_password.setError("Please fill something");
                } else if (resetpass_confirm_password.getText().toString().equals("")) {
                    resetpass_confirm_password.setError("Please fill something");
                } /*else if (!Patterns.EMAIL_ADDRESS.matcher(resetpass_password.getText().toString()).matches()) {
                    resetpass_password.setError("Enter valid email address");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(resetpass_confirm_password.getText().toString()).matches()) {
                    resetpass_confirm_password.setError("Enter valid email address");
                } */else {

                    final ProgressDialog mProgressDialog = new ProgressDialog(ResetPassword.this,R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();



                    Call<ChangePassResponse> call = apiInterface.ChangePass(preferences.getString("user_id",""),resetpass_password.getText().toString(),preferences.getString("token",""));
                    call.enqueue(new Callback<ChangePassResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ChangePassResponse> call, @NonNull Response<ChangePassResponse> response) {
                            mProgressDialog.dismiss();
                            ChangePassResponse changePassResponse=response.body();
                            List<com.pro.wardrobe.ApiResponse.ChangePassResponse.Response> responseList=changePassResponse.getResponse();
                            com.pro.wardrobe.ApiResponse.ChangePassResponse.Response res=responseList.get(0);
                            if (res.getStatus().equals("true")){
                                new AlertDialog.Builder(ResetPassword.this)
                                        .setTitle("Forgot Password")
                                        .setMessage(res.getResponseMsg())
                                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                onBackPressed();
                                            }
                                        })
                                        .show();
                            }else {
                                Toast.makeText(ResetPassword.this, res.getResponseMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<ChangePassResponse> call, @NonNull Throwable t) {

                        }
                    });
                }


            }
        });

        resetpass_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
