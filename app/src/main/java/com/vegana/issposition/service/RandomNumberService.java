package com.vegana.issposition.service;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RandomNumberService {
    private Retrofit retrofit = null;


    /**
     * This method creates a new instance of the API interface.
     *
     * @return The ISS API interface
     */
    public RandomNumberAPI getAPI() {
        String BASE_URL = "http://numbersapi.com/";

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }

        return retrofit.create(RandomNumberAPI.class);
    }
}
