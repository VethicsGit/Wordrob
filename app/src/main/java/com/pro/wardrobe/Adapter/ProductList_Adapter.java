package com.pro.wardrobe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.AddToFavorite.AddToFavorite;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.VendorList;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductList;
import com.pro.wardrobe.ApiResponse.RemoveToFavorite.RemoveToFavorite;
import com.pro.wardrobe.Fragment.Fragment_product_list;
import com.pro.wardrobe.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductList_Adapter extends RecyclerView.Adapter<ProductList_Adapter.ViewHolder> {

    Context context;
    List<ProductList> productLists;
    int formation;


   /* public ProductList_Adapter(Context context,int formation) {
        this.context = context;
        this.formation=formation;
    }*/

   Fragment_product_list product_list;
    public ProductList_Adapter(List<ProductList> productLists, Context applicationContext,Fragment_product_list product_list) {
this.product_list=product_list;

        this.context = applicationContext;
        this.productLists = productLists;
    }

    @NonNull
    @Override
    public ProductList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ProductList_Adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list_layout, null));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final ProductList_Adapter.ViewHolder viewHolder, int i) {


        final ProductList productList = productLists.get(i);
        Glide.with(context).load(productList.getImage()).into(viewHolder.product_list_img);
        viewHolder.product_list_title.setText(productList.getTitle());
        viewHolder.product_list_category.setText(productList.getCategoryName());
        viewHolder.product_list_price.setText(productList.getPrice());
        viewHolder.product_id.setText(productList.getProductId());


        if (formation == 0) {
            viewHolder.prolist_relative.setVisibility(View.GONE);
        } else {
            viewHolder.prolist_linear.setVisibility(View.GONE);
        }

        viewHolder.prodlist_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Product_details.class);
                i.putExtra("product_id", viewHolder.product_id.getText().toString());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });






        viewHolder.prolist_isfav.setText(productList.getIsFav());
        if (Integer.parseInt(productList.getIsFav()) == 0) {
            viewHolder.product_addtofav.setImageDrawable(context.getResources().getDrawable(R.drawable.favourite));
        } else
            viewHolder.product_addtofav.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_filled));

        viewHolder.product_addtofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                if (viewHolder.product_addtofav.getDrawable() == context.getResources().getDrawable(R.drawable.favourite)) {
                if (Integer.parseInt(viewHolder.prolist_isfav.getText().toString()) == 0) {
                    viewHolder.prolist_isfav.setText("1");

                    viewHolder.product_addtofav.setImageResource(R.drawable.heart_filled);

//                    notifyDataSetChanged();

                    Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();
                    final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<AddToFavorite> call = apiInterface.add_fav(preferences.getString("user_id", ""), productList.getProductId(), preferences.getString("token", ""));
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

                             /*   if (response1.getStatus().equals("true")){
                                    product_list.apiCll();
                                }*/

                            }
//                        }

                            @Override
                            public void onFailure(Call<AddToFavorite> call, Throwable t) {

                            }
                        });
                } else {
                    viewHolder.prolist_isfav.setText("0");
                    final SharedPreferences preferences = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
//                    notifyDataSetChanged();
//                        product_removetofav.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
                    viewHolder.product_addtofav.setImageResource(R.drawable.favourite);
                    Toast.makeText(context, "remove", Toast.LENGTH_SHORT).show();


                                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                                Call<RemoveToFavorite> call = apiInterface.remove_fav(preferences.getString("user_id", ""), productList.getProductId(), preferences.getString("token", ""));
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
//                        });
//                    }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }



    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productLists = productLists;
                } else {
                    List<ProductList> filteredList = new ArrayList<>();
                    for (ProductList row : productLists) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    productLists = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productLists = (ArrayList<ProductList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_designers_main_image, product_list_img, product_addtofav, product_removetofav;
        TextView product_list_title, product_list_category, product_list_price, product_id,prolist_isfav;
        String str_product_id;
        LinearLayout prolist_linear;
        RelativeLayout prolist_relative;
        LinearLayout prodlist_item_root;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            item_designers_main_image = itemView.findViewById(R.id.item_designers_main_image);
            prolist_linear = itemView.findViewById(R.id.prolist_linear);
            prolist_relative = itemView.findViewById(R.id.prolist_relative);
            prodlist_item_root = itemView.findViewById(R.id.prodlist_item_root);

            product_list_img = itemView.findViewById(R.id.product_list_img);
            product_list_title = itemView.findViewById(R.id.product_list_title);
            product_list_category = itemView.findViewById(R.id.product_list_category);
            product_list_price = itemView.findViewById(R.id.product_list_price);
            product_id = itemView.findViewById(R.id.product_id);

            final String str_product_id = productLists.get(0).getProductId();


            product_addtofav = itemView.findViewById(R.id.product_addtofav);
            prolist_isfav= itemView.findViewById(R.id.prolist_isfav);
            product_removetofav = itemView.findViewById(R.id.product_removetofav);


        }
    }
}
