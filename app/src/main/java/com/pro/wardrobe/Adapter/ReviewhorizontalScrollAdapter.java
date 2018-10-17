package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pro.wardrobe.R;

import java.util.List;

public class ReviewhorizontalScrollAdapter extends RecyclerView.Adapter<ReviewhorizontalScrollAdapter.ViewHolder>{

    List<String> categories;
    Context context;

    public ReviewhorizontalScrollAdapter(List<String> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_category_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {



                viewHolder.home_cate_txt.setText(categories.get(i));

        viewHolder.home_cate_txt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                viewHolder.home_cate_txt.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.home_cate_layout.setBackgroundResource(R.drawable.accent_selected_background);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView home_search;
        TextView home_cate_txt;
        LinearLayout home_cate_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_cate_txt=itemView.findViewById(R.id.home_cate_txt);
            home_search=itemView.findViewById(R.id.home_search);
            home_cate_layout=itemView.findViewById(R.id.home_cate_layout);
        }
    }
}
