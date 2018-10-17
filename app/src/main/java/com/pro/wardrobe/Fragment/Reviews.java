package com.pro.wardrobe.Fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.Activity.Activty_MyBag;
import com.pro.wardrobe.Adapter.ReviewhorizontalScrollAdapter;
import com.pro.wardrobe.Adapter.Reviews_Adapter;
import com.pro.wardrobe.Adapter.horizontalScrollAdapter;
import com.pro.wardrobe.R;

import java.util.ArrayList;
import java.util.List;

public class Reviews extends AppCompatActivity {

    TextView prodetails_title;
    TextView title;
    ImageView prodetails_Mybag,prodetails_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);


        prodetails_title=findViewById(R.id.prodetails_title);
        prodetails_back=findViewById(R.id.prodetails_back);
        prodetails_Mybag=findViewById(R.id.prodetails_Mybag);

        prodetails_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

        prodetails_Mybag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                startActivity(intentbag);
            }
        }); prodetails_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        reviews_horizontal_view=findViewById(R.id.reviews_horizontal_view);
        reviews_list=findViewById(R.id.reviews_list);


        cates.add("Most Helpful");
        cates.add("Positive");
        cates.add("Negative");
        cates.add("Most Recent");



        ReviewhorizontalScrollAdapter adapter1=new ReviewhorizontalScrollAdapter(cates,getApplicationContext());
        reviews_horizontal_view.setAdapter(adapter1);
        Reviews_Adapter reviews_adapter=new Reviews_Adapter(getApplicationContext());
        reviews_list.setAdapter(reviews_adapter);
        LinearLayoutManager manager1=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        reviews_horizontal_view.setLayoutManager(manager1);

        LinearLayoutManager manager2=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        reviews_list.setLayoutManager(manager2);


    }

    RecyclerView reviews_horizontal_view,reviews_list;

//    String[] cates=new String[]{"Most Helpful","Positive","Negative","Most Recent"};

    List<String> cates=new ArrayList<>();
/*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_reviews,container,false);


        reviews_horizontal_view=view.findViewById(R.id.designer_horizontal_view);
        reviews_list=view.findViewById(R.id.reviews_list);
        horizontalScrollAdapter adapter1=new horizontalScrollAdapter(cates,getActivity());
        reviews_horizontal_view.setAdapter(adapter1);
        Reviews_Adapter reviews_adapter=new Reviews_Adapter(getActivity());
        reviews_list.setAdapter(reviews_adapter);
        LinearLayoutManager manager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        reviews_horizontal_view.setLayoutManager(manager1);

 LinearLayoutManager manager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        reviews_list.setLayoutManager(manager2);




        return view;
    }*/
}
