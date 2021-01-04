package com.meetutech.baosteel.ui.adapter.interfaces;

import com.meetutech.baosteel.ui.adapter.ViewHolder;

public interface OnItemChildClickListener<T> {
    void onItemChildClick(ViewHolder viewHolder, T data, int position);
}
