package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.adapter.GateEntryAdapter;
import com.softwise.tracewizs.helper.DialogHelper;
import com.softwise.tracewizs.helper.HelperPreference;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.listeners.IBooleanListener;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.ServerResponse;
import com.softwise.tracewizs.models.UnloadingCheck;
import com.softwise.tracewizs.receiver.InternetConnectionReceiver;
import com.softwise.tracewizs.serverUtils.ApiClients;
import com.softwise.tracewizs.serverUtils.ServiceListeners.APIService;
import com.softwise.tracewizs.tracewizapplication.TraceWizApplication;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.softwise.tracewizs.helper.MethodHelper.getTokenFromSp;
import static com.softwise.tracewizs.utils.TraceWizConstant.CONTENT_TYPE;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_ALL_GATE_ENTRY;
import static com.softwise.tracewizs.utils.TraceWizConstant.UNAUTHORISED;

public class ListGateEntryActivity extends AppCompatActivity implements
        GateEntryAdapter.OnMaterialSelectListeners, InternetConnectionReceiver.ConnectivityReceiverListener {

    @BindView(R.id.rcv_gate_entry)
    RecyclerView rcvGateEntry;

    @BindView(R.id.txt_empty_data)
    TextView txtEmptyData;

    @BindView(R.id.prb_load)
    ProgressBar prbLoad;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    Toolbar mToolbar;

    List<AllGateEntry> mGateEntryMaterialList = new ArrayList<>();
    GateEntryAdapter mGateEntryAdapter;
    InternetConnectionReceiver myReceiver;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gate_entry);
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.main_toolbar);
        txtTitle.setText(getString(R.string.title_all_gate_entry));
        myReceiver = new InternetConnectionReceiver();
        checkConnection();
    }

    private void checkConnection() {
        isConnected = InternetConnectionReceiver.isConnected();
        if (isConnected) {
            getAllGateEntryList();
        } else {
            MethodHelper.showToast(getApplicationContext(), getString(R.string.msg_no_internet));
        }
    }

    private void loadMoreUploadList() {
        rcvGateEntry.setLayoutManager(new LinearLayoutManager(this));
        mGateEntryAdapter = new GateEntryAdapter(getApplicationContext(), mGateEntryMaterialList, this);
        rcvGateEntry.setNestedScrollingEnabled(false);
        rcvGateEntry.setAdapter(mGateEntryAdapter);
    }

    private void getAllGateEntryList() {
        prbLoad.setVisibility(View.VISIBLE);
        int userId = Integer.parseInt(SPTRaceWizsConstants.getUserId(getApplicationContext()));
        Log.e("User ID",String.valueOf(userId));

        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
        Observable<List<AllGateEntry>> observable = apiService.getAllEntryListWithoutId(getTokenFromSp(getApplicationContext()), CONTENT_TYPE).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<List<AllGateEntry>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("ListGateEntryActivity", e.getMessage());
                if (UNAUTHORISED.equals(e.getMessage().trim())) {
                    //call refresh token
                    getRefreshToken(userId);
                } else {
                    MethodHelper.showToast(getApplicationContext(), getString(R.string.msg_something_went_wrong));
                    // prbLoad.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNext(List<AllGateEntry> allGateEntries) {
                if (allGateEntries != null) {
                    mGateEntryMaterialList = allGateEntries;
                    loadMoreUploadList();
                    initAdapter();
                }
            }
        });

    }

    private void initAdapter() {
        if (mGateEntryMaterialList.size() > 0) {
            prbLoad.setVisibility(View.GONE);
            rcvGateEntry.setVisibility(View.VISIBLE);
            txtEmptyData.setVisibility(View.GONE);
            mGateEntryAdapter.notifyDataSetChanged();
        } else {
            prbLoad.setVisibility(View.GONE);
            rcvGateEntry.setVisibility(View.GONE);
            txtEmptyData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*for registering to broadcast receiver*/
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, filter);
        TraceWizApplication.getInstance().setInternetConnectedListeners(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void materialSelect(AllGateEntry materials) {
        Intent intent = new Intent(ListGateEntryActivity.this, GateEntryDetailsActivity.class);
        intent.putExtra(I_ALL_GATE_ENTRY,(Parcelable)materials);
        startActivity(intent);
      /*  Intent intent = new Intent(ListGateEntryActivity.this, UnloadingActivity.class);
        intent.putExtra(I_ALL_GATE_ENTRY, (Parcelable) materials);
        startActivity(intent);*/
    }

    @Override
    public void deleteFile(AllGateEntry materials, int pos) {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }


    void getRefreshToken(int userID) {
        Log.e("USerId" ,String.valueOf(userID));
        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
        apiService.refreshToken(userID).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response != null) {
                    if(response.body()!=null) {
                        ServerResponse refreshTokenResponse = response.body();
                        SPTRaceWizsConstants.saveToken(getApplicationContext(), refreshTokenResponse.getMessage());
                        getAllGateEntryList();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("Refresh token", t.getMessage());
            }
        });
    }
    @OnClick(R.id.img_logout)
    void onLogoutClick()
    {
        DialogHelper.conformationDialog(ListGateEntryActivity.this, getString(R.string.lbl_confirmation), getString(R.string.msg_exit_app), new IBooleanListener() {
            @Override
            public void callBack(boolean result) {
                if(result)
                {
                    HelperPreference.clearSharedPreference(getApplicationContext());
                    startActivity(new Intent(ListGateEntryActivity.this, LoginActivity.class));
                    finishAffinity();
                }
            }
        });


    }
}