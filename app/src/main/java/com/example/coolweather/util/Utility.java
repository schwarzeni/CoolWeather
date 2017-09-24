package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nizhenyang on 2017/9/21.
 */

public class Utility {

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String COUNTY = "county";

    public static final String API_KEY = "60526c8a0281412b822e70e5ba97b1d5";

    public static final String API_SITE = "https://free-api.heweather.com/v5/weather?";

    public static final String API_RESPONSE_STATS = "ok";

    public static final String API_BING_IMAGE = "http://guolin.tech/api/bing_pic";

    private static final String TAG = "Utility";

    /**
     *
     * 解析和处理服务器返回的省级数据
     *
     * @param response okHttp返回的数据
     * @return true: 成功  false: 失败
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProviences = new JSONArray(response);
                for (int i = 0, len = allProviences.length(); i < len; i++) {
                    JSONObject provinceObject = allProviences.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *
     *  解析和处理服务器返回的市级数据
     *
     * @param response okHttp 返回的数据
     * @param provinceId 城市所属省的ID
     * @return true: 成功  false: 失败
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for(int i = 0, len = allCities.length(); i < len; i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *
     *  解析和处理服务器返回的县级数据
     *
     * @param response okHttp 返回的数据
     * @param cityId 县级所属市的id
     * @return true: 成功  false: 失败
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if(!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for( int i = 0, len = allCounties.length(); i < len; i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *
     *  返回JSON数据解析成Weather的实体类
     *
     * @param response String
     * @return Weather
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param weatherId 当前城市的ID
     * @return 请求的url
     */
    public static String getWeatherUrl(String weatherId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(API_SITE)
                .append("city=")
                .append(weatherId)
                .append("&")
                .append("key=")
                .append(API_KEY);
        return stringBuilder.toString();
    }
}
