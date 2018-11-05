package com.pro.wardrobe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.telecom.Call;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.innovattic.rangeseekbar.RangeSeekBar;
import com.pro.wardrobe.Adapter.CateByAlphabeticAdapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CateByAlphabeticResponse.CategoryList;
import com.pro.wardrobe.ApiResponse.CateByAlphabeticResponse.ResponseCatebyalphabetic;
import com.pro.wardrobe.ApiResponse.ColorListResponse.ColorList;
import com.pro.wardrobe.ApiResponse.ColorListResponse.ResponseColorList;
import com.pro.wardrobe.ApiResponse.PriceRangeResponse.MinMaxPriceRange;
import com.pro.wardrobe.ApiResponse.PriceRangeResponse.ResponsePriceRange;
import com.pro.wardrobe.ApiResponse.SizeListResponse.ResponseSizeList;
import com.pro.wardrobe.ApiResponse.SizeListResponse.SizeList;
import com.pro.wardrobe.Extra.FIlterCateByAlphabet;
import com.pro.wardrobe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Filter extends AppCompatActivity {

    RangeSeekBar seekBar;
    TextView filter_minRange, filter_maxRange;

//    int minRnage = 50;
//    int maxRnage = 350;

    RecyclerView filter_size_layout;
    RecyclerView filter_color_layout, filter_catelist;
    CardView filter_catelist_card, filter_cate_card;
    /*String[] size=new String[]{
            "XS",
            "S",
            "M",
            "L",
            "XL",
            "2XL"
    };*/

    List<SizeList> size;

    List<ColorList> colorStr;

    /*=new String[]{
     *//*   "#F44336",
            "#E91E63",
            "#7B1FA2",
            "#3949AB",
            "#00897B",
            "#C0CA33",*//*
    };*/

    ImageView filter_back;

    Button filter_btn_apply;

    TextView filter_cate_name,filter_cate_name_id;
    TextView filter_clear;

    String fil_color = "", fil_size = "", fil_minprice = "", fil_maxprice = "", fil_cateid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        this.setFinishOnTouchOutside(false);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        seekBar = findViewById(R.id.rangeSeekBar);
        filter_cate_name = findViewById(R.id.filter_cate_name);
        filter_cate_name_id= findViewById(R.id.filter_cate_name_id);
        filter_clear= findViewById(R.id.filter_clear);
        filter_back = findViewById(R.id.filter_back);
        filter_btn_apply = findViewById(R.id.filter_btn_apply);
//        seekBar.setMax(300);
//        seekBar.setMinRange(50);

        filter_minRange = findViewById(R.id.filter_minRange);
        filter_maxRange = findViewById(R.id.filter_maxRange);
        filter_size_layout = findViewById(R.id.filter_size_layout);
        LinearLayoutManager manager111=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        filter_size_layout.setLayoutManager(manager111);
        filter_color_layout = findViewById(R.id.filter_color_layout);
        filter_catelist = findViewById(R.id.filter_catelist);
        filter_catelist_card = findViewById(R.id.filter_catelist_card);
        filter_cate_card = findViewById(R.id.filter_cate_card);


        LinearLayoutManager manager11 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        filter_catelist.setLayoutManager(manager11);
        cateByAlphabetic();


        filter_cate_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filter_catelist_card.getVisibility() == View.VISIBLE) {
                    filter_catelist_card.setVisibility(View.GONE);
//                    filter_catelist_card.animate().translationY(filter_catelist_card.getHeight()).alpha(0.0f).setDuration(300);
                } else {
                    filter_catelist_card.setVisibility(View.VISIBLE);
//                    filter_catelist_card.animate().translationY(filter_catelist_card.getHeight()).alpha(1.0f).setDuration(300);
                }
            }
        });


        filter_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


