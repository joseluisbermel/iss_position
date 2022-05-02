package com.vegana.issposition.model;

import com.google.gson.annotations.SerializedName;

public class ResponseISS {
    @SerializedName("duration")
    private Integer duration;

    @SerializedName("risetime")
    private Long risetime;

    public Integer getDuration() {
        return duration;
    }

    public Long getRisetime() {
        return risetime;
    }
}
