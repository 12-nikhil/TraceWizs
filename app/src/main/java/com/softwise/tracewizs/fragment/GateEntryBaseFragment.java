package com.softwise.tracewizs.fragment;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.impl.GateEntryPresenterImpl;
import com.softwise.tracewizs.listeners.FragmentButtonsClickListener;
import com.softwise.tracewizs.viewListeners.GateEntryPresenter;

import butterknife.OnClick;
import butterknife.Optional;



public abstract class GateEntryBaseFragment extends HelperFragment  {


    public GateEntryPresenter mGateEntryPresenter;

    public FragmentButtonsClickListener mSignUpFragmentClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGateEntryPresenter = GateEntryPresenterImpl.getInstance();
        mSignUpFragmentClickListener = (FragmentButtonsClickListener) getFragment().getActivity();
    }

    protected abstract boolean isFormValid();

    @Optional
    @OnClick(R.id.btn_next)
    void onClickNext() {
        if (isFormValid()) {
            mSignUpFragmentClickListener.onNextClick();
        }
    }

    @Optional
    @OnClick(R.id.btn_previous)
    void onClickPrevious() {
        //if (isFormValid()) {
            mSignUpFragmentClickListener.onPreviousClick();
        //}
    }

    /**
     * Request focus to edit text
     *
     * @param view - view to request
     */
    protected void requestFocus(View view) {
        if (view.requestFocus()) {
            getFragment().getActivity()
                    .getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
