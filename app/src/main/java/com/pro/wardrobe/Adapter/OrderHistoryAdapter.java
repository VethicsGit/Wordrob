package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.ApiResponse.OrderHistoryResponse.MyOrderList;
import com.pro.wardrobe.ApiResponse.OrderHistoryResponse.OrderDetail;
import com.pro.wardrobe.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {


    Context context;

    List<MyOrderList> myOrderList;
    String[] months=new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};

    public void add(MyOrderList myOrder) {
        myOrderList.add(myOrder);
        notifyDataSetChanged();
    }

    public void clear() {
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
        viewHolder.order_id.setText("#"+myOrder.getWardrobeOrderId());


        List<OrderDetail> orderDetailList = myOrder.getOrderDetails();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm k");

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myOrder.getCreatedAt());
            if (date.getHours()>12)
            viewHolder.order_updatedat.setText((date.getDate()) + " "+months[date.getMonth()] + " "+(date.getYear()+1900) +" "+ (date.getHours()-12) +":"+ date.getMinutes()+" pm");
            else
            viewHolder.order_updatedat.setText((date.getDate())+ " "+months[date.getMonth()] + " "+(date.getYear()+1900) +" "+ date.getHours() +":"+ date.getMinutes()+" pm");

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "aksasaj", Toast.LENGTH_SHORT).show();
        }
//        String datef = df.format(date);




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
