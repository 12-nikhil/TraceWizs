package com.softwise.tracewizs.impl;


import android.content.Context;
import android.util.Log;

import com.softwise.tracewizs.listeners.IGateEntrySuccessView;
import com.softwise.tracewizs.listeners.IUnloadingSuccessView;
import com.softwise.tracewizs.models.GateEntryMaterial;
import com.softwise.tracewizs.models.MaterialStorage;
import com.softwise.tracewizs.models.ServerResponse;
import com.softwise.tracewizs.models.UnloadingCheck;
import com.softwise.tracewizs.serverUtils.ApiClients;
import com.softwise.tracewizs.serverUtils.ServiceListeners.APIService;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;
import com.softwise.tracewizs.viewListeners.CheckStoragePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.softwise.tracewizs.helper.MethodHelper.getTokenFromSp;
import static com.softwise.tracewizs.utils.TraceWizConstant.CONTENT_TYPE;
import static com.softwise.tracewizs.utils.TraceWizConstant.UNAUTHORISED;


public class CheckStoragePresenterImpl implements CheckStoragePresenter {

    private static CheckStoragePresenterImpl instance;
    private final UnloadingCheck mUnloadingCheck;
    private Context mContext;

    public CheckStoragePresenterImpl() {
        mUnloadingCheck = new UnloadingCheck();
    }

    /**
     * Getting singleton instance of GateEntry presenter
     *
     * @return - instance
     */
    public static CheckStoragePresenterImpl getInstance() {
        if (instance == null) {
            instance = new CheckStoragePresenterImpl();

        }
        return instance;
    }

    @Override
    public void setGateEntryIdAndUserId(Context context, int gateEntryId,int userId) {
        mContext = context;
        mUnloadingCheck.setGateEntryId(String.valueOf(gateEntryId));
        mUnloadingCheck.setUserId(String.valueOf(userId));
    }

    @Override
    public void setInvCheck(boolean invCheck) {
        mUnloadingCheck.setInvoiceCheck(invCheck);
    }

    @Override
    public void setCOACheck(boolean coaCheck) {
        mUnloadingCheck.setCoaCheck(coaCheck);
    }

    @Override
    public void setMaterialCheck(boolean materialCheck) {
        mUnloadingCheck.setMaterialCheck(materialCheck);
    }

    @Override
    public void setQtyCheck(boolean qtyCheck) {
        mUnloadingCheck.setQtyCheck(qtyCheck);
    }

    @Override
    public void setBatchNo(String batchNo) {
        Log.e("batch No",batchNo);
        mUnloadingCheck.setBatchNo(batchNo);
    }

    @Override
    public void setNote(String note) {
        mUnloadingCheck.setNotes(note);
    }

    @Override
    public void setStorageFragmentView(List<MaterialStorage> materialStorageList, IUnloadingSuccessView iUnloadingSuccessView) {
        mUnloadingCheck.setMaterialStorage(materialStorageList);
        saveUnloadingDataOnServer(iUnloadingSuccessView);
    }

    private void saveUnloadingDataOnServer(IUnloadingSuccessView mIUnloadingSuccessView) {
        if(mUnloadingCheck!=null)
        {
            APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
            Observable<ServerResponse> observable = apiService.createUnloading(getTokenFromSp(mContext), CONTENT_TYPE, mUnloadingCheck).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(new Observer<ServerResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Save gate entry", e.getMessage());

                    if (UNAUTHORISED.equals(e.getMessage())) {
                        // call refresh token
                        callRefreshToken(mContext, Integer.parseInt(mUnloadingCheck.getUserId()),mIUnloadingSuccessView);
                    }else {
                        mIUnloadingSuccessView.gateUnloadingFailed();
                    }
                }

                @Override
                public void onNext(ServerResponse serverResponse) {

                    if(serverResponse!=null)
                    {
                        if(serverResponse.getSuccess())
                        {
                            mIUnloadingSuccessView.gateUnloadingSuccessView(String.valueOf(serverResponse.getUnloadingTestId()));
                        }
                        else {
                            mIUnloadingSuccessView.gateUnloadingFailed();
                        }
                    }
                    else {
                        mIUnloadingSuccessView.gateUnloadingFailed();
                    }
                }
            });

        }
    }
    private void callRefreshToken(Context context, int userID, IUnloadingSuccessView iUnloadingSuccessView) {
        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
        apiService.refreshToken(userID).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response != null) {
                    ServerResponse refreshTokenResponse = response.body();
                    SPTRaceWizsConstants.saveToken(context, refreshTokenResponse.getMessage());
                    saveUnloadingDataOnServer(iUnloadingSuccessView);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }
}
