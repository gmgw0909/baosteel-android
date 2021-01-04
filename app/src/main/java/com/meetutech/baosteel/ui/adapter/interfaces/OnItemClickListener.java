package com.meetutech.baosteel.ui.adapter.interfaces;


import com.meetutech.baosteel.ui.adapter.ViewHolder;

public interface OnItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
