package com.pro.wardrobe.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Adapter.Favorite_adapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.FavoriteProductListResponse.FavProductList;
import com.pro.wardrobe.ApiResponse.FavoriteProductListResponse.FavoritieProductListResponse;
import com.pro.wardrobe.Extra.SwipeController;
import com.pro.wardrobe.Extra.SwipeControllerActions;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite extends Fragment {

    ImageView fav_back;
    RecyclerView favorite_recyclar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_favorite,container,false);
        ((Dashboard)getActivity()).toggle(0);
        ((Dashboard)getActivity()).setFrament(4);
//        fav_back=view.findViewById(R.id.fav_back);
        favorite_recyclar=view.findViewById(R.id.favorite_recyclar);

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        favorite_recyclar.setLayoutManager(manager);

       /* final SwipeController  swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
               *//* mAdapter.players.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());*//*
            }
        },getActivity());

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(favorite_recyclar);

        favorite_recyclar.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });*/
        final SharedPreferences preferences = getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);


        APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
        Call<FavoritieProductListResponse>call=apiInterface.fav_product_list(preferences.getString("user_id",""),preferences.getString("token",""));
        call.enqueue(new Callback<FavoritieProductListResponse>() {
            @Override
            public void onResponse(Call<FavoritieProductListResponse> call, Response<FavoritieProductListResponse> response) {

                FavoritieProductListResponse favoritieProductListResponse = response.body();
                List<com.pro.wardrobe.ApiResponse.FavoriteProductListResponse.Response> responses = favoritieProductListResponse.getResponse();


                for (int i = 0; i < responses.size(); i++) {
                    com.pro.wardrobe.ApiResponse.FavoriteProductListResponse.Response response1 = responses.get(i);

                    if (response1.getStatus().equals("true")) {
                        List<FavProductList> favProductLists = response1.getFavProductList();

                        Favorite_adapter favorite_adapter = new Favorite_adapter(favProductLists, getContext());
                        favorite_recyclar.setAdapter(favorite_adapter);

                    }
                }
            }
            @Override
            public void onFailure(Call<FavoritieProductListResponse> call, Throwable t) {

            }
        });







        return view;
    }

}
