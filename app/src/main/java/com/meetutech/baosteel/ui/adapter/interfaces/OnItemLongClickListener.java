package com.meetutech.baosteel.ui.adapter.interfaces;


import com.meetutech.baosteel.ui.adapter.ViewHolder;

public interface OnItemLongClickListener<T> {
    void onItemLongClick(ViewHolder viewHolder, T data, int position);
}
