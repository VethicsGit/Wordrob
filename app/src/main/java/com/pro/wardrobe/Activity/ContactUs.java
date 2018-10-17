package com.pro.wardrobe.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.ContactUsResponse.ContactUsResponse;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs extends Fragment {

    APIInterface apiInterface;
    EditText contactus_name;
    EditText contactus_email;
    EditText contactus_number;
    EditText contactus_message;

    Button contactus_btn_submit;

    ImageView contactus_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_contact_us,container,false);
        ((Dashboard)getActivity()).toggle(2);
        contactus_name=view.findViewById(R.id.contactus_name);
        contactus_email=view.findViewById(R.id.contactus_email);
        contactus_number=view.findViewById(R.id.contactus_number);
        contactus_message=view.findViewById(R.id.contactus_message);
        contactus_btn_submit=view.findViewById(R.id.contactus_btn_submit);
        ((Dashboard)getActivity()).toggle(5);
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
                    apiInterface =APIClient.getClient().create(APIInterface.class);
                    Call<ContactUsResponse> call = apiInterface.ContactUs(contactus_name.getText().toString(),contactus_email.getText().toString(),contactus_message.getText().toString(),contactus_number.getText().toString());
                    call.enqueue(new Callback<ContactUsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ContactUsResponse> call, @NonNull Response<ContactUsResponse> response) {
                            ContactUsResponse contactUsResponse=response.body();
                            List<com.pro.wardrobe.ApiResponse.ContactUsResponse.Response> resList=contactUsResponse.getResponse();
                            com.pro.wardrobe.ApiResponse.ContactUsResponse.Response res=resList.get(0);
                            if (res.getStatus().equals("true")){

                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Contact Us")
                                        .setMessage(res.getResponseMsg())
                                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                getActivity().onBackPressed();
                                            }
                                        })
                                        .show();


                            }else {
                                Toast.makeText(getActivity(), res.getResponseMsg(), Toast.LENGTH_SHORT).show();
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
        return view;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        // apiInterface =APIClient.getClient().create(APIInterface.class);
        contactus_name=findViewById(R.id.contactus_name);
        contactus_email=findViewById(R.id.contactus_email);
        contactus_number=findViewById(R.id.contactus_number);
        contactus_message=findViewById(R.id.contactus_message);
        contactus_btn_submit=findViewById(R.id.contactus_btn_submit);
        contactus_back=findViewById(R.id.contactus_back);

//        contactus_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        contactus_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               *//* if (contactus_name.getText().toString().equals("")){contactus_name.setError("Please fill something");}
                else if (contactus_email.getText().toString().equals("")){contactus_email.setError("Please fill something");}
                else if (contactus_message.getText().toString().equals("")){contactus_message.setError("Please fill something");}
else if (!Patterns.EMAIL_ADDRESS.matcher(contactus_email.getText().toString()).matches()){
                    contactus_email.setError("Enter valid Email");
                }else if (!contactus_number.getText().toString().equals("") && !Patterns.PHONE.matcher(contactus_number.getText().toString()).matches()){
                    contactus_number.setError("Enter valid Phone number");
                }else {
                    Call<String> call = apiInterface.doGetListResources();
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }*//*

                onBackPressed();
            }
        });


    }*/
}
