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

public class Confirm extends AppCompatActivity {

    Button confirm_btn_placeorder;
    ImageView confirm_back;
    TextView confirm_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        confirm_btn_placeorder=findViewById(R.id.confirm_btn_placeorder);
        confirm_title=findViewById(R.id.confirm_title);

        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Bold.ttf");
        confirm_title.setTypeface(facebold);


        confirm_back=findViewById(R.id.confirm_back);
        confirm_btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),OrderPlaced.class);
                startActivity(intent);
            }
        });

        confirm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
