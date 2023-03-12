package com.jcvalenzuela.gcashweatherapp.presentation.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> weatherItems;

    public BaseAdapter(List<T> weatherItems) {
        this.weatherItems = weatherItems;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return weatherItems != null && weatherItems.size() > 0 ? weatherItems.size() : 1;
    }

    public List<T> get() {
        return weatherItems;
    }

    public void add(List<T> weatherItems) {
        this.weatherItems.addAll(weatherItems);
        notifyDataSetChanged();
    }

    public void  clear() {
        weatherItems.clear();
    }
}
