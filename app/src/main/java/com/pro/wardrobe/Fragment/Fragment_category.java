package com.pro.wardrobe.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Adapter.Category_adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CateListResponse.CategoryList;
import com.pro.wardrobe.ApiResponse.CateListResponse.CatelistResponse;
import com.pro.wardrobe.ApiResponse.ProfileResponse.ProfileResponse;
import com.pro.wardrobe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_category  extends Fragment {

    RecyclerView category_recycler;
//    String categories[]=new String[]{"Whats new", "Designers"," Clothing","Abayas"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_categories,container,false);
        ((Dashboard)getActivity()).toggle(0);
        ((Dashboard)getActivity()).setFrament(0);
        category_recycler=view.findViewById(R.id.category_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        category_recycler.setLayoutManager(manager);

        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        final SharedPreferences preferences = getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        Call<CatelistResponse> call = apiInterface.category_list(preferences.getString("user_id", ""),
                preferences.getString("token", ""));
        call.enqueue(new Callback<CatelistResponse>() {
            @Override
            public void onResponse(@NonNull Call<CatelistResponse> call, @NonNull Response<CatelistResponse> response) {
/*
                String res=new Gson().toJson(response.body());
Log.e("category_response",res);
                try {
                    JSONObject object=new JSONObject(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                CatelistResponse profileResponse = response.body();
                List<com.pro.wardrobe.ApiResponse.CateListResponse.Response> resList = profileResponse.getResponse();
                com.pro.wardrobe.ApiResponse.CateListResponse.Response res = resList.get(0);

                if (res.getStatus().equals("true")) {
                    mProgressDialog.dismiss();
                    Log.e("cateListSize", res.getCategoryList().toString());
                  List<CategoryList> categoryLists=res.getCategoryList();

                    Category_adapter category_adapter=new Category_adapter(getActivity(),categoryLists);
                    category_recycler.setAdapter(category_adapter);

                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CatelistResponse> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
            }
        });




        return view;
    }
}
