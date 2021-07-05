package com.softwise.tracewizs.listeners;

import com.softwise.tracewizs.models.AllGateEntry;

public interface IGateEntrySuccessView {
    void gateEntrySuccessView(String gateEntryId);
    void gateEntryUpdateSuccessView(AllGateEntry allGateEntry);
    void gateEntryFailed();
}
