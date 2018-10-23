package com.pro.wardrobe.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.LoginResponse.LoginResponse;
import com.pro.wardrobe.ApiResponse.SocialSiginResponse.ResponseSocialSignin;
import com.pro.wardrobe.ForebaseSupportClasses.Config;
import com.pro.wardrobe.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView image;
    TextView splash_text;

    APIInterface apiInterface;

    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.pro.wardrobe", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("facebook_KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
//       KEYHASH:  T2CrLvxU9jJ8Ug5BNPHSuDNuczA=
//      SHA1:  13:4D:75:DE:FC:D7:90:51:21:01:28:12:3B:9A:34:F5:A8:72:91:AD

        setContentView(R.layout.activity_splash_screen);
        image = findViewById(R.id.splash_image);
        splash_text = findViewById(R.id.splash_text);
        Fabric.with(this, new Crashlytics());

        Typeface face = Typeface.createFromAsset(getAssets(), "Philosopher_Bold.ttf");

        splash_text.setTypeface(face);
        final SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)) {

                   /* if (preferences.contains("email")) {
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }*/
                    apiInterface = APIClient.getClient().create(APIInterface.class);

                   if (preferences.getString("type","").equals("internal")){

                       final ProgressDialog mProgressDialog = new ProgressDialog(SplashScreen.this, R.style.AppCompatAlertDialogStyle);
                       mProgressDialog.setIndeterminate(false);
                       mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                       mProgressDialog.setCancelable(false);
                       mProgressDialog.setMessage("Please wait...");
                       mProgressDialog.show();
                       final SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                       Call<LoginResponse> call = apiInterface.signin(preferences.getString("email",""), preferences.getString("pass",""));
                       call.enqueue(new Callback<LoginResponse>() {
                           @Override
                           public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                               LoginResponse loginResponse = response.body();
                               List<com.pro.wardrobe.ApiResponse.LoginResponse.Response> list_response = loginResponse.getResponse();

                               for (int i = 0; i < list_response.size(); i++) {

                                   com.pro.wardrobe.ApiResponse.LoginResponse.Response responsee = list_response.get(i);
//                                Log.e("resnse", responsee.getResponseMsg());

                                   if (responsee.getStatus().equals("true")) {

                                       SharedPreferences.Editor editor = preferences.edit();
                                       editor.putString("type", "internal");
                                       editor.putString("token", responsee.getToken());
                                       editor.putString("screen_code", responsee.getScreenCode());
                                       editor.putString("user_id", responsee.getUserId());
                                       editor.putString("name", responsee.getName());
                                       editor.putString("email", responsee.getEmail()).apply();

                                       Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(intent);

                                   } else {
                                       Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                                   }

                                   mProgressDialog.dismiss();

                               }
                           }

                           @Override
                           public void onFailure(Call<LoginResponse> call, Throwable t) {

                           }
                       });

                   }
                   else if (preferences.getString("type","").equals("social")){

                       final ProgressDialog mProgressDialog = new ProgressDialog(SplashScreen.this, R.style.AppCompatAlertDialogStyle);
                       mProgressDialog.setIndeterminate(false);
                       mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                       mProgressDialog.setCancelable(false);
                       mProgressDialog.setMessage("Please wait...");
                       mProgressDialog.show();
                       Call<ResponseSocialSignin> call = apiInterface.check_social_id(preferences.getString("media_type",""), preferences.getString("media_id",""));

                       call.enqueue(new Callback<ResponseSocialSignin>() {
                           @Override
                           public void onResponse(Call<ResponseSocialSignin> call, Response<ResponseSocialSignin> response) {

                               ResponseSocialSignin responseSocialSignin = response.body();
                               List<com.pro.wardrobe.ApiResponse.SocialSiginResponse.Response> responseList = responseSocialSignin.getResponse();
                               com.pro.wardrobe.ApiResponse.SocialSiginResponse.Response response1 = responseList.get(0);

                               if (response1.getStatus().equals("true")) {
                                   SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                                   SharedPreferences.Editor editor = preferences.edit();
                                   editor.putString("type", "social");
                                   editor.putString("token", response1.getToken());
                                   editor.putString("screen_code", response1.getScreenCode());
                                   editor.putString("user_id", response1.getUserId());

                                   editor.putString("name", response1.getName());
                                   editor.putString("email", response1.getEmail()).apply();



                                   mProgressDialog.dismiss();
                               }
                           }
                           @Override
                           public void onFailure(Call<ResponseSocialSignin> call, Throwable t) {

                           }
                       });

                   }else {
                       Intent intent = new Intent(getApplicationContext(), Login.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                   }


                }else{
PermissionDialog();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        Log.i("SplashPermissionTAG", "checkSelfPermission " + permission + " " + requestCode);
        if (PermissionChecker.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

           /* ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);*/
            return false;
        }
        return true;
    }

    public void PermissionDialog(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            Intent i=new Intent(getApplicationContext(),SplashScreen.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            PermissionDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }
}



