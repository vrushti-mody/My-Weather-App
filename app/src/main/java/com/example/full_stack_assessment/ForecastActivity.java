package com.example.full_stack_assessment;

import static java.security.AccessController.getContext;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.full_stack_assessment.Data.Forecast.Forecast;
import com.example.full_stack_assessment.Data.Forecast.Period;
import com.example.full_stack_assessment.RecyclerView.RecyclerAdapter;
import com.example.full_stack_assessment.ViewModels.APIStatus;
import com.example.full_stack_assessment.ViewModels.ForecastViewModel;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * ForeCast activity manages the weather api and populates the resulting data
 * into a recycler view
 */
        public class ForecastActivity extends AppCompatActivity {

            //View model where all network calls are being preformed
            private RecyclerView recyclerView;

            //Hashmap containing possible forecasts and their corresponding icons
            private HashMap<String,Integer> forecastMap = new HashMap<String, Integer>(){{
                put("Sunny",R.drawable.ic_sun);
                put("Rain", R.drawable.ic_rain);
                put("Partially Sunny", R.drawable.ic_part_cloud);
                put("Snow",R.drawable.ic_snow);
                put("Cloud",R.drawable.ic_cloud);
            }};

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_forecast);
                ForecastViewModel forecastViewModel = new ForecastViewModel();
                //ToDo: Fetch the results from the viewModel and populate the recyclerView
                recyclerView = (RecyclerView)findViewById(R.id.recycler_view_forecasts);
                recyclerView.setLayoutManager(new LinearLayoutManager(ForecastActivity.this));

                forecastViewModel.status.observe(this, new Observer<APIStatus>() {
                    @Override
                    public void onChanged(APIStatus apiStatus) {
                        switch(apiStatus) {
                            case LOADING:
                                Toast.makeText(getApplicationContext(),"Forecast loading",Toast.LENGTH_SHORT).show();
                                break;
                            case DONE:
                                for (Forecast forecast : forecastViewModel.forecasts) {
                                    String weather = forecast.weatherIcon;
                                    String drawableResourceId = forecastMap.get(weather).toString();
                                    forecast.weatherIcon = drawableResourceId;
                                }
                                RecyclerAdapter adapter = new RecyclerAdapter(forecastViewModel.forecasts);
                                recyclerView.setAdapter(adapter);
                                break;

                            case ERROR:
                                // Show error message
                                Toast.makeText(getApplicationContext(),"Error Loading forecasts",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });



            }
            @Override
            public void onBackPressed()
            {
                super.onBackPressed();
            }
        }