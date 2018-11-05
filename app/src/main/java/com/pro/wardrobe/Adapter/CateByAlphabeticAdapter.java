package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pro.wardrobe.ApiResponse.CateByAlphabeticResponse.CategoryList;
import com.pro.wardrobe.Extra.FIlterCateByAlphabet;
import com.pro.wardrobe.R;

import java.util.List;

public class CateByAlphabeticAdapter extends RecyclerView.Adapter<CateByAlphabeticAdapter.ViewHolder> {
    Context context;
    List<FIlterCateByAlphabet> categoryListList;
    TextView fil_cateid;
    CardView filter_catelist_card;
    TextView filter_cate_name;

    public CateByAlphabeticAdapter(Context context, List<FIlterCateByAlphabet> categoryListList, TextView fil_cateid, CardView filter_catelist_card, TextView filter_cate_name) {
        this.context = context;
        this.categoryListList = categoryListList;
        this.fil_cateid = fil_cateid;
        this.filter_catelist_card = filter_catelist_card;
        this.filter_cate_name = filter_cate_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_by_alphabetic_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        FIlterCateByAlphabet cate = categoryListList.get(i);
        Log.e("cate_name", cate.getTxt());

        if (cate.getType() == 0) {
            viewHolder.catebyalphabetic_txt_alpha.setVisibility(View.VISIBLE);
            viewHolder.catebyalphabetic_txt.setVisibility(View.GONE);
            viewHolder.catebyalphabetic_txt_alpha.setText(cate.getTxt());

        } else {
            viewHolder.catebyalphabetic_txt_alpha.setVisibility(View.GONE);
            viewHolder.catebyalphabetic_txt.setVisibility(View.VISIBLE);
            viewHolder.catebyalphabetic_txt.setText(cate.getTxt());
            viewHolder.catebyalphabetic_id.setText(cate.getTxt_id());


            viewHolder.catebyalphabetic_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fil_cateid.setText( viewHolder.catebyalphabetic_id.getText().toString());
                    filter_catelist_card.setVisibility(View.GONE);
                    filter_cate_name.setText(viewHolder.catebyalphabetic_txt.getText().toString());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return categoryListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catebyalphabetic_id;
        TextView catebyalphabetic_txt;
        TextView catebyalphabetic_txt_alpha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catebyalphabetic_id = itemView.findViewById(R.id.catebyalphabetic_id);
            catebyalphabetic_txt = itemView.findViewById(R.id.catebyalphabetic_txt);
            catebyalphabetic_txt_alpha = itemView.findViewById(R.id.catebyalphabetic_txt_alpha);

        }
    }
}
