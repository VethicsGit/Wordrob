package com.pro.wardrobe.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovattic.rangeseekbar.RangeSeekBar;
import com.pro.wardrobe.R;

import org.json.JSONArray;

public class Filter extends AppCompatActivity {

    RangeSeekBar seekBar;
    TextView filter_minRange, filter_maxRange;

    int minRnage = 50;
    int maxRnage = 350;

    LinearLayout filter_size_layout;
    RecyclerView filter_color_layout,filter_catelist;
    CardView filter_catelist_card,filter_cate_card;

    String[] size=new String[]{
            "XS",
            "S",
            "M",
            "L",
            "XL",
            "2XL"
    };

    String[] color=new String[]{
            "#F44336",
            "#E91E63",
            "#7B1FA2",
            "#3949AB",
            "#00897B",
            "#C0CA33",
    };

    ImageView filter_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        seekBar = findViewById(R.id.rangeSeekBar);
        filter_back = findViewById(R.id.filter_back);
        seekBar.setMax(300);
        seekBar.setMinRange(50);

        filter_minRange = findViewById(R.id.filter_minRange);
        filter_maxRange = findViewById(R.id.filter_maxRange);
        filter_size_layout = findViewById(R.id.filter_size_layout);
        filter_color_layout = findViewById(R.id.filter_color_layout);
        filter_catelist = findViewById(R.id.filter_catelist);
        filter_catelist_card = findViewById(R.id.filter_catelist_card);
        filter_cate_card = findViewById(R.id.filter_cate_card);

        filter_cate_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filter_catelist_card.getVisibility()==View.VISIBLE){
                    filter_catelist_card.setVisibility(View.GONE);
                    filter_catelist_card.animate().translationY(filter_catelist_card.getHeight()).alpha(0.0f).setDuration(300);
                }else {
                    filter_catelist_card.setVisibility(View.VISIBLE);
                    filter_catelist_card.animate().translationY(filter_catelist_card.getHeight()).alpha(1.0f).setDuration(300);
                }
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager manager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        filter_catelist.setLayoutManager(manager1);
        filter_color_layout.setLayoutManager(manager);

        filter_color_layout.setAdapter(new FiltercolorPickerAdapter(getApplicationContext()));

        for(int i=0;i<size.length;i++){
            final LinearLayout layout=new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT,1);
               /* params.width=40;
            params.height=40;*/
           /* params.setMargins(5,5,5,5);
            layout.setPadding(5,5,5,5);*/
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(params);

            final LinearLayout layout1=new LinearLayout(getApplicationContext());
            layout.addView(layout1);
            LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                /*params1.width=40;
            params1.height=40;*/
//            params.setMargins(5,5,5,5);
            layout.setPadding(5,5,5,5);
            layout1.setOrientation(LinearLayout.VERTICAL);
            layout1.setLayoutParams(params1);
            layout1.setBackground(getResources().getDrawable(R.drawable.size_border_dark_gray));

            final TextView tQty=new TextView(getApplicationContext());
            LinearLayout.LayoutParams paramstqty=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);

            paramstqty.setMargins(20,0,20,10);
            paramstqty.gravity= Gravity.CENTER;
            paramstqty.setMargins(5,5,5,5);
