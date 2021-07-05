package com.softwise.tracewizs.viewListeners;


import android.content.Context;

import com.softwise.tracewizs.listeners.IGateEntrySuccessView;
import com.softwise.tracewizs.listeners.fragmentview.IProgressView;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.GateEntryMaterial;


public interface GateEntryPresenter {
    void setGateEntryFragmentView(IProgressView gateEntryFragmentView);
    void setUid(String userID);
    void setGateEntryId(String gateEntryId);
    void setMaterialType(String materialType);
    void setMaterialNameId(String id);
    void setQty(String qty);
    void setUnit(String unit);
    void setInvoiceNo(String invoiceNo);
    void setCOANo(String coaNo);
    void setSupplierName(String supplierName);
    void setDriverName(String driverName);
    void setMobileNo(String mobileNo);
    void setLicenceNo(String licenceNo);
    void setTruckNo(String truckNo);
    void setListenersForGateEntry(Context context, AllGateEntry allGateEntry,IGateEntrySuccessView iGateEntrySuccessView);
    //void setGateEntryMaterialFields(Context context,GateEntryMaterial gateEntryMaterialFields, IGateEntrySuccessView iGateEntrySuccessView);



}
