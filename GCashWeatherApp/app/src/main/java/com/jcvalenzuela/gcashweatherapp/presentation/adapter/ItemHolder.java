package com.jcvalenzuela.gcashweatherapp.presentation.adapter;

import android.view.View;

import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.List;

public class ItemHolder {
    private final List listWeatherItems;
    private final CurrentWeatherItemClickListener itemClickListener;

    public ItemHolder(List listWeatherItems, CurrentWeatherItemClickListener itemClickListener) {
        this.listWeatherItems = listWeatherItems;
        this.itemClickListener = itemClickListener;
    }

    public void onItemClick(View view) {
        itemClickListener.onItemClick(view, listWeatherItems);
    }

    public interface CurrentWeatherItemClickListener extends ItemCallBack<List> {

    }
}
