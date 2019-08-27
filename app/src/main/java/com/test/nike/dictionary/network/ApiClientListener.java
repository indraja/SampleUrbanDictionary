package com.test.nike.dictionary.network;

import retrofit2.Call;;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
*  ApiClientListener interface, declare Retrofit APi call methods.
*/
public interface ApiClientListener {

    @GET("/define")
    Call<Dictionary> makeWordSearchApiCall(@Query("term") String searchHint);

}