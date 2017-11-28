package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.ResponseLogin;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ListApi {


@GET("listory")
Call<List<CellData>> list(@Header("Content-Type") String contentType, @Header("Authorization") String auth);



}
