package com.example.gambit.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("39")
    Call<List<ResponseData>> getDataGambit( @Query  ( "page" ) Integer page);

}
