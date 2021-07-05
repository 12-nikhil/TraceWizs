package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.softwise.tracewizs.R;
import com.softwise.tracewizs.helper.MethodHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softwise.tracewizs.utils.TraceWizConstant.I_IMAGE_FILE;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_IMAGE_FILE_NAME;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_PDF_FILE;

public class PDFViewActivity extends AppCompatActivity {
    String mFile;
    String mFileName;
    @BindView(R.id.web_view)
    WebView webViewFile;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_logout)
    ImageView imgLogout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.main_toolbar);
        imgLogout.setVisibility(View.GONE);
        if (getIntent().getExtras() != null) {
            mFile = getIntent().getExtras().getString(I_PDF_FILE);
            mFileName = getIntent().getExtras().getString(I_IMAGE_FILE_NAME);
            txtTitle.setText(mFileName);
            if(!mFile.isEmpty()) {
                loadPDF();
            }else {
                MethodHelper.showToast(getApplicationContext(),getString(R.string.msg_image_not_found));
                finish();
            }
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void loadPDF()
    {
        webViewFile.getSettings().setJavaScriptEnabled(true);
        webViewFile.loadUrl(mFile);
    }
}