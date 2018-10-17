package com.pro.wardrobe.Adapter;

import android.widget.Filter;

import com.pro.wardrobe.ApiResponse.DesignerListResponse.VendorList;

import java.util.ArrayList;
import java.util.List;

class CustomFillter extends Filter {

    Designers_Recyclarview_adapter designers_recyclarview_adapter;
    List<VendorList>fillterlist;



    public CustomFillter(List<VendorList> fillterlist, Designers_Recyclarview_adapter designers_recyclarview_adapter) {
        this.designers_recyclarview_adapter = designers_recyclarview_adapter;
        this.fillterlist = fillterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results=new FilterResults();


        if (charSequence !=null && charSequence.length()>0)
        {
            charSequence=charSequence.toString().toLowerCase();
            List<VendorList>vendorLists=new ArrayList<>();

            for (int i=0;i<vendorLists.size();i++)
            {
                if (vendorLists.get(i).getBusinessName().contains(charSequence))
                {
                    vendorLists.add(vendorLists.get(i));
                }
            }
            results .count=vendorLists.size();
            results.values=vendorLists;
        }else
        {
            results.count=fillterlist.size();
            results.values=fillterlist;
        }
        return null;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

    }
}
