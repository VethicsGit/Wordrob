package com.pro.wardrobe.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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
import com.pro.wardrobe.ApiResponse.Signup_Response.SignupResponse;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    LinearLayout signup_login_btn;
    Button signup_btn_signup;
    EditText login_confirm_password, login_password, signup_email, signup_username;
    TextView signup_in, signup_sign;
    APIInterface apiInterface;
    ImageView signup_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
         apiInterface =APIClient.getClient().create(APIInterface.class);
        signup_login_btn = findViewById(R.id.signup_login_btn);
        signup_btn_signup = findViewById(R.id.signup_btn_signup);
        login_confirm_password = findViewById(R.id.login_confirm_password);
        login_password = findViewById(R.id.login_password);
        signup_email = findViewById(R.id.signup_email);
        signup_username = findViewById(R.id.signup_username);
        signup_in = findViewById(R.id.signup_in);
        signup_sign = findViewById(R.id.signup_sign);
        signup_back = findViewById(R.id.signup_back);

        signup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Typeface face = Typeface.createFromAsset(getAssets(), "Roboto_Regular.ttf");
        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Medium.ttf");
        signup_in.setTypeface(face);
        signup_sign.setTypeface(facebold);

        signup_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (signup_username.getText().toString().equals("") &&
                                signup_email.getText().toString().equals("") &&
                                login_password.getText().toString().equals("") &&
                                login_confirm_password.getText().toString().equals("")){
                            signup_username.setError("Please fill something");
                            signup_email.setError("Please fill something");
                            login_password.setError("Please fill something");
                            login_confirm_password.setError("Please fill something");
                }
                else if (signup_username.getText().toString().equals("")){ signup_username.setError("Please fill something");}
                else if (signup_email.getText().toString().equals("")){ signup_email.setError("Please fill something");}
                else if (login_password.getText().toString().equals("")){ login_password.setError("Please fill something");}
                else if (login_confirm_password.getText().toString().equals("")){ login_confirm_password.setError("Please fill something");}
                else if(!Patterns.EMAIL_ADDRESS.matcher(signup_email.getText().toString()).matches()){signup_email.setError("Enter valid Email address");}
                else if (!login_password.getText().toString().equals(login_confirm_password.getText().toString())){
                    Toast.makeText(SignUp.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else{
                    final ProgressDialog mProgressDialog = new ProgressDialog(SignUp.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressDialog.setContentView(R.layout.progress_dialog_layout);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();
                    Call<SignupResponse> call = apiInterface.Signup(signup_username.getText().toString(),signup_email.getText().toString(),login_password.getText().toString());
                    call.enqueue(new Callback<SignupResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SignupResponse> call, @NonNull Response<SignupResponse> response) {
                            SignupResponse res = response.body();
                            List<com.pro.wardrobe.ApiResponse.Signup_Response.Response> list_response = res.getResponse();
                            for (int i= 0;i<list_response.size();i++) {

                                com.pro.wardrobe.ApiResponse.Signup_Response.Response response_ = list_response.get(i);
//                    Log.e("response status",response_.getStatus());

                                if (response_.getStatus().equals("true")) {
                                    mProgressDialog.dismiss();
//                        Log.e("Register", "register" + response.body().toString());
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }else {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"please check your email and verify you account first",Toast.LENGTH_LONG).show();
                                }


                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<SignupResponse> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        });
    }
}
