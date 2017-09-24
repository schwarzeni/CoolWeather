package com.example.coolweather.gson;

/**
 * Created by nizhenyang on 2017/9/23.
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
