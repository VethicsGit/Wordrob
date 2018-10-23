package com.pro.wardrobe.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.Activity.Activty_MyBag;
import com.pro.wardrobe.Activity.Filter;
import com.pro.wardrobe.Adapter.ProductList_Adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
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
    TextView title,prolist_title;
    String vendor_id;
    String category_id;


    ProductList_Adapter productList_adapter;

    SearchView prolist_search;


    public Fragment_product_list() {
    }

    @SuppressLint("ValidFragment")
    public Fragment_product_list(TextView title) {
        this.title = title;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_list);
        designer_filter = findViewById(R.id.product_filter);
        designers_list_filter = findViewById(R.id.product_list_filter);
        designers_grid_filter = findViewById(R.id.product_grid_filter);
        prolist_title = findViewById(R.id.prolist_title);
        Typeface facebold = Typeface.createFromAsset(getAssets(),
                "Philosopher_Bold.ttf");
        prolist_title.setTypeface(facebold);

        ImageView mybag = findViewById(R.id.prolist_Mybag);
        ImageView dashboard_back = findViewById(R.id.prolist_back);
        prolist_search=findViewById(R.id.prolist_search);

        if (getIntent().hasExtra("category_id")){
            category_id=getIntent().getStringExtra("category_id");
        }

        if (getIntent().hasExtra("vendor_id")){
            vendor_id=getIntent().getStringExtra("vendor_id");
        }


       prolist_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               productList_adapter.getFilter().filter(s);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
               productList_adapter.getFilter().filter(s);
               return false;
           }
       });
        SearchView.OnCloseListener onCloseListener=new SearchView.OnCloseListener() {
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
                startActivityForResult(i,1);


            }
        });

        prolist_recycler = findViewById(R.id.prolist_recycler);
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
               /* ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 0);
                prolist_recycler.setAdapter(adapter);
                */
            }
        });
        designers_list_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prolist_recycler.setLayoutManager(manager);
               /* ProductList_Adapter adapter = new ProductList_Adapter(getApplicationContext(), 1);
                prolist_recycler.setAdapter(adapter);
                */
            }
        });


        Intent intent = getIntent();
        vendor_id = intent.getStringExtra("vendor_id");


        preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
apiCll();

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

    SharedPreferences preferences;
    APIInterface apiInterface;
    public void apiCll(){




        Call<ProductListResponse> call = apiInterface.product_list(preferences.getString("user_id", ""), "0",category_id,vendor_id, preferences.getString("token", ""));


        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {

                ProductListResponse productListResponse=response.body();
                List<com.pro.wardrobe.ApiResponse.ProductListResponse.Response>responses=productListResponse.getResponse();

                for (int i=0;i<responses.size();i++)
                {
                    com.pro.wardrobe.ApiResponse.ProductListResponse.Response response1=responses.get(i);


                    if (response1.getStatus().equals("true"))
                    {
                        List<ProductList>productLists=response1.getProductList();



                        ProductList_Adapter productList_adapter=new ProductList_Adapter(productLists,getApplicationContext(),new Fragment_product_list());
                        prolist_recycler.setAdapter(productList_adapter);


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
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

                try {
                    JSONObject obj=new JSONObject(result);

                    final SharedPreferences preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);


                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<ProductListResponse> call = apiInterface.product_list_filter(preferences.getString("user_id", ""), "0",category_id,vendor_id, obj.getString("minprice"), obj.getString("maxprice"), obj.getString("colorid"), obj.getString("sizeid"),preferences.getString("token", ""));


                    call.enqueue(new Callback<ProductListResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ProductListResponse> call, @NonNull Response<ProductListResponse> response) {
                            ProductListResponse productListResponse=response.body();
                            List<com.pro.wardrobe.ApiResponse.ProductListResponse.Response>responses=productListResponse.getResponse();

                            for (int i=0;i<responses.size();i++)
                            {
                                com.pro.wardrobe.ApiResponse.ProductListResponse.Response response1=responses.get(i);


                                if (response1.getStatus().equals("true"))
                                {
                                    List<ProductList>productLists=response1.getProductList();



                                    ProductList_Adapter productList_adapter=new ProductList_Adapter(productLists,getApplicationContext(),new Fragment_product_list());
                                    prolist_recycler.setAdapter(productList_adapter);


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
}
