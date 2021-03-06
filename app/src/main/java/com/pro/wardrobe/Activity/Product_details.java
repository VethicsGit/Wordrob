package com.pro.wardrobe.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.wardrobe.Adapter.ProductList_Adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.AddToCartResponse.ResponseAddToCart;
import com.pro.wardrobe.ApiResponse.AddToFavorite.AddToFavorite;
import com.pro.wardrobe.ApiResponse.CartListResponse.CartList;
import com.pro.wardrobe.ApiResponse.CartListResponse.ResponseCartList;
import com.pro.wardrobe.ApiResponse.GiveRatingResponse.GiveRatingResponse;
import com.pro.wardrobe.ApiResponse.ProductDetailResponse.ProductColorDetail;
import com.pro.wardrobe.ApiResponse.ProductDetailResponse.ProductDetail;
import com.pro.wardrobe.ApiResponse.ProductDetailResponse.ProductDetailResponse;
import com.pro.wardrobe.ApiResponse.ProductDetailResponse.ProductSizeDetail;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductList;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductListResponse;
import com.pro.wardrobe.ApiResponse.RemoveToFavorite.RemoveToFavorite;
import com.pro.wardrobe.ApiResponse.SizeListResponse.SizeList;
import com.pro.wardrobe.Fragment.Fragment_product_list;
import com.pro.wardrobe.Fragment.Reviews;
import com.pro.wardrobe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_details extends AppCompatActivity {

    Button prodetails_addtobag, prodetails_giverating;
    ImageView prodetails_Mybag, prodetails_back, addtofav, detail_addtofav;
    TextView prodetails_title;
    EditText prodetails_length;
    EditText prodetails_hips;
    APIInterface apiInterface;
    String product_id;
    TextView product_detail_title, product_detail_category, product_detail_price, product_detail_desc,prodetails_size_txt,prodetails_size_txt_id;
    ImageView product_detail_img;
    AppCompatRatingBar prodetail_ratngbar;
    //Spinner prodetails_selectcolor;
    int position = -1;
    RecyclerView prodetails_similar_recycler;
    ProductList_Adapter productList_adapter;
    TextView title, prodetails_selectsizelayout, prolist_isfav;

    LinearLayout prodetails_togglelayout, prodetails_selectcolor, prodetails_sizelayout;
    RecyclerView prodetails_colorlayout;
    RecyclerView prodetails_sizerecycler;
    ImageView prodetails_selectsize_icon, prodetails_selectcolor_icon;
    ImageView selectcolor_icon;
    //    String[] color = new String[]{"#F44336", "#E91E63", "#7B1FA2", "#3949AB", "#00897B", "#C0CA33", "#F44336", "#E91E63", "#7B1FA2", "#3949AB", "#00897B", "#C0CA33",};
    List<ProductColorDetail> colorDetails;
    TextView prodetails_rating;/* submit_review;*/

    String fil_size = "null";
    String fil_color = "null";

    String vendor_id;
    String category_id;
    TextView prodetails_specialinst, prodetails_deliveryinst;
    String offer_id;
    TextView prodetails_washinginst;
    TextView prodetails_materialinst;

//    List<Product_details>product_details;

    //    ScrollView prodetails_colorlayout_root;
    public Product_details() {
    }

    TextView bag_product_count;

    @SuppressLint("ValidFragment")
    public Product_details(TextView title) {
        this.title = title;
    }

    LinearLayout washing_inst_layout,material_inst_layout,special_inst_layout,delivery_inst_layout;


    @Override
    protected void onResume() {
        super.onResume();
        myBagApiCall();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
//        apiInterface = APIClient.getClient().create(APIInterface.class);
        prodetails_Mybag = findViewById(R.id.prodetails_Mybag);
        bag_product_count = findViewById(R.id.bag_product_count);
        prodetails_washinginst= findViewById(R.id.prodetails_washinginst);
        prodetails_materialinst= findViewById(R.id.prodetails_materialinst);
        prodetails_addtobag = findViewById(R.id.prodetails_addtobag);
        prodetails_back = findViewById(R.id.prodetails_back);
        prodetails_title = findViewById(R.id.prodetails_title);
        prodetails_length = findViewById(R.id.prodetails_length);
        prodetails_hips = findViewById(R.id.prodetails_hips);
        prodetails_rating = findViewById(R.id.prodetails_rating);

prodetails_title.setText(getIntent().getStringExtra("product_name"));


        delivery_inst_layout= findViewById(R.id.delivery_inst_layout);
        washing_inst_layout= findViewById(R.id.washing_inst_layout);
        material_inst_layout= findViewById(R.id.material_inst_layout);
        special_inst_layout= findViewById(R.id.special_inst_layout);





//        submit_review = findViewById(R.id.submit_review);
        prodetails_togglelayout = findViewById(R.id.prodetails_togglelayout);
        prodetails_selectcolor = findViewById(R.id.prodetails_selectcolor);
        prodetails_colorlayout = findViewById(R.id.prodetails_colorlayout);
        prodetails_sizerecycler = findViewById(R.id.prodetails_sizerecycler);
        LinearLayoutManager manager111=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        prodetails_sizerecycler .setLayoutManager(manager111);
        product_detail_desc = findViewById(R.id.product_detail_desc);
        prodetails_size_txt= findViewById(R.id.prodetails_size_txt);
        prodetails_size_txt_id= findViewById(R.id.prodetails_size_txt_id);

        prodetails_specialinst = findViewById(R.id.prodetails_specialinst);
        prodetails_deliveryinst = findViewById(R.id.prodetails_deliveryinst);

        product_detail_img = findViewById(R.id.product_detail_img);
        product_detail_title = findViewById(R.id.product_detail_title);
        product_detail_category = findViewById(R.id.product_detail_category);
        product_detail_price = findViewById(R.id.product_detail_price);
        addtofav = findViewById(R.id.detail_addtofav);

        prolist_isfav = findViewById(R.id.prolist_isfav);
        prodetail_ratngbar = findViewById(R.id.prodetail_ratngbar);

        prolist_isfav = findViewById(R.id.prodetails_isfav);
        detail_addtofav = findViewById(R.id.related_addtofav);

        prodetails_similar_recycler = findViewById(R.id.prodetails_similar_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager manager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager manager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        prodetails_similar_recycler.setLayoutManager(manager);
        prodetails_colorlayout.setLayoutManager(manager1);
//        prodetails_sizerecycler.setLayoutManager(manager2);


//        prodetails_colorlayout_root=findViewById(R.id.prodetails_colorlayout_root);
        prodetails_selectsize_icon = findViewById(R.id.prodetails_selectsize_icon);
        prodetails_selectcolor_icon = findViewById(R.id.prodetails_selectcolor_icon);
        selectcolor_icon = findViewById(R.id.selectcolor_icon);
        prodetails_giverating = findViewById(R.id.prodetails_giverating);


        final Intent intent = getIntent();
        if (intent.hasExtra("product_id"))
            product_id = intent.getStringExtra("product_id");

        if (intent.hasExtra("offer_id"))
            offer_id = intent.getStringExtra("offer_id");



        prodetails_giverating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Product_details.this);
                LayoutInflater inflater = Product_details.this.getLayoutInflater();
                View dailgo = inflater.inflate(R.layout.submit_review_dialog, null);
                builder.setView(dailgo);


                final AlertDialog dialog = builder.create();


                Button button = dailgo.findViewById(R.id.dialog_btn_submit);
                final EditText description = dailgo.findViewById(R.id.description);
                final EditText review_text = dailgo.findViewById(R.id.review_title);
                final RatingBar review_statr = dailgo.findViewById(R.id.detail_reviewstar);

                dialog.show();


                review_statr.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            review_statr.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),R.color.colorseleced)));
                            review_statr.getRating();
                        }


                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<GiveRatingResponse> call = apiInterface.give_rating(preferences.getString("user_id", ""), product_id, String.valueOf(review_statr.getRating()), review_text.getText().toString(), description.getText().toString(), vendor_id, preferences.getString("token", ""));
                        call.enqueue(new Callback<GiveRatingResponse>() {
                            @Override
                            public void onResponse(Call<GiveRatingResponse> call, Response<GiveRatingResponse> response) {
                                GiveRatingResponse giveRatingResponse = response.body();
                                List<com.pro.wardrobe.ApiResponse.GiveRatingResponse.Response> responses1 = giveRatingResponse.getResponse();

                                for (int i = 0; i < responses1.size(); i++) {

                                    com.pro.wardrobe.ApiResponse.GiveRatingResponse.Response response2 = responses1.get(i);
                                    Log.e("messge", "" + response2.getStatus());

                                    if (response2.getStatus().equals("true")) {


                                        dialog.dismiss();
//                                        Toast.makeText(getApplicationContext(), ""+response2.getResponseMsg(), Toast.LENGTH_SHORT).show();

                                    }


                                }
                            }

                            @Override
                            public void onFailure(Call<GiveRatingResponse> call, Throwable t) {

                            }
                        });


                    }
                });


            }
        });


        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ProductDetailResponse> call = apiInterface.product_detail(preferences.getString("user_id", ""), product_id, preferences.getString("token", ""));
        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductDetailResponse> call, @NonNull Response<ProductDetailResponse> response) {
mProgressDialog.dismiss();
                Gson gson = new GsonBuilder().create();
                String myCustomArray = gson.toJson(response).toString();
//                String obj=new JsonObject().get(response.toString()).getAsString();
                Log.e("details_Response ", myCustomArray.toString());
                ProductDetailResponse productDetailResponse = response.body();
                List<com.pro.wardrobe.ApiResponse.ProductDetailResponse.Response> responses = productDetailResponse.getResponse();


                final com.pro.wardrobe.ApiResponse.ProductDetailResponse.Response response1 = responses.get(0);
                Log.e("details_status", response1.getStatus());
                if (response1.getStatus().equals("true")) {
                    final List<ProductDetail> productDetails = response1.getProductDetails();
//                        Toast.makeText(getApplicationContext(), "product id" + product_id, Toast.LENGTH_SHORT).show();

                    final ProductDetail productDetail = productDetails.get(0);
                    Glide.with(getApplicationContext()).load(productDetail.getImage()).into(product_detail_img);

                    product_detail_title.setText(productDetail.getTitle());
                    product_detail_category.setText(productDetail.getCategoryName());
                    product_detail_price.setText(productDetail.getPrice() +" QAR");
                    product_detail_desc.setText(productDetail.getDescription());
                    prodetails_rating.setText(productDetail.getRatingCount() + " Reviews");

                    if (productDetail.getMaterial().equals("")) {
                        material_inst_layout.setVisibility(View.GONE);
                    } else {
                        material_inst_layout.setVisibility(View.VISIBLE);
                        prodetails_materialinst.setText(productDetail.getMaterial());
                    }

                    if (productDetail.getWashing_instruction().equals("")) {
                        washing_inst_layout.setVisibility(View.GONE);
                    } else {
                        washing_inst_layout.setVisibility(View.VISIBLE);
                        prodetails_washinginst.setText(productDetail.getWashing_instruction());
                    }






                    vendor_id = productDetail.getVendorId();
                    product_id = productDetail.getProductId();

                    prodetail_ratngbar.setClickable(true);
                    prodetail_ratngbar.setFocusable(true);
                    prodetail_ratngbar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentbag = new Intent(getApplicationContext(), Reviews.class);
                            intentbag.putExtra("product_id",productDetail.getProductId());
                            startActivity(intentbag);
                        }
                    });

                    TextView prodetails_viewall = findViewById(R.id.prodetails_viewall);

                    prodetails_viewall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), Fragment_product_list.class);
                            i.putExtra("vendor_id", vendor_id);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    });

                    prodetail_ratngbar.setRating(Float.parseFloat(productDetail.getRatingAvg()));
                    colorDetails = productDetail.getProductColorDetails();
                    if (colorDetails.size() == 0) {
                        prodetails_colorlayout.setVisibility(View.GONE);
                        prodetails_selectcolor.setClickable(false);
                        fil_color = "";
                        prodetails_selectcolor.setFocusable(false);
                    } else {
                        prodetails_colorlayout.setAdapter(new colorPickerAdapter(getApplicationContext(), colorDetails));
                    }
                    final List<ProductSizeDetail> size = productDetail.getProductSizeDetails();

                    FilterSizePickerAdapter adapter=new FilterSizePickerAdapter(size);
                    prodetails_sizerecycler.setAdapter(adapter);
                    /*for (int i = 0; i < size.size(); i++) {
                        ProductSizeDetail siz = size.get(i);
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
                        tQty.setTag(siz.getProductSizeId());
                        layout1.addView(tQty);

                        layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                layout1.setBackground(getResources().getDrawable(R.drawable.login_facebook_blue_border));
                                tQty.setTextColor(Color.parseColor("#ffffff"));
                                fil_size = tQty.getTag().toString();
                                prodetails_togglelayout.setVisibility(View.GONE);
                            }
                        });

                        prodetails_sizerecycler.addView(layout);
                    }*/

                    prodetails_sizelayout = findViewById(R.id.prodetails_sizelayout);
                    prodetails_selectsizelayout = findViewById(R.id.prodetails_selectsizelayout);
                    prodetails_selectcolor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (colorDetails.size()==0){
                                Toast.makeText(Product_details.this, "No Color Available", Toast.LENGTH_SHORT).show();
                            }
