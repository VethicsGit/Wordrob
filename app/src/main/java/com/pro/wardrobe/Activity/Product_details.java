package com.pro.wardrobe.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.AddToFavorite.AddToFavorite;
import com.pro.wardrobe.ApiResponse.ProductDetailResponse.ProductDetail;
import com.pro.wardrobe.ApiResponse.ProductDetailResponse.ProductDetailResponse;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductListResponse;
import com.pro.wardrobe.ApiResponse.RemoveToFavorite.RemoveToFavorite;
import com.pro.wardrobe.Fragment.Reviews;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_details extends AppCompatActivity {

    Button prodetails_addtobag;
    ImageView prodetails_Mybag, prodetails_back,addtofav,detail_addtofav;
    TextView prodetails_title;
    EditText prodetails_length;
    EditText prodetails_hips;
    APIInterface apiInterface;
    String product_id;
    TextView product_detail_title,product_detail_category,product_detail_price,product_detail_desc;
    ImageView product_detail_img;
    //Spinner prodetails_selectcolor;
    int position = -1;
    TextView title, prodetails_selectsizelayout,prolist_isfav;

    LinearLayout prodetails_togglelayout, prodetails_selectcolor, prodetails_sizelayout;
    RecyclerView prodetails_colorlayout;
    ImageView prodetails_selectsize_icon, prodetails_selectcolor_icon;
    ImageView selectcolor_icon;
    String[] color = new String[]{"#F44336", "#E91E63", "#7B1FA2", "#3949AB", "#00897B", "#C0CA33", "#F44336", "#E91E63", "#7B1FA2", "#3949AB", "#00897B", "#C0CA33",};
    TextView prodetails_rating, submit_review;

//    List<Product_details>product_details;

    //    ScrollView prodetails_colorlayout_root;
    public Product_details() {
    }

    @SuppressLint("ValidFragment")
    public Product_details(TextView title) {
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
//        apiInterface = APIClient.getClient().create(APIInterface.class);
        prodetails_Mybag = findViewById(R.id.prodetails_Mybag);
        prodetails_addtobag = findViewById(R.id.prodetails_addtobag);
        prodetails_back = findViewById(R.id.prodetails_back);
        prodetails_title = findViewById(R.id.prodetails_title);
        prodetails_length = findViewById(R.id.prodetails_length);
        prodetails_hips = findViewById(R.id.prodetails_hips);
        prodetails_rating = findViewById(R.id.prodetails_rating);
        submit_review = findViewById(R.id.submit_review);
        prodetails_togglelayout = findViewById(R.id.prodetails_togglelayout);
        prodetails_selectcolor = findViewById(R.id.prodetails_selectcolor);
        prodetails_colorlayout = findViewById(R.id.prodetails_colorlayout);
        product_detail_desc=findViewById(R.id.product_detail_desc);

        product_detail_img=findViewById(R.id.product_detail_img);
        product_detail_title=findViewById(R.id.product_detail_title);
        product_detail_category=findViewById(R.id.product_detail_category);
        product_detail_price=findViewById(R.id.product_detail_price);
        addtofav=findViewById(R.id.addtofav);
        prolist_isfav= findViewById(R.id.prolist_isfav);
        detail_addtofav=findViewById(R.id.related_addtofav);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        prodetails_colorlayout.setLayoutManager(manager);

        prodetails_colorlayout.setAdapter(new colorPickerAdapter(getApplicationContext()));
//        prodetails_colorlayout_root=findViewById(R.id.prodetails_colorlayout_root);
        prodetails_selectsize_icon = findViewById(R.id.prodetails_selectsize_icon);
        prodetails_selectcolor_icon = findViewById(R.id.prodetails_selectcolor_icon);
        selectcolor_icon = findViewById(R.id.selectcolor_icon);


        Intent intent=getIntent();
        if (intent.hasExtra("product_id"))
        product_id=intent.getStringExtra("product_id");


        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);


            APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<ProductDetailResponse>call=apiInterface.product_detail(preferences.getString("user_id",""),product_id,preferences.getString("token",""));
        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductDetailResponse> call, @NonNull Response<ProductDetailResponse> response) {

                Gson gson = new GsonBuilder().create();
                String myCustomArray = gson.toJson(response).toString();
//                String obj=new JsonObject().get(response.toString()).getAsString();
                Log.e("details_Response ",myCustomArray.toString());
                ProductDetailResponse productDetailResponse=response.body();
                List<com.pro.wardrobe.ApiResponse.ProductDetailResponse.Response>responses=productDetailResponse.getResponse();


                    com.pro.wardrobe.ApiResponse.ProductDetailResponse.Response response1=responses.get(0);
Log.e("details_status",response1.getStatus());
                    if (response1.getStatus().equals("true"))
                    {
                        List<ProductDetail>productDetails=response1.getProductDetails();
//                        Toast.makeText(getApplicationContext(), "product id" + product_id, Toast.LENGTH_SHORT).show();

                            ProductDetail productDetail=productDetails.get(0);
                            Glide.with(getApplicationContext()).load(productDetail.getImage()).into(product_detail_img);

                            product_detail_title.setText(productDetail.getTitle());
                            product_detail_category.setText(productDetail.getCategoryName());
                            product_detail_price.setText(productDetail.getPrice());
                            product_detail_desc.setText(productDetail.getDescription());
                            prodetails_rating.setText(productDetail.getRatingCount());
                        }
                        else Toast.makeText(Product_details.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();


                }



            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

            }
        });






        prolist_isfav.setText(productDetail.getIsFav());
        if (Integer.parseInt(product_details.getIsFav()) == 0) {
            addtofav.setImageDrawable(getResources().getDrawable(R.drawable.favourite));
        } else
            addtofav.setImageDrawable(getResources().getDrawable(R.drawable.heart_filled));
        addtofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addtofav.getDrawable() == getResources().getDrawable(R.drawable.favourite)) {
                    if (Integer.parseInt(prolist_isfav.getText().toString()) == 0) {
                        prolist_isfav.setText("1");

                        addtofav.setImageResource(R.drawable.heart_filled);

//                    notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
                        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<AddToFavorite> call = apiInterface.add_fav(preferences.getString("user_id", ""), product_id, preferences.getString("token", ""));
                        call.enqueue(new Callback<AddToFavorite>() {
                            @Override
                            public void onResponse(Call<AddToFavorite> call, Response<AddToFavorite> response) {


                                Gson gson = new GsonBuilder().create();
                                String myCustomArray = gson.toJson(response).toString();
                                AddToFavorite addToFavorite = response.body();
                                List<com.pro.wardrobe.ApiResponse.AddToFavorite.Response> responses = addToFavorite.getResponse();

//                            for (int i=0;i<responses.size();i++){
                                com.pro.wardrobe.ApiResponse.AddToFavorite.Response response1 = responses.get(0);
                                Log.e("add", response1.getStatus());
//
//                                if (response1.getStatus().equals("true")){
//                                    product_list.apiCll();
//                                }

                            }
//                        }

                            @Override
                            public void onFailure(Call<AddToFavorite> call, Throwable t) {

                            }
                        });
                    } else {
                        prolist_isfav.setText("0");
                        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
//                    notifyDataSetChanged();
//                        product_removetofav.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
                        addtofav.setImageResource(R.drawable.favourite);
                        Toast.makeText(getApplicationContext(), "remove", Toast.LENGTH_SHORT).show();


                        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<RemoveToFavorite> call = apiInterface.remove_fav(preferences.getString("user_id", ""), product_id, preferences.getString("token", ""));
                        call.enqueue(new Callback<RemoveToFavorite>() {
                            @Override
                            public void onResponse(Call<RemoveToFavorite> call, Response<RemoveToFavorite> response) {
                                RemoveToFavorite removeToFavorite = response.body();
                                List<com.pro.wardrobe.ApiResponse.RemoveToFavorite.Response> responses = removeToFavorite.getResponse();

                                com.pro.wardrobe.ApiResponse.RemoveToFavorite.Response response1 = responses.get(0);
//                                        if (response1.getStatus().equals("true")){
//                                            product_list.apiCll();
//                                        }

                            }

                            @Override
                            public void onFailure(Call<RemoveToFavorite> call, Throwable t) {

                            }
                        });
                    }
//                        });
//                    }
                }
            }
        });

      /*  HorizontalScrollView sv = new HorizontalScrollView(this);
        sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

sv.setFillViewport(true);
sv.setHorizontalScrollBarEnabled(false);
        final LinearLayout layout1=new LinearLayout(getApplicationContext());
//        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT);

        final LinearLayout layout=new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layout.setGravity(Gravity.CENTER);
//        params.setMargins(5, 5, 5, 5);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(params);
        sv.addView(layout);
layout.setFitsSystemWindows(true);
        for(int i=0;i<color.length;i++){


            RoundRectShape roundRectShape = new RoundRectShape(new float[]{10, 10, 10, 10, 10, 10, 10, 10}, null, new float[]{10, 10, 10, 10, 10, 10, 10, 10});
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(Color.parseColor(color[i]));

            final GradientDrawable gD = new GradientDrawable();
            gD.setColor(Color.parseColor(color[i]));
            gD.setShape(GradientDrawable.OVAL);
            gD.setStroke(1, Color.parseColor("#ffffff"));
            gD.setSize(62, 62);

            final ImageView tQty = new ImageView(getApplicationContext());
//            LinearLayout.LayoutParams paramstqty = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams paramstqty = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 62);
            paramstqty.gravity = Gravity.START;
            paramstqty.setMargins(15,15,15,15);
            tQty.setPadding(5, 5, 5, 5);
            tQty.setLayoutParams(paramstqty);
            tQty.setBackground(gD);
            tQty.setTag(i);
            layout.addView(tQty);
            tQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    layout1.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
//                    tQty.setTextColor(Color.parseColor("#ffffff"));
//                    gD.setStroke(2, Color.parseColor("#000000"));
                    tQty.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
                    synchronized(layout) {
                        layout.notifyAll();
                    }
                    selectcolor_icon.setImageDrawable(tQty.getBackground());
                    prodetails_togglelayout.setVisibility(View.GONE);
                    Toast.makeText(Product_details.this, "tQty tag "+tQty.getTag(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<color.length;i++){
                        if (Integer.parseInt(tQty.getTag().toString())!=i){
                            tQty.setImageDrawable(null);
//                            layout.notify();
                        }else {
                            Log.e("else","condition");
                            tQty.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
                        }
                    }

        }
    });

//            prodetails_colorlayout.addView(layout);
//            layout1.addView(layout);
        }
        prodetails_colorlayout.addView(sv);*/

        prodetails_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        prodetails_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbag = new Intent(getApplicationContext(), Reviews.class);
                startActivity(intentbag);
            }
        });

        prodetails_addtobag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                startActivity(intentbag);
            }
        });
        prodetails_Mybag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                startActivity(intentbag);
            }
        });
        prodetails_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        submit_review.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(Product_details.this, R.style.MyDialogTheme);

                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.submit_review_dialog);
                Window window = d.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                d.getWindow().setBackgroundDrawableResource(R.color.colorwhitetransparent);
                d.show();

            }
        });

        prodetails_sizelayout = findViewById(R.id.prodetails_sizelayout);
        prodetails_selectsizelayout = findViewById(R.id.prodetails_selectsizelayout);
        prodetails_selectcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               if (x == 0) {
              /* prodetails_togglelayout.setVisibility(View.VISIBLE);
               prodetails_sizelayout.setVisibility(View.GONE);
               prodetails_colorlayout.setVisibility(View.VISIBLE);*/
                if (prodetails_colorlayout.getVisibility() == View.VISIBLE) {
                    prodetails_togglelayout.setVisibility(View.GONE);
                  /*  prodetails_sizelayout.setVisibility(View.GONE);
                    prodetails_colorlayout.setVisibility(View.GONE);*/
                    prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                    prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                }

                if (prodetails_colorlayout.getVisibility() == View.GONE) {
                    prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                    prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                    prodetails_togglelayout.setVisibility(View.VISIBLE);
                    prodetails_sizelayout.setVisibility(View.GONE);
                    prodetails_colorlayout.setVisibility(View.VISIBLE);
                }
            }
        });
        prodetails_selectsizelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               if (x == 0) {
//                   x = 1;
                 /*  prodetails_togglelayout.setVisibility(View.VISIBLE);
                   prodetails_sizelayout.setVisibility(View.VISIBLE);
                   prodetails_colorlayout.setVisibility(View.GONE);*/
                if (prodetails_sizelayout.getVisibility() == View.VISIBLE) {
                    prodetails_togglelayout.setVisibility(View.GONE);
                    /*prodetails_sizelayout.setVisibility(View.GONE);
                    prodetails_colorlayout.setVisibility(View.GONE);*/
                    prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                    prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                }
                if (prodetails_sizelayout.getVisibility() == View.GONE) {
                    prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                    prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                    prodetails_togglelayout.setVisibility(View.VISIBLE);
                    prodetails_sizelayout.setVisibility(View.VISIBLE);
                    prodetails_colorlayout.setVisibility(View.GONE);
                }
            }
        });

        /* colorPickerAdapter colorPickerAdapter=new colorPickerAdapter(getApplicationContext());
        prodetails_selectcolor.setAdapter(colorPickerAdapter);*/
    }

    class colorPickerAdapter extends RecyclerView.Adapter<colorPickerAdapter.ViewHolder> {

        Context context;

        public colorPickerAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.color_picker_spinner_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
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
                    prodetails_togglelayout.setVisibility(View.GONE);
                    selectcolor_icon.setImageDrawable(gD);
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

}
