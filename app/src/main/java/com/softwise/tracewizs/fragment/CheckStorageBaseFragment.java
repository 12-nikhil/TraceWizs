package com.softwise.tracewizs.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.impl.CheckStoragePresenterImpl;
import com.softwise.tracewizs.listeners.FragmentButtonsClickListener;
import com.softwise.tracewizs.viewListeners.CheckStoragePresenter;

import butterknife.OnClick;
import butterknife.Optional;


public abstract class CheckStorageBaseFragment extends HelperFragment  {

    public CheckStoragePresenter mCheckStoragePresenter;

    public FragmentButtonsClickListener mFragmentButtonsClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCheckStoragePresenter = CheckStoragePresenterImpl.getInstance();
        mFragmentButtonsClickListener = (FragmentButtonsClickListener) getFragment().getActivity();
    }

    protected abstract boolean isFormValid();

    @Optional
    @OnClick(R.id.btn_check_next)
    void onNextClick()
    {
        if (isFormValid()) {
            mFragmentButtonsClickListener.onNextClick();
        }
    }
    @Optional
    @OnClick(R.id.btn_check_previous)
    void onPreviousClick()
    {
        if (isFormValid()) {
            mFragmentButtonsClickListener.onPreviousClick();
        }
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
