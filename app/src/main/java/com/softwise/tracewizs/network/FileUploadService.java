package com.softwise.tracewizs.network;

import com.softwise.tracewizs.models.ServerResponse;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService {
    @Multipart
    @POST("gate/entry/files")
    Single<ServerResponse> onFileUploadSingle(@Header("Authorization") String token,
                                            @Part("gateEntryId") RequestBody mUserName, @Part MultipartBody.Part file);
    @Multipart
    @POST("gate/entry/files")
    Single<ResponseBody> onFileUploadDouble(@Header("Authorization") String token,
                                            @Part("gateEntryId") RequestBody requestBody, @Part MultipartBody.Part file1, @Part MultipartBody.Part file2);

    // http://tracewiz.trumonitor.tech:8080/api/gate/entry/additional/files
    @Multipart
    @POST("gate/entry/additional/files")
    Single<ResponseBody> onFileUploadList(@Header("Authorization") String token,
                                          @Part("gateEntryId") RequestBody mUserName, @Part() List<MultipartBody.Part> file1);
    @Multipart
    @POST("gate/entry/files")
    Single<ServerResponse> onFileUploadThree(@Header("Authorization") String token,
                                           @Part("gateEntryId") RequestBody mUserName, @Part MultipartBody.Part file,@Part MultipartBody.Part file2,@Part MultipartBody.Part file3);
}
