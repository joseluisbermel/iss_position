package com.vegana.issposition.presenter;

import com.vegana.issposition.Constants;
import com.vegana.issposition.model.DataISS;
import com.vegana.issposition.model.ResponseISS;
import com.vegana.issposition.service.RandomNumberService;
import com.vegana.issposition.view.RandomNumberView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomNumberPresenter {
    private RandomNumberView view;
    private RandomNumberService service;

    public RandomNumberPresenter(RandomNumberView view){
        this.view = view;

        if(this.service == null){
            this.service = new RandomNumberService();
        }
    }

    public void getRandomNumber(){
        this.service.getAPI()
                .getRandomNumber(Constants.selected.getDuration())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String data = response.body();

                        if (data != null) {
                            view.getRandomNumber(data);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        try {
                            t.printStackTrace();
                            throw new InterruptedException("Something went wrong!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
