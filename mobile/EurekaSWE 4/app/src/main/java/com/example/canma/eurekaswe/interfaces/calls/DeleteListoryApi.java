package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.CategoryFormat;
import com.example.canma.eurekaswe.data.CreateData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface DeleteListoryApi {


@DELETE("listory/{id}/")
Call<ResponseBody> delete(@Header("Authorization") String auth,@Path("id") String id);



}
