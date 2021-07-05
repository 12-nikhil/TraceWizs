package com.softwise.tracewizs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnloadingCheck {
    @SerializedName("batchNo")
    @Expose
    private String batchNo;
    @SerializedName("gateEntryId")
    @Expose
    private String gateEntryId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("coaCheck")
    @Expose
    private boolean coaCheck;
    @SerializedName("invoiceCheck")
    @Expose
    private boolean invoiceCheck;
    @SerializedName("qtyCheck")
    @Expose
    private boolean qtyCheck;
    @SerializedName("materialCheck")
    @Expose
    private boolean materialCheck;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("materialStorage")
    @Expose
    private List<MaterialStorage> materialStorage = null;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getGateEntryId() {
        return gateEntryId;
    }

    public void setGateEntryId(String gateEntryId) {
        this.gateEntryId = gateEntryId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<MaterialStorage> getMaterialStorage() {
        return materialStorage;
    }

    public void setMaterialStorage(List<MaterialStorage> materialStorage) {
        this.materialStorage = materialStorage;
    }

    public boolean isCoaCheck() {
        return coaCheck;
    }

    public void setCoaCheck(boolean coaCheck) {
        this.coaCheck = coaCheck;
    }

    public boolean isInvoiceCheck() {
        return invoiceCheck;
    }

    public void setInvoiceCheck(boolean invoiceCheck) {
        this.invoiceCheck = invoiceCheck;
    }

    public boolean isQtyCheck() {
        return qtyCheck;
    }

    public void setQtyCheck(boolean qtyCheck) {
        this.qtyCheck = qtyCheck;
    }

    public boolean isMaterialCheck() {
        return materialCheck;
    }

    public void setMaterialCheck(boolean materialCheck) {
        this.materialCheck = materialCheck;
    }
}
