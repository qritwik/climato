package com.library.apple.weather;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by ritwik on 8/16/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    ArrayList<Contact> arrayList = new ArrayList<>();



    Context ctx;
    public Adapter(ArrayList<Contact> arrayList,Context ctx)
    {
        this.arrayList=arrayList;
        this.ctx=ctx;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        MyViewHolder m = new MyViewHolder(view,ctx,arrayList);
        return m;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.max_t_f.setText(arrayList.get(position).getMax_t_f());
        holder.min_t_f.setText(arrayList.get(position).getMin_t_f());
        holder.day_f.setText(arrayList.get(position).getDay_f());

        String url = arrayList.get(position).getIcon().toString();
//        Toast.makeText(ctx,url,Toast.LENGTH_LONG).show();

        Glide.with(ctx).load("https:"+url).placeholder(R.drawable.not).error(R.drawable.not).into(holder.imageView1);


        int num = Integer.parseInt(arrayList.get(position).getCode());




//        //Sunny
//        if(num==1000){
//            holder.imageView1.setImageResource(R.drawable.suniiio);
//        }
//        //Light rain shower
//        if(num==1240){
//            holder.imageView1.setImageResource(R.drawable.rainiiio);
//        }
//        //Partly cloudy
//        if(num==1003){
//            holder.imageView1.setImageResource(R.drawable.partlycloudyday);
//        }
//        //Patchy rain possible
//        if(num==1063){
//            holder.imageView1.setImageResource(R.drawable.littlerainiiio);
//        }
//        //Cloudy
//        if(num==1006){
//            holder.imageView1.setImageResource(R.drawable.cloudyiiio);
//        }
//        //Mist
//        if(num==1030){
//            holder.imageView1.setImageResource(R.drawable.fogday);
//        }
//        //Moderate or heavy rain with thunder
//        if(num==1276){
//            holder.imageView1.setImageResource(R.drawable.chanceofstorm);
//        }
//        //Moderate or heavy rain shower
//        if(num==1243){
//            holder.imageView1.setImageResource(R.drawable.rainiiio);
//        }
//        //Patchy light rain with thunder
//        if(num==1273){
//            holder.imageView1.setImageResource(R.drawable.chanceofstorm);
//        }
//        //Patchy light drizzle
//        if(num==1150){
//            holder.imageView1.setImageResource(R.drawable.sleetiiio);
//        }
//        //Overcast
//        if(num==1009){
//            holder.imageView1.setImageResource(R.drawable.overcastiiio);
//        }
//        //Fog
//        if(num==1135){
//            holder.imageView1.setImageResource(R.drawable.fogday);
//        }
//        //Thunder outbreak possible
//        if(num==1087){
//            holder.imageView1.setImageResource(R.drawable.cloudlighting);
//        }
//





    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView date_f,max_t_f,min_t_f,type_f,day_f;
        ImageView imageView1;
        ArrayList<Contact> arrayList = new ArrayList<>();

        Context ctx;

        public MyViewHolder(View itemView,Context ctx,ArrayList<Contact> arrayList) {
            super(itemView);

            this.ctx=ctx;
            this.arrayList=arrayList;

            itemView.setOnClickListener(this);

            //date_f = (TextView)itemView.findViewById(R.id.date_f);
            max_t_f = (TextView)itemView.findViewById(R.id.temp_max_f);
            min_t_f = (TextView)itemView.findViewById(R.id.temp_min_f);
            //type_f = (TextView)itemView.findViewById(R.id.type_f);
            day_f = (TextView)itemView.findViewById(R.id.day_f);
            imageView1 = (ImageView)itemView.findViewById(R.id.image1);


        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Contact contact = this.arrayList.get(position);
            Intent intent = new Intent(this.ctx,Display.class);
            intent.putExtra("avg_temp",contact.getAvg_temp());
            intent.putExtra("humidity",contact.getHumidity());
            intent.putExtra("precipatation",contact.getPreci());
            intent.putExtra("Type",contact.getType_f());
            intent.putExtra("city",contact.getLocation_name());

            intent.putExtra("date",contact.getDate_f());
            intent.putExtra("day",contact.getDay_f());
            intent.putExtra("max_t",contact.getMax_t_f());
            intent.putExtra("min_t",contact.getMin_t_f());
            intent.putExtra("sunrise",contact.getSunrise());
            intent.putExtra("sunset",contact.getSunset());
            intent.putExtra("moonrise",contact.getMoonrise());
            intent.putExtra("moonset",contact.getMoonset());
            intent.putExtra("code",contact.getCode());
            intent.putExtra("icon",contact.getIcon());

            this.ctx.startActivity(intent);














        }
    }
}
