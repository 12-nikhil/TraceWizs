package com.softwise.tracewizs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialStorage {
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("qty")
    @Expose
    private String qty;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
