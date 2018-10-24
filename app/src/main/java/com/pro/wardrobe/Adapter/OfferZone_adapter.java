package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pro.wardrobe.Activity.Product_details;
import com.pro.wardrobe.ApiResponse.OfferZoneResponse.OfferZoneList;
import com.pro.wardrobe.Fragment.Fragment_product_list;
import com.pro.wardrobe.R;

import java.util.List;

public class OfferZone_adapter extends RecyclerView.Adapter<OfferZone_adapter.ViewHolder> {


    List<OfferZoneList>offerZoneLists;
    Context context;

    public OfferZone_adapter(List<OfferZoneList> offerZoneLists, Context applicationContext) {

        this.context=applicationContext;
        this.offerZoneLists=offerZoneLists;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.offerzone_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        OfferZoneList offerZoneList=offerZoneLists.get(i);

        Glide.with(context).load(offerZoneList.getOfferZoneImage()).into(viewHolder.offerzone_img);
        viewHolder.offer_id.setText(offerZoneList.getOfferZoneId());

    }

    @Override
    public int getItemCount() {
        return offerZoneLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView offerzone_img;
        TextView offer_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            offerzone_img=itemView.findViewById(R.id.offerzone_img);
            offer_id=itemView.findViewById(R.id.offer_id);


            offerzone_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(context, Fragment_product_list.class);
                    i.putExtra("offer_id",offer_id.getText().toString());
                     i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });
        }
    }
}
