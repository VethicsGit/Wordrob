package com.pro.wardrobe.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pro.wardrobe.Activity.Dashboard;
import com.pro.wardrobe.Adapter.NotificationAdapter;
import com.pro.wardrobe.R;

public class Fragment_Notification extends Fragment {

    RecyclerView notification_list;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Dashboard)getActivity()).toggle(0);
        view=inflater.inflate(R.layout.fragment_notification,container,false);
        notification_list=view.findViewById(R.id.notification_list);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
notification_list.setLayoutManager(manager);

        NotificationAdapter adapter=new NotificationAdapter(getActivity());
        notification_list.setAdapter(adapter);

        return view;
    }
}
