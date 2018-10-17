package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pro.wardrobe.Extra.Dashboard_list_model;
import com.pro.wardrobe.R;

import java.util.List;

public class DashboardListAdapter extends BaseAdapter {

    List<Dashboard_list_model> dashboard_list_models;
    Context context;

    public DashboardListAdapter(List<Dashboard_list_model> dashboard_list_models, Context context) {
        this.dashboard_list_models = dashboard_list_models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dashboard_list_models.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View convertView = LayoutInflater.from(context).inflate(R.layout.abs_layout, null);

        Dashboard_list_model model = dashboard_list_models.get(i);

        ImageView abs_icon = convertView.findViewById(R.id.abs_icon);
        TextView abs_txt = convertView.findViewById(R.id.abs_name);


        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "Roboto_Regular.ttf");

        abs_txt.setTypeface(face);


        abs_icon.setImageDrawable(model.getDrawable());
        abs_txt.setText(model.getName());

        return convertView;
    }
}
