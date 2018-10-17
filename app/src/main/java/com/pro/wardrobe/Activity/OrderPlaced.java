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

public class OrderPlaced extends AppCompatActivity {

    Button orderplaced_btn_continue;
    ImageView placeorder_back;
    TextView orderplaced_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        orderplaced_btn_continue=findViewById(R.id.orderplaced_btn_continue);
        orderplaced_title=findViewById(R.id.orderplaced_title);

        Typeface facebold = Typeface.createFromAsset(getAssets(), "Roboto_Bold.ttf");
        orderplaced_title.setTypeface(facebold);

        placeorder_back=findViewById(R.id.placeorder_back);
        orderplaced_btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent);
            }
        });

        placeorder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
