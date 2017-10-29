package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.ResponseRegister;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface RegisterApi {


@POST("user/register/")
Call<ResponseRegister> register(@Header("Content-Type") String contentType,@Header("Accept") String contentType2,@Body RequestBody params);


}
