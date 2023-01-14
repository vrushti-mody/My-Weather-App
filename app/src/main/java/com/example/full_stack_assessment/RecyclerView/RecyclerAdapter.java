package com.example.full_stack_assessment.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.full_stack_assessment.Data.Forecast.Forecast;
import com.example.full_stack_assessment.Data.Forecast.Period;
import com.example.full_stack_assessment.Data.Forecast.Weather;
import com.example.full_stack_assessment.R;

import java.util.Collections;
import java.util.List;

/**
 * The primary director of the recycler view
 * Examples: https://www.geeksforgeeks.org/android-recyclerview/
 */
public class RecyclerAdapter extends RecyclerView.Adapter<WeatherCardHolder> {
    List<Forecast> forecasts = Collections.emptyList();

    public RecyclerAdapter(List<Forecast> forecasts){
        this.forecasts = forecasts;
    }

    @NonNull
    @Override
    public WeatherCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View recyclerview = layoutInflater.inflate(R.layout.forecast_card,parent,false);
        WeatherCardHolder viewHolder = new WeatherCardHolder(recyclerview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCardHolder holder, int position) {
        final int index = holder.getAbsoluteAdapterPosition();
        holder.temperature.setText(String.valueOf(forecasts.get(position).temperature));
        holder.timeOfDay.setText(forecasts.get(position).timeOfDay);
        holder.weatherIcon.setImageResource(Integer.valueOf(forecasts.get(position).weatherIcon));
        }

    @Override
    public int getItemCount() {
        //ToDo: Implement item count
        if(forecasts == null) return 0;
        return forecasts.size();
    }
}
