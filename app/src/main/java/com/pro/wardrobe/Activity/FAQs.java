package com.pro.wardrobe.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.R;

public class FAQs extends AppCompatActivity {

    ImageView faq_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        ((TextView)findViewById(R.id.faq_title)).setTypeface(facebold);


        faq_back=findViewById(R.id.faq_back);
        faq_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
