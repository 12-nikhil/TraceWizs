package com.softwise.tracewizs.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.activitys.UploadDocumentActivity;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.listeners.IGateEntrySuccessView;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.GateEntryMaterial;
import com.softwise.tracewizs.utils.ConnectionUtils;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.softwise.tracewizs.utils.TraceWizConstant.I_GATE_ENTRY;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class TruckDetailsFragment extends GateEntryBaseFragment {

    @BindView(R.id.edt_supplier_name)
    EditText edtSupplierName;
    @BindView(R.id.edt_driver_name)
    EditText edtDriverName;
    @BindView(R.id.edt_mobile_number)
    EditText edtMobileNo;
    @BindView(R.id.edt_license_no)
    EditText edtLicenceNo;
    @BindView(R.id.edt_truck_number)
    EditText edtTruckNo;
   /* @BindView(R.id.edt_invoice_no)
    EditText edtInvoiceNo;
    @BindView(R.id.edt_coa)
    EditText edtCOA;*/
    private AllGateEntry mAllGateEntry;


    public TruckDetailsFragment(AllGateEntry allGateEntry) {
        // Required empty public constructor
        mAllGateEntry = allGateEntry;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getFragment() {
        return this;
    }

    @Override
    protected void start() {
        loadFields();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_truck_details;
    }

    @Override
    protected boolean isFormValid() {
        if (edtSupplierName.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_enter_supplier_name));
            return false;
        }
        if (edtDriverName.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_enter_driver_name));
            return false;
        }
        if (edtMobileNo.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_enter_mobile_no));
            return false;
        }
        if (edtLicenceNo.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_enter_licence_no));
            return false;
        }
        if (edtTruckNo.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_enter_truck_no));
            return false;
        }

        return true;
    }

    @OnTextChanged(value = R.id.edt_supplier_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onSupplierAfterTextChange(CharSequence text){

        mGateEntryPresenter.setSupplierName(text.toString().trim());
    }
    @OnTextChanged(value = R.id.edt_driver_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onDriverAfterTextChange(CharSequence text){
        mGateEntryPresenter.setDriverName(text.toString().trim());
    }
    @OnTextChanged(value = R.id.edt_mobile_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onMobileAfterTextChange(CharSequence text){
        mGateEntryPresenter.setMobileNo(text.toString().trim());
    }
    @OnTextChanged(value = R.id.edt_license_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onLicenceAfterTextChange(CharSequence text){
        mGateEntryPresenter.setLicenceNo(text.toString().trim());
    }
    @OnTextChanged(value = R.id.edt_truck_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTruckAfterTextChange(CharSequence text){
        mGateEntryPresenter.setTruckNo(text.toString().trim());
    }

   /* @OnClick(R.id.btn_submit)
    void onButtonSubmitClickListener() {
        if (ConnectionUtils.getConnectivityStatusString(getActivity())) {
            showProgressDialog(getString(R.string.msg_please_wait), true);
            GateEntryMaterial gateEntryMaterial = new GateEntryMaterial();
            gateEntryMaterial.setSupplierName(edtSupplierName.getText().toString());
            gateEntryMaterial.setDriverName(edtDriverName.getText().toString());
            gateEntryMaterial.setMobileNo(edtMobileNo.getText().toString());
            gateEntryMaterial.setLicenseNo(edtLicenceNo.getText().toString());
            gateEntryMaterial.setTruckNo(edtTruckNo.getText().toString());
          *//*  gateEntryMaterial.setInvocieNo(edtInvoiceNo.getText().toString());
            gateEntryMaterial.setCoa(edtCOA.getText().toString());*//*
            if(mAllGateEntry!=null)
            {
                gateEntryMaterial.setGateENtryId(String.valueOf(mAllGateEntry.getGateEntryId()));
            }

            mGateEntryPresenter.setGateEntryMaterialFields(getActivity(), gateEntryMaterial, new IGateEntrySuccessView() {
                @Override
                public void gateEntrySuccessView(String gateEntryId) {
                    // launch new activity
                    dismissProgressDialog();
                    launchNewActivity(gateEntryId);
                }

                @Override
                public void gateEntryUpdateSuccessView(AllGateEntry allGateEntry) {
                    dismissProgressDialog();
                    getActivity().finish();
                }

                @Override
                public void gateEntryFailed() {
                    showToast(getString(R.string.msg_failed));
                    dismissProgressDialog();
                }
            });

        }
    }*/

    void launchNewActivity(String entryId) {
        SPTRaceWizsConstants.saveGateEntryId(getActivity(), entryId);
        Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
        intent.putExtra(I_GATE_ENTRY, entryId);
        startActivity(intent);
        getActivity().finish();
    }

    void loadFields() {
        if (mAllGateEntry != null) {
            edtSupplierName.setText(mAllGateEntry.getSupplierName());
            edtDriverName.setText(mAllGateEntry.getDriverName());
            edtMobileNo.setText(mAllGateEntry.getMobileNo());
            edtLicenceNo.setText(mAllGateEntry.getLicenseNo());
            edtTruckNo.setText(mAllGateEntry.getTruckNo());
           /* edtInvoiceNo.setText(mAllGateEntry.getInvoiceNo());
            edtCOA.setText(mAllGateEntry.getCoa());*/
        }
    }
}