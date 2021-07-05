package com.softwise.tracewizs.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.listeners.fragmentview.IProgressView;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.UnloadingCheck;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class CheckAndCorrectFragment extends CheckStorageBaseFragment implements IProgressView {
    @BindView(R.id.chk_inv)
    CheckBox chkInv;
    @BindView(R.id.chk_coa)
    CheckBox chkCOA;
    @BindView(R.id.chk_qty)
    CheckBox chkQty;
    @BindView(R.id.chk_material)
    CheckBox chkMaterial;
    @BindView(R.id.edt_batch_no)
    EditText edtBatchNo;
    @BindView(R.id.edt_note)
    EditText edtNote;
    //private UnloadingCheck mUnloadingCheck;
    private AllGateEntry mAllGateEntry;
    private boolean isInvCheck;
    private boolean isCOACheck;
    private boolean isMaterialCheck;
    private boolean isI;


    public CheckAndCorrectFragment() {
        // Required empty public constructor
    }

    public CheckAndCorrectFragment(AllGateEntry allGateEntry) {
        mAllGateEntry = allGateEntry;
    }

    @Override
    protected Fragment getFragment() {
        return this;
    }

    @Override
    protected void start() {
        int gateEntryId = mAllGateEntry.getGateEntryId();
        int userId = Integer.parseInt(SPTRaceWizsConstants.getUserId(getActivity()));
        mCheckStoragePresenter.setGateEntryIdAndUserId(getActivity(),gateEntryId,userId);
        clickListeners();
    }

    private void clickListeners() {
        chkInv.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mCheckStoragePresenter.setInvCheck(isChecked);
        });
        chkCOA.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mCheckStoragePresenter.setCOACheck(isChecked);
        });
        chkMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mCheckStoragePresenter.setMaterialCheck(isChecked);
        });
        chkQty.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mCheckStoragePresenter.setQtyCheck(isChecked);
        });
        edtBatchNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("Batch No text",s.toString());
                mCheckStoragePresenter.setBatchNo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCheckStoragePresenter.setNote(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnTextChanged(value = R.id.edt_batch_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void nameChanged(CharSequence text) {
        //do stuff
        Log.e("B No",text.toString());
        mCheckStoragePresenter.setBatchNo(text.toString());
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_check_and_correct;
    }

    @Override
    protected boolean isFormValid() {
       /* if(!chkInv.isChecked())
        {
            MethodHelper.showToast(getActivity(),getString(R.string.msg_please_check_inv));
            return false;
        }
        if(!chkCOA.isChecked())
        {
            MethodHelper.showToast(getActivity(),getString(R.string.msg_please_check_coa));
            return false;
        }
        if(!chkMaterial.isChecked())
        {
            MethodHelper.showToast(getActivity(),getString(R.string.msg_please_check_material));
            return false;
        }
        if(!chkQty.isChecked())
        {
            MethodHelper.showToast(getActivity(),getString(R.string.msg_please_check_qty));
            return false;
        }*/
        if (edtBatchNo.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_please_enter_batch_no));
            return false;
        }
        return true;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void handleGateEntryFailure(String errorMsg) {
        MethodHelper.showToast(getActivity(), errorMsg);
    }
}