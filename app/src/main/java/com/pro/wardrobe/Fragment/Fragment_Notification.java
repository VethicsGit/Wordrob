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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Adapter.NotificationAdapter;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.NotificationReponse.ResponseNotification;
import com.pro.wardrobe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Notification extends Fragment {

    RecyclerView notification_list;
    View view;


    SharedPreferences preferences;
    APIInterface apiInterface;

    Context c;
    int offset = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = offset;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Dashboard) getActivity()).toggle(0);
        c = getContext();
        preferences = getContext().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        notification_list = view.findViewById(R.id.notification_list);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        notification_list.setLayoutManager(manager);

        apiCall();
        notification_list.addOnScrollListener(new PaginationScrollListener(manager) {
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


        return view;
    }

    NotificationAdapter adapter;

    public void apiCall() {
        final ProgressDialog mProgressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        Call<ResponseNotification> call = apiInterface.activity_list(preferences.getString("user_id", ""), String.valueOf(offset), preferences.getString("token", ""));

        call.enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(Call<ResponseNotification> call, Response<ResponseNotification> response) {
                mProgressDialog.dismiss();
                ResponseNotification responseNotification = response.body();
                List<com.pro.wardrobe.ApiResponse.NotificationReponse.Response> reslist = responseNotification.getResponse();
                com.pro.wardrobe.ApiResponse.NotificationReponse.Response res = reslist.get(0);


                if (res.getStatus().equals("true")) {

                    if (offset == 0) {
                        adapter = new NotificationAdapter(getActivity(), res.getActivityList());
                        notification_list.setAdapter(adapter);
                    } else {
                        for (int i = 0; i < res.getActivityList().size(); i++)
                            adapter.add(res.getActivityList().get(i));
                    }
                    offset=res.getOffset();
                } else {
                    Toast.makeText(c, "Oops! No results found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseNotification> call, Throwable t) {

            }
        });
    }

    public abstract static class PaginationScrollListener extends RecyclerView.OnScrollListener {

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