//               if (x == 0) {
              /* prodetails_togglelayout.setVisibility(View.VISIBLE);
               prodetails_sizelayout.setVisibility(View.GONE);
               prodetails_colorlayout.setVisibility(View.VISIBLE);*/
                            if (prodetails_togglelayout.getVisibility() == View.VISIBLE) {
                                prodetails_togglelayout.setVisibility(View.GONE);
                  /*  prodetails_sizelayout.setVisibility(View.GONE);
                    prodetails_colorlayout.setVisibility(View.GONE);*/
                                prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                                prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                            }

                            else if (prodetails_togglelayout.getVisibility() == View.GONE) {
                                prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                                prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                                prodetails_togglelayout.setVisibility(View.VISIBLE);
                                prodetails_sizerecycler.setVisibility(View.GONE);
                                prodetails_colorlayout.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    prodetails_selectsizelayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (size.size()==0){
                                Toast.makeText(Product_details.this, "No Size Available", Toast.LENGTH_SHORT).show();
                            }
//               if (x == 0) {
//                   x = 1;
                 /*  prodetails_togglelayout.setVisibility(View.VISIBLE);
                   prodetails_sizelayout.setVisibility(View.VISIBLE);
                   prodetails_colorlayout.setVisibility(View.GONE);*/
                            if (prodetails_togglelayout.getVisibility() == View.VISIBLE) {
                                prodetails_togglelayout.setVisibility(View.GONE);
                    /*prodetails_sizelayout.setVisibility(View.GONE);
                    prodetails_colorlayout.setVisibility(View.GONE);*/
                                prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                                prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                            }
                            else if (prodetails_togglelayout.getVisibility() == View.GONE) {
                                prodetails_selectsize_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                                prodetails_selectcolor_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                                prodetails_togglelayout.setVisibility(View.VISIBLE);
                                prodetails_sizerecycler.setVisibility(View.VISIBLE);
                                prodetails_colorlayout.setVisibility(View.GONE);
                            }
                        }
                    });

                    prodetails_addtobag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (size.size() > 0 && fil_size.equals("null")) {
                                Toast.makeText(Product_details.this, "Please select size first", Toast.LENGTH_SHORT).show();
                            } else if (colorDetails.size() > 0 && fil_color.equals("null")) {
                                Toast.makeText(Product_details.this, "PLease select color first", Toast.LENGTH_SHORT).show();
                            } else {

                                Log.e("user id", preferences.getString("user_id", ""));
                                Log.e("product id", productDetail.getProductId());
                                Log.e("Qty", "1");
                                Log.e("size iD", fil_size);
                                Log.e("color iD", fil_color);
                                Log.e("length", prodetails_length.getText().toString());
                                Log.e("hips", prodetails_hips.getText().toString());

//                                if (fil_color.equals("")) {
                                if (colorDetails.size() == 0 && size.size() > 0) {
                                    Call<ResponseAddToCart>   callcartsize = apiInterface.add_to_cart_size(preferences.getString("user_id", ""),
                                            productDetail.getProductId(), "1", fil_size
                                            , prodetails_length.getText().toString(), prodetails_hips.getText().toString(), preferences.getString("token", ""));

                                    callcartsize.enqueue(new Callback<ResponseAddToCart>() {
                                        @Override
                                        public void onResponse(@NonNull Call<ResponseAddToCart> call, @NonNull Response<ResponseAddToCart> response) {
                                            Gson gson = new GsonBuilder().create();
                                            String myCustomArray = gson.toJson(response.body()).toString();
                                            Log.e("addToCartResponse", String.valueOf(response));
                                            ResponseAddToCart addToCart = response.body();

                                            List<com.pro.wardrobe.ApiResponse.AddToCartResponse.Response> reslist = addToCart.getResponse();
                                            com.pro.wardrobe.ApiResponse.AddToCartResponse.Response response2 = reslist.get(0);
                                            if (response2.getStatus().equals("true")) {
                                                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                                                startActivity(intentbag);
                                                Toast.makeText(Product_details.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(Product_details.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseAddToCart> call, Throwable t) {
                                            Call<ProductListResponse> call1 = apiInterface.product_list(preferences.getString("user_id", ""), "0", category_id, vendor_id, offer_id, preferences.getString("token", ""));

                                        }
                                    });
                                } else if (size.size() == 0 && colorDetails.size() > 0) {
                                    Call<ResponseAddToCart>   callcartcolor = apiInterface.add_to_cart_color(preferences.getString("user_id", ""),
                                            productDetail.getProductId(), "1", fil_color
                                            , prodetails_length.getText().toString(), prodetails_hips.getText().toString(), preferences.getString("token", ""));

                                    callcartcolor.enqueue(new Callback<ResponseAddToCart>() {
                                        @Override
                                        public void onResponse(@NonNull Call<ResponseAddToCart> call, @NonNull Response<ResponseAddToCart> response) {
                                            Gson gson = new GsonBuilder().create();
                                            String myCustomArray = gson.toJson(response.body()).toString();
                                            Log.e("addToCartResponse", String.valueOf(response));
                                            ResponseAddToCart addToCart = response.body();

                                            List<com.pro.wardrobe.ApiResponse.AddToCartResponse.Response> reslist = addToCart.getResponse();
                                            com.pro.wardrobe.ApiResponse.AddToCartResponse.Response response2 = reslist.get(0);
                                            if (response2.getStatus().equals("true")) {
                                                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                                                startActivity(intentbag);
                                                Toast.makeText(Product_details.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(Product_details.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseAddToCart> call, Throwable t) {
                                            Call<ProductListResponse> call1 = apiInterface.product_list(preferences.getString("user_id", ""), "0", category_id, vendor_id, offer_id, preferences.getString("token", ""));

                                        }
                                    });
                                } else if (size.size() > 0 && colorDetails.size() > 0) {
                                    Call<ResponseAddToCart>   callcart = apiInterface.add_to_cart(preferences.getString("user_id", ""),
                                            productDetail.getProductId(), "1", fil_size, fil_color
                                            , prodetails_hips.getText().toString(), prodetails_length.getText().toString(), preferences.getString("token", ""));
                                    callcart.enqueue(new Callback<ResponseAddToCart>() {
                                        @Override
                                        public void onResponse(@NonNull Call<ResponseAddToCart> call, @NonNull Response<ResponseAddToCart> response) {
                                            Gson gson = new GsonBuilder().create();
                                            String myCustomArray = gson.toJson(response.body()).toString();
                                            Log.e("addToCartResponse", String.valueOf(response));
                                            ResponseAddToCart addToCart = response.body();

                                            List<com.pro.wardrobe.ApiResponse.AddToCartResponse.Response> reslist = addToCart.getResponse();
                                            com.pro.wardrobe.ApiResponse.AddToCartResponse.Response response2 = reslist.get(0);
                                            if (response2.getStatus().equals("true")) {
                                                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                                                startActivity(intentbag);
                                                Toast.makeText(Product_details.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(Product_details.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseAddToCart> call, Throwable t) {
                                            Call<ProductListResponse> call1 = apiInterface.product_list(preferences.getString("user_id", ""), "0", category_id, vendor_id, offer_id, preferences.getString("token", ""));

                                        }
                                    });
                                }
                                /*callcart.enqueue(new Callback<ResponseAddToCart>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ResponseAddToCart> call, @NonNull Response<ResponseAddToCart> response) {
                                        Gson gson = new GsonBuilder().create();
                                        String myCustomArray = gson.toJson(response.body()).toString();
                                        Log.e("addToCartResponse", String.valueOf(response));
                                        ResponseAddToCart addToCart = response.body();

                                        List<com.pro.wardrobe.ApiResponse.AddToCartResponse.Response> reslist = addToCart.getResponse();
                                        com.pro.wardrobe.ApiResponse.AddToCartResponse.Response response2 = reslist.get(0);
                                        if (response2.getStatus().equals("true")) {
                                            Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                                            startActivity(intentbag);
                                            Toast.makeText(Product_details.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(Product_details.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseAddToCart> call, Throwable t) {
                                        Call<ProductListResponse> call1 = apiInterface.product_list(preferences.getString("user_id", ""), "0", category_id, vendor_id, offer_id, preferences.getString("token", ""));

                                    }
                                });*/



                          /*      final OkHttpClient client = new OkHttpClient();

                                MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
                                RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
                                        "name=\"user_id\"\r\n\r\n"+preferences.getString("user_id","")+
                                        "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
                                        "name=\"product_id\"\r\n\r\n"+productDetail.getProductId()+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
                                        "name=\"quantity\"\r\n\r\n"+1+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
                                        "name=\"product_size_id\"\r\n\r\n"+fil_size+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data;" +
                                        " name=\"product_color_id\"\r\n\r\n"+fil_color+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
                                        "name=\"length\"\r\n\r\n"+prodetails_length.getText().toString()+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
                                        "name=\"hips\"\r\n\r\n"+prodetails_hips.getText().toString()+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
                                final Request request = new Request.Builder()
                                        .url(APIClient.BASE_URL1+"add_to_cart")
                                        .post(body)
                                        .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                                        .addHeader("Authorization", preferences.getString("token",""))
                                        .addHeader("cache-control", "no-cache")
                                        .addHeader("Postman-Token", "1050d892-ecd1-4a13-9d19-b186c694ddec")
                                        .build();

                                    new AsyncTask<Void, Void, String>() {
                                         protected String doInBackground(Void... params) {
                                            // Background Code
                                            okhttp3.Response response = null;
                                            try {
                                                response = client.newCall(request).execute();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            Log.e("addToCartResponse",response.toString());
                                        return response.toString();
                                         }


                                    }.execute();
*/


                            }
                        }
                    });
                    if (productDetail.getDelivery_instruction().equals("")) {
                        delivery_inst_layout.setVisibility(View.GONE);
                    } else {
                        delivery_inst_layout.setVisibility(View.GONE);
                        prodetails_deliveryinst.setText(productDetail.getDelivery_instruction());
                    }

                    if (productDetail.getSpecial_instruction().equals("")) {
                        special_inst_layout.setVisibility(View.GONE);
                    } else {
                     special_inst_layout.setVisibility(View.VISIBLE);
                        prodetails_specialinst.setText(productDetail.getSpecial_instruction());
                    }
                    Call<ProductListResponse> call1 = apiInterface.product_list(preferences.getString("user_id", ""), "0", category_id, "", offer_id, preferences.getString("token", ""));
                    final ProgressDialog mProgressDialog = new ProgressDialog(Product_details.this, R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();

                    call1.enqueue(new Callback<ProductListResponse>() {
                        @Override
                        public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {

                            ProductListResponse productListResponse = response.body();
                            List<com.pro.wardrobe.ApiResponse.ProductListResponse.Response> responses = productListResponse.getResponse();

                            for (int i = 0; i < responses.size(); i++) {
                                com.pro.wardrobe.ApiResponse.ProductListResponse.Response response1 = responses.get(i);


                                if (response1.getStatus().equals("true")) {
                                    List<ProductList> productLists = response1.getProductList();

                                    List<ProductList> prolist=new ArrayList<>();
                                    for (int x=0;x<productLists.size();x++){
                                        if (Integer.parseInt(productDetail.getProductId())!=Integer.parseInt(productLists.get(x).getProductId())){
                                            prolist.add(productLists.get(x));
                                        }
                                    }

mProgressDialog.dismiss();
                                    ProductList_Adapter productList_adapter = new ProductList_Adapter(productLists, getApplicationContext(), new Fragment_product_list(),-1,0);
                                    prodetails_similar_recycler.setAdapter(productList_adapter);
//                                    productList_adapter.notifyItemRemoved(0);


                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<ProductListResponse> call, Throwable t) {

                        }
                    });


                    prolist_isfav.setText(productDetail.getIsFav());
                    if (Integer.parseInt(productDetail.getIsFav()) == 0) {
                        addtofav.setImageDrawable(getResources().getDrawable(R.drawable.favourite));
                    } else
                        addtofav.setImageDrawable(getResources().getDrawable(R.drawable.heart_filled));

                } else
                    Toast.makeText(Product_details.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

            }
        });


        addtofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Integer.parseInt(prolist_isfav.getText().toString()) == 0) {
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

        /*submit_review.setOnClickListener(new View.OnClickListener() {
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
        });*/

      

        /* colorPickerAdapter colorPickerAdapter=new colorPickerAdapter(getApplicationContext());
        prodetails_selectcolor.setAdapter(colorPickerAdapter);*/
    }


    public void myBagApiCall() throws NumberFormatException {
        final ProgressDialog mProgressDialog = new ProgressDialog(Product_details.this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseCartList> call = apiInterface.cart_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ResponseCartList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCartList> call, @NonNull Response<ResponseCartList> response) {
                try {
                    mProgressDialog.dismiss();
                    Gson gson = new GsonBuilder().create();
                    String myCustomArray = gson.toJson(response).toString();
                    Log.e("BagResponse", myCustomArray);
                    ResponseCartList res = response.body();
                    List<com.pro.wardrobe.ApiResponse.CartListResponse.Response> resList = res.getResponse();
                    com.pro.wardrobe.ApiResponse.CartListResponse.Response response1 = resList.get(0);
                    if (response1.getStatus().equals("true")) {
                        List<CartList> cartList = response1.getCartList();
                        bag_product_count.setText(String.valueOf(cartList.size()));

                    } else {
//                        Toast.makeText(getApplicationContext(), "Oops, No result found..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) throws NumberFormatException {
//                Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_SHORT).show();
                Log.e("BagError", t.getMessage());
            }
        });
    }
    class colorPickerAdapter extends RecyclerView.Adapter<colorPickerAdapter.ViewHolder> {

        Context context;
        List<ProductColorDetail> colorDetails;

        public colorPickerAdapter(Context context, List<ProductColorDetail> colorDetails) {
            this.context = context;
            this.colorDetails = colorDetails;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.color_picker_spinner_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            ProductColorDetail color = colorDetails.get(i);

            RoundRectShape roundRectShape = new RoundRectShape(new float[]{10, 10, 10, 10, 10, 10, 10, 10}, null, new float[]{10, 10, 10, 10, 10, 10, 10, 10});
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(Color.parseColor(color.getColorCode()));

            if (i == position) {
                viewHolder.colorpicker_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
            } else {
                viewHolder.colorpicker_imageView.setImageDrawable(null);
            }
            final GradientDrawable gD = new GradientDrawable();
            gD.setColor(Color.parseColor(color.getColorCode()));
            gD.setShape(GradientDrawable.OVAL);
            gD.setStroke(1, Color.parseColor("#ffffff"));
            gD.setSize(62, 62);
            viewHolder.colorpicker_tv.setText(color.getProductColorId());

            viewHolder.colorpicker_imageView.setBackground(gD);

            viewHolder.colorpicker_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = i;
                    viewHolder.colorpicker_imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
                    prodetails_togglelayout.setVisibility(View.GONE);
                    selectcolor_icon.setImageDrawable(gD);
                    notifyDataSetChanged();
                    fil_color = viewHolder.colorpicker_tv.getText().toString();
                }
            });
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return colorDetails.size();
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


    SharedPreferences preferences;

    public void apiCll() {


    }
    int pos=-1;
    class FilterSizePickerAdapter extends RecyclerView.Adapter<FilterSizePickerAdapter.ViewHolder> {

        List<ProductSizeDetail> sizests;

        public FilterSizePickerAdapter(List<ProductSizeDetail> sizests) {
            this.sizests = sizests;
        }

        @NonNull
        @Override
        public FilterSizePickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new FilterSizePickerAdapter.ViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_size_layout, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final FilterSizePickerAdapter.ViewHolder viewHolder, final int i) {
            ProductSizeDetail sizeList = sizests.get(i);
            viewHolder.filter_size_txt.setText(sizeList.getSize());
            viewHolder.filter_size_txt_id.setText(sizeList.getProductSizeId());

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
                    prodetails_togglelayout.setVisibility(View.GONE);
                    prodetails_size_txt.setText(viewHolder.filter_size_txt.getText().toString());
                    prodetails_size_txt_id.setText(viewHolder.filter_size_txt_id.getText().toString());
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

}
