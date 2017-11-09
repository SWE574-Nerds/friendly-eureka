package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.ResponseLogin;
import com.example.canma.eurekaswe.data.ResponseRegister;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface LoginApi {


@POST("user/login/")
Call<ResponseLogin> login(@Header("Content-Type") String contentType, @Header("Accept") String contentType2, @Body RequestBody params);



}
