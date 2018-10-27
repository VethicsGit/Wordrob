package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.ProductRatingListResponse.MyLikeUnlike;
import com.pro.wardrobe.ApiResponse.ProductRatingListResponse.ProductListRatingResponse;
import com.pro.wardrobe.ApiResponse.ProductRatingListResponse.ProductRatingList;
import com.pro.wardrobe.ApiResponse.SendRatingLikeUnlike.SendRatingResponse;
import com.pro.wardrobe.Fragment.Reviews;
import com.pro.wardrobe.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Reviews_Adapter extends RecyclerView.Adapter<Reviews_Adapter.ViewHolder> {

    Context context;
    List<ProductRatingList>productRatingLists;
    RecyclerView reviews_list;
//    public Reviews_Adapter(Context context) {
//        this.context = context;
//    }

    Reviews reviews;
    public Reviews_Adapter(List<ProductRatingList> productRatingLists, Context applicationContext, RecyclerView reviews) {

        this.context=applicationContext;
        this.productRatingLists=productRatingLists;
        this.reviews_list=reviews;
    }



    public void   setData (List<ProductRatingList>productRatingLists1){
        this.productRatingLists.clear();
        productRatingLists.addAll(productRatingLists1);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.reviews_item_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final ProductRatingList productRatingList=productRatingLists.get(i);

        Glide.with(context).load(productRatingList.getProfilePic()).into(viewHolder.rating_profile);
        viewHolder.rating_username.setText(productRatingList.getName());
        viewHolder.rating_title.setText(productRatingList.getTitle());
        viewHolder.rating_description.setText(productRatingList.getDescription());
        viewHolder.rating_like.setText(productRatingList.getTotalLikeCount());
        viewHolder.rating_unlike.setText(productRatingList.getTotalUnlikeCount());
        viewHolder.rating_star.setRating(Float.parseFloat(productRatingList.getRatingPoint()));
        viewHolder.review_id.setText(productRatingList.getRatingReviewId());



        DateFormat input = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date = input.parse(productRatingList.getCreatedAt());
            DateFormat output = new SimpleDateFormat("dd MMM,yyyy");
            String out=output.format(date);
            viewHolder.rating_time.setText(out);


        } catch (ParseException e) {
            e.printStackTrace();
        }



        List<MyLikeUnlike>myLikeUnlikes=productRatingList.getMyLikeUnlike();


        for ( i=0;i<myLikeUnlikes.size();i++){


            MyLikeUnlike myLikeUnlike=myLikeUnlikes.get(i);

            viewHolder.review_status.setText(myLikeUnlike.getLikeUnlikeStatus());
            if (myLikeUnlike.getLikeUnlikeStatus().equals("0"))
            {

                viewHolder.thumbdown.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_down_fill));
                viewHolder.thumbup.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_up));




            }else if(myLikeUnlike.getLikeUnlikeStatus().equals("1")){

                viewHolder.thumbdown.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_down));
                viewHolder.thumbup.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_up_fill));

            }else{

                viewHolder.thumbdown.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_down));
                viewHolder.thumbup.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_up));

            }


        }




        viewHolder.thumbdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.thumbdown.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_down_fill));
                viewHolder.thumbup.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_up));


                final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);


                APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
                retrofit2.Call<SendRatingResponse>call=apiInterface.rating_likes(preferences.getString("user_id",""),viewHolder.review_id.getText().toString(),"0",preferences.getString("token",""));
                call.enqueue(new Callback<SendRatingResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<SendRatingResponse> call, Response<SendRatingResponse> response) {
                        SendRatingResponse sendRatingResponse =response.body();
                        List<com.pro.wardrobe.ApiResponse.SendRatingLikeUnlike.Response>responses=sendRatingResponse.getResponse();

                        for (int i=0;i<responses.size();i++){

                            com.pro.wardrobe.ApiResponse.SendRatingLikeUnlike.Response response1 =responses.get(i);

                            if (response1.getStatus().equals("true")){
//                                Toast.makeText(context, ""+response1.getResponseMsg(), Toast.LENGTH_SHORT).show();





                                    final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                                    final  APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
                                    retrofit2.Call<ProductListRatingResponse> call2=apiInterface.product_ratinglist(preferences.getString("user_id",""),productRatingList.getProductId(),"new_to_old","0",preferences.getString("token", ""));
                                    Log.e("moo",""+preferences.getString("token",""));
                                    call2.enqueue(new Callback<ProductListRatingResponse>() {


                                        @Override
                                        public void onResponse(retrofit2.Call<ProductListRatingResponse> call, Response<ProductListRatingResponse> response) {

                                            Log.e("pass",""+response);
                                            ProductListRatingResponse productRatingList=response.body();
                                            List<com.pro.wardrobe.ApiResponse.ProductRatingListResponse.Response>responses=productRatingList.getResponse();

                                            for (int i=0;i<responses.size();i++){

                                                com.pro.wardrobe.ApiResponse.ProductRatingListResponse.Response response1=responses.get(i);
                                                Log.e("review","massge"+response1.getStatus());
                                                if (response1.getStatus().equals("true")){
                                                    List<ProductRatingList>productRatingLists=response1.getProductRatingList();

                                                    Reviews_Adapter reviews_adapter1=new Reviews_Adapter(productRatingLists,context,reviews_list);
                                                    reviews_list.setAdapter(reviews_adapter1);




                                                }



                                            }

                                        }

                                        @Override
                                        public void onFailure(retrofit2.Call<ProductListRatingResponse> call, Throwable t) {

                                        }

                                    });

                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<SendRatingResponse> call, Throwable t) {

                    }
                });

            }
        });

        viewHolder.thumbup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.thumbup.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_up_fill));
                viewHolder.thumbdown.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbs_down));



                final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);


                APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
                retrofit2.Call<SendRatingResponse>call=apiInterface.rating_likes(preferences.getString("user_id",""),viewHolder.review_id.getText().toString(),"1",preferences.getString("token",""));
                call.enqueue(new Callback<SendRatingResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<SendRatingResponse> call, Response<SendRatingResponse> response) {
                        SendRatingResponse sendRatingResponse =response.body();
                        List<com.pro.wardrobe.ApiResponse.SendRatingLikeUnlike.Response>responses=sendRatingResponse.getResponse();

                        for (int i=0;i<responses.size();i++){

                            com.pro.wardrobe.ApiResponse.SendRatingLikeUnlike.Response response1 =responses.get(i);

                            if (response1.getStatus().equals("true")){
//                                Toast.makeText(context, ""+response1.getResponseMsg(), Toast.LENGTH_SHORT).show();


                                final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                                final  APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
                                retrofit2.Call<ProductListRatingResponse> call2=apiInterface.product_ratinglist(preferences.getString("user_id",""),productRatingList.getProductId(),"new_to_old","0",preferences.getString("token", ""));
                                Log.e("moo",""+preferences.getString("token",""));
                                call2.enqueue(new Callback<ProductListRatingResponse>() {


                                    @Override
                                    public void onResponse(retrofit2.Call<ProductListRatingResponse> call, Response<ProductListRatingResponse> response) {

                                        Log.e("pass",""+response);
                                        ProductListRatingResponse productRatingList=response.body();
                                        List<com.pro.wardrobe.ApiResponse.ProductRatingListResponse.Response>responses=productRatingList.getResponse();

                                        for (int i=0;i<responses.size();i++){

                                            com.pro.wardrobe.ApiResponse.ProductRatingListResponse.Response response1=responses.get(i);
                                            Log.e("review","massge"+response1.getStatus());
                                            if (response1.getStatus().equals("true")){
                                                List<ProductRatingList>productRatingLists=response1.getProductRatingList();

                                                Reviews_Adapter reviews_adapter1=new Reviews_Adapter(productRatingLists,context,reviews_list);
                                                reviews_list.setAdapter(reviews_adapter1);




                                            }



                                        }

                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<ProductListRatingResponse> call, Throwable t) {

                                    }

                                });


                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<SendRatingResponse> call, Throwable t) {

                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return productRatingLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView rating_profile,thumbup,thumbdown;
        TextView rating_username,rating_title,rating_description,rating_time,rating_unlike,rating_like,review_id,review_status;
        RatingBar rating_star;



    public ViewHolder(@NonNull View itemView) {

        super(itemView);

        rating_profile=itemView.findViewById(R.id.rating_profile);
        rating_username=itemView.findViewById(R.id.rating_username);
        rating_title=itemView.findViewById(R.id.rating_title);
        rating_description=itemView.findViewById(R.id.rating_description);
        rating_time=itemView.findViewById(R.id.rating_time);
        rating_unlike=itemView.findViewById(R.id.rating_unlike);
        rating_like=itemView.findViewById(R.id.rating_like);
        rating_star=itemView.findViewById(R.id.rating_star);
        thumbdown=itemView.findViewById(R.id.thumbdown);
        thumbup=itemView.findViewById(R.id.thumbup);
        review_id=itemView.findViewById(R.id.review_id);
        review_status=itemView.findViewById(R.id.review_status);






    }
}










}

