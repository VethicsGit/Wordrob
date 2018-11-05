package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.FavoriteProductListResponse.FavProductList;
import com.pro.wardrobe.ApiResponse.RemoveToFavorite.RemoveToFavorite;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite_adapter extends RecyclerView.Adapter<Favorite_adapter.ViewHolder> {

    Context context;
    List<FavProductList>favProductLists;

//    public Favorite_adapter(Context context) {
//        this.context = context;
//    }

    public Favorite_adapter(List<FavProductList> favProductLists, Context context) {

        this.context=context;
        this.favProductLists=favProductLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_item_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final FavProductList favProductList=favProductLists.get(i);
        Glide.with(context).load(favProductList.getImage()).into(viewHolder.fav_product_img);
        viewHolder.fav_product_title.setText(favProductList.getTitle());
        viewHolder.fav_product_title_id.setText(favProductList.getProductId());
        viewHolder.fav_product_category.setText(favProductList.getCategoryName());
        viewHolder.fav_product_price.setText(favProductList.getPrice());


        viewHolder.fav_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Product_details.class);
                i.putExtra("product_id", viewHolder.fav_product_title_id.getText().toString());
                i.putExtra("product_name", viewHolder.fav_product_title.getText().toString());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        viewHolder.fav_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                favProductLists.get(i);

                favProductLists.remove(i);

                notifyItemRemoved(i);

                notifyItemRangeChanged(i,favProductLists.size());


                notifyDataSetChanged();


                final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
//                    notifyDataSetChanged();
//                        product_removetofav.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                Toast.makeText(context, "remove", Toast.LENGTH_SHORT).show();


                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<RemoveToFavorite> call = apiInterface.remove_fav(preferences.getString("user_id", ""), favProductList.getProductId(), preferences.getString("token", ""));
                call.enqueue(new Callback<RemoveToFavorite>() {
                    @Override
                    public void onResponse(Call<RemoveToFavorite> call, Response<RemoveToFavorite> response) {
                        RemoveToFavorite removeToFavorite = response.body();
                        List<com.pro.wardrobe.ApiResponse.RemoveToFavorite.Response> responses = removeToFavorite.getResponse();

                        com.pro.wardrobe.ApiResponse.RemoveToFavorite.Response response1 = responses.get(0);
                                       /* if (response1.getStatus().equals("true")){
                                            product_list.apiCll();
                                        }*/

                    }

                    @Override
                    public void onFailure(Call<RemoveToFavorite> call, Throwable t) {

                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return favProductLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fav_product_img,fav_delet;
        TextView fav_product_price,fav_product_category,fav_product_title,fav_product_title_id;
        SwipeLayout fav_item_root;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            fav_product_img=itemView.findViewById(R.id.fav_product_img);
            fav_product_category=itemView.findViewById(R.id.fav_product_category);
            fav_product_price=itemView.findViewById(R.id.fav_product_price);
            fav_product_title=itemView.findViewById(R.id.fav_product_title);
            fav_delet=itemView.findViewById(R.id.fav_delet);
            fav_item_root=itemView.findViewById(R.id.fav_item_root);
            fav_product_title_id=itemView.findViewById(R.id.fav_product_title_id);

        }




    }
}
