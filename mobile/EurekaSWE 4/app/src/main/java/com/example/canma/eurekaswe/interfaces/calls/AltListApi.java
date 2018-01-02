package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.AltCellData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface AltListApi {


@GET("listory/{id}/annotations")
Call<List<AltCellData>> list(@Header("Authorization") String auth, @Path("id") String postId);



}
