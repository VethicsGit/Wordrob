package com.pro.wardrobe.Adapter;

import android.service.autofill.FillRequest;
import android.widget.Filter;

import com.pro.wardrobe.ApiResponse.ProductListResponse.ProductList;

import java.util.ArrayList;
import java.util.List;

public class Product_CustomFillter extends Filter {



    ProductList_Adapter productList_adapter;
    List<ProductList> fillterlist;



    public Product_CustomFillter(List<ProductList> fillterlist, ProductList_Adapter productList_adapter) {
        this.productList_adapter = productList_adapter;
        this.fillterlist = fillterlist;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results=new FilterResults();
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
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

    }
}
