package com.vegana.issposition.model;

import com.google.gson.annotations.SerializedName;

public class RequestISS {
    @SerializedName("altitude")
    private String altitude;

    @SerializedName("datetime")
    private Long datetime;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("passes")
    private Integer passes;

    public String getAltitude() {
        return altitude;
    }

    public Long getDatetime() {
        return datetime;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Integer getPasses() {
        return passes;
    }
}
