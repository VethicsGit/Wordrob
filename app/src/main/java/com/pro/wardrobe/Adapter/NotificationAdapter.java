package com.pro.wardrobe.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pro.wardrobe.ApiResponse.NotificationReponse.ActivityList;
import com.pro.wardrobe.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    Context context;
    List<ActivityList> list;


    String[] months=new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
    public NotificationAdapter( Context context,List<ActivityList> list) {
        this.context = context;
        this.list=list;
    }

    public void add(ActivityList activityList){
        list.add(activityList);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        ActivityList activityList=list.get(i);
        viewHolder.notification_txt.setText(activityList.getDisplayMsg());

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());


        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(activityList.getCreatedAt());
            viewHolder.notification_time.setText(months[date.getMonth()]+ " "+date.getDay()+ ","+(date.getYear()+1900));
        } catch (ParseException e) {
            e.printStackTrace();
        }



      /*  Date date = null;
        Date currdate = null;
        try {
            date = df.parse(activityList.getCreatedAt());
            currdate =df.parse(formattedDate);

            if (date.getYear()<currdate.getYear()){
                viewHolder.notification_time.setText("A year ago");
            }
            if (date.getMonth()<currdate.getMonth() && date.getYear()== currdate.getYear()){
                viewHolder.notification_time.setText("A month ago");
            }
            if (date.getMonth()==currdate.getMonth() && date.getYear()== currdate.getYear()){
                int diffhour=currdate.getHours()-date.getHours();
                int diffminutes=currdate.getMinutes()-date.getMinutes();

                if (diffhour>0){
                    viewHolder.notification_time.setText(diffhour+" hour ago");
                }if (diffhour==0){
                    viewHolder.notification_time.setText(diffminutes+" minutes ago");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView notification_txt;
        TextView notification_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_time=itemView.findViewById(R.id.notification_time);
            notification_txt=itemView.findViewById(R.id.notification_txt);
        }
    }
}
