package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.pro.wardrobe.Activity.Activty_MyBag;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.AddToCartResponse.ResponseAddToCart;
import com.pro.wardrobe.ApiResponse.AddToFavorite.AddToFavorite;
import com.pro.wardrobe.ApiResponse.CartListResponse.CartList;
import com.pro.wardrobe.ApiResponse.CartListResponse.ResponseCartList;
import com.pro.wardrobe.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mybag_list_Adapter extends RecyclerView.Adapter<mybag_list_Adapter.ViewHolder> {
    List<CartList> cartList;
    Context context;
    RecyclerView recyclerView;
    TextView mybag_totalprice;

    int total_price=0;
    public mybag_list_Adapter(Context context, List<CartList> cartList,RecyclerView recyclerView,TextView mybag_totalprice) {
        this.context = context; this.cartList=cartList;
        this.recyclerView=recyclerView;
        this.mybag_totalprice=mybag_totalprice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.mybag_recyclar_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final CartList cart=cartList.get(i);
        Glide.with(context).load(cart.getImage()).into(viewHolder.cartitem_image);
        viewHolder.cartitem_title.setText(cart.getTitle());
        viewHolder.cartitem_category.setText(cart.getCategoryName());
        viewHolder.cartitem_price.setText(cart.getPrice());
        viewHolder.cartitem_qty.setText(cart.getQuantity());
        mybag_totalprice.setText(String.valueOf(Integer.parseInt(mybag_totalprice.getText().toString())+cart.getTotalPrice()));


        viewHolder.cartitem_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateQty(cart.getCartId(),Integer.parseInt(viewHolder.cartitem_qty.getText().toString())+1);
                viewHolder.cartitem_qty.setText(String.valueOf(Integer.valueOf(viewHolder.cartitem_qty.getText().toString())+1));
            }
        });
        viewHolder.cartitem_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(viewHolder.cartitem_qty.getText().toString())>1){
                UpdateQty(cart.getCartId(),Integer.parseInt(viewHolder.cartitem_qty.getText().toString())-1);
                viewHolder.cartitem_qty.setText(String.valueOf(Integer.valueOf(viewHolder.cartitem_qty.getText().toString())-1));


                }


            }
        });

        viewHolder.cartitem_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
RemoveItem(cart.getCartId(),i);
            }
        });

        viewHolder.cartitem_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFav(cart.getProductId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartitem_image;
        ImageView cartitem_plus;
        ImageView cartitem_minus;
        TextView cartitem_title;
        TextView cartitem_category;
        TextView cartitem_price;
        TextView cartitem_qty;
        LinearLayout cartitem_delete;
        LinearLayout cartitem_fav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartitem_image=itemView.findViewById(R.id.cartitem_image);
            cartitem_plus=itemView.findViewById(R.id.cartitem_plus);
            cartitem_minus=itemView.findViewById(R.id.cartitem_minus);
            cartitem_title=itemView.findViewById(R.id.cartitem_title);
            cartitem_category=itemView.findViewById(R.id.cartitem_category);
            cartitem_price=itemView.findViewById(R.id.cartitem_price);
            cartitem_qty=itemView.findViewById(R.id.cartitem_qty);
            cartitem_delete=itemView.findViewById(R.id.cartitem_delete);
            cartitem_fav=itemView.findViewById(R.id.cartitem_fav);
        }
    }

    public interface DynamicHeight {
        void HeightChange(int position, int height);
    }

    public void UpdateQty(
                          String cart_id,
                          int quantity){
        final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ResponseAddToCart> call=apiInterface.update_cart_quantity(preferences.getString("user_id",""),cart_id,String.valueOf(quantity),preferences.getString("token",""));

        call.enqueue(new Callback<ResponseAddToCart>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddToCart> call, @NonNull Response<ResponseAddToCart> response) {

                ResponseAddToCart addToCart = response.body();

                List<com.pro.wardrobe.ApiResponse.AddToCartResponse.Response> reslist = addToCart.getResponse();
                com.pro.wardrobe.ApiResponse.AddToCartResponse.Response response2 = reslist.get(0);
                if (response2.getStatus().equals("true")) {
                    Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseAddToCart> call, @NonNull Throwable t) {

            }
        });

    }

    public void RemoveItem(
            String cart_id, final int pos){
        final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ResponseAddToCart> call=apiInterface.remove_from_cart(preferences.getString("user_id",""),cart_id,preferences.getString("token",""));

        call.enqueue(new Callback<ResponseAddToCart>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddToCart> call, @NonNull Response<ResponseAddToCart> response) {

                ResponseAddToCart addToCart = response.body();

                List<com.pro.wardrobe.ApiResponse.AddToCartResponse.Response> reslist = addToCart.getResponse();
                com.pro.wardrobe.ApiResponse.AddToCartResponse.Response response2 = reslist.get(0);
                if (response2.getStatus().equals("true")) {
                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                    int s=cartList.size();
                    cartList.clear();
                    notifyItemRemoved(0);
                    for(int i=0;i<s;i++){
                        recyclerView.removeViewAt(i);

                    }
                    myBagApiCall();




                } else
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseAddToCart> call, @NonNull Throwable t) {

            }
        });

    }
    public void myBagApiCall() throws NumberFormatException{
        final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseCartList> call = apiInterface.cart_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ResponseCartList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCartList> call, @NonNull Response<ResponseCartList> response) {
                try {
                    recyclerView.removeAllViews();
                    Gson gson = new GsonBuilder().create();
                    String myCustomArray = gson.toJson(response).toString();
                    Log.e("BagResponse", myCustomArray);
                    ResponseCartList res = response.body();
                    List<com.pro.wardrobe.ApiResponse.CartListResponse.Response> resList = res.getResponse();
                    com.pro.wardrobe.ApiResponse.CartListResponse.Response response1 = resList.get(0);
                    if (response1.getStatus().equals("true")) {

                        List<CartList> cartList = response1.getCartList();
                        mybag_list_Adapter mybag_list_adapter = new mybag_list_Adapter(context, cartList,recyclerView,mybag_totalprice);
                        recyclerView.setAdapter(mybag_list_adapter);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Oops, No result found..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCartList> call, @NonNull Throwable t) throws NumberFormatException {
                Toast.makeText(context, "error ", Toast.LENGTH_SHORT).show();
                Log.e("BagError", t.getMessage());
            }
        });
    }

    public void addToFav(String product_id){
        final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
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
    }

}
