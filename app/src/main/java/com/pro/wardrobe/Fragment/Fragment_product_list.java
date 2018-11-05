package com.pro.wardrobe.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pro.wardrobe.Activity.Activty_MyBag;
import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Activity.Filter;
import com.pro.wardrobe.Adapter.ProductList_Adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CartListResponse.CartList;
import com.pro.wardrobe.ApiResponse.CartListResponse.ResponseCartList;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductList;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductListResponse;
import com.pro.wardrobe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_product_list extends AppCompatActivity {

    TextView designer_filter;
    ImageView designers_list_filter;
    ImageView designers_grid_filter;
    RecyclerView prolist_recycler;
    TextView title, prolist_title;
    String vendor_id;
    String category_id;
    String offer_id;
    int formation=0;

    ProductList_Adapter productList_adapter;

    SearchView prolist_search;


    public Fragment_product_list() {
    }

   String sortStr="";

    public Fragment_product_list(String sortStr,String vendor_id,String category_id) {
        this.sortStr = sortStr;
        this.vendor_id=vendor_id;
        this.category_id=category_id;
    }

    String[] sorting = new String[]{
            "New to Old",
            "Old to New",
            "Price : High to Low",
            "Price : Low to High",
            "Title : A to Z",
            "Title : Z to A"

    };


    TextView prolist_sort;
    TextView proemptytext;
    TextView bag_product_count;


    @Override
    protected void onResume() {
        super.onResume();
        myBagApiCall();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_list);
        designer_filter = findViewById(R.id.product_filter);
        designers_list_filter = findViewById(R.id.product_list_filter);
        prolist_sort = findViewById(R.id.prolist_sort);
        proemptytext = findViewById(R.id.proemptytext);
        designers_grid_filter = findViewById(R.id.product_grid_filter);
        prolist_title = findViewById(R.id.prolist_title);
        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Roboto_Regular.ttf");
        prolist_title.setTypeface(facebold);

        bag_product_count = findViewById(R.id.bag_product_count);
        ImageView mybag = findViewById(R.id.prolist_Mybag);
        ImageView dashboard_back = findViewById(R.id.prolist_back);
        prolist_search = findViewById(R.id.prolist_search);

       /* ImageView searchIcon = (ImageView)prolist_search.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.mipmap.search);*/

        if (getIntent().hasExtra("category_id")) {
            category_id = getIntent().getStringExtra("category_id");
        }

        if (getIntent().hasExtra("vendor_id")) {
            vendor_id = getIntent().getStringExtra("vendor_id");
        }


        if (getIntent().hasExtra("offer_id")) {
            offer_id = getIntent().getStringExtra("offer_id");

        }


        prolist_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//               productList_adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productList_adapter.getFilter().filter(s);


                return false;
            }
        });
        SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                apiCll();
                return false;
            }
        };
        prolist_search.setOnCloseListener(onCloseListener);


        dashboard_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mybag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbag = new Intent(getApplicationContext(), Activty_MyBag.class);
                startActivity(intentbag);
            }
        });

        designer_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Filter.class);
                startActivityForResult(i, 1);


            }
        });

        prolist_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Fragment_product_list.this);
                builder.setTitle("SORT BY");
                builder.setItems(sorting, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection

                      /*  Intent i=new Intent(Fragment_product_list.this,new Fragment_product_list(sorting[item].replace(" : ", "_").replace(" ", "_"),vendor_id,category_id).getClass());
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/

                        apiCllSorting(sorting[item].replace(" : ", "_").replace(" ", "_").toLowerCase());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        prolist_recycler = findViewById(R.id.prolist_recycler);
        /*RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        prolist_recycler.setLayoutParams(lp);*/
        final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        prolist_recycler.setLayoutManager(gridLayoutManager);
      /*  ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 0);
        prolist_recycler.setAdapter(adapter);
*/
        prolist_recycler.setNestedScrollingEnabled(false);

        designers_grid_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(gridLayoutManager);
                formation=0;
                productList_adapter.notifyDataSetChanged();
               /* ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 0);
                prolist_recycler.setAdapter(adapter);
                */
            }
        });
        designers_list_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formation=1;
                prolist_recycler.setLayoutManager(manager);
                productList_adapter.notifyDataSetChanged();
               /* ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 1);
                prolist_recycler.setAdapter(adapter);
                */
            }
        });

        prolist_recycler.setOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                apiCll();
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });

        Intent intent = getIntent();
        vendor_id = intent.getStringExtra("vendor_id");


        preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (sortStr.equals(""))
        apiCll();
        else apiCllSorting(sortStr);

