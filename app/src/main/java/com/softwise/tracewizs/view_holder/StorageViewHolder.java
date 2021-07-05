package com.softwise.tracewizs.view_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.softwise.tracewizs.R;

public class StorageViewHolder extends RecyclerView.ViewHolder {

    public TextView txtCount;
    public TextView txtQty;
    public TextView txtLocation;

    public StorageViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCount = itemView.findViewById(R.id.txt_count);
        txtQty = itemView.findViewById(R.id.txt_qty);
        txtLocation = itemView.findViewById(R.id.txt_location);
    }
}
