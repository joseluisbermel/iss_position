package com.vegana.issposition.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataISS {
    @SerializedName("message")
    private String message;

    @SerializedName("request")
    private RequestISS request;

    @SerializedName("response")
    private ArrayList<ResponseISS> response;

    public String getMessage() {
        return message;
    }

    public RequestISS getRequest() {
        return request;
    }

    public ArrayList<ResponseISS> getResponse() {
        return response;
    }
}
