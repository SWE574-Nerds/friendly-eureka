package com.example.canma.eurekaswe.interfaces.calls;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface UnFollowApi {


@GET("listory/{id}/unfollow")
Call<ResponseBody> follow(@Header("Authorization") String auth, @Path("id") String id);



}
