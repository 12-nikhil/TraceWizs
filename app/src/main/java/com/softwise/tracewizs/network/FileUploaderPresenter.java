package com.softwise.tracewizs.network;

import android.content.Context;
import android.text.TextUtils;

import com.softwise.tracewizs.models.UploadFiles;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FileUploaderPresenter implements FileUploaderContract.Presenter {

    private final FileUploaderContract.Model model;
    private final FileUploaderContract.View view;

    private Disposable videoUploadDisposable;

    public FileUploaderPresenter(FileUploaderContract.View view, FileUploaderContract.Model model) {
        this.view = view;
        this.model = model;
    }


    /*@Override
    public void onFileSelectedWithoutShowProgress(String selectedFilePath, String userName, String email) {
        if (TextUtils.isEmpty(selectedFilePath)) {
            view.showErrorMessage("Incorrect file path");
            return;
        }
        videoUploadDisposable = model.uploadFileWithoutProgress(selectedFilePath, userName, email)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> view.uploadCompleted(),
                        error -> view.showErrorMessage(error.getMessage())
                );
    }*/

    @Override
    public void onFileSelected(Context context, String selectedFileInvoice, String selectedFileCOA, String gateEntryId) {
        if (TextUtils.isEmpty(selectedFileInvoice)) {
            view.showErrorMessage("Incorrect file path");
            return;
        }
        if (TextUtils.isEmpty(selectedFileCOA)) {
            view.showErrorMessage("Incorrect file path");
            return;
        }
        videoUploadDisposable = model.uploadFile(context,selectedFileInvoice, selectedFileCOA, gateEntryId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        progress -> view.setUploadProgress((int) (100 * progress)),
                        error -> view.showErrorMessage(error.getMessage()),
                        view::uploadCompleted
                );
    }

    @Override
    public void onFileSelectedWithoutShowProgress(Context context, String selectedFileInvoice, String selectedFileCOA, String gateEntryId) {
        if (TextUtils.isEmpty(selectedFileInvoice)) {
            view.showErrorMessage("Incorrect file path");
            return;
        }
        if (TextUtils.isEmpty(selectedFileCOA)) {
            view.showErrorMessage("Incorrect file path");
            return;
        }
        videoUploadDisposable = model.uploadFileWithoutProgress(context,selectedFileInvoice, selectedFileCOA, gateEntryId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> view.uploadCompleted(),
                        error -> view.showErrorMessage(error.getMessage())
                );
    }

    @Override
    public void onMultipleFileSelected(Context context, List<UploadFiles> uploadFilesList, String gateEntryId) {
        videoUploadDisposable = model.uploadMultipleFiles(context,uploadFilesList,gateEntryId).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> view.uploadCompleted(),
                        error -> view.showErrorMessage(error.getMessage())
                );
    }


    @Override
    public void cancel() {
        if (videoUploadDisposable != null && !videoUploadDisposable.isDisposed()) {
            videoUploadDisposable.dispose();
        }
    }
}
