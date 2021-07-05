package com.softwise.tracewizs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.view_holder.GateEntryViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GateEntryAdapter extends RecyclerView.Adapter<GateEntryViewHolder> {
    List<AllGateEntry> mGateEntryMaterialList = new ArrayList<>();
    OnMaterialSelectListeners mOnMaterialSelectListeners;
    private Context mContext;

    public GateEntryAdapter(Context context, List<AllGateEntry> gateEntryMaterials, OnMaterialSelectListeners materialSelectListeners) {
        this.mContext = context;
        this.mGateEntryMaterialList = gateEntryMaterials;
        this.mOnMaterialSelectListeners = materialSelectListeners;
    }

    @NotNull
    @Override
    public GateEntryViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new GateEntryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_gate_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull GateEntryViewHolder holder, int position) {
        AllGateEntry materials = mGateEntryMaterialList.get(position);
        holder.txtMaterialName.setText(materials.getMaterial().getMaterialName());
        holder.txtMaterialType.setText(materials.getMaterial().getMaterialType());
        holder.txtTruckNo.setText(materials.getTruckNo());
        holder.imgDelete.setOnClickListener(v -> {
            mOnMaterialSelectListeners.deleteFile(materials,position);
        });
        holder.cardGateEntry.setOnClickListener(v -> {
            mOnMaterialSelectListeners.materialSelect(materials);
        });
    }

    @Override
    public int getItemCount() {
        return mGateEntryMaterialList.size();
    }


    public interface OnMaterialSelectListeners {
        void materialSelect(AllGateEntry materials);
        void deleteFile(AllGateEntry materials,int pos);
    }

}
