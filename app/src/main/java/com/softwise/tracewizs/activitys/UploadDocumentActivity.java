package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.softwise.tracewizs.BuildConfig;
import com.softwise.tracewizs.R;
import com.softwise.tracewizs.adapter.UploadMultipleFilesAdapter;
import com.softwise.tracewizs.helper.DialogHelper;

import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.listeners.IBooleanListener;
import com.softwise.tracewizs.listeners.OnPDFCreatedInterface;
import com.softwise.tracewizs.models.ImageToPDFOptions;
import com.softwise.tracewizs.models.UploadFiles;
import com.softwise.tracewizs.network.FileUploaderContract;
import com.softwise.tracewizs.network.FileUploaderModel;
import com.softwise.tracewizs.network.FileUploaderPresenter;
import com.softwise.tracewizs.network.ServiceGenerator;
import com.softwise.tracewizs.picker.ImageContract;
import com.softwise.tracewizs.picker.ImagePresenter;
import com.softwise.tracewizs.utils.CreatePdf;
import com.softwise.tracewizs.utils.FileCompressor;
import com.softwise.tracewizs.utils.ImageUtils;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;
import com.zhihu.matisse.Matisse;

import org.spongycastle.crypto.agreement.srp.SRP6Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.itextpdf.text.PageSize.*;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_IMAGE_FILE;

