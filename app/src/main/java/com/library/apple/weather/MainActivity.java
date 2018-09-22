package com.library.apple.weather;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //EditText searchBox;


//    FloatingActionButton floatingActionButton;
    String url_p;
    String code1, code2;
    boolean click;
    String location_name_ip, state_ip, country_ip, temp_ip, type_ip;

    ProgressDialog progressDialog, pd;
    TextView temp1, city1, type1, state1, country1;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter1;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Contact> arrayList = new ArrayList<>();

    private PlaceAutocompleteFragment placeAutocompleteFragment;


    FusedLocationProviderClient fusedLocationProviderClient;

    private int STORAGE_PERMISSION_CODE = 1;


    int i;
    String icon_f;
    String icon_FF;
    String day_f,lat,lon,val;
    String code;
    String type_f;
    Context context;


    LinearLayout linearLayout;
    ImageView imageView4, imageView10;


    @Override
    protected void onStart() {

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception ex){}
        try{
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception ex){}
        if(!gps_enabled && !network_enabled){

            Toast.makeText(getApplicationContext(),"Turn ON your device location",Toast.LENGTH_LONG).show();
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


        } else {
            requestLocationPermission();

        }




        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout rvb1 =  (RelativeLayout)findViewById(R.id.rvb1);
        rvb1.setVisibility(View.GONE);

        final RelativeLayout rvb2 = (RelativeLayout)findViewById(R.id.card_forcast);
        rvb2.setVisibility(View.GONE);


