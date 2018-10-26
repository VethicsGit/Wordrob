package com.pro.wardrobe.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CountryResponse.CountryList;
import com.pro.wardrobe.ApiResponse.CountryResponse.CountryResponse;
import com.pro.wardrobe.R;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shipping extends AppCompatActivity {

    ImageView shipping_back;
    Button shipping_btn_next;
    TextView shipping_title;
    TextView shipping_country_spn, shipping_country_spn_id, shipping_ship_country_spn_id;
    TextView shipping_ship_country_spn;
    EditText shipping_ship_region_spn;
    EditText shipping_region_spn;

    EditText shipping_firstname;
    EditText shipping_lastname;
    EditText shipping_phno;
    EditText shipping_city;
    EditText shipping_address1;

    EditText shipping_ship_firstname;
    EditText shipping_ship_lastname;
    EditText shipping_ship_phno;
    EditText shipping_ship_city;
    EditText shipping_ship_address1;

    SwitchButton setting_switch_push;

    CardView shipping_add_root;
    String region = "";
    String ship_region = "";
    String bill_country = "";
    String ship_country = "";

    Context c;
    APIInterface apiInterface;
    MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        shipping_back = findViewById(R.id.shipping_back);
        shipping_title = findViewById(R.id.shipping_title);
        shipping_ship_country_spn_id = findViewById(R.id.shipping_ship_country_spn_id);
        shipping_country_spn_id = findViewById(R.id.shipping_country_spn_id);

        c = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);

        shipping_firstname = findViewById(R.id.shipping_firstname);
        shipping_lastname = findViewById(R.id.shipping_lastname);
        shipping_phno = findViewById(R.id.shipping_phno);
        shipping_city = findViewById(R.id.shipping_city);
        shipping_address1 = findViewById(R.id.shipping_address1);

        shipping_ship_firstname = findViewById(R.id.shipping_ship_firstname);
        shipping_ship_lastname = findViewById(R.id.shipping_ship_lastname);
        shipping_ship_phno = findViewById(R.id.shipping_ship_phno);
        shipping_ship_city = findViewById(R.id.shipping_ship_city);
        shipping_ship_address1 = findViewById(R.id.shipping_ship_address1);


        setting_switch_push = findViewById(R.id.setting_switch_push);
        shipping_add_root = findViewById(R.id.shipping_add_root);

        setting_switch_push.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    shipping_add_root.setVisibility(View.GONE);
                } else {
                    shipping_add_root.setVisibility(View.VISIBLE);
                }
            }
        });

        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Bold.ttf");
        shipping_title.setTypeface(facebold);

        shipping_btn_next = findViewById(R.id.shipping_btn_next);
        shipping_country_spn = findViewById(R.id.shipping_country_spn);
        shipping_ship_country_spn = findViewById(R.id.shipping_ship_country_spn);
        shipping_region_spn = findViewById(R.id.shipping_region_spn);
        shipping_ship_region_spn = findViewById(R.id.shipping_ship_region_spn);


        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.Country, R.layout.spinner_textview);
