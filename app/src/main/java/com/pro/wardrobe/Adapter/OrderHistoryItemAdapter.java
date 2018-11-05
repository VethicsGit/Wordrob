package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.ApiResponse.OrderHistoryResponse.OrderDetail;
import com.pro.wardrobe.R;

import java.util.List;

public class OrderHistoryItemAdapter extends RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder> {


    Context context;
    List<OrderDetail> orderDetailList;

    public OrderHistoryItemAdapter(Context context, List<OrderDetail> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_history_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        OrderDetail orderDetail=orderDetailList.get(i);

        Glide.with(context).load(orderDetail.getImage()).into(viewHolder.order_history_item_img);
        viewHolder.order_history_item_title.setText(orderDetail.getTitle());
        viewHolder.order_history_item_catename.setText(orderDetail.getCategoryName());
        viewHolder.order_history_item_qty.setText(orderDetail.getQuantity());
        viewHolder.order_history_item_price .setText(orderDetail.getPrice()+" QAR");

    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView order_history_item_img;
        TextView order_history_item_title;
        TextView order_history_item_catename;
        TextView order_history_item_qty;
        TextView order_history_item_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_history_item_img = itemView.findViewById(R.id.order_history_item_img);
            order_history_item_title= itemView.findViewById(R.id.order_history_item_title);
            order_history_item_catename= itemView.findViewById(R.id.order_history_item_catename);
            order_history_item_qty= itemView.findViewById(R.id.order_history_item_qty);
            order_history_item_price= itemView.findViewById(R.id.order_history_item_price);
        }
    }
}
