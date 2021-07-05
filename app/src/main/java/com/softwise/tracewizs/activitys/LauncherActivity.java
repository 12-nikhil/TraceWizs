package com.softwise.tracewizs.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.helper.HelperPreference;
import com.softwise.tracewizs.helper.HelperUtils;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import static com.softwise.tracewizs.utils.TraceWizConstant.I_GATE_ENTRY;
import static com.softwise.tracewizs.utils.TraceWizConstant.ROLE_GATE_ENTRY;

public class LauncherActivity extends AppCompatActivity {

    private int PERMISSION_ALL_REQ_CODE = 201;
    private String[] PERMISSIONS_NEEDED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        PERMISSIONS_NEEDED = HelperUtils.getManifestPermissions(this);

        if (HelperUtils.hasPermissions(this, PERMISSIONS_NEEDED)) {
            handleDirection();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_NEEDED, PERMISSION_ALL_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ALL_REQ_CODE && (grantResults.length > 0) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handleDirection();
        } else {
            MethodHelper.showToast(getApplicationContext(), getString(R.string.permission_not_granted_msg));
            finishAffinity();
        }
    }

    private void handleDirection() {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                String entryId = SPTRaceWizsConstants.getGateEntryId(getApplicationContext());
                String role = SPTRaceWizsConstants.getUserRole(getApplicationContext());
                if (role!=null) {
                    if (ROLE_GATE_ENTRY.equals(role)) {
                        if (entryId != null) {
                            SPTRaceWizsConstants.saveGateEntryId(getApplicationContext(), entryId);
                            intent = new Intent(getApplicationContext(), UploadDocumentActivity.class);
                            intent.putExtra(I_GATE_ENTRY, entryId);
                        } else {
                            intent = new Intent(LauncherActivity.this, GateEntryActivity.class);
                        }
                    } else {
                        intent = new Intent(LauncherActivity.this, ListGateEntryActivity.class);
                    }
                } else {
                    intent = new Intent(LauncherActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, getResources().getInteger(R.integer.splashscreen_duration));
    }
}