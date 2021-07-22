package ru.rodionov.apitests.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"lon", "lat"})

public class Coord {

    @JsonProperty("lon")
    private float lon;
    @JsonProperty("lat")
    private float lat;

    @JsonProperty("lon")
    public float getLon() {
        return lon;
    }

    @JsonProperty("lat")
    public float getLat() {
        return lat;
    }

}
