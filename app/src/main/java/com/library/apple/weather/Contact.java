package com.library.apple.weather;

import org.json.JSONObject;

/**
 * Created by ritwik on 8/16/2017.
 */

public class Contact {

    String date_f;
    String max_t_f;
    String min_t_f;
    String type_f;
    String day_f;
    String code;
    String avg_temp;
    String preci;
    String icon;



    String humidity;
    String sunrise;
    String sunset;
    String moonrise;
    String moonset;
    String location_name;








    public Contact(String date_f, String max_t_f, String min_t_f, String type_f, String day_f, String code, String icon, String avg_temp, String preci,String humidity, String sunrise,String sunset,String moonrise,String moonset,String location_name) {

        this.setDate_f(date_f);
        this.setMax_t_f(max_t_f);
        this.setMin_t_f(min_t_f);
        this.setType_f_e(type_f);
        this.setDay_f(day_f);
        this.setCode(code);
        this.setIcon(icon);
        this.setAvg_temp(avg_temp);
        this.setPreci(preci);
        this.setHumidity(humidity);
        this.setSunrise(sunrise);
        this.setSunset(sunset);
        this.setMoonrise(moonrise);
        this.setMoonset(moonset);
        this.setLocation_name(location_name);


    }




    public String getDate_f() {
        return date_f;
    }

    public void setDate_f(String date_f) {
        this.date_f = date_f;
    }

    public String getMax_t_f() {
        return max_t_f;
    }

    public void setMax_t_f(String max_t_f) {
        this.max_t_f = max_t_f;
    }

    public String getMin_t_f() {
        return min_t_f;
    }

    public void setMin_t_f(String min_t_f) {
        this.min_t_f = min_t_f;
    }

    public String getType_f() {
        return type_f;
    }

    public void setType_f_e(String type_f) {
        this.type_f = type_f;
    }


    public String getDay_f() {
        return day_f;
    }

    public void setDay_f(String day_f) {
        this.day_f = day_f;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAvg_temp() {
        return avg_temp;
    }

    public void setAvg_temp(String avg_temp) {
        this.avg_temp = avg_temp;
    }

    public String getPreci() {
        return preci;
    }

    public void setPreci(String preci) {
        this.preci = preci;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }



}
