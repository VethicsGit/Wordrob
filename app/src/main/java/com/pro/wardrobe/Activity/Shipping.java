package com.pro.wardrobe.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pro.wardrobe.R;

public class Shipping extends AppCompatActivity {

    ImageView shipping_back;
    Button shipping_btn_next;
    TextView shipping_title;
    Spinner shipping_country_spn,shipping_region_spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        shipping_back=findViewById(R.id.shipping_back);
        shipping_title=findViewById(R.id.shipping_title);

        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Bold.ttf");
        shipping_title.setTypeface(facebold);

        shipping_btn_next=findViewById(R.id.shipping_btn_next);
        shipping_country_spn=findViewById(R.id.shipping_country_spn);
        shipping_region_spn=findViewById(R.id.shipping_region_spn);




        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.Country, R.layout.spinner_textview);
        shipping_country_spn.setAdapter(aa);

        ArrayAdapter<CharSequence> a = ArrayAdapter.createFromResource(this, R.array.region, R.layout.spinner_textview);
        shipping_region_spn.setAdapter(a);
        shipping_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        shipping_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentfav = new Intent(getApplicationContext(), Payment.class);
                startActivity(intentfav);
            }
        });
    }
}
