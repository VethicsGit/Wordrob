package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pro.wardrobe.ApiResponse.CateListResponse.CategoryList;
import com.pro.wardrobe.Fragment.Fragment_product_list;
import com.pro.wardrobe.R;

import java.util.List;

public class Category_adapter extends RecyclerView.Adapter<Category_adapter.ViewHolder> {


    Context context;
    private List<CategoryList> categories;

    public Category_adapter(Context context, List<CategoryList> categories) {
        this.context = context;
        this.categories=categories;
    }

    public static final int Item_odd=0;
    public static final int Item_even=1;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_layout,null));
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 ==0) {
            return Item_odd;
        } else {
            return Item_even;
        }
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        CategoryList category=categories.get(i);
        final int itemType = getItemViewType(i);

        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "Philosopher_Bold.ttf");
        viewHolder.left_txt.setTypeface(face);
        viewHolder.right_txt.setTypeface(face);
        if (itemType==Item_odd){
            viewHolder.right_texted.setVisibility(View.GONE);
            viewHolder.left_txt.setText(category.getCategoryName());

        }else {
            viewHolder.left_texted.setVisibility(View.GONE);
            viewHolder.right_txt.setText(category.getCategoryName());
        }

        viewHolder.category_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,Fragment_product_list.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView left_texted;
        CardView right_texted;
        TextView left_txt;
        TextView right_txt;
        LinearLayout category_item_root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

                    left_texted =itemView.findViewById(R.id.left_texted);
                    right_texted=itemView.findViewById(R.id.right_texted);
                    left_txt=itemView.findViewById(R.id.left_txt);
                    right_txt=itemView.findViewById(R.id.right_text);
            category_item_root=itemView.findViewById(R.id.category_item_root);
        }
    }
}