//
//
//        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_loc);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//
//
//
//            }
//        });
//


        recyclerView = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter1 = new Adapter(arrayList, this);
        recyclerView.setAdapter(adapter1);






        temp1 = (TextView) findViewById(R.id.temp_m);
        city1 = (TextView) findViewById(R.id.city_m);
        type1 = (TextView) findViewById(R.id.type_m);
        state1 = (TextView) findViewById(R.id.state_w);
        country1 = (TextView) findViewById(R.id.country_w);
        linearLayout = (LinearLayout) findViewById(R.id.li1);
        imageView4 = (ImageView) findViewById(R.id.image_front);
        imageView10 = (ImageView) findViewById(R.id.image_button_up);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        click = true;

        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click == true) {
                    recyclerView.setVisibility(View.GONE);
                    imageView10.setImageResource(R.drawable.ic_unfold_more_black_24dp);
                    click = false;
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView10.setImageResource(R.drawable.ic_unfold_less_black_24dp);
                    click = true;
                }
            }
        });

        pd = new ProgressDialog(this);
        pd.setMessage("Getting your current location");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.



            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                if(location != null) {

                    lon = String.valueOf(location.getLongitude());
                    lat = String.valueOf(location.getLatitude());


                    val = lat+","+lon;
//                    Toast.makeText(getApplicationContext(),val,Toast.LENGTH_LONG).show();





                    url_p = "http://api.apixu.com/v1/current.json?key=779e2a51e94d4716bd051832171308&q="+val;
                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url_p, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            try {
                                JSONObject location = response.getJSONObject("location");
                                location_name_ip = location.getString("name");
                                state_ip = location.getString("region");
                                country_ip = location.getString("country");
                                JSONObject current = response.getJSONObject("current");
                                String last_updated = current.getString("last_updated");
                                temp_ip = current.getString("temp_c");
                                JSONObject condition = current.getJSONObject("condition");
                                type_ip = condition.getString("text");
                                code2 = condition.getString("code");
                                String icon1 = condition.getString("icon").toString();






                                rvb1.setVisibility(View.VISIBLE);



//
//                                int num = Integer.parseInt(code2);
//
//
//
//
//                                //Sunny
//                                if(num==1000){
//                                    imageView4.setImageResource(R.drawable.suniiio);
//                                }
//                                //Light rain shower
//                                if(num==1240){
//                                    imageView4.setImageResource(R.drawable.rainiiio);
//                                }
//                                //Partly cloudy
//                                if(num==1003){
//                                    imageView4.setImageResource(R.drawable.partlycloudyday);
//                                }
//                                //Patchy rain possible
//                                if(num==1063){
//                                    imageView4.setImageResource(R.drawable.littlerainiiio);
//                                }
//                                //Cloudy
//                                if(num==1006){
//                                    imageView4.setImageResource(R.drawable.cloudyiiio);
//                                }
//                                //Mist
//                                if(num==1030){
//                                    imageView4.setImageResource(R.drawable.fogday);
//                                }
//                                //Moderate or heavy rain with thunder
//                                if(num==1276){
//                                    imageView4.setImageResource(R.drawable.chanceofstorm);
//                                }
//                                //Moderate or heavy rain shower
//                                if(num==1243){
//                                    imageView4.setImageResource(R.drawable.rainiiio);
//                                }
//                                //Patchy light rain with thunder
//                                if(num==1273){
//                                    imageView4.setImageResource(R.drawable.chanceofstorm);
//                                }
//                                //Patchy light drizzle
//                                if(num==1150){
//                                    imageView4.setImageResource(R.drawable.sleetiiio);
//                                }
//                                //Overcast
//                                if(num==1009){
//                                    imageView4.setImageResource(R.drawable.overcastiiio);
//                                }
//                                //Fog
//                                if(num==1135){
//                                    imageView4.setImageResource(R.drawable.fogday);
//                                }
//                                //Thunder outbreak possible
//                                if(num==1087){
//                                    imageView4.setImageResource(R.drawable.cloudlighting);
//                                }


                                Glide.with(MainActivity.this).load("https:"+icon1).placeholder(R.drawable.not).error(R.drawable.not).into(imageView4);




                                state1.setText(state_ip);
                                country1.setText(country_ip);
                                type1.setText(type_ip);

                                city1.setText(location_name_ip);
                                temp1.setText(temp_ip);





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }







                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            pd.dismiss();

                            Toast.makeText(getApplicationContext(),"Connection error. Try again", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                        }
                    });

                    MySingleton2.getMySingleton(MainActivity.this).addToRequestque(jsonObjectRequest1);






                    //For 7 day forecast by automatic location detection
                    String url = "http://api.apixu.com/v1/forecast.json?key=779e2a51e94d4716bd051832171308&q="+val+"&days=7";

                    //Volley JsonObjectRequest
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject location = response.getJSONObject("location");
                                String location_name = location.getString("name");
                                String state = location.getString("region");
                                String country = location.getString("country");
                                JSONObject current = response.getJSONObject("current");
                                String last_updated = current.getString("last_updated");
                                String temp = current.getString("temp_c");
                                JSONObject condition = current.getJSONObject("condition");
                                String type = condition.getString("text");
                                String wind = current.getString("wind_kph");
                                code1 = condition.getString("code");
                                String icon2 = condition.getString("icon");
                                int num1 = Integer.parseInt(code1);

                                JSONObject forecast = response.getJSONObject("forecast");
                                JSONArray forecastday = forecast.getJSONArray("forecastday");

                                for(i=0;i<forecastday.length();i++)
                                {
                                    JSONObject none = (JSONObject)forecastday.get(i);
                                    String date_f = none.getString("date");
                                    JSONObject day = none.getJSONObject("day");
                                    String max_t_f = day.getString("maxtemp_c");
                                    String min_t_f = day.getString("mintemp_c");
                                    String avg_temp = day.getString("avgtemp_c");
                                    String preci = day.getString("totalprecip_mm");
                                    String humidity = day.getString("avghumidity");
                                    JSONObject condition_f = day.getJSONObject("condition");
                                    type_f = condition_f.getString("text");
                                    code = condition_f.getString("code");
                                    icon_f = condition_f.getString("icon");


                                    JSONObject astro = none.getJSONObject("astro");
                                    String sunrise = astro.getString("sunrise");
                                    String sunset = astro.getString("sunset");
                                    String moonrise = astro.getString("moonrise");
                                    String moonset = astro.getString("moonset");

                                    //To get day from date
                                    SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = inFormat.parse(date_f);
                                    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                                    day_f = outFormat.format(date);

                                    int num = Integer.parseInt(code);



                                    rvb2.setVisibility(View.VISIBLE);






                                    if(i==0)
                                    {
                                        day_f = "Today";
                                    }

                                    else if(i==1)
                                    {
                                        day_f = "Tomorrow";
                                    }
                                    if(num==1087)
                                    {
                                        type_f="Thundery outbreaks";
                                    }


                                    Contact contact = new Contact(date_f,max_t_f,min_t_f,type_f,day_f,code,icon_f,avg_temp,preci,humidity,sunrise,sunset,moonrise,moonset,location_name);
                                    arrayList.add(contact);


                                }

                                int num = Integer.parseInt(code1);





                                pd.dismiss();
