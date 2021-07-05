package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwise.tracewizs.R;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.models.AllGateEntry;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softwise.tracewizs.utils.TraceWizConstant.IMAGE_BASE_URL;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_ALL_GATE_ENTRY;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_IMAGE_FILE;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_IMAGE_FILE_NAME;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_PDF_FILE;

public class GateEntryDetailsActivity extends AppCompatActivity {
    Toolbar mToolbar;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_material_name)
    TextView txtMaterialName;
    @BindView(R.id.txt_qty_unit)
    TextView txtQtyUnit;
    @BindView(R.id.txt_supplier_name)
    TextView txtSupplierName;
    @BindView(R.id.txt_supplier_number)
    TextView txtSupplierNumber;
    @BindView(R.id.txt_inv_no)
    TextView txtInvNo;
    @BindView(R.id.txt_coa_no)
    TextView txtCOANo;
    @BindView(R.id.txt_driver_name)
    TextView txtDriverName;
    @BindView(R.id.txt_licence_no)
    TextView txtLicenceNo;
    @BindView(R.id.txt_truck_no)
    TextView txtTruckNo;
    @BindView(R.id.img_logout)
    ImageView imgLogout;
    private AllGateEntry mAllGateEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_entry_details);
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        txtTitle.setText(getString(R.string.title_all_gate_entry));
        imgLogout.setVisibility(View.GONE);
        if (getIntent().getExtras() != null) {
            mAllGateEntry = (AllGateEntry) getIntent().getExtras().get(I_ALL_GATE_ENTRY);
            loadData();
        }
    }

    private void loadData() {
        txtMaterialName.setText(mAllGateEntry.getMaterial().getMaterialName());
        txtQtyUnit.setText(mAllGateEntry.getQuantity() + " " + mAllGateEntry.getMaterial().getUnit().getName());
        txtSupplierName.setText(mAllGateEntry.getSupplierName());
        txtSupplierNumber.setText(mAllGateEntry.getMobileNo());
        txtInvNo.setText(mAllGateEntry.getInvoiceNo());
        txtCOANo.setText(mAllGateEntry.getCoa());
        txtDriverName.setText(mAllGateEntry.getDriverName());
        txtLicenceNo.setText(mAllGateEntry.getLicenseNo());
        txtTruckNo.setText(mAllGateEntry.getTruckNo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mn_unloading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mn_edit:
                Intent intent = new Intent(GateEntryDetailsActivity.this, GateEntryActivity.class);
                intent.putExtra(I_ALL_GATE_ENTRY, (Parcelable) mAllGateEntry);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.btn_continue)
    void OnClickContinue() {
        Intent intent = new Intent(GateEntryDetailsActivity.this, UnloadingActivity.class);
        intent.putExtra(I_ALL_GATE_ENTRY, (Parcelable) mAllGateEntry);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.img_invoice)
    void OnInvoiceCheckListener() {
        if (mAllGateEntry.getInvoiceFile() != null) {
            String file = IMAGE_BASE_URL + mAllGateEntry.getGateEntryId() + "/" + mAllGateEntry.getInvoiceFile();
            viewImage(file,getString(R.string.lbl_invoice));
        } else {
            MethodHelper.showToast(getApplicationContext(), getString(R.string.msg_image_not_found));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.img_coa)
    void OnCOACheckListener() {
        if (mAllGateEntry.getCoaFile() != null) {
            String file = IMAGE_BASE_URL + mAllGateEntry.getGateEntryId() + "/" + mAllGateEntry.getCoaFile();
            viewImage(file,getString(R.string.lbl_coa));
        } else {
            MethodHelper.showToast(getApplicationContext(), getString(R.string.msg_image_not_found));
        }
    }

    private void viewImage(String file,String fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(file));
        startActivity(intent);
       /* Intent intent = new Intent(GateEntryDetailsActivity.this, PDFViewActivity.class);
        intent.putExtra(I_PDF_FILE, file);
        intent.putExtra(I_IMAGE_FILE_NAME, fileName);
        startActivity(intent);*/
       /* Intent intent = new Intent(GateEntryDetailsActivity.this, ImageViewActivity.class);
        intent.putExtra(I_IMAGE_FILE, file);
        intent.putExtra(I_IMAGE_FILE_NAME, fileName);
        startActivity(intent);*/
    }
}