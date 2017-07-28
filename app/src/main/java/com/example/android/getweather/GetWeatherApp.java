package com.example.android.getweather;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dominic on 2017-07-08.
 */

public class GetWeatherApp extends Application {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "29a452de43686922256f337d7debbfc4";

    Retrofit retrofit;
    public static WeatherApi weatherApi;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        weatherApi = retrofit.create(WeatherApi.class);
    }
}