//                                //Sunny
//                                if(num==1000){
//                                    imageView4.setImageResource(R.drawable.suniiio);
//                                }
//                                //Light rain shower
//                                if(num==1240){
//                                    imageView4.setImageResource(R.drawable.rainiiio);
//                                }
//                                //Partly cloudy
//                                if(num==1003){
//                                    imageView4.setImageResource(R.drawable.partlycloudyday);
//                                }
//                                //Patchy rain possible
//                                if(num==1063){
//                                    imageView4.setImageResource(R.drawable.littlerainiiio);
//                                }
//                                //Cloudy
//                                if(num==1006){
//                                    imageView4.setImageResource(R.drawable.cloudyiiio);
//                                }
//                                //Mist
//                                if(num==1030){
//                                    imageView4.setImageResource(R.drawable.fogday);
//                                }
//                                //Moderate or heavy rain with thunder
//                                if(num==1276){
//                                    imageView4.setImageResource(R.drawable.chanceofstorm);
//                                }
//                                //Moderate or heavy rain shower
//                                if(num==1243){
//                                    imageView4.setImageResource(R.drawable.rainiiio);
//                                }
//                                //Patchy light rain with thunder
//                                if(num==1273){
//                                    imageView4.setImageResource(R.drawable.chanceofstorm);
//                                }
//                                //Patchy light drizzle
//                                if(num==1150){
//                                    imageView4.setImageResource(R.drawable.sleetiiio);
//                                }
//                                //Overcast
//                                if(num==1009){
//                                    imageView4.setImageResource(R.drawable.overcastiiio);
//                                }
//                                //Fog
//                                if(num==1135){
//                                    imageView4.setImageResource(R.drawable.fogday);
//                                }
//                                //Thunder outbreak possible
//                                if(num==1087){
//                                    imageView4.setImageResource(R.drawable.cloudlighting);
//                                }

                                Glide.with(MainActivity.this).load("https:"+icon2).placeholder(R.drawable.not).error(R.drawable.not).into(imageView4);
//






                                temp1.setText(temp);
                                city1.setText(location_name);
                                if(num!=1276) {
                                    type1.setText(type);
                                }
                                state1.setText(state);
                                country1.setText(country);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();


                            Toast.makeText(getApplicationContext(),"Connection error. Try again", Toast.LENGTH_SHORT).show();

                            error.printStackTrace();

                        }
                    });


                    MySingleton2.getMySingleton(MainActivity.this).addToRequestque(jsonObjectRequest);










                }




            }
        });





























     /*   searchBox = (EditText)findViewById(R.id.search_et);
        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==searchBox.getId())
                {
                    searchBox.setCursorVisible(true);
                    searchBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp,0,0,0);
                }
            }
        });

       */

     placeAutocompleteFragment = (PlaceAutocompleteFragment)
             getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        placeAutocompleteFragment.setFilter(typeFilter);

        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                String city = place.getName().toString();


                //For clearing recyclerView on next search
                arrayList.clear();
                adapter1.notifyDataSetChanged();

                //To hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                //Show Progress Dialog
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading..");
                progressDialog.setCancelable(false);
                progressDialog.show();


                url_p = "http://api.apixu.com/v1/current.json?key=779e2a51e94d4716bd051832171308&q="+city;
                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url_p, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONObject location = response.getJSONObject("location");
                            location_name_ip = location.getString("name");
                            state_ip = location.getString("region");
                            country_ip = location.getString("country");
                            JSONObject current = response.getJSONObject("current");
                            String last_updated = current.getString("last_updated");
                            temp_ip = current.getString("temp_c");
                            JSONObject condition = current.getJSONObject("condition");
                            type_ip = condition.getString("text");
                            code2 = condition.getString("code");
                            String icon3 = condition.getString("icon");








                            rvb1.setVisibility(View.VISIBLE);




                            int num = Integer.parseInt(code2);




