package com.softwise.tracewizs.impl;


import android.content.Context;
import android.util.Log;

import com.softwise.tracewizs.listeners.IGateEntrySuccessView;
import com.softwise.tracewizs.listeners.IUnloadingSuccessView;
import com.softwise.tracewizs.listeners.fragmentview.IProgressView;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.GateEntryMaterial;
import com.softwise.tracewizs.models.ServerResponse;
import com.softwise.tracewizs.serverUtils.ApiClients;
import com.softwise.tracewizs.serverUtils.ServiceListeners.APIService;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;
import com.softwise.tracewizs.viewListeners.GateEntryPresenter;

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


public class GateEntryPresenterImpl implements GateEntryPresenter {

    private static GateEntryPresenterImpl instance;
    private final GateEntryMaterial mGateEntryMaterial;

    public GateEntryPresenterImpl() {
        mGateEntryMaterial = new GateEntryMaterial();
    }

    /**
     * Getting singleton instance of GateEntry presenter
     *
     * @return - instance
     */
    public static GateEntryPresenterImpl getInstance() {
        if (instance == null) {
            instance = new GateEntryPresenterImpl();
        }
        return instance;
    }

    @Override
    public void setGateEntryFragmentView(IProgressView gateEntryFragmentView) {

    }

    @Override
    public void setUid(String userID) {
        mGateEntryMaterial.setUserId(userID);
    }

    @Override
    public void setGateEntryId(String gateEntryId) {
        mGateEntryMaterial.setGateENtryId(gateEntryId);
    }

    @Override
    public void setMaterialType(String materialType) {
        mGateEntryMaterial.setMaterialType(materialType);

    }

    @Override
    public void setMaterialNameId(String id) {
        mGateEntryMaterial.setMaterialId(id);
        Log.e("Material id ", id);
    }

    @Override
    public void setQty(String qty) {
        mGateEntryMaterial.setQuantity(qty);
    }

    @Override
    public void setUnit(String unit) {
        mGateEntryMaterial.setUnit(unit);
    }

    @Override
    public void setInvoiceNo(String invoiceNo) {
        mGateEntryMaterial.setInvocieNo(invoiceNo);
    }

    @Override
    public void setCOANo(String coaNo) {
        mGateEntryMaterial.setCoa(coaNo);
    }

    @Override
    public void setSupplierName(String supplierName) {
        Log.e("Supplier name",supplierName);
        mGateEntryMaterial.setSupplierName(supplierName);
    }

    @Override
    public void setDriverName(String driverName) {
        Log.e("Driver name",driverName);
        mGateEntryMaterial.setDriverName(driverName);
    }

    @Override
    public void setMobileNo(String mobileNo) {
        Log.e("Supplier mobile",mobileNo);
        mGateEntryMaterial.setMobileNo(mobileNo);
    }

    @Override
    public void setLicenceNo(String licenceNo) {
        Log.e("Supplier licence",licenceNo);
        mGateEntryMaterial.setLicenseNo(licenceNo);
    }

    @Override
    public void setTruckNo(String truckNo) {
        Log.e("Supplier truck",truckNo);
        mGateEntryMaterial.setTruckNo(truckNo);
    }

    @Override
    public void setListenersForGateEntry(Context context,AllGateEntry allGateEntry, IGateEntrySuccessView iGateEntrySuccessView) {
        sendGateEntryOnServer(context,allGateEntry, iGateEntrySuccessView);
    }

   /* @Override
    public void setGateEntryMaterialFields(Context context, GateEntryMaterial gEMF, IGateEntrySuccessView iGateEntrySuccessView) {
        mGateEntryMaterial.setSupplierName(gEMF.getSupplierName());
        mGateEntryMaterial.setDriverName(gEMF.getDriverName());
        mGateEntryMaterial.setMobileNo(gEMF.getMobileNo());
        mGateEntryMaterial.setLicenseNo(gEMF.getLicenseNo());
        mGateEntryMaterial.setTruckNo(gEMF.getTruckNo());
      *//*  mGateEntryMaterial.setInvocieNo(gEMF.getInvocieNo());
        mGateEntryMaterial.setCoa(gEMF.getCoa());*//*
        if (gEMF.getGateENtryId() != null) {
            mGateEntryMaterial.setGateENtryId(gEMF.getGateENtryId());
            Log.e("Gate entry Id ", mGateEntryMaterial.getGateENtryId());
        }
        sendGateEntryOnServer(context, iGateEntrySuccessView);
    }*/

    private void sendGateEntryOnServer(Context context,AllGateEntry allGateEntry, IGateEntrySuccessView mIGateEntrySuccessView) {
        if (mGateEntryMaterial != null) {
            APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);

            if (mGateEntryMaterial.getGateENtryId() == null) {
                Observable<ServerResponse> observable = apiService.createGateEntry(getTokenFromSp(context), CONTENT_TYPE, mGateEntryMaterial).subscribeOn(Schedulers.newThread())
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
                            callRefreshToken(context,allGateEntry, Integer.parseInt(mGateEntryMaterial.getUserId()), mIGateEntrySuccessView);
                        } else {
                            mIGateEntrySuccessView.gateEntryFailed();
                        }
                    }

                    @Override
                    public void onNext(ServerResponse serverResponse) {

                        if (serverResponse != null) {
                            if (serverResponse.getSuccess()) {
                                Log.e("gate entry id ",String.valueOf(serverResponse.getGateEntryId()));
                                mIGateEntrySuccessView.gateEntrySuccessView(String.valueOf(serverResponse.getGateEntryId()));
                            } else {
                                mIGateEntrySuccessView.gateEntryFailed();
                            }
                        }
                    }
                });
            } else {
               /* if(allGateEntry!=null)
                {
                    if(mGateEntryMaterial.getInvoiceFile()==null && mGateEntryMaterial.getInvoiceFile().isEmpty())
                    {
                        mGateEntryMaterial.setInvoiceFile(allGateEntry.getInvoiceFile());
                    }
                    if(mGateEntryMaterial.getCoaFile()==null && mGateEntryMaterial.getCoaFile().isEmpty())
                    {
                        mGateEntryMaterial.setCoaFile(allGateEntry.getCoaFile());
                    }
                }*/
                Observable<AllGateEntry> observable = apiService.updateGateEntry(getTokenFromSp(context), CONTENT_TYPE, mGateEntryMaterial).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
                observable.subscribe(new Observer<AllGateEntry>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Update gate entry", e.getMessage());

                        if (UNAUTHORISED.equals(e.getMessage())) {
                            // call refresh token
                            callRefreshToken(context,allGateEntry, Integer.parseInt(mGateEntryMaterial.getUserId()), mIGateEntrySuccessView);
                        }
                        mIGateEntrySuccessView.gateEntryFailed();
                    }

                    @Override
                    public void onNext(AllGateEntry allGateEntry) {

                        if (allGateEntry != null) {
                            mIGateEntrySuccessView.gateEntryUpdateSuccessView(allGateEntry);
                        } else {
                            mIGateEntrySuccessView.gateEntryFailed();
                        }
                    }
                });
            }
        }
    }

    private void callRefreshToken(Context context,AllGateEntry allGateEntry, int userID, IGateEntrySuccessView iGateEntrySuccessView) {
        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
        apiService.refreshToken(userID).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response != null) {
                    ServerResponse refreshTokenResponse = response.body();
                    SPTRaceWizsConstants.saveToken(context, refreshTokenResponse.getMessage());
                    sendGateEntryOnServer(context,allGateEntry, iGateEntrySuccessView);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
            }
        });
    }
}
