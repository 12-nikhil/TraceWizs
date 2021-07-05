package com.softwise.tracewizs.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.softwise.tracewizs.models.ServerResponse;
import com.softwise.tracewizs.models.UploadFiles;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;
import com.softwise.tracewizs.utils.TraceWizConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class FileUploaderModel implements FileUploaderContract.Model {
    private final FileUploadService service;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public FileUploaderModel(FileUploadService service) {
        this.service = service;
    }

    /**
     * Create request body for image resource
     * @param file
     * @return
     */
    private RequestBody createRequestForImage(File file) {
        return RequestBody.create(MediaType.parse("image/*"), file);
    }

    /**
     * Create request body for video resource
     * @param file
     * @return
     */
    private RequestBody createRequestForVideo(File file) {
        return RequestBody.create(MediaType.parse("video/*"), file);
    }

    /**
     * Create request body for string
     *
     * @param descriptionString
     * @return
     */
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    /**
     * return multipart part request body
     * @param filePath
     * @return
     */
    private MultipartBody.Part createMultipartBody(String filePath, String keyName) {
        File file = new File(filePath);
        RequestBody requestBody = createRequestForImage(file);
        return MultipartBody.Part.createFormData(keyName, file.getName(), requestBody);
    }

    /**
     * return multi part body in format of FlowableEmitter
     * @param filePath
     * @param emitter
     * @return
     */
    private MultipartBody.Part createMultipartBody(String filePath, FlowableEmitter<Double> emitter, String keyName) {
        File file = new File(filePath);
        return MultipartBody.Part.createFormData(keyName, file.getName(), createCountingRequestBody(file, emitter));
    }

    private List<MultipartBody.Part> createListMultipartBody(List<UploadFiles> uploadFilesList, String keyName) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        for(UploadFiles uploadFiles:uploadFilesList) {
            File file = new File(uploadFiles.getFilePath());
            RequestBody requestBody = createRequestForImage(file);
            partList.add(MultipartBody.Part.createFormData(keyName, file.getName(), requestBody));
        }
        return partList;
    }

    private RequestBody createCountingRequestBody(File file, FlowableEmitter<Double> emitter) {
        RequestBody requestBody = createRequestForImage(file);
        return new CountingRequestBody(requestBody, (bytesWritten, contentLength) -> {
            double progress = (1.0 * bytesWritten) / contentLength;
            emitter.onNext(progress);
        });
    }



    @Override
    public Flowable<Double> uploadFile(Context context,String selectedFileInvoice,String selectedFileCOA, String gateEntryId) {
        RequestBody gId = createPartFromString(gateEntryId);
        String token = TraceWizConstant.BEARER+" "+ SPTRaceWizsConstants.getToken(context);
        return Flowable.create(emitter -> {
            try {
                service.onFileUploadDouble(token,gId,createMultipartBody(selectedFileInvoice, emitter,"invoice"),createMultipartBody(selectedFileInvoice, emitter,"coa"));
               /* ServerResponse response = service.onFileUploadDouble(token,gId,
                        createMultipartBody(selectedFileInvoice, emitter,"invoice"),createMultipartBody(selectedFileCOA, emitter,"coa"));*/
                emitter.onComplete();
            } catch (Exception e) {
                emitter.tryOnError(e);
                e.printStackTrace();
            }
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Single<ResponseBody> uploadFileWithoutProgress(Context context, String selectedFilePathInvoice, String selectedFilePathCOA, String gateEntryId) {
        RequestBody gId = createPartFromString(gateEntryId);
        String token = TraceWizConstant.BEARER+" "+ SPTRaceWizsConstants.getToken(context);
        return service.onFileUploadDouble(token,gId,
                createMultipartBody(selectedFilePathInvoice,"invoice"),createMultipartBody(selectedFilePathCOA,"coa"));
    }

    @Override
    public Single<ResponseBody> uploadMultipleFiles(Context context, List<UploadFiles> uploadFilesList, String gateEntryId) {
        RequestBody gId = createPartFromString(gateEntryId);
        String token = TraceWizConstant.BEARER+" "+ SPTRaceWizsConstants.getToken(context);
         return service.onFileUploadList(token,gId,createListMultipartBody(uploadFilesList,"photos"));
               /* ServerResponse response = service.onFileUploadDouble(token,gId,
                        createMultipartBody(selectedFileInvoice, emitter,"invoice"),createMultipartBody(selectedFileCOA, emitter,"coa"));*/

    }

   /* @Override
    public Single<ResponseBody> uploadFileWithoutProgress(String filePath, String username, String email) {
        RequestBody gId = createPartFromString("3");
        RequestBody mEmail = createPartFromString(email);
        return service.onFileUpload("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjE5MDAyOTkyLCJleHAiOjE2MTk4NjY5OTJ9.0Z11wwqDmRkp3W0vswTB5miHsG1aRVN7PoHXYwtkFOekFnFETwnhrb7NF5JM002XZK65gaH2U0xpIkLwSUVrYQ",gId,
                createMultipartBody(filePath,"invoice"),createMultipartBody("","coa"),createMultipartBody("","photo"));
       *//* return service.onFileUpload("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjE5MDAyOTkyLCJleHAiOjE2MTk4NjY5OTJ9.0Z11wwqDmRkp3W0vswTB5miHsG1aRVN7PoHXYwtkFOekFnFETwnhrb7NF5JM002XZK65gaH2U0xpIkLwSUVrYQ",gId,
                createMultipartBody(filePath,"invoice"),createMultipartBody(filePath,"coa"),createMultipartBody(filePath,"photo"));*//*
        //return service.onFileUpload(mUserName, mEmail, createMultipartBody(filePath));
    }*/
}