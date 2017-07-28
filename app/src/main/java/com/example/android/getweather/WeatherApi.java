package com.example.android.getweather;


import com.example.android.getweather.api.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dominic on 2017-07-08.
 */

public interface WeatherApi {
    @GET("weather") Call<WeatherResponse> getWeather(@Query("APPID") String appid, @Query("q") String location);
    @GET("weather") Call<WeatherResponse> getWeather(@Query("APPID") String appid, @Query("lat") Double lat, @Query("lon") Double lon);
}