//        shipping_country_spn.setAdapter(aa);
        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);

        shipping_country_spn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                Call<CountryResponse> callCountry = apiInterface.country_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
                callCountry.enqueue(new Callback<CountryResponse>() {
                    @Override
                    public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                        CountryResponse countryResponse = response.body();
                        List<com.pro.wardrobe.ApiResponse.CountryResponse.Response> resList = countryResponse.getResponse();
                        com.pro.wardrobe.ApiResponse.CountryResponse.Response res = resList.get(0);
                        if (res.getStatus().equals("true")) {
                            final List<CountryList> countryList = res.getCountryList();

                            final List<String> country = new ArrayList<>();
                            for (int i = 0; i < countryList.size(); i++) {
                                country.add(countryList.get(i).getName());
                            }


                            dialog = new MaterialDialog.Builder(c).title("Country List").items(country).itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    String countryId = countryList.get(which).getCountriesId();
                                    bill_country = countryId;
                                    Toast.makeText(Shipping.this, "country id " + countryId, Toast.LENGTH_SHORT).show();
                                    shipping_country_spn_id.setText(countryId);
//                                    country_selected_position=which;
                                    shipping_country_spn.setText(text);

                                }
                            }).show();
                            for (int x = 0; x < countryList.size(); x++) {

                                if (country.get(x).equals(shipping_country_spn.getText().toString())) {

//                                    country_selected_position = x;
                                }
                            /*    if (country_selected_position==0){
                                    dialog.setSelectedIndex(0);
                                    country_selected_position=0;
                                }else*/
//                                dialog.setSelectedIndex(country_selected_position);
                            }
                            mProgressDialog.dismiss();
                        } else {
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryResponse> call, Throwable t) {
                        mProgressDialog.dismiss();
                    }
                });
            }
        });


        shipping_ship_country_spn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                Call<CountryResponse> callCountry = apiInterface.country_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
                callCountry.enqueue(new Callback<CountryResponse>() {
                    @Override
                    public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                        CountryResponse countryResponse = response.body();
                        List<com.pro.wardrobe.ApiResponse.CountryResponse.Response> resList = countryResponse.getResponse();
                        com.pro.wardrobe.ApiResponse.CountryResponse.Response res = resList.get(0);
                        if (res.getStatus().equals("true")) {
                            final List<CountryList> countryList = res.getCountryList();

                            final List<String> country = new ArrayList<>();
                            for (int i = 0; i < countryList.size(); i++) {
                                country.add(countryList.get(i).getName());
                            }


                            dialog = new MaterialDialog.Builder(c).title("Country List").items(country).itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    String countryId = countryList.get(which).getCountriesId();
                                    Toast.makeText(Shipping.this, "country id " + countryId, Toast.LENGTH_SHORT).show();
                                    ship_country = countryId;
                                    shipping_ship_country_spn_id.setText(countryId);
//                                    country_selected_position=which;
                                    shipping_ship_country_spn.setText(text);

                                }
                            }).show();
                            for (int x = 0; x < countryList.size(); x++) {

                                if (country.get(x).equals(shipping_ship_country_spn.getText().toString())) {

//                                    country_selected_position = x;
                                }
                            /*    if (country_selected_position==0){
                                    dialog.setSelectedIndex(0);
                                    country_selected_position=0;
                                }else*/
//                                dialog.setSelectedIndex(country_selected_position);
                            }
                            mProgressDialog.dismiss();
                        } else {
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryResponse> call, Throwable t) {
                        mProgressDialog.dismiss();
                    }
                });
            }
        });


        ArrayAdapter<CharSequence> a = ArrayAdapter.createFromResource(this, R.array.region, R.layout.spinner_textview);
