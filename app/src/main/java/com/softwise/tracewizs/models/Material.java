package com.softwise.tracewizs.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Material implements Parcelable {
    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };
    @SerializedName("materialId")
    @Expose
    private Integer materialId;
    @SerializedName("materialNo")
    @Expose
    private String materialNo;
    @SerializedName("materialName")
    @Expose
    private String materialName;
    @SerializedName("materialType")
    @Expose
    private String materialType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("unit")
    @Expose
    private Unit unit;
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
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Material() {
    }

    public Material(Parcel in) {
        if (in.readByte() == 0) {
            materialId = null;
        } else {
            materialId = in.readInt();
        }
        materialNo = in.readString();
        materialName = in.readString();
        materialType = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            createdBy = null;
        } else {
            createdBy = in.readInt();
        }
        createdDate = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        unit = in.readParcelable(Unit.class.getClassLoader());
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (materialId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(materialId);
        }
        dest.writeString(materialNo);
        dest.writeString(materialName);
        dest.writeString(materialType);
        dest.writeString(description);
        if (createdBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdBy);
        }
        dest.writeString(createdDate);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeParcelable(unit,flags);
    }
}
