package com.pro.wardrobe.Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Activity.Filter;
import com.pro.wardrobe.Adapter.Designers_Recyclarview_adapter;
import com.pro.wardrobe.Adapter.horizontalScrollAdapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.AddbannerResponse.Addbanner;
import com.pro.wardrobe.ApiResponse.AddbannerResponse.Value;
import com.pro.wardrobe.ApiResponse.CateListResponse.CatelistResponse;
import com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Designercategory;
import com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.ProductTypeList;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.Designerimages;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.VendorList;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.AdapterView.*;

public class Fragment_Designers extends Fragment {

    TextView designer_filter,viewall,designer;

    int Dashboard;
    SearchView search;
    LinearLayout top_layout;
    private BroadcastReceiver mMessageReceiver;
    private Context broadcastManager;

    public Fragment_Designers() {
    }
    TextView title;
    @SuppressLint("ValidFragment")
    public Fragment_Designers(int dashboard,TextView title) {
        Dashboard = dashboard;
        this.title=title;
    }

    RecyclerView designers_recyclarview;
    ImageView designers_list_filter;
    ImageView designers_grid_filter;
    ImageView designer_banner;
    Designers_Recyclarview_adapter designers_recyclarview_adapter;
    horizontalScrollAdapter horizontalScrollAdapter;

    final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    String product_id;


    int[] icons = new int[]{R.drawable.gucci, R.drawable.prada, R.drawable.zara, R.drawable.gucci, R.drawable.prada, R.drawable.zara, R.drawable.gucci, R.drawable.prada, R.drawable.zara, R.drawable.gucci, R.drawable.prada, R.drawable.zara, R.drawable.gucci, R.drawable.prada, R.drawable.zara,

    };

    CardView designers_header;

    RecyclerView home_horizontal_view;

//    String[] cates=new String[]{"Featured","Footwear","Accessories"};

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer, container, false);
        designer_filter = view.findViewById(R.id.designer_filter);
        top_layout=view.findViewById(R.id.top_layout);
        search=view.findViewById(R.id.search);
        designer_banner=view.findViewById(R.id.designer_banner);
        designers_list_filter = view.findViewById(R.id.designers_list_filter);
        designers_grid_filter = view.findViewById(R.id.designers_grid_filter);
//        designers_header = view.findViewById(R.id.designers_header);
        viewall=view.findViewById(R.id.view);
        designer=view.findViewById(R.id.designer);

        ((Dashboard)getActivity()).toggle(3);
        ((Dashboard)getActivity()).setFrament(1);
        designer_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Filter.class);
                startActivity(i);
            }
        });
        fatchcategory("");

        home_horizontal_view=view.findViewById(R.id.designer_horizontal_view);
//        final horizontalScrollAdapter adapter1=new horizontalScrollAdapter(cates,getActivity());
//        home_horizontal_view.setAdapter(adapter1);
        LinearLayoutManager manager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        home_horizontal_view.setLayoutManager(manager1);

        if (Dashboard==0){
            designer_banner.setVisibility(View.GONE);
            top_layout.setVisibility(View.GONE);
            designer.setVisibility(View.GONE);
            viewall.setVisibility(View.GONE);
        }
//designers_header.setPreventCornerOverlap(false);


        designers_recyclarview = view.findViewById(R.id.designers_recyclarview);
        designers_recyclarview.setNestedScrollingEnabled(false);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        designers_recyclarview.setLayoutManager(gridLayoutManager);
//        Designers_Recyclarview_adapter adapter = new Designers_Recyclarview_adapter(getActivity(),icons);
//        designers_recyclarview.setAdapter(adapter);

        designers_grid_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                designers_recyclarview.setLayoutManager(gridLayoutManager);
//                Designers_Recyclarview_adapter adapter = new Designers_Recyclarview_adapter(getActivity(),icons);
//                designers_recyclarview.setAdapter(adapter);
            }
        });
        designers_list_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                designers_recyclarview.setLayoutManager(manager);
//                Designers_Recyclarview_adapter adapter = new Designers_Recyclarview_adapter(getActivity(),icons);
//                designers_recyclarview.setAdapter(adapter);
            }
        });

        viewall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                fatchcategory("");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                title.setText("Designers");
//                    ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager, new Fragment_Designers(0,title)).commit();
            }
        });


