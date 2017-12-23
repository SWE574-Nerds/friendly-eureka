package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.AnnotateData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface AnnotateTextApi {


@POST("annotation/{id}/text/")
Call<ResponseBody> annotate(@Header("Content-Type") String contentType, @Header("Authorization") String contentType2, @Path("id") String postId, @Body AnnotateData content);



}
