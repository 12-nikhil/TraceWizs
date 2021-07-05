package com.softwise.tracewizs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.MaterialStorage;
import com.softwise.tracewizs.view_holder.GateEntryViewHolder;
import com.softwise.tracewizs.view_holder.StorageViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StorageLocationAdapter extends RecyclerView.Adapter<StorageViewHolder> {
    List<MaterialStorage> mMaterialStorageList = new ArrayList<>();
    private Context mContext;

    public StorageLocationAdapter(Context context, List<MaterialStorage> materialStorages) {
        this.mContext = context;
        this.mMaterialStorageList = materialStorages;
    }

    @NotNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new StorageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_location_qty, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull StorageViewHolder holder, int position) {
        MaterialStorage materials = mMaterialStorageList.get(position);
        holder.txtCount.setText(String.valueOf(position+1)+".");
        holder.txtLocation.setText(materials.getLocation());
        holder.txtQty.setText(materials.getQty());
    }

    @Override
    public int getItemCount() {
        return mMaterialStorageList.size();
    }

    public interface OnLocationSelectListeners{

    }
}
