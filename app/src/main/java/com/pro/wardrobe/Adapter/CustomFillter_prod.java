package com.pro.wardrobe.Adapter;

import com.pro.wardrobe.Activity.Filter;
import com.pro.wardrobe.ApiResponse.DesignerListResponse.VendorList;

import java.nio.file.FileVisitResult;
import java.util.List;

public class CustomFillter_prod extends Filter {


    Designers_Recyclarview_adapter designers_recyclarview_adapter;
    List<VendorList> fillterlist;


    public CustomFillter_prod(List<VendorList> fillterlist, Designers_Recyclarview_adapter designers_recyclarview_adapter) {
        this.designers_recyclarview_adapter = designers_recyclarview_adapter;
        this.fillterlist = fillterlist;
    }




}
