package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pro.wardrobe.ApiResponse.FAQResponse.FaqList;
import com.pro.wardrobe.R;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {

    Context context;
    List<FaqList> faqLists;

    public FAQAdapter(Context applicationContext, List<FaqList> faqList) {
        this.context = applicationContext;
        this.faqLists = faqList;
    }

    @NonNull
    @Override
    public FAQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.faq_item_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FAQAdapter.ViewHolder viewHolder, int i) {

        FaqList faq=faqLists.get(i);
        viewHolder.faq_que.setText(faq.getQuestion());
        viewHolder.faq_ans.setText(faq.getAnswer());

    }

    @Override
    public int getItemCount() {
        return faqLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView faq_que;
        TextView faq_ans;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            faq_que=itemView.findViewById(R.id.faq_que);
            faq_ans=itemView.findViewById(R.id.faq_ans);
        }
    }
}
