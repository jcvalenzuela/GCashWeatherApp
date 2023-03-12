package com.jcvalenzuela.gcashweatherapp.presentation.adapter;

import android.view.View;

public interface ItemCallBack <T> {
    void onItemClick(View view, T item);
}
