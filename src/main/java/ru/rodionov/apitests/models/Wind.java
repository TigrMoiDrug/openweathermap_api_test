package ru.rodionov.apitests.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "speed",
        "deg",
        "gust"
})
public class Wind {

    @JsonProperty("speed")
    private float speed;
    @JsonProperty("deg")
    private int deg;
    @JsonProperty("gust")
    private int gust;

    @JsonProperty("speed")
    public float getSpeed() {
        return speed;
    }

    @JsonProperty("deg")
    public int getDeg() {
        return deg;
    }

    @JsonProperty("gust")
    public int getGust() {
        return gust;
    }

}
