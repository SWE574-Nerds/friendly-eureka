package com.example.canma.eurekaswe.interfaces.calls;


import com.example.canma.eurekaswe.data.CellData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public interface SearchListApi {


@GET("listory/search/")
Call<List<CellData>> search( @Header("Authorization") String auth,@Query("keywords") String keyword);



}
