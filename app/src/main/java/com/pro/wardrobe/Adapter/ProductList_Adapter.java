package com.pro.wardrobe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.R;

public class ProductList_Adapter extends RecyclerView.Adapter<ProductList_Adapter.ViewHolder> {

    Context context;
    int formation;

    public ProductList_Adapter(Context context,int formation) {
        this.context = context;
        this.formation=formation;
    }

    @NonNull
    @Override
    public ProductList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ProductList_Adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list_layout,null));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ProductList_Adapter.ViewHolder viewHolder, int i) {

        if (formation==0){
            viewHolder.prolist_relative.setVisibility(View.GONE);
        }else {
            viewHolder.prolist_linear.setVisibility(View.GONE);
        }

        viewHolder.prodlist_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, Product_details.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_designers_main_image;
        LinearLayout prolist_linear;
        RelativeLayout prolist_relative;
        LinearLayout prodlist_item_root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_designers_main_image=itemView.findViewById(R.id.item_designers_main_image);
            prolist_linear=itemView.findViewById(R.id.prolist_linear);
            prolist_relative=itemView.findViewById(R.id.prolist_relative);
            prodlist_item_root=itemView.findViewById(R.id.prodlist_item_root);
        }
    }
}
