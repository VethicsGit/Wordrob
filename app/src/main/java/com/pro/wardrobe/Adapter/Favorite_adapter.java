package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.ApiResponse.FavoriteProductListResponse.FavProductList;
import com.pro.wardrobe.R;

import java.util.List;

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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_item_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final FavProductList favProductList=favProductLists.get(i);
        Glide.with(context).load(favProductList.getImage()).into(viewHolder.fav_product_img);
        viewHolder.fav_product_title.setText(favProductList.getTitle());
        viewHolder.fav_product_category.setText(favProductList.getCategoryName());
        viewHolder.fav_product_price.setText(favProductList.getPrice());


        viewHolder.fav_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                favProductLists.get(i);

                favProductLists.remove(i);

                notifyItemRemoved(i);

                notifyItemRangeChanged(i,favProductLists.size());


                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return favProductLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fav_product_img,fav_delet;
        TextView fav_product_price,fav_product_category,fav_product_title;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            fav_product_img=itemView.findViewById(R.id.fav_product_img);
            fav_product_category=itemView.findViewById(R.id.fav_product_category);
            fav_product_price=itemView.findViewById(R.id.fav_product_price);
            fav_product_title=itemView.findViewById(R.id.fav_product_title);
            fav_delet=itemView.findViewById(R.id.fav_delet);

        }




    }
}
