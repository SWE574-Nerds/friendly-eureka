package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.CreateData;
import com.example.canma.eurekaswe.data.ResponseLogin;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface CreateApi {


@POST("listory/create/")
Call<CellData> list(@Header("Content-Type") String contentType, @Header("Authorization") String contentType2, @Body CreateData content);



}
