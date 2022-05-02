package com.vegana.issposition.presenter;

import com.vegana.issposition.Constants;
import com.vegana.issposition.model.DataISS;
import com.vegana.issposition.model.ResponseISS;
import com.vegana.issposition.service.ISSService;
import com.vegana.issposition.view.ISSView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ISSPresenter {
    private ISSView view;
    private ISSService service;

    public ISSPresenter(ISSView view){
        this.view = view;

        if(this.service == null){
            this.service = new ISSService();
        }
    }

    public void getNextTenPosition(){
        this.service.getISSAPI()
                .getNextTenISSPositions(Constants.latitude, Constants.longitude, Constants.altitude)
                .enqueue(new Callback<DataISS>() {
                    @Override
                    public void onResponse(Call<DataISS> call, Response<DataISS> response) {
                        DataISS data = response.body();

                        if (data != null && data.getResponse() != null) {
                            ArrayList<ResponseISS> result = data.getResponse();
                            view.getNextPositions(result);
                        }
                    }

                    @Override
                    public void onFailure(Call<DataISS> call, Throwable t) {
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
