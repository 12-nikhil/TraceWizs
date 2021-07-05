package com.softwise.tracewizs.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unit implements Parcelable {
    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };
    @SerializedName("unitId")
    @Expose
    private Integer unitId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
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

    public Unit() {

    }

    protected Unit(Parcel in) {
        if (in.readByte() == 0) {
            unitId = null;
        } else {
            unitId = in.readInt();
        }
        name = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            createdBy = null;
        } else {
            createdBy = in.readInt();
        }
        createdDate = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (unitId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(unitId);
        }
        dest.writeString(name);
        dest.writeString(description);
        if (createdBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdBy);
        }
        dest.writeString(createdDate);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
    }
}
