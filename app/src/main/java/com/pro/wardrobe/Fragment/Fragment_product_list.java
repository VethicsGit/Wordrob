package com.pro.wardrobe.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pro.wardrobe.Activity.Activty_MyBag;
import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Activity.Filter;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.Adapter.Designers_Recyclarview_adapter;
import com.pro.wardrobe.Adapter.ProductList_Adapter;
import com.pro.wardrobe.R;

public class Fragment_product_list extends AppCompatActivity {

    TextView designer_filter;
    ImageView designers_list_filter;
    ImageView designers_grid_filter;
    RecyclerView prolist_recycler;
    TextView title,prolist_title;

    public Fragment_product_list() {
    }

    @SuppressLint("ValidFragment")
    public Fragment_product_list(TextView title) {
        this.title = title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_list);
        designer_filter = findViewById(R.id.product_filter);
        designers_list_filter = findViewById(R.id.product_list_filter);
        designers_grid_filter = findViewById(R.id.product_grid_filter);
        prolist_title = findViewById(R.id.prolist_title);
        Typeface facebold =Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        prolist_title.setTypeface(facebold);

        ImageView mybag = findViewById(R.id.prolist_Mybag);
        ImageView dashboard_search = findViewById(R.id.prolist_search);
        ImageView dashboard_back = findViewById(R.id.prolist_back);

        dashboard_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dashboard_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mybag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                startActivity(intentbag);
            }
        });

        designer_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Filter.class);
                startActivity(i);
            }
        });

        prolist_recycler = findViewById(R.id.prolist_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        prolist_recycler.setLayoutManager(gridLayoutManager);
        ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 0);
        prolist_recycler.setAdapter(adapter);

        prolist_recycler.setNestedScrollingEnabled(false);

        designers_grid_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(gridLayoutManager);
                ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 0);
                prolist_recycler.setAdapter(adapter);
            }
        });
        designers_list_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(manager);
                ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 1);
                prolist_recycler.setAdapter(adapter);
            }
        });
    }
/*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_product_list,container,false);
        ((Dashboard)getApplicationContext()).toggle(0);
        designer_filter = view.findViewById(R.id.product_filter);
        designers_list_filter = view.findViewById(R.id.product_list_filter);
        designers_grid_filter = view.findViewById(R.id.product_grid_filter);

        designer_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Filter.class);
                startActivity(i);
            }
        });




        prolist_recycler=view.findViewById(R.id.prolist_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        prolist_recycler.setLayoutManager(gridLayoutManager);
        ProductList_Adapter adapter = new ProductList_Adapter(getActivity(),0);
        prolist_recycler.setAdapter(adapter);

        designers_grid_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(gridLayoutManager);
                ProductList_Adapter adapter = new ProductList_Adapter(getActivity(),0);
                prolist_recycler.setAdapter(adapter);
            }
        });
        designers_list_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(manager);
                ProductList_Adapter adapter = new ProductList_Adapter(getActivity(),1);
                prolist_recycler.setAdapter(adapter);
            }
        });

//                Intent i=new Intent(getActivity(),Product_details.class);
//                startActivity(i);


        return view;
    }*/
}
