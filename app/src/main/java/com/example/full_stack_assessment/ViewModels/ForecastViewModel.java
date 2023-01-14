package com.example.full_stack_assessment.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.full_stack_assessment.Data.Forecast.Forecast;
import com.example.full_stack_assessment.Data.Forecast.Period;
import com.example.full_stack_assessment.Data.Forecast.Weather;
import com.example.full_stack_assessment.Data.Grid.GridCall;
import com.example.full_stack_assessment.DataSource.WeatherApi;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * The ForecastViewModel sets the UI state of the ForecastActivity
 * It asynchronously calls the National Weather API and returns a Json
 * A Gson builder converts the returning Json into objects found in the data directory
 */
public class ForecastViewModel extends ViewModel {

    private String BASE_URL = "https://api.weather.gov/";

    private final LatLng location = new LatLng(40.091135131249494, -88.24013532344047);

    //Variables used for the final
    private String gridId = "";
    private int gridX;
    private int gridY;

    //Network status holder
    private MutableLiveData<APIStatus> _status = new MutableLiveData<>();
    public LiveData<APIStatus> status = _status;

    public List<Forecast> forecasts = new ArrayList<Forecast>();

    //Retrofit REST Network caller
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //Initialize national Weather Api class
    private WeatherApi weatherApi = retrofit.create(WeatherApi.class);

    //Api call on init of the viewModel
    public ForecastViewModel(){
        makeGridApiCall();
    }

    public void makeGridApiCall(){
        _status.postValue(APIStatus.LOADING);
        try{
            getGridProperties();

        }catch(Exception e){
            Log.e("Forecast ViewModel Grid",e.toString());
            _status.postValue(APIStatus.ERROR);
        }
    }

    /**
     * Sends GET request to the weather api with a location point
     * Use this URL example: https://api.weather.gov/points/38.8894,-77.0352
     * Manipulates a GridCall type object
     */
    private void getGridProperties(){
        //The additional url
        String locationString = "points/"+ location.latitude + "," + location.longitude;

        //The GET call using Retrofit
        Call<GridCall> call = weatherApi.createGridProperties(locationString);

        //The asynchronous GET request
        call.enqueue(new Callback<GridCall>(){
            @Override
            public void onResponse(Call<GridCall> call, Response<GridCall> response) {
                //The first Api response will be put into a GridCall object
                GridCall gridCall = response.body();

                //Fill in the grid variables for the second weather api call
                if(gridCall != null) {
                    gridId = gridCall.getGridProperties().getGridID();
                    gridX = gridCall.getGridProperties().getGridX();
                    gridY = gridCall.getGridProperties().getGridY();
                }

                //Preform the final API call
                getWeatherProperties();
            }

            @Override
            public void onFailure(Call<GridCall> call, Throwable t) {
                Log.e("Forecast ViewModel Grid Call",t.toString());
                _status.postValue(APIStatus.ERROR);
            }
        });
    }

    /**
     * Sends GET request to the weather api with grid data
     * Use this URL example: https://api.weather.gov/gridpoints/LWX/96,70/forecast
     * Manipulates a Weather type object
     */
    private void getWeatherProperties(){

        //ToDO: Write your own api call

        // The URL
        String locationString = "https://api.weather.gov/gridpoints/" + gridId + "/" + gridX + "," + gridY + "/forecast";

        // Make the call to Weather API
        Call<Weather> call = weatherApi.createWeatherData(locationString);

        // Execute the request asynchronously like previous function
        call.enqueue(new Callback<Weather>(){
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                // The final API response will be put into a Weather object
                Weather weather = response.body();

                // Process the weather data and update the UI
                if (weather != null) {
                    List<Period> forecasts_list =  weather.getProperties().getPeriods();
                    for (Period period : forecasts_list) {
                        String weatherIcon;
                        String timeOfDay = period.getName();
                        int temperature = period.getTemperature();
                        if (period.getDetailedForecast().toLowerCase().contains("partly sunny")){
                            weatherIcon = "Partially Sunny";
                        } else if (period.getDetailedForecast().toLowerCase().contains("sunny")){
                            weatherIcon = "Sunny";
                        }else if (period.getDetailedForecast().toLowerCase().contains("rain")){
                            weatherIcon = "Rain";
                        }else if (period.getDetailedForecast().toLowerCase().contains("snow")){
                            weatherIcon = "Snow";
                        }else if (period.getDetailedForecast().toLowerCase().contains("cloud")){
                            weatherIcon = "Cloud";
                        }else{
                            weatherIcon = "Sunny";
                        }
                        Forecast forecast = new Forecast(weatherIcon, timeOfDay, temperature);
                        forecasts.add(forecast);
                    }
                    // Set API Value to DONE
                    _status.postValue(APIStatus.DONE);

                } else {
                    // Set API Value to ERROR
                    _status.postValue(APIStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

                // Set API Value to ERROR
                _status.postValue(APIStatus.ERROR);
            }
        });
    }
}










