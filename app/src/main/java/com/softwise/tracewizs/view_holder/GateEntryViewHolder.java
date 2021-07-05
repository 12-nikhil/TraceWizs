package com.softwise.tracewizs.view_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.softwise.tracewizs.R;

public class GateEntryViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgDelete;

    public TextView txtMaterialName;
    public TextView txtMaterialType;

    public TextView txtTruckNo;
    public CardView cardGateEntry;

    public GateEntryViewHolder(@NonNull View itemView) {
        super(itemView);
        imgDelete = itemView.findViewById(R.id.img_delete);
        txtMaterialName = itemView.findViewById(R.id.txt_material_name);
        txtMaterialType = itemView.findViewById(R.id.txt_material_type);
        txtTruckNo = itemView.findViewById(R.id.txt_truck_no);
        cardGateEntry = itemView.findViewById(R.id.card_gate_entry);
    }
}