/*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_product_list,container,false);
        ((Dashboard)getApplicationContext()).toggle(0);
        designer_filter = view.findViewById(R.id.product_filter);
        designers_list_filter = view.findViewById(R.id.product_list_filter);
        designers_grid_filter = view.findViewById(R.id.product_grid_filter);

        designer_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Filter.class);
                startActivity(i);
            }
        });




        prolist_recycler=view.findViewById(R.id.prolist_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        prolist_recycler.setLayoutManager(gridLayoutManager);
        ProductList_Adapter adapter = new ProductList_Adapter(getActivity(),0);
        prolist_recycler.setAdapter(adapter);

        designers_grid_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(gridLayoutManager);
                ProductList_Adapter adapter = new ProductList_Adapter(getActivity(),0);
                prolist_recycler.setAdapter(adapter);
            }
        });
        designers_list_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(manager);
                ProductList_Adapter adapter = new ProductList_Adapter(getActivity(),1);
                prolist_recycler.setAdapter(adapter);
            }
        });

//                Intent i=new Intent(getActivity(),Product_details.class);
//                startActivity(i);


        return view;
    }*/

    }

    int offset = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = offset;

    SharedPreferences preferences;
    APIInterface apiInterface;

    public void apiCll() {


        Call<ProductListResponse> call = apiInterface.product_list(preferences.getString("user_id", ""), String.valueOf(offset), category_id, vendor_id, offer_id, preferences.getString("token", ""));


        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {

                ProductListResponse productListResponse = response.body();
                List<com.pro.wardrobe.ApiResponse.ProductListResponse.Response> responses = productListResponse.getResponse();

                for (int i = 0; i < responses.size(); i++) {
                    com.pro.wardrobe.ApiResponse.ProductListResponse.Response response1 = responses.get(i);


                    if (response1.getStatus().equals("true")) {
                        List<ProductList> productLists = response1.getProductList();


                        if (offset == 0) {


                            productList_adapter = new ProductList_Adapter(productLists, getApplicationContext(), new Fragment_product_list(), -1,formation);
                            prolist_recycler.setAdapter(productList_adapter);
                        } else {
                            for (int x = 0; x < productLists.size(); x++)
                                productList_adapter.add(productLists.get(x));
                        }

                    } else {
                        proemptytext.setVisibility(View.VISIBLE);
                        prolist_recycler.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {

            }
        });

    }

    public void apiCllSorting(String sorting_str) {

//        prolist_recycler.setAdapter(null);

//        Toast.makeText(this, "sort string "+sorting_str, Toast.LENGTH_SHORT).show();

        Call<ProductListResponse> call = apiInterface.product_list_sorting(preferences.getString("user_id", ""), String.valueOf(offset), category_id, vendor_id, sorting_str, preferences.getString("token", ""));


        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {

                ProductListResponse productListResponse = response.body();
                List<com.pro.wardrobe.ApiResponse.ProductListResponse.Response> responses = productListResponse.getResponse();

                for (int i = 0; i < responses.size(); i++) {
                    com.pro.wardrobe.ApiResponse.ProductListResponse.Response response1 = responses.get(i);


                    if (response1.getStatus().equals("true")) {
                        List<ProductList> productLists = response1.getProductList();


                        prolist_recycler.removeAllViews();
                        int size = ((ProductList_Adapter) prolist_recycler.getAdapter()).productLists.size();

                        for (int x = 0; x < size; x++) {
                            ((ProductList_Adapter) prolist_recycler.getAdapter()).notifyItemRemoved(i);
                        }
                        ((ProductList_Adapter) prolist_recycler.getAdapter()).notifyItemRemoved(size);
                        productList_adapter = new ProductList_Adapter(productLists, getApplicationContext(), new Fragment_product_list(), -1,formation);
                        prolist_recycler.setAdapter(productList_adapter);
                        productList_adapter.notifyDataSetChanged();

                    }
                }


            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");

                try {
                    JSONObject obj = new JSONObject(result);

                    final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                    offset=0;

                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<ProductListResponse> call = apiInterface.product_list_filter(preferences.getString("user_id", ""), String.valueOf(offset), obj.getString("cateid"), vendor_id, obj.getString("minprice"), obj.getString("maxprice"), obj.getString("colorid"), obj.getString("sizeid"), preferences.getString("token", ""));


                    call.enqueue(new Callback<ProductListResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ProductListResponse> call, @NonNull Response<ProductListResponse> response) throws NullPointerException {
                            ProductListResponse productListResponse = response.body();
                            List<com.pro.wardrobe.ApiResponse.ProductListResponse.Response> responses = productListResponse.getResponse();

                            for (int i = 0; i < responses.size(); i++) {
                                com.pro.wardrobe.ApiResponse.ProductListResponse.Response response1 = responses.get(i);


                                if (response1.getStatus().equals("true")) {
                                    List<ProductList> productLists = response1.getProductList();


                                    productList_adapter = new ProductList_Adapter(productLists, getApplicationContext(), new Fragment_product_list(), -1,formation);
                                    prolist_recycler.setAdapter(productList_adapter);


                                }
                                else {
                                    Toast.makeText(Fragment_product_list.this, "Oops, no results found!", Toast.LENGTH_SHORT).show();
                                apiCll();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ProductListResponse> call, @NonNull Throwable t) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void myBagApiCall() throws NumberFormatException {
        final ProgressDialog mProgressDialog = new ProgressDialog(Fragment_product_list.this, R.style.AppCompatAlertDialogStyle);
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

    public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

        LinearLayoutManager layoutManager;

        public PaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading() && !isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadMoreItems();
                }
            }
        }

        protected abstract void loadMoreItems();

        public abstract int getTotalPageCount();

        public abstract boolean isLastPage();

        public abstract boolean isLoading();
    }
}
