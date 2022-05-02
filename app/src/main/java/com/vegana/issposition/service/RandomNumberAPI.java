package com.vegana.issposition.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RandomNumberAPI {
    @Headers({
            "Accept: application/json, text/javascript"
    })
    @GET("{number}/trivia")
    Call<String> getRandomNumber(@Path("number") Integer number);
}