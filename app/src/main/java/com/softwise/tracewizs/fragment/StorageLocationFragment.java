package com.softwise.tracewizs.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.adapter.GateEntryAdapter;
import com.softwise.tracewizs.adapter.StorageLocationAdapter;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.listeners.IUnloadingSuccessView;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.Material;
import com.softwise.tracewizs.models.MaterialStorage;
import com.softwise.tracewizs.models.UnloadingCheck;
import com.softwise.tracewizs.utils.ConnectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StorageLocationFragment extends CheckStorageBaseFragment implements IUnloadingSuccessView {

    @BindView(R.id.rcv_location)
    RecyclerView rcvLocation;
    @BindView(R.id.txt_empty_data)
    TextView txtEmptyData;
    @BindView(R.id.edt_location)
    EditText edtLocation;
    @BindView(R.id.edt_qty)
    EditText edtQty;
    @BindView(R.id.rel_button_click)
    RelativeLayout relButtonClick;

    List<MaterialStorage> mStorageArrayList = new ArrayList<>();
    StorageLocationAdapter mStorageLocationAdapter;
    // private UnloadingCheck mUnloadingCheck;
    private AllGateEntry mAllGateEntry;

    public StorageLocationFragment() {
        // Required empty public constructor
    }

    public StorageLocationFragment(AllGateEntry allGateEntry) {
        mAllGateEntry = allGateEntry;
    }

    @Override
    protected Fragment getFragment() {
        return this;
    }

    @Override
    protected void start() {
        loadLocation();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_storage_localtion;
    }

    @Override
    protected boolean isFormValid() {
        if (edtLocation.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_please_enter_location));
            return false;
        }
        if (edtQty.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_please_enter_qty));
            return false;
        }
        return true;
    }

    @OnClick(R.id.img_add_location)
    void OnAddLocation() {
        MaterialStorage materialStorage = new MaterialStorage();
        materialStorage.setLocation(edtLocation.getText().toString().trim());
        materialStorage.setQty(edtQty.getText().toString().trim());
        mStorageArrayList.add(materialStorage);
        rcvLocation.setVisibility(View.VISIBLE);
        txtEmptyData.setVisibility(View.GONE);
        mStorageLocationAdapter.notifyDataSetChanged();
        edtLocation.setText("");
        edtQty.setText("");
        relButtonClick.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_add_data)
    void OnSubmitButtonListener() {
        if (ConnectionUtils.getConnectivityStatusString(getActivity())) {
            showProgressDialog(getString(R.string.msg_please_wait),false);
            mCheckStoragePresenter.setStorageFragmentView(mStorageArrayList, this);
        } else {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_no_internet));
        }
    }

    private void loadLocation() {
            rcvLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
            mStorageLocationAdapter = new StorageLocationAdapter(getActivity(), mStorageArrayList);
            rcvLocation.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
            rcvLocation.setNestedScrollingEnabled(false);
            rcvLocation.setItemAnimator(new DefaultItemAnimator());
            rcvLocation.setAdapter(mStorageLocationAdapter);
    }

    @Override
    public void gateUnloadingSuccessView(String unloadingId) {
        dismissProgressDialog();
        MethodHelper.showToast(getActivity(), getString(R.string.msg_data_save_successfully));
        getActivity().finish();
    }

    @Override
    public void gateUnloadingUpdateSuccessView(UnloadingCheck unloadingCheck) {
        dismissProgressDialog();
        MethodHelper.showToast(getActivity(), getString(R.string.msg_data_update_successfully));
        getActivity().finish();
    }

    @Override
    public void gateUnloadingFailed() {
        dismissProgressDialog();
        MethodHelper.showToast(getActivity(), getString(R.string.msg_data_save_failed));
    }
}