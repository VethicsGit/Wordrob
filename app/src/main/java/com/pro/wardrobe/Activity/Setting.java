package com.pro.wardrobe.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.ProfileResponse.ProfileResponse;
import com.pro.wardrobe.R;
import com.suke.widget.SwitchButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting extends AppCompatActivity {

    RelativeLayout setting_profile;
    RelativeLayout setting_change_pwd;
    RelativeLayout setting_help;
    RelativeLayout setting_terms;
    RelativeLayout setting_privacy;

    SwitchButton setting_switch_push;
    SwitchButton setting_switch_backinstock;
    SwitchButton setting_switch_promotions;

    TextView title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);


//        title.setText("Settings");
        setting_profile=findViewById(R.id.setting_profile);
        setting_change_pwd=findViewById(R.id.setting_change_pwd);
        setting_help=findViewById(R.id.setting_help);
        setting_terms=findViewById(R.id.setting_terms);
        setting_privacy=findViewById(R.id.setting_privacy);
        setting_switch_push=findViewById(R.id.setting_switch_push);
        setting_switch_backinstock=findViewById(R.id.setting_switch_backinstock);
        setting_switch_promotions=findViewById(R.id.setting_switch_promotions);

        setting_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                title.setText("Profile");
//                ft.addToBackStack("Dashboard");
//                ft.replace(R.id.viewpager, new Profile()).commit();

                Intent intent=new Intent(getApplicationContext(),Profile_.class);
                startActivity(intent);
            }
        });
        setting_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(intent);
            }
        });
        setting_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FAQs.class);
                startActivity(intent);
            }
        });
        setting_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TermsAndCondition.class);
                startActivity(intent);
            }
        });
        setting_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);
            }
        });


        final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        Call<ProfileResponse> call = apiInterface.user_profile(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                ProfileResponse profileResponse = response.body();
                List<com.pro.wardrobe.ApiResponse.ProfileResponse.Response> resList = profileResponse.getResponse();
                com.pro.wardrobe.ApiResponse.ProfileResponse.Response res = resList.get(0);
                if (res.getStatus().equals("true")) {
                    mProgressDialog.dismiss();
                    com.pro.wardrobe.ApiResponse.ProfileResponse.Profile profile = res.getProfile();
                    if (profile.getNotificationStatus().equals("1"))
                        setting_switch_push.setChecked(true);
                    else setting_switch_push.setChecked(false);


                    if (profile.getInstockNotification().equals("1"))
                        setting_switch_backinstock.setChecked(true);
                    else setting_switch_backinstock.setChecked(false);


                    if (profile.getPromotionNotification().equals("1"))
                        setting_switch_promotions.setChecked(true);
                    else setting_switch_promotions.setChecked(false);

                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
            }
        });


        setting_switch_push.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                String status;
                if (isChecked) status= String.valueOf(1);
                else status= String.valueOf(0);

                Call<ProfileResponse> call = apiInterface.update_user_profile_statusnot(preferences.getString("token",""),preferences.getString("user_id",""),status);
                call.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                        ProfileResponse profileResponse = response.body();
                        List<com.pro.wardrobe.ApiResponse.ProfileResponse.Response> resList = profileResponse.getResponse();
                        com.pro.wardrobe.ApiResponse.ProfileResponse.Response res = resList.get(0);
                        if (res.getStatus().equals("true")) {
                            mProgressDialog.dismiss();
                            com.pro.wardrobe.ApiResponse.ProfileResponse.Profile profile = res.getProfile();


                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {

                    }
                });


            }
        });
        setting_switch_backinstock.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                String status;
                if (isChecked) status= String.valueOf(1);
                else status= String.valueOf(0);

                Call<ProfileResponse> call = apiInterface.update_user_profile_instocknot(preferences.getString("token",""),preferences.getString("user_id",""),status);
                call.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                        ProfileResponse profileResponse = response.body();
                        List<com.pro.wardrobe.ApiResponse.ProfileResponse.Response> resList = profileResponse.getResponse();
                        com.pro.wardrobe.ApiResponse.ProfileResponse.Response res = resList.get(0);
                        if (res.getStatus().equals("true")) {
                            mProgressDialog.dismiss();
                            com.pro.wardrobe.ApiResponse.ProfileResponse.Profile profile = res.getProfile();


                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {

                    }
                });

            }
        });
        setting_switch_promotions.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                String status;
                if (isChecked) status= String.valueOf(1);
                else status= String.valueOf(0);

                Call<ProfileResponse> call = apiInterface.update_user_profile_promotionnot(preferences.getString("token",""),preferences.getString("user_id",""),status);
                call.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                        ProfileResponse profileResponse = response.body();
                        List<com.pro.wardrobe.ApiResponse.ProfileResponse.Response> resList = profileResponse.getResponse();
                        com.pro.wardrobe.ApiResponse.ProfileResponse.Response res = resList.get(0);
                        if (res.getStatus().equals("true")) {
                            mProgressDialog.dismiss();
                            com.pro.wardrobe.ApiResponse.ProfileResponse.Profile profile = res.getProfile();


                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {

                    }
                });

            }
        });



    }
}