//        shipping_region_spn.setAdapter(a);
        shipping_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        shipping_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (shipping_firstname.getText().toString().equals("")) {
                    shipping_firstname.setError("Fill this field");
                } else if (shipping_lastname.getText().toString().equals("")) {
                    shipping_lastname.setError("Fill this field");
                } else if (shipping_phno.getText().toString().equals("")) {
                    shipping_phno.setError("Fill this field");
                } else if (shipping_phno.getText().toString().equals("")) {
                    shipping_phno.setError("Fill this field");
                } else if (shipping_phno.getText().toString().length() > 10) {
                    shipping_phno.setError("Fill valid phone number");
                } else if (shipping_city.getText().toString().equals("")) {
                    shipping_city.setError("Fill this field");
                } else if (shipping_address1.getText().toString().equals("")) {
                    shipping_address1.setError("Fill this field");
                } else if (!setting_switch_push.isChecked() && shipping_ship_firstname.getText().toString().equals("")) {
                    shipping_ship_firstname.setError("Fill this field");
                } else if (!setting_switch_push.isChecked() && shipping_ship_lastname.getText().toString().equals("")) {
                    shipping_ship_lastname.setError("Fill this field");
                } else if (!setting_switch_push.isChecked() && shipping_ship_phno.getText().toString().equals("")) {
                    shipping_ship_phno.setError("Fill this field");
                } else if (!setting_switch_push.isChecked() && shipping_ship_phno.getText().toString().equals("")) {
                    shipping_ship_phno.setError("Fill this field");
                } else if (!setting_switch_push.isChecked() && shipping_ship_phno.getText().toString().length() > 10) {
                    shipping_ship_phno.setError("Fill valid phone number");
                } else if (!setting_switch_push.isChecked() && shipping_ship_city.getText().toString().equals("")) {
                    shipping_ship_city.setError("Fill this field");
                } else if (!setting_switch_push.isChecked() && shipping_ship_address1.getText().toString().equals("")) {
                    shipping_ship_address1.setError("Fill this field");
                } else {

                    SharedPreferences billingPref = getSharedPreferences("billingadd", MODE_PRIVATE);
                    SharedPreferences.Editor editor = billingPref.edit();
                    editor.putString("firstname", shipping_firstname.getText().toString());
                    editor.putString("lastname", shipping_lastname.getText().toString());
                    editor.putString("phone", shipping_phno.getText().toString());
                    editor.putString("city", shipping_city.getText().toString());
                    editor.putString("address", shipping_address1.getText().toString());
                    editor.putString("country", shipping_country_spn.getText().toString());
                    editor.putString("country_id", shipping_country_spn_id.getText().toString());
                    editor.putString("region", shipping_region_spn.getText().toString()).apply();

                    SharedPreferences shippingPref = getSharedPreferences("shippingadd", MODE_PRIVATE);
                    SharedPreferences.Editor editorship = shippingPref.edit();
                    if (shipping_add_root.getVisibility() == View.VISIBLE) {

                        editorship.putString("firstname", shipping_ship_firstname.getText().toString());
                        editorship.putString("lastname", shipping_ship_lastname.getText().toString());
                        editorship.putString("phone", shipping_ship_phno.getText().toString());
                        editorship.putString("city", shipping_ship_city.getText().toString());
                        editorship.putString("address", shipping_ship_address1.getText().toString());
                        editorship.putString("country", shipping_ship_country_spn.getText().toString());
                        editorship.putString("country_id", shipping_ship_country_spn_id.getText().toString());
                        editorship.putString("region", shipping_ship_region_spn.getText().toString()).apply();
                    } else {
                        editorship.putString("firstname", shipping_firstname.getText().toString());
                        editorship.putString("lastname", shipping_lastname.getText().toString());
                        editorship.putString("phone", shipping_phno.getText().toString());
                        editorship.putString("city", shipping_city.getText().toString());
                        editorship.putString("address", shipping_address1.getText().toString());
                        editorship.putString("country", shipping_country_spn.getText().toString());
                        editorship.putString("country_id", shipping_country_spn_id.getText().toString());
                        editorship.putString("region", shipping_region_spn.getText().toString()).apply();
                    }
                    Intent intentfav = new Intent(getApplicationContext(), Payment.class);
                    startActivity(intentfav);
                }


            }
        });
        SharedPreferences billingPref = getSharedPreferences("billingadd", MODE_PRIVATE);
        if (billingPref.contains("firstname")) {
            shipping_firstname.setText(billingPref.getString("firstname", ""));
            shipping_lastname.setText(billingPref.getString("lastname", ""));
            shipping_phno.setText(billingPref.getString("phone", ""));
            shipping_city.setText(billingPref.getString("city", ""));
            shipping_address1.setText(billingPref.getString("address", ""));
            shipping_country_spn.setText(billingPref.getString("country", ""));
            shipping_country_spn_id.setText(billingPref.getString("country_id", ""));
            shipping_region_spn.setText(billingPref.getString("region", ""));
        }
        SharedPreferences shippingPref = getSharedPreferences("shippingadd", MODE_PRIVATE);
        if (shippingPref.contains("firstname")) {
            shipping_ship_firstname.setText(shippingPref.getString("firstname", ""));
            shipping_ship_lastname.setText(shippingPref.getString("lastname", ""));
            shipping_ship_phno.setText(shippingPref.getString("phone", ""));
            shipping_ship_city.setText(shippingPref.getString("city", ""));
            shipping_ship_address1.setText(shippingPref.getString("address", ""));
            shipping_ship_country_spn.setText(shippingPref.getString("country", ""));
            shipping_ship_country_spn_id.setText(shippingPref.getString("country_id", ""));
            shipping_ship_region_spn.setText(shippingPref.getString("region", ""));
        }
    }
}
