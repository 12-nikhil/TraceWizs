package com.softwise.tracewizs.network;

import android.content.Context;

import com.softwise.tracewizs.models.ServerResponse;
import com.softwise.tracewizs.models.UploadFiles;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.ResponseBody;


public class FileUploaderContract {
    public interface View {
        void showErrorMessage(String message);

        void uploadCompleted();

        void setUploadProgress(int progress);
    }

    interface Presenter {
        void onFileSelected(Context context,String selectedFileInvoice, String selectedFileCOA, String gateEntryId);

        void onFileSelectedWithoutShowProgress(Context context,String selectedFileInvoice, String selectedFileCOA, String gateEntryId);

        void onMultipleFileSelected(Context context,List<UploadFiles> uploadFilesList, String gateEntryId);

        void cancel();
    }

    interface Model {
        Flowable<Double> uploadFile(Context context,String selectedFilePathInvoice,String selectedFilePathCOA, String gateEntryId);

        Single<ResponseBody> uploadFileWithoutProgress(Context context,String selectedFilePathInvoice,String selectedFilePathCOA, String gateEntryId);

        Single<ResponseBody> uploadMultipleFiles(Context context, List<UploadFiles> uploadFilesList, String gateEntryId);
    }
}
