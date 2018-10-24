package com.pro.wardrobe.Activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.R;


public class OrderHistory extends Activity {


    TextView title;
    @Override
    public void onCreate( Bundle savedInstanceState,  PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_order_history);


        title.setText("ORDER HISTORY");
    }


    /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


    }*/
}
