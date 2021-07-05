package com.softwise.tracewizs.serverUtils;

import android.util.Log;

import com.softwise.tracewizs.utils.TraceWizConstant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClients {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(boolean isUploadCall) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TraceWizConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        return retrofit;
    }
}
