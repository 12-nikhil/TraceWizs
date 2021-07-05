package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.adapter.GateEntryAdapter;
import com.softwise.tracewizs.adapter.GateEntryStepsPagerAdapter;
import com.softwise.tracewizs.customView.NonSwipingViewPager;
import com.softwise.tracewizs.helper.DialogHelper;
import com.softwise.tracewizs.helper.HelperPreference;
import com.softwise.tracewizs.listeners.FragmentButtonsClickListener;
import com.softwise.tracewizs.listeners.IBooleanListener;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softwise.tracewizs.utils.TraceWizConstant.I_ALL_GATE_ENTRY;

public class GateEntryActivity extends AppCompatActivity implements FragmentButtonsClickListener {

    //ActivityGateEntryBinding binding;
    @BindView(R.id.vip_gate_entry)
    NonSwipingViewPager vipGateEntry;
    Toolbar mToolbar;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_logout)
    ImageView imgLogout;
    private GateEntryStepsPagerAdapter mGateEntryStepsPagerAdapter;
    private AllGateEntry mAllGateEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* binding = ActivityGateEntryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();*/
        setContentView(R.layout.activity_gate_entry);
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        txtTitle.setText(getString(R.string.title_gate_entry));
        if(getIntent().getExtras()!=null)
        {
            mAllGateEntry = (AllGateEntry) getIntent().getExtras().get(I_ALL_GATE_ENTRY);
            txtTitle.setText(getString(R.string.title_gate_entry_edit));
        }
        setUpGateEntryForm();

    }

    private void setUpGateEntryForm() {
        mGateEntryStepsPagerAdapter = new GateEntryStepsPagerAdapter(getSupportFragmentManager(),mAllGateEntry);
        vipGateEntry.setAdapter(mGateEntryStepsPagerAdapter);
    }

    @Override
    public void onNextClick() {
        int current = vipGateEntry.getCurrentItem();
        if (current < 3) {
            vipGateEntry.setCurrentItem(current + 1);
        }
    }

    @Override
    public void onPreviousClick() {
        int current = vipGateEntry.getCurrentItem();
        if (current > 0) {
            vipGateEntry.setCurrentItem(current - 1);
        }
    }

    @OnClick(R.id.img_logout)
    void onLogoutClick()
    {
        DialogHelper.conformationDialog(GateEntryActivity.this, getString(R.string.lbl_confirmation), getString(R.string.msg_exit_app), new IBooleanListener() {
            @Override
            public void callBack(boolean result) {
                if(result)
                {
                    HelperPreference.clearSharedPreference(getApplicationContext());
                    startActivity(new Intent(GateEntryActivity.this, LoginActivity.class));
                    finishAffinity();
                }
            }
        });
    }
}