//        Intent intent=getActivity().getIntent();
//      product_id=intent.getStringExtra("product_type_id");
//        fatchcategory();
        final SharedPreferences preferences = getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
//        APIInterface apiInterface=APIClient.getClient().create(APIInterface.class);
        Call<Addbanner> call=apiInterface.add_banner(preferences.getString("user_id",""),
                preferences.getString("token",""));
        call.enqueue(new Callback<Addbanner>() {
            @Override
            public void onResponse(Call<Addbanner> call, Response<Addbanner> response) {

//                Log.e("banner",response+response.body().toString());
                Addbanner addbanner=response.body();
                List<com.pro.wardrobe.ApiResponse.AddbannerResponse.Response> responseList= addbanner.getResponse();
                com.pro.wardrobe.ApiResponse.AddbannerResponse.Response response1=responseList.get(0);

                if (response1.getStatus().equals("true"))
                {
                    Value value=response1.getValue();
                    Glide.with(getContext()).load(value.getAppBanner()).into(designer_banner);

                }

            }

            @Override
            public void onFailure(Call<Addbanner> call, Throwable t) {

            }
        });



        Call<Designercategory> call2 =apiInterface.design_category(preferences.getString("user_id","")
                ,preferences.getString("token",""));
        call2.enqueue(new Callback<Designercategory>() {
            @Override
            public void onResponse(Call<Designercategory> call, Response<Designercategory> response) {

                Log.e("category","category"+response.body().toString());
                Designercategory catelistResponse=response.body();
                List<com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Response>responses=catelistResponse.getResponse();

                for (int i = 0;i<responses.size();i++){
                    com.pro.wardrobe.ApiResponse.DesignerCategoryResponse.Response response1=responses.get(i);

                    if (response1.getStatus().equals("true"))
                    {
                        final List<ProductTypeList>productTypeLists=response1.getProductTypeList();


//                        intent.getStringExtra("product_type_id");

                  /*      horizontalScrollAdapter = new horizontalScrollAdapter(productTypeLists,getContext(), new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();

                                Log.e("vethisc","vethics");

                                fatchcategory();




                            }
                        });*/


                        horizontalScrollAdapter= new horizontalScrollAdapter(productTypeLists,getContext(),designers_recyclarview);
                        home_horizontal_view.setAdapter(horizontalScrollAdapter);

                    }

                }

            }

            @Override
            public void onFailure(Call<Designercategory> call, Throwable t) {

            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
             designers_recyclarview_adapter.getFilter().filter(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                designers_recyclarview_adapter.getFilter().filter(s);
                return false;
            }
        });
        SearchView.OnCloseListener closeListener = new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                fatchcategory("");
                return false;
            }
        };
        search.setOnCloseListener(closeListener);



        return view;
    }




     /* private  BroadcastReceiver  receiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

                final  String command=intent.getStringExtra("product_type_id");

            broadcastManager.registerReceiver(mMessageReceiver,new IntentFilter("custom"));

        }
    };*/




    private void fatchcategory(String pro_id) {


        Bundle argumnents= getArguments();

        if (argumnents!=null)
        {
            product_id =getArguments().getString("product_type_id");
        }


        final SharedPreferences preferences = getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);

        Call<Designerimages>call1=apiInterface.design_list(preferences.getString("user_id",""),"0",pro_id
                ,preferences.getString("token",""));
        call1.enqueue(new Callback<Designerimages>() {

            @Override
            public void onResponse(Call<Designerimages> call, Response<Designerimages> response) {

//                Log.e("message","darshak"+intent.getStringExtra("product_type_id"));

                Designerimages designerimages=response.body();
                List<com.pro.wardrobe.ApiResponse.DesignerListResponse.Response>responses=designerimages.getResponse();

                for (int i = 0;i<responses.size();i++) {

                    com.pro.wardrobe.ApiResponse.DesignerListResponse.Response response1 = responses.get(i);

                    if (response1.getStatus().equals("true")) {


                        List<VendorList> vendorLists = response1.getVendorList();

                        for (i = 0; i<vendorLists.size(); i++) {


                            designers_recyclarview_adapter = new Designers_Recyclarview_adapter(vendorLists, getContext());
                            designers_recyclarview.setAdapter(designers_recyclarview_adapter);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Designerimages> call, Throwable t) {

            }
        });
    }



}
