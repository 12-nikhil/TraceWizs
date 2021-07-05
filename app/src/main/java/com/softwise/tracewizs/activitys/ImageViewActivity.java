package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.softwise.tracewizs.R;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.models.AllGateEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softwise.tracewizs.utils.TraceWizConstant.I_ALL_GATE_ENTRY;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_IMAGE_FILE;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_IMAGE_FILE_NAME;

public class ImageViewActivity extends AppCompatActivity {
    String mFile;
    String mFileName;
    @BindView(R.id.img_view)
    ImageView imgViewFile;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_logout)
    ImageView imgLogout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.main_toolbar);
        imgLogout.setVisibility(View.GONE);
        if (getIntent().getExtras() != null) {
            mFile = getIntent().getExtras().getString(I_IMAGE_FILE);
            mFileName = getIntent().getExtras().getString(I_IMAGE_FILE_NAME);
            txtTitle.setText(mFileName);
            if(!mFile.isEmpty()) {
                loadImage();
            }else {
                MethodHelper.showToast(getApplicationContext(),getString(R.string.msg_image_not_found));
                finish();
            }
        }
    }
    private void loadImage()
    {
        Glide.with(ImageViewActivity.this).load(mFile).apply(new RequestOptions().centerCrop().centerInside()).into(imgViewFile);
    }
}