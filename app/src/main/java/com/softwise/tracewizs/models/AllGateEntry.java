package com.softwise.tracewizs.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllGateEntry implements Parcelable {
    public static final Creator<AllGateEntry> CREATOR = new Creator<AllGateEntry>() {
        @Override
        public AllGateEntry createFromParcel(Parcel in) {
            return new AllGateEntry(in);
        }

        @Override
        public AllGateEntry[] newArray(int size) {
            return new AllGateEntry[size];
        }
    };
    @SerializedName("gateEntryId")
    @Expose
    private Integer gateEntryId;
    @SerializedName("supplierName")
    @Expose
    private String supplierName;
    @SerializedName("materialType")
    @Expose
    private String materialType;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("invoiceFile")
    @Expose
    private String invoiceFile;
    @SerializedName("coa")
    @Expose
    private String coa;
    @SerializedName("coaFile")
    @Expose
    private String coaFile;
    @SerializedName("status")
    @Expose
    private Boolean status;
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
    @SerializedName("photos")
    @Expose
    private String photos;
    @SerializedName("material")
    @Expose
    private Material material;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("deletedBy")
    @Expose
    private Object deletedBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("deletedDate")
    @Expose
    private Object deletedDate;


    public AllGateEntry() {

    }

    protected AllGateEntry(Parcel in) {
        if (in.readByte() == 0) {
            gateEntryId = null;
        } else {
            gateEntryId = in.readInt();
        }
        supplierName = in.readString();
        materialType = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        invoiceNo = in.readString();
        invoiceFile = in.readString();
        coa = in.readString();
        coaFile = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        truckNo = in.readString();
        driverName = in.readString();
        licenseNo = in.readString();
        mobileNo = in.readString();
        photos = in.readString();
        if (in.readByte() == 0) {
            createdBy = null;
        } else {
            createdBy = in.readInt();
        }
        createdDate = in.readString();
        material = in.readParcelable(Material.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (gateEntryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(gateEntryId);
        }
        dest.writeString(supplierName);
        dest.writeString(materialType);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        dest.writeString(invoiceNo);
        dest.writeString(invoiceFile);
        dest.writeString(coa);
        dest.writeString(coaFile);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeString(truckNo);
        dest.writeString(driverName);
        dest.writeString(licenseNo);
        dest.writeString(mobileNo);
        dest.writeString(photos);
        if (createdBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdBy);
        }
        dest.writeString(createdDate);
        dest.writeParcelable(material,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getGateEntryId() {
        return gateEntryId;
    }

    public void setGateEntryId(Integer gateEntryId) {
        this.gateEntryId = gateEntryId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceFile() {
        return invoiceFile;
    }

    public void setInvoiceFile(String invoiceFile) {
        this.invoiceFile = invoiceFile;
    }

    public String getCoa() {
        return coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    public String getCoaFile() {
        return coaFile;
    }

    public void setCoaFile(String coaFile) {
        this.coaFile = coaFile;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Object deletedDate) {
        this.deletedDate = deletedDate;
    }

}
