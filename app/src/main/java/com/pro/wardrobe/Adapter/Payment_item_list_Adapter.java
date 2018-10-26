package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.AddToCartResponse.ResponseAddToCart;
import com.pro.wardrobe.ApiResponse.AddToFavorite.AddToFavorite;
import com.pro.wardrobe.ApiResponse.CartListResponse.CartList;
import com.pro.wardrobe.ApiResponse.CartListResponse.ResponseCartList;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment_item_list_Adapter extends RecyclerView.Adapter<Payment_item_list_Adapter.ViewHolder> {
    List<CartList> cartList;
    Context context;
    RecyclerView recyclerView;
    TextView payment_total;
    TextView payment_subtotal;

    int total_price=0;
    public Payment_item_list_Adapter(Context context, List<CartList> cartList,TextView payment_subtotal,TextView payment_total) {
        this.context = context; this.cartList=cartList;
        this.payment_total=payment_total;
        this.payment_subtotal=payment_subtotal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.payment_item_list_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final CartList cart=cartList.get(i);
        viewHolder.cartitem_title.setText(cart.getTitle());
        viewHolder.cartitem_price.setText(cart.getPrice());
        payment_total.setText(String.valueOf(Float.parseFloat(payment_total.getText().toString())+cart.getTotalPrice()));
        payment_subtotal.setText(String.valueOf(Float.parseFloat(payment_subtotal.getText().toString())+cart.getTotalPrice()));


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView cartitem_title;
        TextView cartitem_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartitem_title=itemView.findViewById(R.id.payment_list_item_name);
            cartitem_price=itemView.findViewById(R.id.payment_list_item_price);
        }
    }



}
