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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductList;
import com.pro.wardrobe.R;

import java.util.List;

public class ProductList_Adapter extends RecyclerView.Adapter<ProductList_Adapter.ViewHolder> {

    Context context;
    List<ProductList> productLists;
    int formation;

   /* public ProductList_Adapter(Context context,int formation) {
        this.context = context;
        this.formation=formation;
    }*/

    public ProductList_Adapter(List<ProductList> productLists, Context applicationContext) {


        this.context=applicationContext;
        this.productLists=productLists;
    }

    @NonNull
    @Override
    public ProductList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ProductList_Adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list_layout,null));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final ProductList_Adapter.ViewHolder viewHolder, int i) {


        ProductList productList=productLists.get(i);
        Glide.with(context).load(productList.getImage()).into(viewHolder.product_list_img);
        viewHolder.product_list_title.setText(productList.getTitle());
        viewHolder.product_list_category.setText(productList.getCategoryName());
        viewHolder.product_list_price.setText(productList.getPrice());
        viewHolder.product_id.setText(productList.getProductId());




        if (formation==0){
            viewHolder.prolist_relative.setVisibility(View.GONE);
        }else {
            viewHolder.prolist_linear.setVisibility(View.GONE);
        }

        viewHolder.prodlist_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, Product_details.class);
                i.putExtra("product_id",viewHolder.product_id.getText().toString());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_designers_main_image,product_list_img;
        TextView product_list_title,product_list_category,product_list_price,product_id;

        LinearLayout prolist_linear;
        RelativeLayout prolist_relative;
        LinearLayout prodlist_item_root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_designers_main_image=itemView.findViewById(R.id.item_designers_main_image);
            prolist_linear=itemView.findViewById(R.id.prolist_linear);
            prolist_relative=itemView.findViewById(R.id.prolist_relative);
            prodlist_item_root=itemView.findViewById(R.id.prodlist_item_root);

            product_list_img=itemView.findViewById(R.id.product_list_img);
            product_list_title=itemView.findViewById(R.id.product_list_title);
            product_list_category=itemView.findViewById(R.id.product_list_category);
            product_list_price=itemView.findViewById(R.id.product_list_price);
            product_id=itemView.findViewById(R.id.product_id);
        }
    }
}
