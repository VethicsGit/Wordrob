package com.pro.wardrobe.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.ChangePassResponse.ChangePassResponse;
import com.pro.wardrobe.ApiResponse.LoginResponse.LoginResponse;
import com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.UpdateDeviceTokenResponse;
import com.pro.wardrobe.ForebaseSupportClasses.Config;
import com.pro.wardrobe.ForebaseSupportClasses.NotificationUtils;
import com.pro.wardrobe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = Login.class.getSimpleName();
    LinearLayout login_signup_btn;
    EditText login_username, login_password;
    Button login_forgot_pass, login_btn_login;
    ImageView login_fb_signin, login_gplus_signin;
    TextView login_in, login_sign;
    APIInterface apiInterface;
    LoginButton login_fb_button;
    private GoogleApiClient mGoogleApiClient;
    private AccessToken mAccessToken;
    private CallbackManager callbackManager;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        login_signup_btn = findViewById(R.id.login_signup_btn);
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        login_forgot_pass = findViewById(R.id.login_forgot_pass);
        login_btn_login = findViewById(R.id.login_btn_login);
        login_fb_signin = findViewById(R.id.login_fb_signin);
        login_gplus_signin = findViewById(R.id.login_gplus_signin);
        login_in = findViewById(R.id.login_in);
        login_sign = findViewById(R.id.login_sign);

        Typeface face = Typeface.createFromAsset(getAssets(), "Roboto_Regular.ttf");
        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Medium.ttf");
        login_in.setTypeface(face);
        login_sign.setTypeface(facebold);

        login_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);

            }
        });

        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (login_username.getText().toString().equals("") && login_password.getText().toString().equals("")) {
                    login_username.setError("Please fill something");
                    login_password.setError("Please fill something");
                } else if (login_username.getText().toString().equals("")) {
                    login_username.setError("Please fill something");
                } else if (login_password.getText().toString().equals("")) {
                    login_password.setError("Please fill something");
                } else if (login_password.getText().toString().length() < 6) {
                    login_password.setError("Password must be contains more than 6 characters");
                } else {
                    final ProgressDialog mProgressDialog = new ProgressDialog(Login.this, R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();

                    Call<LoginResponse> call = apiInterface.signin(login_username.getText().toString(), login_password.getText().toString());
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                            LoginResponse loginResponse = response.body();
                            List<com.pro.wardrobe.ApiResponse.LoginResponse.Response> list_response = loginResponse.getResponse();

                            for (int i = 0; i < list_response.size(); i++) {

                                com.pro.wardrobe.ApiResponse.LoginResponse.Response responsee = list_response.get(i);
//                                Log.e("resnse", responsee.getResponseMsg());

                                if (responsee.getStatus().equals("true")) {
                                    SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("token", responsee.getToken());
                                    editor.putString("screen_code", responsee.getScreenCode());
                                        editor.putString("user_id", responsee.getUserId());
                                    editor.putString("name", responsee.getName());
                                    editor.putString("pass", login_password.getText().toString());
                                    editor.putString("email", responsee.getEmail()).apply();

//                                SharedPreferences preferences=getSharedPreferences("LoginStatus",MODE_PRIVATE);
/*

                                final ProgressDialog mProgressDialog = new ProgressDialog(Login.this,R.style.AppCompatAlertDialogStyle);
                                mProgressDialog.setIndeterminate(false);
                                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                mProgressDialog.setCancelable(false);
                                mProgressDialog.setMessage("Please wait...");
                                mProgressDialog.show();
*/

//                                FirebaseMessaging.getInstance().subscribeToTopic('news');

                                    mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                                        @Override
                                        public void onReceive(Context context, Intent intent) {

                                            // checking for type intent filter
                                            if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                                                // gcm successfully registered
                                                // now subscribe to `global` topic to receive app wide notifications
                                                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                                                displayFirebaseRegId();

                                            } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                                                // new push notification is received

                                                String message = intent.getStringExtra("message");

                                                Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    };

                                    displayFirebaseRegId();
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
            }
        });

//login_fb_button=new LoginButton(getApplicationContext());
        login_fb_button = findViewById(R.id.login_fb_button);
        login_fb_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_fb_button.performClick();
            }
        });
        callbackManager = CallbackManager.Factory.create();
        login_fb_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getUserProfile(mAccessToken);
                Log.e("Access_token", mAccessToken.getToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        login_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        login_gplus_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void revokeAccess() {

        /* Disconnect accounts : It is highly recommended that you provide users that signed in with Google the ability
        to disconnect their Google account from your app. If the user deletes their account,
        you must delete the information that your app obtained from the Google APIs.

        Note. clear user records from all storage places, i haven't stored any user records so i am just updating the UI.
        */

        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
            }
        });
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Log.e("fb_name", object.getString("name"));
                    Toast.makeText(Login.this, "Facebook Login Name: " + object.getString("name"), Toast.LENGTH_SHORT).show();
                    //You can fetch user info like this…
                    //object.getJSONObject(“picture”).
//                            getJSONObject("data").getString("url");
                    //object.getString(“name”);
                    //object.getString(“email”));
                    //object.getString(“id”));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Toast.makeText(this, "result status code " + result.getStatus().getStatusCode(), Toast.LENGTH_SHORT).show();
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfolly, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("Sign_in_name", acct.getDisplayName());
            Log.e("Profile_Name :", acct.getDisplayName() + "\nEmail : " + acct.getEmail() + "\nFamily Name :" + acct.getFamilyName() + "\n Given Name :" + acct.getGivenName() + "\n ID :" + acct.getId());

            Toast.makeText(Login.this, "Google plus Login Name: " + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()

            /*  Picasso.with(SignInActivity.this)
                        .load(acct.getPhotoUrl())
                        .into(ivProfileImage);*/
          /*  if(acct.getPhotoUrl() != noll)
                new LoadProfileImage(imgProfilePic).execute(acct.getPhotoUrl().toString());

            updateUI(true);*/
        } else {
            Toast.makeText(this, "SUCESS IS FALSE", Toast.LENGTH_SHORT).show();
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {

            SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<UpdateDeviceTokenResponse> call1 = apiInterface.UpdateDeviceToken(preferences.getString("user_id", ""), "android", regId, Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID), preferences.getString("token", ""));
            call1.enqueue(new Callback<UpdateDeviceTokenResponse>() {
                @Override
                public void onResponse(@NonNull Call<UpdateDeviceTokenResponse> call, @NonNull Response<UpdateDeviceTokenResponse> response) {
                    UpdateDeviceTokenResponse changePassResponse = response.body();
                    List<com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.Response> responseList = changePassResponse.getResponse();
                    com.pro.wardrobe.ApiResponse.UpdateDeviceTokenResponse.Response res = responseList.get(0);
                    if (res.getStatus().equals("true")) {

                        SharedPreferences preferences = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("firebase_id",regId).apply();

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, res.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<UpdateDeviceTokenResponse> call, @NonNull Throwable t) {

                }
            });

        } else {
            Toast.makeText(this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();
            displayFirebaseRegId();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