tQty.setGravity(Gravity.CENTER);
            tQty.setLayoutParams(paramstqty);
            tQty.setTextColor(Color.BLACK);
            tQty.setText(size[i]);
            layout1.addView(tQty);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout1.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
                    tQty.setTextColor(Color.parseColor("#ffffff"));
                }
            });

            filter_size_layout.addView(layout);
        }





      /*  for(int i=0;i<color.length;i++){
            final LinearLayout layout=new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
          *//*  params.width=30;
            params.height=30;*//*
            layout.setGravity(Gravity.CENTER);
            params.setMargins(5,5,5,5);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(params);
            RoundRectShape roundRectShape = new RoundRectShape(new float[]{
                    10, 10, 10, 10,
                    10, 10, 10, 10}, null,new float[]{
                    10, 10, 10, 10,
                    10, 10, 10, 10});
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(Color.parseColor(color[i]));



            final GradientDrawable gD = new GradientDrawable();
            gD.setColor(Color.parseColor(color[i]));
            gD.setShape(GradientDrawable.OVAL);
            gD.setStroke(1, Color.parseColor("#ffffff"));
            gD.setSize(62,62);



            final ImageView tQty=new ImageView(getApplicationContext());
            LinearLayout.LayoutParams paramstqty=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            paramstqty.gravity= Gravity.START;
//            paramstqty.setMargins(5,5,5,5);
            tQty.setPadding(10,10,10,10);
            tQty.setLayoutParams(paramstqty);
            tQty.setBackground(gD);
            layout.addView(tQty);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    layout.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
//                    tQty.setTextColor(Color.parseColor("#ffffff"));
                    gD.setStroke(2,Color.parseColor("#000000"));
                }
            });

            filter_color_layout.addView(layout);
        }*/


        seekBar.setSeekBarChangeListener(new RangeSeekBar.SeekBarChangeListener() {
            @Override
            public void onStartedSeeking() {
            }

            @Override
            public void onStoppedSeeking() {

            }

            @Override
            public void onValueChanged(int i, int i1) {

//                minRnage+=i;
//                maxRnage=maxRnage+i1;

                filter_minRange.setText((i+50) + " QAR");
                filter_maxRange.setText((i1+50) + " QAR");

            }
        });

        filter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    int position=-1;
    class FiltercolorPickerAdapter extends RecyclerView.Adapter<FiltercolorPickerAdapter.ViewHolder> {

        Context context;

        public FiltercolorPickerAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public FiltercolorPickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new FiltercolorPickerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.color_picker_spinner_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FiltercolorPickerAdapter.ViewHolder viewHolder, final int i) {
            RoundRectShape roundRectShape = new RoundRectShape(new float[]{10, 10, 10, 10, 10, 10, 10, 10}, null, new float[]{10, 10, 10, 10, 10, 10, 10, 10});
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(Color.parseColor(color[i]));

            if (i == position) {
                viewHolder.colorpicker_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
            } else {
                viewHolder.colorpicker_imageView.setImageDrawable(null);
            }
            final GradientDrawable gD = new GradientDrawable();
            gD.setColor(Color.parseColor(color[i]));
            gD.setShape(GradientDrawable.OVAL);
            gD.setStroke(1, Color.parseColor("#ffffff"));
            gD.setSize(62, 62);

            viewHolder.colorpicker_imageView.setBackground(gD);

            viewHolder.colorpicker_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = i;
                    viewHolder.colorpicker_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
                   /* prodetails_togglelayout.setVisibility(View.GONE);
                    selectcolor_icon.setImageDrawable(gD);
                    notifyDataSetChanged();*/
                }
            });
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return color.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView colorpicker_imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                colorpicker_imageView = itemView.findViewById(R.id.colorpicker_imageView);
            }
        }
    }
    String cateList="{\n" + "\"catelist\":[\n" + "{\n" + "\"name\":\"a\",\n" + "\"list\":[\n" + "\"a\",\"a\",\"a\",\"a\"\n" + "]\n" + "},\n" + "{\n" + "\"name\":\"b\",\n" + "\"list\":[\n" + "\"b\",\"b\",\"b\",\"b\"\n" + "]\n" + "},{\n" + "\"name\":\"c\",\n" + "\"list\":[\n" + "\"c\",\"c\",\"c\",\"c\"\n" + "]\n" + "},{\n" + "\"name\":\"d\",\n" + "\"list\":[\n" + "\"d\",\"d\",\"d\",\"d\"\n" + "]\n" + "},{\n" + "\"name\":\"e\",\n" + "\"list\":[\n" + "\"e\",\"e\",\"e\",\"e\"\n" + "]\n" + "}\n" + "]\n" + "}";

    class FilterCateListAdapter extends RecyclerView.Adapter<FilterCateListAdapter.ViewHolder>{

        JSONArray array;
        Context context;


        public static final int TYPE_HEADER = 0;
        public static final int TYPE_EVENT = 1;

        public FilterCateListAdapter(JSONArray array, Context context) {
            this.array = array;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
