package com.example.freightviewer.HttpRequests;

import com.example.freightviewer.Model.LoadModel;
import com.example.freightviewer.Model.User;
import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface JSONPlaceHolderApi {
    @GET
    Call<JsonObject> getLoadsForDriver(@Header("Authorization")String auth,@Url String url);

    @GET
    Call<LoadModel> getLoadByIdForDriver(@Header("Authorization")String auth, @Url String url);

    @GET
    Call<Void> updateLoadStopStatus(@Header("Authorization")String auth,@Url String url);

    @POST("/token/generate-token")
    Call<User> generateTokenDriver(@Body User user);

    @Multipart
    @POST("file/upload/pdf/0")
    Call<Void> uploadPdf(@Header("Authorization")String auth,@Part MultipartBody.Part file);
}
