package com.softwise.tracewizs.serverUtils.ServiceListeners;

import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.GateEntryMaterial;
import com.softwise.tracewizs.models.LoginResponse;
import com.softwise.tracewizs.models.Material;
import com.softwise.tracewizs.models.ServerResponse;
import com.softwise.tracewizs.models.UnloadingCheck;
import com.softwise.tracewizs.models.UserCredentials;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;


public interface APIService {
    //public static String BASE_URL="http://tracewiz.trumonitor.tech:8080/api/";
    @POST("auth/applogin")
    Observable<LoginResponse> login(@Header("Content-Type")String contentType, @Body UserCredentials userCredentials);

    //http://tracewiz.trumonitor.tech:8080/api/auth/token/2/regenerate
    @GET("/auth/token/{userId}/regenerate")
    Call<ServerResponse> refreshToken(@Path("userId")int userId);

    @GET("/auth/token/{userId}/regenerate")
    Call<ServerResponse> refreshTokenString(@Path("userId")String userId);

    @GET("materials/{id}")
    Observable<List<Material>> getMaterialType(@Header("Authorization")String token, @Header("Content-Type")String contentType, @Path("id")String material);

    @POST("gate/entry/create")
    Observable<ServerResponse> createGateEntry(@Header("Authorization")String token,@Header("Content-Type")String contentType, @Body GateEntryMaterial gateEntryMaterial);

    @POST("gate/entry/update")
    Observable<AllGateEntry> updateGateEntry(@Header("Authorization")String token,@Header("Content-Type")String contentType, @Body GateEntryMaterial gateEntryMaterial);

    @GET("{userId}")
    Observable<ServerResponse> getRefreshToken(@Header("Content-Type")String contentType,@Path("userId")int userId);

    @GET("gate/entrys/{id}")
    Observable<List<AllGateEntry>> getAllEntryList(@Header("Authorization") String token, @Header("Content-Type")String contentType, @Path("id")int userId);

    @GET("gate/entrys")
    Observable<List<AllGateEntry>> getAllEntryListWithoutId(@Header("Authorization") String token, @Header("Content-Type")String contentType);

    @Multipart
    @POST("gate/entry/files")
    Observable<ServerResponse> uploadFiles(@Header("Authorization") String token,@Header("Content-Type")String contentType, @Part RequestBody requestBody);

    // http://tracewiz.trumonitor.tech:8080/api/unloading/test/create
    @POST("unloading/test/create")
    Observable<ServerResponse> createUnloading(@Header("Authorization")String token,@Header("Content-Type")String contentType, @Body UnloadingCheck unloadingCheck);

    @POST("unloading/test/update")
    Observable<ServerResponse> updateUnloading(@Header("Authorization")String token,@Header("Content-Type")String contentType, @Body UnloadingCheck unloadingCheck);


}