//                            //Sunny
//                            if(num==1000){
//                                imageView4.setImageResource(R.drawable.suniiio);
//                            }
//                            //Light rain shower
//                            if(num==1240){
//                                imageView4.setImageResource(R.drawable.rainiiio);
//                            }
//                            //Partly cloudy
//                            if(num==1003){
//                                imageView4.setImageResource(R.drawable.partlycloudyday);
//                            }
//                            //Patchy rain possible
//                            if(num==1063){
//                                imageView4.setImageResource(R.drawable.littlerainiiio);
//                            }
//                            //Cloudy
//                            if(num==1006){
//                                imageView4.setImageResource(R.drawable.cloudyiiio);
//                            }
//                            //Mist
//                            if(num==1030){
//                                imageView4.setImageResource(R.drawable.fogday);
//                            }
//                            //Moderate or heavy rain with thunder
//                            if(num==1276){
//                                imageView4.setImageResource(R.drawable.chanceofstorm);
//                            }
//                            //Moderate or heavy rain shower
//                            if(num==1243){
//                                imageView4.setImageResource(R.drawable.rainiiio);
//                            }
//                            //Patchy light rain with thunder
//                            if(num==1273){
//                                imageView4.setImageResource(R.drawable.chanceofstorm);
//                            }
//                            //Patchy light drizzle
//                            if(num==1150){
//                                imageView4.setImageResource(R.drawable.sleetiiio);
//                            }
//                            //Overcast
//                            if(num==1009){
//                                imageView4.setImageResource(R.drawable.overcastiiio);
//                            }
//                            //Fog
//                            if(num==1135){
//                                imageView4.setImageResource(R.drawable.fogday);
//                            }
//                            //Thunder outbreak possible
//                            if(num==1087){
//                                imageView4.setImageResource(R.drawable.cloudlighting);
//                            }
//




                            Glide.with(MainActivity.this).load("https:"+icon3).placeholder(R.drawable.not).error(R.drawable.not).into(imageView4);



                            state1.setText(state_ip);
                            country1.setText(country_ip);
                            type1.setText(type_ip);

                            city1.setText(location_name_ip);
                            temp1.setText(temp_ip);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }







                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pd.dismiss();

                        Toast.makeText(getApplicationContext(),"Connection error. Try again", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                });

                MySingleton2.getMySingleton(MainActivity.this).addToRequestque(jsonObjectRequest1);





                //For 7 day forecast based on searched location
                String url = "http://api.apixu.com/v1/forecast.json?key=779e2a51e94d4716bd051832171308&q="+city+"&days=7";


                //Volley JsonObjectRequest
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject location = response.getJSONObject("location");
                            String location_name = location.getString("name");
                            String state = location.getString("region");
                            String country = location.getString("country");
                            JSONObject current = response.getJSONObject("current");
                            String last_updated = current.getString("last_updated");
                            String temp = current.getString("temp_c");
                            JSONObject condition = current.getJSONObject("condition");
                            String type = condition.getString("text");
                            String wind = current.getString("wind_kph");
                            code1 = condition.getString("code");
                            int num1 = Integer.parseInt(code1);
                            String icon5 = condition.getString("icon");

                            JSONObject forecast = response.getJSONObject("forecast");
                            JSONArray forecastday = forecast.getJSONArray("forecastday");

                            for(i=0;i<forecastday.length();i++)
                            {
                                JSONObject none = (JSONObject)forecastday.get(i);
                                String date_f = none.getString("date");
                                JSONObject day = none.getJSONObject("day");
                                String max_t_f = day.getString("maxtemp_c");
                                String min_t_f = day.getString("mintemp_c");
                                String avg_temp = day.getString("avgtemp_c");
                                String preci = day.getString("totalprecip_mm");
                                String humidity = day.getString("avghumidity");
                                JSONObject condition_f = day.getJSONObject("condition");
                                type_f = condition_f.getString("text");
                                code = condition_f.getString("code");
                                icon_FF = condition_f.getString("icon");


                                JSONObject astro = none.getJSONObject("astro");
                                String sunrise = astro.getString("sunrise");
                                String sunset = astro.getString("sunset");
                                String moonrise = astro.getString("moonrise");
                                String moonset = astro.getString("moonset");

                                //To get day from date
                                SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = inFormat.parse(date_f);
                                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                                day_f = outFormat.format(date);

                                int num = Integer.parseInt(code);



                                rvb2.setVisibility(View.VISIBLE);




                                if(i==0)
                                {
                                    day_f = "Today";
                                }

                                else if(i==1)
                                {
                                    day_f = "Tomorrow";
                                }
                                if(num==1087)
                                {
                                    type_f="Thundery outbreaks";
                                }


                                Contact contact = new Contact(date_f,max_t_f,min_t_f,type_f,day_f,code,icon_FF,avg_temp,preci,humidity,sunrise,sunset,moonrise,moonset,location_name);
                                arrayList.add(contact);


                            }

                            int num = Integer.parseInt(code1);


                            progressDialog.dismiss();

