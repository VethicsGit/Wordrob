package com.pro.wardrobe.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.Adapter.horizontalScrollAdapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Designercategory;
import com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.ProductTypeList;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {

    LinearLayout products_layout;
    TextView home_viewmore;

    TextView title;
    ImageView search;
    horizontalScrollAdapter horizontalScrollAdapter;

    public Fragment_Home() {
    }

    @SuppressLint("ValidFragment")
    public Fragment_Home(TextView title,ImageView search) {
        this.title = title;
        this.search=search;
    }

    RecyclerView home_horizontal_view;

    String[] cates=new String[]{"Featured","Footwear","Accessories"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_fragment,container,false);

        products_layout=view.findViewById(R.id.products_layout);
        home_viewmore=view.findViewById(R.id.home_viewmore);
        home_horizontal_view=view.findViewById(R.id.home_horizontal_view);
//        horizontalScrollAdapter adapter=new horizontalScrollAdapter(getActivity());
//        home_horizontal_view.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        home_horizontal_view.setLayoutManager(manager);


        ((Dashboard)getActivity()).toggle(0);
        ((Dashboard)getActivity()).setFrament(3);
        title.setText("Wardrobe");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

        home_viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                title.setText("Kurtis");
                ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Fragment_product_list(title)).commit();*/

                Intent i=new Intent(getActivity(),Fragment_product_list.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });

        products_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                title.setText("Product name");
                ft.addToBackStack("Home");
                ft.replace(R.id.viewpager, new Product_details(title)).commit();*/
            }
        });
      /*  final SharedPreferences preferences = getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<Designercategory>call2=apiInterface.design_category(preferences.getString("user_id",""),preferences.getString("token",""));
        call2.enqueue(new Callback<Designercategory>() {
            @Override
            public void onResponse(Call<Designercategory> call, Response<Designercategory> response) {
                Designercategory designercategory=response.body();
                List<com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Response> responses=designercategory.getResponse();
                com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Response response1 = responses.get(0);

                if (response1.getStatus().equals("true"))
                {
                    List<ProductTypeList>   productTypeLists=response1.getProductTypeList();


                    horizontalScrollAdapter = new horizontalScrollAdapter(getActivity(),productTypeLists);
                    home_horizontal_view.setAdapter(horizontalScrollAdapter);

                }


            }

            @Override
            public void onFailure(Call<Designercategory> call, Throwable t) {

            }
        });

*/
        return view;
    }

}
