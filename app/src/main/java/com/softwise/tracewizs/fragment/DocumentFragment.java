package com.softwise.tracewizs.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.softwise.tracewizs.BuildConfig;
import com.softwise.tracewizs.R;
import com.softwise.tracewizs.activitys.GateEntryActivity;
import com.softwise.tracewizs.activitys.ListGateEntryActivity;
import com.softwise.tracewizs.activitys.UploadDocumentActivity;
import com.softwise.tracewizs.helper.DialogHelper;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.listeners.IBooleanListener;
import com.softwise.tracewizs.listeners.IGateEntrySuccessView;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.GateEntryMaterial;
import com.softwise.tracewizs.models.ImageToPDFOptions;
import com.softwise.tracewizs.models.UploadFiles;
import com.softwise.tracewizs.network.FileUploaderContract;
import com.softwise.tracewizs.network.FileUploaderModel;
import com.softwise.tracewizs.network.FileUploaderPresenter;
import com.softwise.tracewizs.network.ServiceGenerator;
import com.softwise.tracewizs.picker.ImageContract;
import com.softwise.tracewizs.picker.ImagePresenter;
import com.softwise.tracewizs.utils.ConnectionUtils;
import com.softwise.tracewizs.utils.CreatePdf;
import com.softwise.tracewizs.utils.FileCompressor;
import com.softwise.tracewizs.utils.ImageUtils;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static android.app.Activity.RESULT_OK;
import static com.softwise.tracewizs.utils.TraceWizConstant.IMAGE_BASE_URL;
import static com.softwise.tracewizs.utils.TraceWizConstant.I_GATE_ENTRY;

public class DocumentFragment extends GateEntryBaseFragment implements ImageContract.View, FileUploaderContract.View{
    @BindView(R.id.edt_invoice_no)
    EditText edtInvoiceNo;
    @BindView(R.id.edt_coa)
    EditText edtCOA;
    @BindView(R.id.txt_invoice_path)
    TextView txtInvoicePath;
    @BindView(R.id.txt_coa_path)
    TextView txtCOAPath;
    @BindView(R.id.rel_invoice)
    RelativeLayout relInvoice;
    @BindView(R.id.rel_coa)
    RelativeLayout relCoa;
    @BindView(R.id.img_invoice)
    ImageView imgInvoice;
    @BindView(R.id.img_coa)
    ImageView imgCOA;
    @BindView(R.id.img_upload_invoice_no)
    ImageView imgInvoiceUpload;
    @BindView(R.id.img_upload_cao)
    ImageView imgCOAUpload;

    private AllGateEntry mAllGateEntry;
    File mPhotoFile;
    private String mGateEntryId;
    private ImagePresenter mImagePresenter;
    private FileUploaderPresenter mUploaderPresenter;
    private FileCompressor mCompressor;
    private String invoicePath, coaPath;
    static final int REQUEST_TAKE_PHOTO = 1001;
    static final int REQUEST_GALLERY_PHOTO = 1002;
    static String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static int IMAGE = 100;
    private String callType;


    public DocumentFragment(AllGateEntry allGateEntry) {
        // Required empty public constructor
        mAllGateEntry = allGateEntry;
    }


    @Override
    protected Fragment getFragment() {
        return this;
    }

    @Override
    protected void start() {
        mImagePresenter = new ImagePresenter(this);
        mUploaderPresenter = new FileUploaderPresenter(this, new FileUploaderModel(ServiceGenerator.createService()));
        mCompressor = new FileCompressor(Objects.requireNonNull(getActivity()));
        loadFields();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_document;
    }

