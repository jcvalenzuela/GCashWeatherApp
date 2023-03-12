package com.jcvalenzuela.gcashweatherapp.presentation.adapter;

import static com.jcvalenzuela.gcashweatherapp.helper.utils.Utility.dateAndTimeFormatter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.jcvalenzuela.gcashweatherapp.R;
import com.jcvalenzuela.gcashweatherapp.data.model.response.forecast_weather.List;
import com.jcvalenzuela.gcashweatherapp.databinding.ItemWeatherListBinding;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseAdapter;
import com.jcvalenzuela.gcashweatherapp.presentation.base.BaseViewHolder;

import java.util.Locale;

public class CurrentWeatherAdapter extends BaseAdapter<List> {

    private ItemAdapterListener itemAdapterListener;

    public CurrentWeatherAdapter(java.util.List<List> weatherItems) {
        super(weatherItems);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrentWeatherAdapter.CurrentWeatherViewHolder(
                ItemWeatherListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    public void setItemAdapterListener(ItemAdapterListener itemAdapterListener) {
        this.itemAdapterListener = itemAdapterListener;
    }

    public interface ItemAdapterListener extends ItemCallBack<List> {
    }

    public class CurrentWeatherViewHolder extends BaseViewHolder implements ItemHolder.CurrentWeatherItemClickListener {
        private ItemWeatherListBinding itemWeatherListBinding;



        public CurrentWeatherViewHolder(@NonNull ItemWeatherListBinding itemListWeatherBinding) {
            super(itemListWeatherBinding.getRoot());
            this.itemWeatherListBinding = itemListWeatherBinding;
        }

        @Override
        public void onItemClick(View view, List item) {
            if (item != null) {
                itemAdapterListener.onItemClick(view, item);
            }
        }

        @Override
        public void onBind(int position) {
            final java.util.List<List> list = get();
            double celsius = Math.round(list.get(position).getMain().getTemp() - 273.15);
            String temp = String.format(Locale.getDefault(), "%.0f", celsius);
            double minCel = Math.round(list.get(position).getMain().getTempMin() - 273.15);
            String tempMin = String.format(Locale.getDefault(), "%.0f", minCel);
            double maxCel = Math.round(list.get(position).getMain().getTempMax() - 273.15);
            String tempMax = String.format(Locale.getDefault(), "%.0f", maxCel);

            String description = "";
            int weatherId = 0;
            if (list.get(position).getWeatherList().size() > 0) {
                description = "(" + list.get(position).getWeatherList().get(0).getMain() + ") "
                        + list.get(position).getWeatherList().get(0).getDescription();
                weatherId = list.get(position).getWeatherList().get(0).getId();
            }
            itemWeatherListBinding.cardView.setBackgroundColor(itemView.getResources().getColor(R.color.white, null));
            itemWeatherListBinding.setItemHolder(new ItemHolder(list.get(position), this));
            itemWeatherListBinding.textViewDay.setText(dateAndTimeFormatter(list.get(position).getDtTxt()));
            itemWeatherListBinding.textViewTemperature.setText("Temperature \n" + temp + "°C");
            itemWeatherListBinding.textViewMinMax.setText("Min/Max \n" + tempMin + "/" + tempMax + "°C");
            itemWeatherListBinding.textViewStatus.setText(description);
            itemWeatherListBinding.executePendingBindings();
        }
    }
}
