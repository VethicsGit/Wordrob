package com.pro.wardrobe.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.R;

public class OrderPlaced extends AppCompatActivity {

    Button orderplaced_btn_continue;
    ImageView placeorder_back;
    TextView orderplaced_title,placedOrder_txt;

    int orderId;

    public OrderPlaced(int orderId) {
        this.orderId = orderId;
    }


    public OrderPlaced() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);


        orderplaced_btn_continue=findViewById(R.id.orderplaced_btn_continue);
        orderplaced_title=findViewById(R.id.orderplaced_title);
        placedOrder_txt=findViewById(R.id.placedOrder_txt);

//            Toast.makeText(this, "order id "+getIntent().getIntExtra("orderId",0), Toast.LENGTH_SHORT).show();

        placedOrder_txt.setText("Congratulations your order placed successfully\\nyour order code is #"+getIntent().getIntExtra("orderId",0));


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
