package com.pro.wardrobe.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.Adapter.mybag_list_Adapter;
import com.pro.wardrobe.R;

import java.util.HashMap;
import java.util.Map;

public class Activty_MyBag extends AppCompatActivity {

    ImageView mybag_back;
    Button mybag_checkout;
    EditText mybag_promo_edit;
    RecyclerView mybag_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty__my_bag);

        TextView mybag_title=findViewById(R.id.mybag_title);
        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        mybag_title.setTypeface(facebold);

        mybag_back=findViewById(R.id.mybag_back);
        mybag_recycler=findViewById(R.id.mybag_recycler);
        mybag_checkout=findViewById(R.id.mybag_checkout);
        mybag_promo_edit=findViewById(R.id.mybag_promo_edit);
        mybag_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mybag_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Shipping.class);
                startActivity(i);
            }
        });
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mybag_recycler.setLayoutManager(manager);
        mybag_list_Adapter mybag_list_adapter=new mybag_list_Adapter(getApplicationContext());
        mybag_recycler.setAdapter(mybag_list_adapter);
/*mybag_recycler.setNestedScrollingEnabled(false);
mybag_recycler.setHasFixedSize(true);*/

    }


/*    public void HeightChange(int position, int height) {
        itemHeight.put(position, height);
        sumHeight = SumHashItem (itemHeight);

        float density = activity.getResources().getDisplayMetrics().density;
        float viewHeight = sumHeight * density;
        review_recyclerView.getLayoutParams().height = (int) sumHeight;

        int i = review_recyclerView.getLayoutParams().height;
    }

    int SumHashItem (HashMap<Integer, Integer> hashMap) {
        int sum = 0;

        for(Map.Entry<Integer, Integer> myItem: hashMap.entrySet())  {
            sum += myItem.getValue();
        }

        return sum;
    }*/
}
