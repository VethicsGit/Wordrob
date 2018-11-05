package com.pro.wardrobe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.ContactUsResponse.ContactUsResponse;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs_ extends Activity {

    APIInterface apiInterface;
    EditText contactus_name;
    EditText contactus_email;
    EditText contactus_number;
    EditText contactus_message;

    Button contactus_btn_submit;
    TextView title;

    ImageView contactus_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        title=findViewById(R.id.title);
        contactus_back=findViewById(R.id.bottomnav_toolbar_back);
        title.setText("CONTACT US");
        contactus_name=findViewById(R.id.contactus_name);
        contactus_email=findViewById(R.id.contactus_email);
        contactus_number=findViewById(R.id.contactus_number);
        contactus_message=findViewById(R.id.contactus_message);
        contactus_btn_submit=findViewById(R.id.contactus_btn_submit);
//        ((Dashboard)getApplicationContext()).toggle(5);

        contactus_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        contactus_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (contactus_name.getText().toString().equals("")){contactus_name.setError("Please fill something");}
                else if (contactus_email.getText().toString().equals("")){contactus_email.setError("Please fill something");}
                else if (contactus_message.getText().toString().equals("")){contactus_message.setError("Please fill something");}
                else if (!Patterns.EMAIL_ADDRESS.matcher(contactus_email.getText().toString()).matches()){
                    contactus_email.setError("Enter valid Email");
                }else if (!contactus_number.getText().toString().equals("") && !Patterns.PHONE.matcher(contactus_number.getText().toString()).matches()){
                    contactus_number.setError("Enter valid Phone number");
                }else {
                    final ProgressDialog mProgressDialog = new ProgressDialog(ContactUs_.this, R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();
                    apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<ContactUsResponse> call = apiInterface.ContactUs(contactus_name.getText().toString(),contactus_email.getText().toString(),contactus_message.getText().toString(),contactus_number.getText().toString());
                    call.enqueue(new Callback<ContactUsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ContactUsResponse> call, @NonNull Response<ContactUsResponse> response) {
                          mProgressDialog.dismiss();
                            ContactUsResponse contactUsResponse=response.body();
                            List<com.pro.wardrobe.ApiResponse.ContactUsResponse.Response> resList=contactUsResponse.getResponse();
                            com.pro.wardrobe.ApiResponse.ContactUsResponse.Response res=resList.get(0);
                            if (res.getStatus().equals("true")){
/*
                                new AlertDialog.Builder(getApplicationContext())
                                        .setTitle("Contact Us")
                                        .setMessage(res.getResponseMsg())
                                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();*/

                                Toast.makeText(ContactUs_.this, res.getResponseMsg(), Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(getApplicationContext(), res.getResponseMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<ContactUsResponse> call, @NonNull Throwable t
                        ) {

                        }
                    });

                }

//                getActivity().onBackPressed();
            }
        });

    }
}
