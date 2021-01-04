package com.meetutech.baosteel.ui.adapter.interfaces;

import com.meetutech.baosteel.ui.adapter.ViewHolder;

public interface OnMultiItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position, int viewType);
}
