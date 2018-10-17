package com.pro.wardrobe.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.pro.wardrobe.ApiResponse.ForgotPassResponse.ForgotPassResponse;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    EditText forgotpass_email;
    Button forgotpass_btn_reset;
    LinearLayout forgotpass_login_btn;
    ImageView forgotpass_back;
    TextView forgot_reset,forgot_pass;
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
         apiInterface =APIClient.getClient().create(APIInterface.class);
        forgotpass_email=findViewById(R.id.forgotpass_email);
        forgotpass_btn_reset=findViewById(R.id.forgotpass_btn_reset);
        forgotpass_login_btn=findViewById(R.id.forgotpass_login_btn);
        forgotpass_back=findViewById(R.id.forgotpass_back);
        forgot_reset=findViewById(R.id.forgot_reset);
        forgot_pass=findViewById(R.id.forgot_pass);


        Typeface face = Typeface.createFromAsset(getAssets(),
                "Roboto_Regular.ttf");
        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Roboto_Medium.ttf");
        forgot_pass.setTypeface(face);
        forgot_reset.setTypeface(facebold);


        forgotpass_btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (forgotpass_email.getText().toString().equals("")){
forgotpass_email.setError("Please fill something");
                }else if (!Patterns.EMAIL_ADDRESS.matcher(forgotpass_email.getText().toString()).matches()){
                    forgotpass_email.setError("Enter valid email address");
                }else {

                    final ProgressDialog mProgressDialog = new ProgressDialog(ForgotPassword.this,R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();



                    Call<ForgotPassResponse> call = apiInterface.ForgotPass(forgotpass_email.getText().toString());
                    call.enqueue(new Callback<ForgotPassResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ForgotPassResponse> call, @NonNull Response<ForgotPassResponse> response) {
                            mProgressDialog.dismiss();
                            ForgotPassResponse res=response.body();
                            List<com.pro.wardrobe.ApiResponse.ForgotPassResponse.Response> resList=res.getResponse();
                            com.pro.wardrobe.ApiResponse.ForgotPassResponse.Response re=resList.get(0);
                            if (re.getStatus().equals("true")){

                                new AlertDialog.Builder(ForgotPassword.this)
                                        .setTitle("Forgot Password")
                                        .setMessage(re.getResponseMsg())
                                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }else {
                                Toast.makeText(ForgotPassword.this, re.getResponseMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<ForgotPassResponse> call, @NonNull Throwable t) {

                        }
                    });
                }

            }
        });

        forgotpass_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
