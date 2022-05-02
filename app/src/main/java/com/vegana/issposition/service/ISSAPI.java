package com.vegana.issposition.service;

import com.vegana.issposition.model.DataISS;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ISSAPI {
    @Headers({
        "Accept: application/json, text/javascript"
    })
    @GET("iss/v1/?alt=1650&n=10")
    Call<DataISS> getNextTenISSPositions(@Query("lat") String lat, @Query("lon") String lon, @Query("alt") String alt);
}
