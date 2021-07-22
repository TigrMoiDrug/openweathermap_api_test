package ru.rodionov.apitests.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "temp",
        "feels_like",
        "temp_min",
        "temp_max",
        "pressure",
        "humidity"
})
public class Main {

    @JsonProperty("temp")
    private BigDecimal temp;
    @JsonProperty("feels_like")
    private BigDecimal feelsLike;
    @JsonProperty("temp_min")
    private BigDecimal tempMin;
    @JsonProperty("temp_max")
    private BigDecimal tempMax;
    @JsonProperty("pressure")
    private int pressure;
    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("temp")
    public BigDecimal getTemp() {
        return temp;
    }

    @JsonProperty("feels_like")
    public BigDecimal getFeelsLike() {
        return feelsLike;
    }

    @JsonProperty("temp_min")
    public BigDecimal getTempMin() {
        return tempMin;
    }

    @JsonProperty("temp_max")
    public BigDecimal getTempMax() {
        return tempMax;
    }

    @JsonProperty("pressure")
    public int getPressure() {
        return pressure;
    }

    @JsonProperty("humidity")
    public int getHumidity() {
        return humidity;
    }

}