    @Override
    protected boolean isFormValid() {
        if (edtInvoiceNo.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_enter_invoice_no));
            return false;
        }
        if (edtCOA.getText().toString().isEmpty()) {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_enter_coa_no));
            return false;
        }
        if(invoicePath==null)
        {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_select_invoice_image));
            return false;
        }
        if(coaPath==null)
        {
            MethodHelper.showToast(getActivity(), getString(R.string.msg_select_coa_image));
            return false;
        }

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @OnTextChanged(value = R.id.edt_invoice_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onInvoiceAfterTextChange(CharSequence text){
        mGateEntryPresenter.setInvoiceNo(text.toString().trim());
    }
    @SuppressLint("NonConstantResourceId")
    @OnTextChanged(value = R.id.edt_coa, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onCOAAfterTextChange(CharSequence text){
        mGateEntryPresenter.setCOANo(text.toString().trim());
    }

    @OnClick(R.id.btn_submit)
    void onButtonSubmitClickListener() {
        if (ConnectionUtils.getConnectivityStatusString(getActivity())) {
            if(isFormValid()) {
               DialogHelper.showProgressDialog(getActivity(),getString(R.string.msg_please_wait));
                if (mAllGateEntry != null) {
                    mGateEntryPresenter.setGateEntryId(String.valueOf(mAllGateEntry.getGateEntryId()));
                }

                mGateEntryPresenter.setListenersForGateEntry(getActivity(),mAllGateEntry, new IGateEntrySuccessView() {
                    @Override
                    public void gateEntrySuccessView(String gateEntryId) {
                        mGateEntryId = gateEntryId;
                        // launch new activity
                        //dismissProgressDialog();
                        // launchNewActivity(gateEntryId);
                        //DialogHelper.showProgressDialog(getContext(), getString(R.string.msg_please_wait));
                        if("A".equals(callType)) {
                            if (invoicePath != null && coaPath != null) {
                                mUploaderPresenter.onFileSelectedWithoutShowProgress(getContext(), invoicePath, coaPath, gateEntryId);
                            }
                        }/*else {
                        if (mUploadFileList.size() > 0) {
                            mUploaderPresenter.onMultipleFileSelected(getApplicationContext(), mUploadFileList, mGateEntryId);
                        } else {
                            MethodHelper.showToast(getApplicationContext(), getString(R.string.first_seleted_media));
                        }
                    }*/
                    }

                    @Override
                    public void gateEntryUpdateSuccessView(AllGateEntry allGateEntry) {
                        dismissProgressDialog();
                        MethodHelper.showToast(getContext(),getString(R.string.msg_data_update_successfully));
                        launchNewScreen();
                    }

                    @Override
                    public void gateEntryFailed() {
                        showToast(getString(R.string.msg_failed));
                        dismissProgressDialog();
                    }
                });
            }

        }
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.img_upload_invoice_no)
    void onSelectInvoice() {
        IMAGE = 100;
        callType = "A";
        openDialogChooser();
    }

    @OnClick(R.id.img_upload_cao)
    void onSelectCOA() {
        IMAGE = 200;
        callType = "A";
        openDialogChooser();
    }

    public void openDialogChooser()
    {
        try{
            final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_gallery),
                    getString(R.string.cancel)};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File resultedFile = null;
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    resultedFile = mCompressor.compressToFile(mPhotoFile);
                    mImagePresenter.saveImage(resultedFile.getPath());
                    mImagePresenter.showPreview(resultedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    resultedFile = mCompressor.compressToFile(new File(Objects.requireNonNull(getRealPathFromUri(selectedImage))));
                    mImagePresenter.saveImage(resultedFile.getPath());
                    mImagePresenter.showPreview(resultedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (100 == IMAGE) {
                invoicePath = null;
                txtInvoicePath.setText("Invoice File");
                relInvoice.setVisibility(View.VISIBLE);
                invoicePath = mImagePresenter.getImage();
            } else if (200 == IMAGE) {
                coaPath = null;
                txtCOAPath.setText("COA File");
                relCoa.setVisibility(View.VISIBLE);
                coaPath = mImagePresenter.getImage();
            }
        }
    }

    @Override
    public void showErrorMessage(String message) {
        DialogHelper.dismissProgressDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadCompleted() {
        DialogHelper.dismissProgressDialog();
        //Toast.makeText(getApplicationContext(), getString(R.string.file_upload_successful), Toast.LENGTH_SHORT).show();
            DialogHelper.conformationDialog(getActivity(),getString(R.string.dialog_message), getString(R.string.msg_upload_more_image), new IBooleanListener() {
                @Override
                public void callBack(boolean result) {
                    if (result) {
                       launchUploadMoreScreen(mGateEntryId);
                    } else {
                        launchNewScreen();
                    }
                }
            });
    }

    @Override
    public void setUploadProgress(int progress) {

    }

    @Override
    public boolean checkPermission() {
        for (String mPermission : permissions) {
            int result = ActivityCompat.checkSelfPermission(getActivity(), mPermission);
            if (result == PackageManager.PERMISSION_DENIED) return false;
        }
        return true;
    }

    @Override
    public void showPermissionDialog(boolean isGallery) {
        Dexter.withActivity(getActivity()).withPermissions(permissions)
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
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> showErrorDialog())
                .onSameThread()
                .check();
    }

    @Override
    public File getFilePath() {
        return getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public void openSettings() {

    }

    @Override
    public void startCamera(File file) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            if (file != null) {
                Uri mPhotoURI = FileProvider.getUriForFile(getActivity(),
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
            return newFile;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void showErrorDialog() {
        Toast.makeText(getActivity(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayImagePreview(File mFile) {
        if (100 == IMAGE) {
            relInvoice.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(mFile).apply(new RequestOptions().centerCrop().circleCrop()).into(imgInvoice);
        } else if (200 == IMAGE) {
            relCoa.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(mFile).apply(new RequestOptions().centerCrop().circleCrop()).into(imgCOA);
        }
    }

    @Override
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.message_need_permission));
        builder.setMessage(getString(R.string.message_grant_permission));
        builder.setPositiveButton(getString(R.string.label_setting), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    void launchNewScreen() {
        if(mAllGateEntry!=null) {
            SPTRaceWizsConstants.saveGateEntryId(getActivity(), null);
            startActivity(new Intent(getContext(), ListGateEntryActivity.class));
            getActivity().finish();
        }else {
            SPTRaceWizsConstants.saveGateEntryId(getActivity(), null);
            startActivity(new Intent(getContext(), GateEntryActivity.class));
            getActivity().finish();
        }
    }
    void launchUploadMoreScreen(String gateEntryId) {
        SPTRaceWizsConstants.saveGateEntryId(getActivity(),null);
        Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
        intent.putExtra(I_GATE_ENTRY, gateEntryId);
        startActivity(intent);
        getActivity().finish();
    }
    void loadFields() {
        if (mAllGateEntry != null) {
            imgCOAUpload.setVisibility(View.GONE);
            imgInvoiceUpload.setVisibility(View.GONE);

            edtInvoiceNo.setText(mAllGateEntry.getInvoiceNo());
            edtCOA.setText(mAllGateEntry.getCoa());
            String file = IMAGE_BASE_URL + mAllGateEntry.getGateEntryId() + "/" ;
            if(mAllGateEntry.getInvoiceFile()!=null)
            {
                invoicePath = mAllGateEntry.getInvoiceFile();
                relInvoice.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(file+mAllGateEntry.getInvoiceFile()).apply(new RequestOptions().centerCrop().circleCrop()).into(imgInvoice);
            }
            if(mAllGateEntry.getCoaFile()!=null)
            {
                coaPath = mAllGateEntry.getCoaFile();
                relCoa.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(file+mAllGateEntry.getCoaFile()).apply(new RequestOptions().centerCrop().circleCrop()).into(imgCOA);
            }
        }
    }

}