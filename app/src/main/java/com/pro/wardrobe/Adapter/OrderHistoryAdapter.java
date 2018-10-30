package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pro.wardrobe.ApiResponse.OrderHistoryResponse.MyOrderList;
import com.pro.wardrobe.ApiResponse.OrderHistoryResponse.OrderDetail;
import com.pro.wardrobe.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {


    Context context;

    List<MyOrderList> myOrderList;


    public void add(MyOrderList myOrder){
        myOrderList.add(myOrder);
        notifyDataSetChanged();
    }

    public void clear(){
        myOrderList.clear();
    }

    public OrderHistoryAdapter(Context context, List<MyOrderList> myOrderList) {
        this.context = context;
        this.myOrderList = myOrderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_history_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        MyOrderList myOrder = myOrderList.get(i);
        viewHolder.order_id.setText(myOrder.getOrderId());
        viewHolder.order_updatedat.setText(myOrder.getCreatedAt());

        List<OrderDetail> orderDetailList = myOrder.getOrderDetails();


        OrderHistoryItemAdapter orderHistoryItemAdapter = new OrderHistoryItemAdapter(context, orderDetailList);
        viewHolder.order_items_recycler.setAdapter(orderHistoryItemAdapter);


    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView order_id;
        TextView order_updatedat;
        Button order_checkout;
        RecyclerView order_items_recycler;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            order_id = itemView.findViewById(R.id.order_id);
            order_checkout = itemView.findViewById(R.id.order_checkout);
            order_items_recycler = itemView.findViewById(R.id.order_items_recycler);
            final LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            order_items_recycler.setLayoutManager(manager);
            order_updatedat = itemView.findViewById(R.id.order_updatedat);

        }
    }
}