public class UploadDocumentActivity extends AppCompatActivity implements
        ImageContract.View, FileUploaderContract.View,
        UploadMultipleFilesAdapter.OnFileSelectListeners, OnPDFCreatedInterface {
    static final int REQUEST_TAKE_PHOTO = 1001;
    static final int REQUEST_GALLERY_PHOTO = 1002;
    static String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static int IMAGE = 100;
    //Bitmap bitmap;
    @BindView(R.id.img_invoice)
    ImageView imgInvoice;
    @BindView(R.id.img_coa)
    ImageView imgCOA;
    @BindView(R.id.img_upload_extra)
    ImageView imgExtra;
    @BindView(R.id.txt_invoice_path)
    TextView txtInvoicePath;
    @BindView(R.id.txt_coa_path)
    TextView txtCOAPath;
    @BindView(R.id.rel_invoice)
    RelativeLayout relInvoice;
    @BindView(R.id.rel_coa)
    RelativeLayout relCoa;
    @BindView(R.id.rel_extra)
    RelativeLayout relExtra;
    @BindView(R.id.rcv_extra_doc)
    RecyclerView rcvExtraDoc;
    @BindView(R.id.lin_inv_coa)
    LinearLayout linInvCOA;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    Toolbar mToolbar;
    File mPhotoFile;
    //private String mGateEntryId;
    private ImagePresenter mImagePresenter;
    private FileUploaderPresenter mUploaderPresenter;
    private FileCompressor mCompressor;
    private String invoicePath, coaPath;
    private List<UploadFiles> mUploadFileList = new ArrayList<>();
    private UploadMultipleFilesAdapter mUploadMultipleFilesAdapter;
    private String callType;
    private int mMarginTop = 50;
    private int mMarginBottom = 38;
    private int mMarginLeft = 50;
    private int mMarginRight = 38;
    private String mPageNumStyle;
    private ImageToPDFOptions mPdfOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.main_toolbar);
        txtTitle.setText(getString(R.string.title_all_gate_entry));
        mImagePresenter = new ImagePresenter(this);
        mUploaderPresenter = new FileUploaderPresenter(this, new FileUploaderModel(ServiceGenerator.createService()));
        mCompressor = new FileCompressor(this);
        loadMoreUploadList();
       /* if (getIntent().getExtras() != null) {
            //mGateEntryId = getIntent().getStringExtra(TraceWizConstant.I_GATE_ENTRY);
            loadMoreUploadList();
        } else {
            finish();
        }*/
    }

    private void loadMoreUploadList() {
        mUploadFileList = new ArrayList<>();
        rcvExtraDoc.setLayoutManager(new GridLayoutManager(this, 3));
        mUploadMultipleFilesAdapter = new UploadMultipleFilesAdapter(getApplicationContext(), mUploadFileList, this);
        rcvExtraDoc.setNestedScrollingEnabled(false);
        rcvExtraDoc.setAdapter(mUploadMultipleFilesAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.img_upload_invoice_no)
    void onSelectInvoice() {
        IMAGE = 100;
        callType = "A";
        selectImage();
    }

    @OnClick(R.id.img_upload_cao)
    void onSelectCOA() {
        IMAGE = 200;
        callType = "A";
        selectImage();
    }

    @OnClick(R.id.img_upload_extra)
    void onSelectExtraDoc() {
        IMAGE = 300;
        callType = "P";
        selectImage();
    }

    @OnClick(R.id.btn_upload)
    void onUploadFiles() {
        //mUploaderPresenter.onMultipleFileSelected(getApplicationContext(), mUploadFileList, "1");
        //convertButton();
        String directory = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));

        startCreatingPDF("COANewFile",directory);

        /*if (ConnectionUtils.getConnectivityStatusString(getApplicationContext())) {
            // upload doc
            // mUploaderPresenter.onFileSelected(mImagePresenter.getImage(), "androidwave", "info@androidwave");
            DialogHelper.showProgressDialog(UploadDocumentActivity.this, getString(R.string.msg_please_wait));
            if (invoicePath != null && coaPath != null) {
                mUploaderPresenter.onFileSelectedWithoutShowProgress(getApplicationContext(), invoicePath, coaPath, mGateEntryId);
            } else {
                if (mUploadFileList.size() > 0) {
                    mUploaderPresenter.onMultipleFileSelected(getApplicationContext(), mUploadFileList, mGateEntryId);
                } else {
                    MethodHelper.showToast(getApplicationContext(), getString(R.string.first_seleted_media));
                }
            }
        } else {
            MethodHelper.showToast(getApplicationContext(), getString(R.string.msg_no_internet));
        }*/
    }

    @OnClick(R.id.btn_delete)
    void onDeleteClick() {
        String directory = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        //String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // File directory = new File(folder);
        File myPDFFile = new File(directory + "/invoicenew" + ".pdf");
        if (myPDFFile.exists()) {
            myPDFFile.delete();
        }
    }

    public void convertButton() {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // File directory = new File(folder);

        if (!directory.exists()) {
            directory.mkdirs();
        }
        PdfDocument pdfDocument = new PdfDocument();
        int count = 1;
        for (UploadFiles uploadFiles : mUploadFileList) {
            Bitmap bitmap = BitmapFactory.decodeFile(uploadFiles.getFilePath() + "/" + uploadFiles.getFileName());
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(600, 1280, count).create();
            PdfDocument.Page page = pdfDocument.startPage(myPageInfo);

            page.getCanvas().drawBitmap(bitmap, 0, 0, null);

            //String pdfFile = directory + uploadFiles.getFileName();
            // File myPDFFile = new File(uploadFiles.getFilePath()+"/invoice"+".pdf");
            pdfDocument.finishPage(page);
            count++;
        }


        File myPDFFile = new File(directory + "/invoicenew" + ".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(myPDFFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_gallery),
                getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocumentActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Capture Photo")) {
                mImagePresenter.cameraClick();
            } else if (items[item].equals("Choose from Library")) {
                mImagePresenter.chooseGalleryClick();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    void launchNewScreen() {
        SPTRaceWizsConstants.saveGateEntryId(getApplicationContext(), null);
        startActivity(new Intent(UploadDocumentActivity.this, GateEntryActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File resultedFile = null;
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    resultedFile = mCompressor.compressToFile(mPhotoFile);
                    mImagePresenter.saveImage(resultedFile.getAbsolutePath());
                    mImagePresenter.showPreview(resultedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    resultedFile = mCompressor.compressToFile(new File(Objects.requireNonNull(getRealPathFromUri(selectedImage))));
                    mImagePresenter.saveImage(resultedFile.getAbsolutePath());
                    mImagePresenter.showPreview(resultedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (100 == IMAGE) {
                txtInvoicePath.setText("Invoice File");
                relInvoice.setVisibility(View.VISIBLE);
                invoicePath = mImagePresenter.getImage();
            } else if (200 == IMAGE) {
                txtCOAPath.setText("COA File");
                relCoa.setVisibility(View.VISIBLE);
                coaPath = mImagePresenter.getImage();
            } else if (300 == IMAGE) {
                rcvExtraDoc.setVisibility(View.VISIBLE);
                UploadFiles uploadFiles = new UploadFiles();
                uploadFiles.setFilePath(mImagePresenter.getImage());
                //mUploadFileList.add(uploadFiles);
                mUploadMultipleFilesAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showErrorMessage(String message) {
        DialogHelper.dismissProgressDialog();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadCompleted() {
        DialogHelper.dismissProgressDialog();
        //Toast.makeText(getApplicationContext(), getString(R.string.file_upload_successful), Toast.LENGTH_SHORT).show();
        if (mUploadFileList.size() == 0) {
            DialogHelper.conformationDialog(UploadDocumentActivity.this, getString(R.string.dialog_message), getString(R.string.msg_upload_more_image), new IBooleanListener() {
                @Override
                public void callBack(boolean result) {
                    if (result) {
                        linInvCOA.setVisibility(View.GONE);
                        relExtra.setVisibility(View.VISIBLE);
                        invoicePath = null;
                        coaPath = null;
                        loadMoreUploadList();
                    } else {
                        launchNewScreen();
                    }
                }
            });
        } else {
            // MethodHelper.showToast(getApplicationContext(),getString(R.string.file_upload_successful));
            DialogHelper.showMessageDialog(UploadDocumentActivity.this, getString(R.string.dialog_message), getString(R.string.file_upload_successful), new IBooleanListener() {
                @Override
                public void callBack(boolean result) {
                    launchNewScreen();
                }
            });
            // DialogHelper.showMessageDialog(getApplicationContext(),getString(R.string.file_upload_successful));

        }
    }

    @Override
    public void setUploadProgress(int progress) {

    }

    @Override
    public boolean checkPermission() {
        for (String mPermission : permissions) {
            int result = ActivityCompat.checkSelfPermission(this, mPermission);
            if (result == PackageManager.PERMISSION_DENIED) return false;
        }
        return true;
    }

    @Override
    public void showPermissionDialog(boolean isGallery) {
        Dexter.withActivity(this).withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isGallery) {
                                mImagePresenter.chooseGalleryClick();
                            } else {
                                mImagePresenter.cameraClick();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> showErrorDialog())
                .onSameThread()
                .check();
    }

    @Override
    public File getFilePath() {
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public void openSettings() {
    }

    @Override
    public void startCamera(File file) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            if (file != null) {
                Uri mPhotoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider", file);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                mPhotoFile = file;
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    public void chooseGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    @Override
    public File newFile() {
        Calendar cal = Calendar.getInstance();
        long timeInMillis = cal.getTimeInMillis();
        String mFileName = String.valueOf(timeInMillis) + ".jpeg";
        File mFilePath = getFilePath();
        try {
            File newFile = new File(mFilePath.getAbsolutePath(), mFileName);
            newFile.createNewFile();
            UploadFiles uploadFiles = new UploadFiles();
            uploadFiles.setFilePath(mFilePath.getAbsolutePath());
            uploadFiles.setFileName(mFileName);
            mUploadFileList.add(uploadFiles);
            return newFile;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void showErrorDialog() {
        Toast.makeText(getApplicationContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayImagePreview(File mFile) {
        if (100 == IMAGE) {
            relInvoice.setVisibility(View.VISIBLE);
            Glide.with(UploadDocumentActivity.this).load(mFile).apply(new RequestOptions().centerCrop().circleCrop()).into(imgInvoice);
        } else if (200 == IMAGE) {
            relCoa.setVisibility(View.VISIBLE);
            Glide.with(UploadDocumentActivity.this).load(mFile).apply(new RequestOptions().centerCrop().circleCrop()).into(imgCOA);
        }
    }

    @Override
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.message_need_permission));
        builder.setMessage(getString(R.string.message_grant_permission));
        builder.setPositiveButton(getString(R.string.label_setting), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void fileSelect(UploadFiles uploadFiles) {
        mUploadFileList.add(uploadFiles);
        mUploadMultipleFilesAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteFile(UploadFiles uploadFiles, int pos) {
        mUploadFileList.remove(pos);
        mUploadMultipleFilesAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.img_invoice)
    void OnInvoiceCheckListener() {
        String file = invoicePath;
        viewImage(file);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.img_coa)
    void OnCOACheckListener() {
        String file = coaPath;
        viewImage(file);

    }

    private void viewImage(String file) {
        Intent intent = new Intent(UploadDocumentActivity.this, ImageViewActivity.class);
        intent.putExtra(I_IMAGE_FILE, file);
        startActivity(intent);
    }

    private void startCreatingPDF(String fileName, String mPath) {
        mPdfOptions = new ImageToPDFOptions();
        ArrayList<String> mImagesUri = new ArrayList<>();
        for (UploadFiles uploadFiles : mUploadFileList) {
            mImagesUri.add(uploadFiles.getFilePath()+"/"+uploadFiles.getFileName());
        }
        mPdfOptions.setImagesUri(mImagesUri);
        //mPdfOptions.setPageSize(PageSizeUtils.mPageSize);
        mPdfOptions.setImageScaleType(ImageUtils.getInstance().mImageScaleType);
        mPdfOptions.setPageNumStyle(mPageNumStyle);
        mPdfOptions.setOutFileName(fileName);
        new CreatePdf(mPdfOptions, mPath, this).execute();
    }

    @Override
    public void onPDFCreationStarted() {

    }

    @Override
    public void onPDFCreated(boolean success, String path) {
        MethodHelper.showToast(getApplicationContext(), String.valueOf(success) + "\n" + path);
    }
}