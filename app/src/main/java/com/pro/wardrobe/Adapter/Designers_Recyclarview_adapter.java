package com.pro.wardrobe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.VendorList;
import com.pro.wardrobe.Fragment.Fragment_product_list;
import com.pro.wardrobe.R;

import java.util.ArrayList;
import java.util.List;

public class Designers_Recyclarview_adapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

    Context context;
    int[] icons;
    List <VendorList> vendorLists,fillterlist;
    CustomFillter customFillter;

   /* public Designers_Recyclarview_adapter(Context context,int[] icons) {
        this.context = context;
        this.icons=icons;
    }*/

    public Designers_Recyclarview_adapter(List<VendorList> vendorList, Context context) {

        this.context=context;
        this.vendorLists=vendorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view=LayoutInflater.from(context).inflate(R.layout.designers_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }


    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {



        VendorList vendorList = vendorLists.get(i);
        Glide.with(context)
                .load(vendorList.getProfilePic())
                .into(viewHolder.item_designers_main_image);

        viewHolder.item_designers_main_image.setOnClickListener(new View.OnClickListener() {
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
        return vendorLists.size();
    }

   /* public Filter getFillter(){
        if (customFillter == null)
        {
            customFillter = new CustomFillter(vendorLists,Designers_Recyclarview_adapter.this);
        }
        return customFillter;
    }*/


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    vendorLists = vendorLists;
                } else {
                    List<VendorList> filteredList = new ArrayList<>();
                    for (VendorList row : vendorLists) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBusinessName().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    vendorLists = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = vendorLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                vendorLists = (ArrayList<VendorList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_designers_main_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_designers_main_image=itemView.findViewById(R.id.item_designers_main_image);
        }
    }
/*
    public void updatelist(List<VendorList>list)
    {
        vendorLists=new ArrayList<>();
        vendorLists.addAll(list);
        notifyDataSetChanged();
    }*/

