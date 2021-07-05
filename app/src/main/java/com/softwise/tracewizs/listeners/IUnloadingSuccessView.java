package com.softwise.tracewizs.listeners;

import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.UnloadingCheck;

public interface IUnloadingSuccessView {
    void gateUnloadingSuccessView(String unloadingId);
    void gateUnloadingUpdateSuccessView(UnloadingCheck unloadingCheck);
    void gateUnloadingFailed();
}
