package com.softwise.tracewizs.listeners.fragmentview;

public interface IProgressView {
    void showProgress();

    void dismissProgress();

    void handleGateEntryFailure(String errorMsg);
}
