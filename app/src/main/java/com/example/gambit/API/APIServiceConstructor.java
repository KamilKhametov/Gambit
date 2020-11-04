package com.example.gambit.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceConstructor {

    public static <T> T CreateService(Class<T> serviceClass){

        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl ( APIConfig.HOST_URL )
                .addConverterFactory ( GsonConverterFactory.create () )
                .build ();

        return retrofit.create ( serviceClass );

    }

}
