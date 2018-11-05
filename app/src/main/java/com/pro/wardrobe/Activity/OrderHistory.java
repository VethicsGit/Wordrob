package com.pro.wardrobe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.Adapter.OrderHistoryAdapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.OrderHistoryResponse.MyOrderList;
import com.pro.wardrobe.ApiResponse.OrderHistoryResponse.ResponseOrderHistory;
import com.pro.wardrobe.Fragment.Fragment_Notification;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistory extends Activity {


    TextView title;
    RecyclerView order_history;


    SharedPreferences preferences;
    APIInterface apiInterface;

    Context c;
    int offset = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = offset;

    OrderHistoryAdapter orderHistoryAdapter;

    ImageView back;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        c = this;
        order_history = findViewById(R.id.order_history);
        title = findViewById(R.id.title);
        back = findViewById(R.id.bottomnav_toolbar_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        preferences = getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        final LinearLayoutManager manager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        order_history.setLayoutManager(manager);

        apiCall();
        order_history.addOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
//                offset += 1;
                apiCall();
            }

            @Override
            public int getTotalPageCount() {
                return offset + 1;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }


        });


        title.setText("ORDER HISTORY");
    }


    public void apiCall() {

        retrofit2.Call<ResponseOrderHistory> call = apiInterface.my_order_list(preferences.getString("user_id", ""), String.valueOf(offset), preferences.getString("token", ""));
        final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        call.enqueue(
                new Callback<ResponseOrderHistory>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseOrderHistory> call, Response<ResponseOrderHistory> response) {
                        ResponseOrderHistory responseOrderHistory = response.body();
                        List<com.pro.wardrobe.ApiResponse.OrderHistoryResponse.Response> reslist = responseOrderHistory.getResponse();
                        mProgressDialog.dismiss();
                        com.pro.wardrobe.ApiResponse.OrderHistoryResponse.Response resorder = reslist.get(0);

                        if (resorder.getStatus().equals("true")) {
                            List<MyOrderList> myOrderList = resorder.getMyOrderList();

                            if (offset == 0) {
                                orderHistoryAdapter = new OrderHistoryAdapter(getApplicationContext(), myOrderList);
                                order_history.setAdapter(orderHistoryAdapter);
                            } else {
                                for (int i = 0; i < resorder.getMyOrderList().size(); i++)
                                    orderHistoryAdapter.add(resorder.getMyOrderList().get(i));
                            }

                            offset = resorder.getOffset();

                        } else {
                            Toast.makeText(OrderHistory.this, "Oops, no results found!", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseOrderHistory> call, Throwable t) {

                    }
                }
        );

    }

    public static abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

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
