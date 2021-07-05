package com.softwise.tracewizs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GateEntryMaterial {
    @SerializedName("supplierName")
    @Expose
    private String supplierName;
    @SerializedName("materialId")
    @Expose
    private String materialId;
    @SerializedName("invocieNo")
    @Expose
    private String invocieNo;
    @SerializedName("materialType")
    @Expose
    private String materialType;
    @SerializedName("coa")
    @Expose
    private String coa;
    @SerializedName("truckNo")
    @Expose
    private String truckNo;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("licenseNo")
    @Expose
    private String licenseNo;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("gateEntryId")
    @Expose
    private String gateENtryId;
    @SerializedName("unit")
    @Expose
    private String unit;
    /*@Expose
    private String invoiceFile;
    @SerializedName("invoiceFile")
    @Expose
    private String coaFile;
    @SerializedName("coaFile")*/

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getInvocieNo() {
        return invocieNo;
    }

    public void setInvocieNo(String invocieNo) {
        this.invocieNo = invocieNo;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getCoa() {
        return coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGateENtryId() {
        return gateENtryId;
    }

    public void setGateENtryId(String gateENtryId) {
        this.gateENtryId = gateENtryId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

   /* public String getInvoiceFile() {
        return invoiceFile;
    }

    public void setInvoiceFile(String invoiceFile) {
        this.invoiceFile = invoiceFile;
    }

    public String getCoaFile() {
        return coaFile;
    }

    public void setCoaFile(String coaFile) {
        this.coaFile = coaFile;
    }*/
}
