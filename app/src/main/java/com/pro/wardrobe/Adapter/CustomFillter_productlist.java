package com.pro.wardrobe.Adapter;

import com.pro.wardrobe.Activity.Filter;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.VendorList;
import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductList;

import java.util.ArrayList;
import java.util.List;

public class CustomFillter_productlist extends android.widget.Filter {


    ProductList_Adapter productList_adapter;
    List<ProductList> fillterlist;



    public CustomFillter_productlist(List<ProductList> fillterlist, ProductList_Adapter productList_adapter) {
        this.productList_adapter = productList_adapter;
        this.fillterlist = fillterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        android.widget.Filter.FilterResults results=new android.widget.Filter.FilterResults();


        if (charSequence !=null && charSequence.length()>0)
        {
            charSequence=charSequence.toString().toLowerCase();
            List<ProductList>productLists=new ArrayList<>();

            for (int i=0;i<productLists.size();i++)
            {
                if (productLists.get(i).getBusinessName().contains(charSequence))
                {
                    productLists.add(productLists.get(i));
                }
            }
            results .count=productLists.size();
            results.values=productLists;
        }else
        {
            results.count=fillterlist.size();
            results.values=fillterlist;
        }
        return null;
    }

    @Override
    protected void publishResults(CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {

    }
}
