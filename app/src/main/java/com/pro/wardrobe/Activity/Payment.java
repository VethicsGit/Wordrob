package com.pro.wardrobe.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.R;

public class Payment extends AppCompatActivity {

    ImageView payment_back;
    Button payment_btn_next;
    TextView payment_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        payment_back=findViewById(R.id.payment_back);
        payment_title=findViewById(R.id.payment_title);

        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Bold.ttf");
        payment_title.setTypeface(facebold);

        payment_btn_next=findViewById(R.id.payment_btn_next);
        payment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });

        payment_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Confirm.class);
                startActivity(intent);
            }
        });


    }
}
