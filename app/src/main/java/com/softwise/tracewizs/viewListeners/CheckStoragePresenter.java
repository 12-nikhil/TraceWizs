package com.softwise.tracewizs.viewListeners;


import android.content.Context;

import com.softwise.tracewizs.listeners.IUnloadingSuccessView;
import com.softwise.tracewizs.models.MaterialStorage;
import com.softwise.tracewizs.models.UnloadingCheck;

import java.util.List;

public interface CheckStoragePresenter {
    //void setCheckFragmentView(Context context,UnloadingCheck unloadingCheck);
    void setGateEntryIdAndUserId(Context context,int gateEntryId,int userId);
    void setInvCheck(boolean invCheck);
    void setCOACheck(boolean coaCheck);
    void setMaterialCheck(boolean materialCheck);
    void setQtyCheck(boolean qtyCheck);
    void setBatchNo(String batchNo);
    void setNote(String note);
    void setStorageFragmentView(List<MaterialStorage> materialStorageList, IUnloadingSuccessView unloadingSuccessView);
}
