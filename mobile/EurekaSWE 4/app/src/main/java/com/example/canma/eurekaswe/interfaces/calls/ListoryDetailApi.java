package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.CreateData;
import com.example.canma.eurekaswe.data.DetailResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface ListoryDetailApi {


@GET("listory/{id}")
Call<DetailResponse> getDetail(@Header("Authorization") String auth, @Path("id") String id);



}
