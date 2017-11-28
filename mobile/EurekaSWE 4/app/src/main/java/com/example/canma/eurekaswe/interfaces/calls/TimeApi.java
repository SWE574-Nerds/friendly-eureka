package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.TimeFormat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;


public interface TimeApi {


@GET("time")
Call<List<TimeFormat>> list(@Header("Content-Type") String contentType, @Header("Authorization") String auth);



}
