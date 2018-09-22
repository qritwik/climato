package com.library.apple.weather;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Random;


public class Display extends AppCompatActivity {

    TextView city,day,avg_temp,type,maximum_t,minimum_t,humidity,precipitation,sunrise,sunset,moonrise, moonset;
    ImageView imageView1;
    Toolbar toolbar;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.rel_lay2);
//        int[] cards={R.drawable.scroll,R.drawable.scroll1,R.drawable.scroll2,R.drawable.scroll3,R.drawable.scroll4};
//        Random r = new Random();
//        int n=r.nextInt(5);
//
//        relativeLayout.setBackgroundResource(cards[n]);
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("hhhhhhh");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("date")+" ("+getIntent().getStringExtra("day")+")");

        imageView1=(ImageView)findViewById(R.id.image_view);
        maximum_t=(TextView)findViewById(R.id.max_temp);
        minimum_t=(TextView)findViewById(R.id.min_temp);
        humidity=(TextView)findViewById(R.id.humidity);
        precipitation=(TextView)findViewById(R.id.preci);
        sunrise=(TextView)findViewById(R.id.sunrise);
        sunset=(TextView)findViewById(R.id.sunset);
        moonrise=(TextView)findViewById(R.id.moonrise);
        moonset=(TextView)findViewById(R.id.moonset);
        type=(TextView)findViewById(R.id.type1);

        city=(TextView)findViewById(R.id.city1);
        day=(TextView)findViewById(R.id.today1);
        avg_temp=(TextView)findViewById(R.id.avg_temp1);

        int num = Integer.parseInt(getIntent().getStringExtra("code"));
        if(num==1243)
        {
            type.setText("Moderate rain");
        }
        else if(num==1276)
        {
            type.setText("Rain with Thunder");
        }
        else
            {
                type.setText(getIntent().getStringExtra("Type"));
            }

        maximum_t.setText(getIntent().getStringExtra("max_t"));
        minimum_t.setText(getIntent().getStringExtra("min_t"));
        humidity.setText(getIntent().getStringExtra("humidity"));
        precipitation.setText(getIntent().getStringExtra("precipatation"));
        sunrise.setText(getIntent().getStringExtra("sunrise"));
        sunset.setText(getIntent().getStringExtra("sunset"));
        moonrise.setText(getIntent().getStringExtra("moonrise"));
        moonset.setText(getIntent().getStringExtra("moonset"));
        avg_temp.setText(getIntent().getStringExtra("avg_temp"));
        city.setText(getIntent().getStringExtra("city"));
        day.setText(getIntent().getStringExtra("day"));

        String url = getIntent().getStringExtra("icon");

        Glide.with(Display.this).load("https:"+url).placeholder(R.drawable.not).error(R.drawable.not).into(imageView1);





//
//
//        //Sunny
//        if(num==1000){
//            imageView1.setImageResource(R.drawable.suniiio);
//        }
//        //Light rain shower
//        if(num==1240){
//            imageView1.setImageResource(R.drawable.rainiiio);
//        }
//        //Partly cloudy
//        if(num==1003){
//            imageView1.setImageResource(R.drawable.partlycloudyday);
//        }
//        //Patchy rain possible
//        if(num==1063){
//            imageView1.setImageResource(R.drawable.littlerainiiio);
//        }
//        //Cloudy
//        if(num==1006){
//            imageView1.setImageResource(R.drawable.cloudyiiio);
//        }
//        //Mist
//        if(num==1030){
//            imageView1.setImageResource(R.drawable.fogday);
//        }
//        //Moderate or heavy rain with thunder
//        if(num==1276){
//            imageView1.setImageResource(R.drawable.chanceofstorm);
//        }
//        //Moderate or heavy rain shower
//        if(num==1243){
//            imageView1.setImageResource(R.drawable.rainiiio);
//        }
//        //Patchy light rain with thunder
//        if(num==1273){
//            imageView1.setImageResource(R.drawable.chanceofstorm);
//        }
//        //Patchy light drizzle
//        if(num==1150){
//            imageView1.setImageResource(R.drawable.sleetiiio);
//        }
//        //Overcast
//        if(num==1009){
//            imageView1.setImageResource(R.drawable.overcastiiio);
//        }
//        //Fog
//        if(num==1135){
//            imageView1.setImageResource(R.drawable.fogday);
//        }
//        //Thunder outbreak possible
//        if(num==1087){
//            imageView1.setImageResource(R.drawable.cloudlighting);
//        }
//
//
//
//
//
//
//
//








    }

    @Override
    public void onBackPressed()
    {

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.setting)
        {
            Toast.makeText(getApplicationContext(),"Setting clicked",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.about)
        {
            Toast.makeText(getApplicationContext(),"About us clicked",Toast.LENGTH_SHORT).show();
        }
        if(id==android.R.id.home)
        {
           super.onBackPressed();
        }

        if(id==R.id.share)
        {

        }





        return super.onOptionsItemSelected(item);
    }
}