//                            //Sunny
//                            if(num==1000){
//                                imageView4.setImageResource(R.drawable.suniiio);
//                            }
//                            //Light rain shower
//                            if(num==1240){
//                                imageView4.setImageResource(R.drawable.rainiiio);
//                            }
//                            //Partly cloudy
//                            if(num==1003){
//                                imageView4.setImageResource(R.drawable.partlycloudyday);
//                            }
//                            //Patchy rain possible
//                            if(num==1063){
//                                imageView4.setImageResource(R.drawable.littlerainiiio);
//                            }
//                            //Cloudy
//                            if(num==1006){
//                                imageView4.setImageResource(R.drawable.cloudyiiio);
//                            }
//                            //Mist
//                            if(num==1030){
//                                imageView4.setImageResource(R.drawable.fogday);
//                            }
//                            //Moderate or heavy rain with thunder
//                            if(num==1276){
//                                imageView4.setImageResource(R.drawable.chanceofstorm);
//                            }
//                            //Moderate or heavy rain shower
//                            if(num==1243){
//                                imageView4.setImageResource(R.drawable.rainiiio);
//                            }
//                            //Patchy light rain with thunder
//                            if(num==1273){
//                                imageView4.setImageResource(R.drawable.chanceofstorm);
//                            }
//                            //Patchy light drizzle
//                            if(num==1150){
//                                imageView4.setImageResource(R.drawable.sleetiiio);
//                            }
//                            //Overcast
//                            if(num==1009){
//                                imageView4.setImageResource(R.drawable.overcastiiio);
//                            }
//                            //Fog
//                            if(num==1135){
//                                imageView4.setImageResource(R.drawable.fogday);
//                            }
//                            //Thunder outbreak possible
//                            if(num==1087){
//                                imageView4.setImageResource(R.drawable.cloudlighting);
//                            }
//



                            Glide.with(MainActivity.this).load("https:"+icon5).placeholder(R.drawable.not).error(R.drawable.not).into(imageView4);



                            temp1.setText(temp);
                            city1.setText(location_name);
                            if(num!=1276) {
                                type1.setText(type);
                            }
                            state1.setText(state);
                            country1.setText(country);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();


                        Toast.makeText(getApplicationContext(),"Connection error. Try again", Toast.LENGTH_SHORT).show();

                        error.printStackTrace();

                    }
                });


                MySingleton2.getMySingleton(MainActivity.this).addToRequestque(jsonObjectRequest);


            }



            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.getStatus().toString(),Toast.LENGTH_SHORT);

            }
        });

        /*searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //code goes here

                    //For clearing recyclerView on next search
                    arrayList.clear();
                    adapter1.notifyDataSetChanged();

                    //To hide keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    //Show Progress Dialog
                    progressDialog=new ProgressDialog(MainActivity.this);

                    progressDialog.setMessage("Loading..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    //Get user input from editText
                    String city = searchBox.getText().toString();


                    //For 5 day forecast based on location
                    String url = "http://api.apixu.com/v1/forecast.json?key=779e2a51e94d4716bd051832171308&q="+city+"&days=7";

                    //Volley JsonObjectRequest
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject location = response.getJSONObject("location");
                                String location_name = location.getString("name");
                                String state = location.getString("region");
                                String country = location.getString("country");
                                JSONObject current = response.getJSONObject("current");
                                String last_updated = current.getString("last_updated");
                                String temp = current.getString("temp_c");
                                JSONObject condition = current.getJSONObject("condition");
                                String type = condition.getString("text");
                                String wind = current.getString("wind_kph");
                                code1 = condition.getString("code");
                                int num1 = Integer.parseInt(code1);

                                JSONObject forecast = response.getJSONObject("forecast");
                                JSONArray forecastday = forecast.getJSONArray("forecastday");

                                for(i=0;i<forecastday.length();i++)
                                {
                                    JSONObject none = (JSONObject)forecastday.get(i);
                                    String date_f = none.getString("date");
                                    JSONObject day = none.getJSONObject("day");
                                    String max_t_f = day.getString("maxtemp_c");
                                    String min_t_f = day.getString("mintemp_c");
                                    String avg_temp = day.getString("avgtemp_c");
                                    String preci = day.getString("totalprecip_mm");
                                    String humidity = day.getString("avghumidity");
                                    JSONObject condition_f = day.getJSONObject("condition");
                                    type_f = condition_f.getString("text");
                                    code = condition_f.getString("code");

                                    JSONObject astro = none.getJSONObject("astro");
                                    String sunrise = astro.getString("sunrise");
                                    String sunset = astro.getString("sunset");
                                    String moonrise = astro.getString("moonrise");
                                    String moonset = astro.getString("moonset");

                                    //To get day from date
                                    SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = inFormat.parse(date_f);
                                    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                                    day_f = outFormat.format(date);

                                    int num = Integer.parseInt(code);


                                    if(i==0)
                                    {
                                        day_f = "Today";
                                    }

                                    else if(i==1)
                                    {
                                        day_f = "Tomorrow";
                                    }
                                    if(num==1087)
                                    {
                                        type_f="Thundery outbreaks";
                                    }


                                    Contact contact = new Contact(date_f,max_t_f,min_t_f,type_f,day_f,code,avg_temp,preci,humidity,sunrise,sunset,moonrise,moonset,location_name);
                                    arrayList.add(contact);


                                }

                                int num = Integer.parseInt(code1);


                                progressDialog.dismiss();

                                //Sunny
                                if(num==1000){
                                    imageView4.setImageResource(R.drawable.sssssss);
                                }
                                //Light rain shower
                                if(num==1240){
                                    imageView4.setImageResource(R.drawable.qqqq);
                                }
                                //Partly cloudy
                                if(num==1003){
                                   imageView4.setImageResource(R.drawable.www);
                                }
                                //Patchy rain possible
                                if(num==1063){
                                    imageView4.setImageResource(R.drawable.eee);
                                }
                                //Cloudy
                                if(num==1006){
                                    imageView4.setImageResource(R.drawable.rrr);
                                }
                                //Mist
                                if(num==1030){
                                    imageView4.setImageResource(R.drawable.mist);
                                }
                                //Moderate or heavy rain with thunder
                                if(num==1276){
                                    imageView4.setImageResource(R.drawable.gguku);
                                    type1.setText("Moderate Rain");
                                }
                                //Moderate or heavy rain shower
                                if(num==1243){
                                    imageView4.setImageResource(R.drawable.hhhhyy);
                                }
                                //Patchy light rain with thunder
                                if(num==1273){
                                    imageView4.setImageResource(R.drawable.ddddfffg);
                                }
                                //Patchy light drizzle
                                if(num==1150){
                                    imageView4.setImageResource(R.drawable.tttyy);
                                }
                                //Overcast
                                if(num==1009){
                                    imageView4.setImageResource(R.drawable.overcast);
                                }
                                //Fog
                                if(num==1135){
                                    imageView4.setImageResource(R.drawable.fog);
                                }
                                //Thunder outbreak possible
                                if(num==1087){
                                    imageView4.setImageResource(R.drawable.top);
                                }





                                 temp1.setText(temp);
                                 city1.setText(location_name);
                                if(num!=1276) {
                                    type1.setText(type);
                                }
                                 state1.setText(state);
                                 country1.setText(country);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();


                            Toast.makeText(getApplicationContext(),"Connection error. Try again", Toast.LENGTH_SHORT).show();

                            error.printStackTrace();

                        }
                    });


                    MySingleton2.getMySingleton(MainActivity.this).addToRequestque(jsonObjectRequest);


                }
                return false;
            }
        });*/




    }


    private void requestLocationPermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

            new AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("Application needs to detect your location")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},STORAGE_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


//               Toast.makeText(getApplicationContext(),"Permission GRANTED",Toast.LENGTH_LONG).show();

               startActivity(new Intent(this,MainActivity.class));


            }else{
                Toast.makeText(getApplicationContext(),"Permission DENIED",Toast.LENGTH_LONG).show();

            }
        }
    }




    boolean click1 = true;

    @Override
    public void onBackPressed() {

        if(click1==true)
        {
            Toast.makeText(getApplicationContext(),"Press again to exit.",Toast.LENGTH_SHORT).show();
            click1=false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    click1=true;

                }
            },2000);
        }
        else if(click1==false)
        {
            finishAffinity();
            System.exit(0);
        }

    }
}