/*
                if (fil_color.equals("")) fil_color="0";
                if (fil_size.equals("")) fil_size="0";
                if (fil_minprice.equals("")) fil_size=filter_minRange.getText().toString();
                if (fil_maxprice.equals("")) fil_size=filter_maxRange.getText().toString();
                if (fil_cateid.equals("")) fil_size="0";*/


                JSONObject obj = new JSONObject();
                {
                    try {
//                        if (!fil_color.isEmpty())
                        obj.put("colorid", fil_color);
//                        if (!fil_size.isEmpty())
                        obj.put("sizeid", fil_size);
//                        if (!fil_minprice.isEmpty())
                        obj.put("minprice", fil_minprice);
//                        if (!fil_maxprice.isEmpty())
                        obj.put("maxprice", fil_maxprice);
                        obj.put("cateid", filter_cate_name_id.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", obj.toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager manager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        filter_catelist.setLayoutManager(manager1);
        filter_color_layout.setLayoutManager(manager);

        colorListToArray();


        filter_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fil_color = "";
                fil_size = "";
                fil_minprice = "";
                fil_maxprice = "";
                fil_cateid = "";
                filter_cate_name.setText("Select Category");
                filter_cate_name_id.setText("");
                minmaxPricerange();
                sizeApiCall();
                colorListToArray();
/*
              Intent i=getIntent();
              i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP*//*|Intent.FLAG_ACTIVITY_CLEAR_TASK*//*);
              startActivity(i);*/


                filter_btn_apply.performClick();


            }
        });







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

        sizeApiCall();
        minmaxPricerange();


        filter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void minmaxPricerange(){
        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<ResponsePriceRange> call1 = apiInterface.min_max_price_range(preferences.getString("user_id", ""), preferences.getString("token", ""));

        call1.enqueue(new Callback<ResponsePriceRange>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<ResponsePriceRange> call, @NonNull Response<ResponsePriceRange> response) {

                ResponsePriceRange responsePriceRange = response.body();
                List<com.pro.wardrobe.ApiResponse.PriceRangeResponse.Response> responseList = responsePriceRange.getResponse();
                com.pro.wardrobe.ApiResponse.PriceRangeResponse.Response response1 = responseList.get(0);
                if (response1.getStatus().equals("true")) {
                    MinMaxPriceRange minMaxPriceRange = response1.getMinMaxPriceRange();
                    seekBar.setMax(Integer.parseInt(minMaxPriceRange.getMaxPrice()));
                    seekBar.setMinRange(Integer.parseInt(minMaxPriceRange.getMinPrice()));

                    filter_maxRange.setText(minMaxPriceRange.getMaxPrice() + " QAR");
                    filter_minRange.setText(minMaxPriceRange.getMinPrice() + " QAR");
                    fil_minprice=minMaxPriceRange.getMinPrice();
                    fil_maxprice=minMaxPriceRange.getMaxPrice();

                    Log.e("priceRangeMin", minMaxPriceRange.getMinPrice());
                    Log.e("priceRangeMax", minMaxPriceRange.getMaxPrice());

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

                            filter_minRange.setText((i) + " QAR");
                            filter_maxRange.setText((i1) + " QAR");

                            fil_minprice = String.valueOf(i);
                            fil_maxprice = String.valueOf(i1);

                        }
                    });

                } else
                    Toast.makeText(Filter.this, "Something went WRONG..!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<ResponsePriceRange> call, @NonNull Throwable t) {

            }
        });
    }

    public void sizeApiCall() {
        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<ResponseSizeList> call = apiInterface.size_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ResponseSizeList>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<ResponseSizeList> call, @NonNull Response<ResponseSizeList> response) {

                ResponseSizeList responseCall = response.body();
                List<com.pro.wardrobe.ApiResponse.SizeListResponse.Response> responseList = responseCall.getResponse();
                com.pro.wardrobe.ApiResponse.SizeListResponse.Response res = responseList.get(0);
                if (res.getStatus().equals("true")) {
                    List<SizeList> sizests = res.getSizeList();
                    size = sizests;

                    FilterSizePickerAdapter adapter=new FilterSizePickerAdapter(sizests);
                    filter_size_layout.setAdapter(adapter);


                    /*for (int i = 0; i < size.size(); i++) {
                        SizeList siz = size.get(i);
                        final LinearLayout layout = new LinearLayout(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, 1);
               *//* params.width=40;
            params.height=40;*//*
           *//* params.setMargins(5,5,5,5);
            layout.setPadding(5,5,5,5);*//*
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.setLayoutParams(params);

                        final LinearLayout layout1 = new LinearLayout(getApplicationContext());
                        layout.addView(layout1);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                *//*params1.width=40;
            params1.height=40;*//*
//            params.setMargins(5,5,5,5);
                        layout.setPadding(5, 5, 5, 5);
                        layout1.setOrientation(LinearLayout.VERTICAL);
                        layout1.setLayoutParams(params1);
                        layout1.setBackground(getResources().getDrawable(R.drawable.size_border_dark_gray));

                        final TextView tQty = new TextView(getApplicationContext());
                        LinearLayout.LayoutParams paramstqty = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

                        paramstqty.setMargins(20, 0, 20, 10);
                        paramstqty.gravity = Gravity.CENTER;
                        paramstqty.setMargins(5, 5, 5, 5);
                        tQty.setGravity(Gravity.CENTER);
                        tQty.setLayoutParams(paramstqty);
                        tQty.setTextColor(Color.BLACK);
                        tQty.setText(siz.getSize());
                        tQty.setTag(siz.getSizeId());
                        layout1.addView(tQty);


                        if (!fil_size.equals("")) {
                            if (i == Integer.parseInt(fil_size)) {
                                layout1.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
                                tQty.setTextColor(Color.parseColor("#ffffff"));
                            }
                        }
                        layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                layout1.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
                                tQty.setTextColor(Color.parseColor("#ffffff"));
                                fil_size = "";
                                fil_size = tQty.getTag().toString();
                                filter_size_layout.removeAllViews();
                                sizeApiCall();
                            }
                        });

                        filter_size_layout.addView(layout);
                    }*/

                } else
                    Toast.makeText(Filter.this, "Something Went wrong..!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<ResponseSizeList> call, @NonNull Throwable t) {

            }
        });
    }

