package com.softwise.tracewizs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("gateEntryId")
    @Expose
    private Integer gateEntryId;
    @SerializedName("unloadingTestId")
    @Expose
    private Integer unloadingTestId;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getGateEntryId() {
        return gateEntryId;
    }

    public void setGateEntryId(Integer gateEntryId) {
        this.gateEntryId = gateEntryId;
    }

    public Integer getUnloadingTestId() {
        return unloadingTestId;
    }

    public void setUnloadingTestId(Integer unloadingTestId) {
        this.unloadingTestId = unloadingTestId;
    }
}
