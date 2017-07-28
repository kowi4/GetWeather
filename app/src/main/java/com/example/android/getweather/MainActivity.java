package com.example.android.getweather;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.getweather.api.Weather;
import com.example.android.getweather.api.WeatherResponse;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.currentWeatherImageView)
    ImageView currentWeatherImageView;
    @BindView(R.id.cityAndWeather)
    TextView cityAndWeather;
    @BindView(R.id.tempTextView)
    TextView tempTextView;
    @BindView(R.id.pressureTextView)
    TextView pressureTextView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getWeather();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeather();
            }
        });
    }

    private void getWeather() {
        if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        GetWeatherApp.weatherApi.getWeather(GetWeatherApp.API_KEY, "Krakow,pl").enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Weather weather = response.body().weather.get(0);
                        String cityName = response.body().name;
                        Double temperature = response.body().main.temp - 273.15;
                        Double pressure = response.body().main.pressure;

                        pressureTextView.setText("Pressure " + pressure + " " + "hPa");
                        cityAndWeather.setText(cityName + " | " + weather.main);
                        tempTextView.setText(temperature + " ℃");
                        Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + weather.icon + ".png").into(currentWeatherImageView);

                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(MainActivity.this, "FAILURE, check the internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