int pos=-1;
    class FilterSizePickerAdapter extends RecyclerView.Adapter<FilterSizePickerAdapter.ViewHolder> {

        List<SizeList> sizests;

        public FilterSizePickerAdapter(List<SizeList> sizests) {
            this.sizests = sizests;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_size_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            SizeList sizeList = sizests.get(i);
            viewHolder.filter_size_txt.setText(sizeList.getSize());
            viewHolder.filter_size_txt_id.setText(sizeList.getSizeId());

        /*    if (i==0){
                fil_size= viewHolder.filter_size_txt_id.getText().toString();
            }*/

            if (i==pos){
//                fil_size = viewHolder.filter_size_txt_id.getText().toString();
                viewHolder.filter_size_txt.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.filter_size_layout.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
            }else {
                viewHolder.filter_size_layout.setBackground(getResources().getDrawable(R.drawable.size_border_dark_gray));
                viewHolder.filter_size_txt.setTextColor(Color.parseColor("#000000"));
            }
            viewHolder.filter_size_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos=-1;
                    pos=i;
                    viewHolder.filter_size_txt.setTextColor(Color.parseColor("#ffffff"));
                    fil_size = viewHolder.filter_size_txt_id.getText().toString();
                    viewHolder.filter_size_layout.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
                    notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return sizests.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout filter_size_layout;
            TextView filter_size_txt;
            TextView filter_size_txt_id;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                filter_size_layout = itemView.findViewById(R.id.filter_size_layout);
                filter_size_txt = itemView.findViewById(R.id.filter_size_txt);
                filter_size_txt_id = itemView.findViewById(R.id.filter_size_txt_id);
            }
        }
    }

    int position = -1;

    class FiltercolorPickerAdapter extends RecyclerView.Adapter<FiltercolorPickerAdapter.ViewHolder> {

        Context context;
        List<ColorList> colorStr;

        public FiltercolorPickerAdapter(Context context, List<ColorList> colorStr) {
            this.context = context;
            this.colorStr = colorStr;
        }

        @NonNull
        @Override
        public FiltercolorPickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new FiltercolorPickerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.color_picker_spinner_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FiltercolorPickerAdapter.ViewHolder viewHolder, final int i) {
            ColorList colors = colorStr.get(i);
            RoundRectShape roundRectShape = new RoundRectShape(new float[]{10, 10, 10, 10, 10, 10, 10, 10}, null, new float[]{10, 10, 10, 10, 10, 10, 10, 10});
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(Color.parseColor(colors.getColorCode()));


           /* if (i==0){
                fil_color=colors.getColorId();
            }*/

            if (i == position) {
                viewHolder.colorpicker_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_white_24dp));
            } else {
                viewHolder.colorpicker_imageView.setImageDrawable(null);
            }
            final GradientDrawable gD = new GradientDrawable();
            gD.setColor(Color.parseColor(colors.getColorCode()));
            gD.setShape(GradientDrawable.OVAL);
            gD.setStroke(1, Color.parseColor("#ffffff"));
            gD.setSize(62, 62);
            viewHolder.colorpicker_tv.setText(colors.getColorId());

            viewHolder.colorpicker_imageView.setBackground(gD);

            viewHolder.colorpicker_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = i;
                    viewHolder.colorpicker_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_white_24dp));
                   /* prodetails_togglelayout.setVisibility(View.GONE);
                    selectcolor_icon.setImageDrawable(gD);*/
                    fil_color = viewHolder.colorpicker_tv.getText().toString();
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return colorStr.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView colorpicker_imageView;
            TextView colorpicker_tv;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                colorpicker_imageView = itemView.findViewById(R.id.colorpicker_imageView);
                colorpicker_tv = itemView.findViewById(R.id.colorpicker_tv);
            }
        }
    }

    String cateList = "{\n" + "\"catelist\":[\n" + "{\n" + "\"name\":\"a\",\n" + "\"list\":[\n" + "\"a\",\"a\",\"a\",\"a\"\n" + "]\n" + "},\n" + "{\n" + "\"name\":\"b\",\n" + "\"list\":[\n" + "\"b\",\"b\",\"b\",\"b\"\n" + "]\n" + "},{\n" + "\"name\":\"c\",\n" + "\"list\":[\n" + "\"c\",\"c\",\"c\",\"c\"\n" + "]\n" + "},{\n" + "\"name\":\"d\",\n" + "\"list\":[\n" + "\"d\",\"d\",\"d\",\"d\"\n" + "]\n" + "},{\n" + "\"name\":\"e\",\n" + "\"list\":[\n" + "\"e\",\"e\",\"e\",\"e\"\n" + "]\n" + "}\n" + "]\n" + "}";

    class FilterCateListAdapter extends RecyclerView.Adapter<FilterCateListAdapter.ViewHolder> {

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

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }


    public void colorListToArray() {
        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<ResponseColorList> call = apiInterface.color_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ResponseColorList>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<ResponseColorList> call, @NonNull Response<ResponseColorList> response) {

                ResponseColorList responseCall = response.body();
                List<com.pro.wardrobe.ApiResponse.ColorListResponse.Response> responseList = responseCall.getResponse();
                com.pro.wardrobe.ApiResponse.ColorListResponse.Response res = responseList.get(0);
                if (res.getStatus().equals("true")) {
                    List<ColorList> colorLists = res.getColorList();
                    colorStr = colorLists;
                    filter_color_layout.setAdapter(new FiltercolorPickerAdapter(getApplicationContext(), colorLists));

                } else
                    Toast.makeText(Filter.this, "Something Went wrong..!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<ResponseColorList> call, @NonNull Throwable t) {

            }
        });

    }

    public void cateByAlphabetic() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        retrofit2.Call<ResponseCatebyalphabetic> ca = apiInterface.category_list_by_alphabetic(preferences.getString("user_id", ""), preferences.getString("token", ""));
        ca.enqueue(new Callback<ResponseCatebyalphabetic>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseCatebyalphabetic> call, Response<ResponseCatebyalphabetic> response) {
                ResponseCatebyalphabetic responseCatebyalphabetic = response.body();
                List<com.pro.wardrobe.ApiResponse.CateByAlphabeticResponse.Response> reslist = responseCatebyalphabetic.getResponse();
                com.pro.wardrobe.ApiResponse.CateByAlphabeticResponse.Response res = reslist.get(0);
                if (res.getStatus().equals("true")) {
                    List<CategoryList> categoryListList = res.getCategoryList();
                    String cateAlpha="";

                    List<FIlterCateByAlphabet> fIlterCateByAlphabetsList=new ArrayList<>();
                    for (int i=0;i<categoryListList.size();i++){

                        CategoryList categoryList=categoryListList.get(i);
                        if (!String.valueOf(categoryList.getCategoryName().charAt(0)).equals(cateAlpha))
                        {
                            cateAlpha=String.valueOf(categoryList.getCategoryName().charAt(0));
                            FIlterCateByAlphabet fIlterCateByAlphabet=new FIlterCateByAlphabet(String.valueOf(categoryList.getCategoryName().charAt(0)),"0",0);
                            fIlterCateByAlphabetsList.add(fIlterCateByAlphabet);
                        }
                        FIlterCateByAlphabet fIlterCateByAlphabet=new FIlterCateByAlphabet(categoryList.getCategoryName(),categoryList.getCategoryId(),1);
                        fIlterCateByAlphabetsList.add(fIlterCateByAlphabet);

                    }

mProgressDialog.dismiss();
                    CateByAlphabeticAdapter adapter = new CateByAlphabeticAdapter(getApplicationContext(), fIlterCateByAlphabetsList, filter_cate_name_id, filter_catelist_card, filter_cate_name);
                    filter_catelist.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseCatebyalphabetic> call, Throwable t) {

            }
        });
    }
}
