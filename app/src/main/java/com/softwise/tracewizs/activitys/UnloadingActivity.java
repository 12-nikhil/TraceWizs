package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.adapter.GateEntryStepsPagerAdapter;
import com.softwise.tracewizs.adapter.UnloadingStepsPagerAdapter;
import com.softwise.tracewizs.customView.NonSwipingViewPager;
import com.softwise.tracewizs.listeners.FragmentButtonsClickListener;
import com.softwise.tracewizs.models.AllGateEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softwise.tracewizs.utils.TraceWizConstant.I_ALL_GATE_ENTRY;

public class UnloadingActivity extends AppCompatActivity implements FragmentButtonsClickListener {

    @BindView(R.id.vip_gate_entry_check)
    NonSwipingViewPager vipGateEntryCheck;

    @BindView(R.id.txt_title)
    TextView txtTitle;
    Toolbar mToolbar;

    private UnloadingStepsPagerAdapter mUnloadingStepsPagerAdapter;
    private AllGateEntry mAllGateEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unloading);
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.main_toolbar);
        txtTitle.setText(getString(R.string.title_unloading_check));
        if(getIntent().getExtras()!=null)
        {
            mAllGateEntry = (AllGateEntry) getIntent().getExtras().get(I_ALL_GATE_ENTRY);
        }
        setUpGateEntryForm();
    }

    private void setUpGateEntryForm() {
        mUnloadingStepsPagerAdapter = new UnloadingStepsPagerAdapter(getSupportFragmentManager(),mAllGateEntry);
        vipGateEntryCheck.setAdapter(mUnloadingStepsPagerAdapter);
    }

    @Override
    public void onNextClick() {
        int current = vipGateEntryCheck.getCurrentItem();
        if (current < 3) {
            vipGateEntryCheck.setCurrentItem(current + 1);
        }
    }

    @Override
    public void onPreviousClick() {
        int current = vipGateEntryCheck.getCurrentItem();
        if (current > 0) {
            vipGateEntryCheck.setCurrentItem(current - 1);
        }
    }
}