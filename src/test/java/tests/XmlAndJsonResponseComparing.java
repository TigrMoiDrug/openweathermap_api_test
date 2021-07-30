package tests;

import basic.BasicTest;
import basic.CurrentWeatherClient;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.rodionov.apitests.models.RootModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlAndJsonResponseComparing extends BasicTest {

    public ResponseEntity<RootModel> modelMetric = new CurrentWeatherClient()
            .getCurrentWeatherInJsonMetric( "brest");

    @Test
    public void Coordinates () {

        String xmlLon = getXmlResponse("brest", "coord").getAttribute("lon");
        String xmlLat = getXmlResponse("brest", "coord").getAttribute("lat");

        String jsonLon = Float.toString(modelMetric.getBody().getCoord().getLon());
        String jsonLat = Float.toString(modelMetric.getBody().getCoord().getLat());

        log.info("Lon from xml: "+ xmlLon +", lon from json: "+ jsonLon);
        log.info("Lat from xml: "+ xmlLat +", lat from json: "+ jsonLat);

        assertTrue(xmlLon.equals(jsonLon) && xmlLat.equals(jsonLat));
    }

    @Test
    public void windSpeed () {

        float xmlSpeed = Float.parseFloat(getXmlResponse("brest","speed").getAttribute("value"));

        float jsonSpeed = modelMetric.getBody().getWind().getSpeed();

        log.info("Wind speed from xml: "+ xmlSpeed +", from json: "+jsonSpeed);

        assertEquals(xmlSpeed, jsonSpeed);
    }

    @Test
    public void pressure () {

        String xmlPressure = getXmlResponse("brest", "pressure").getAttribute("value");

        String jsonPressure = Integer.toString(modelMetric.getBody().getMain().getPressure());

        log.info("Pressure from xml: "+ xmlPressure +", from json: "+ jsonPressure);

        assertEquals(xmlPressure, jsonPressure);
    }

    @Test
    public void temperature () {

        String xmlTempValue = getXmlResponse("brest","temperature").getAttribute("value");
        String xmlTempMin = getXmlResponse("brest","temperature").getAttribute("min");
        String xmlTempMax = getXmlResponse("brest","temperature").getAttribute("max");
        String xmlTempFeelsLike = getXmlResponse("brest","feels_like").getAttribute("value");

        String jsonTempValue = modelMetric.getBody().getMain().getTemp().toString();
        String jsonTempMin = modelMetric.getBody().getMain().getTempMin().toString();
        String jsonTempMax = modelMetric.getBody().getMain().getTempMax().toString();
        String jsonTempFeelsLike = modelMetric.getBody().getMain().getFeelsLike().toString();

        log.info("Temperature from XML: " + xmlTempValue + ", from json: " + jsonTempValue);
        log.info("Min Temperature from XML: " + xmlTempMin + ", from json: " + jsonTempMin);
        log.info("Max Temperature from XML: " + xmlTempMax + ", from json: " + jsonTempMax);
        log.info("Feels like Temperature from XML: " + xmlTempFeelsLike + ", from json: " + jsonTempFeelsLike);

        assertTrue(xmlTempValue.equals(jsonTempValue)
                && xmlTempMin.equals(jsonTempMin)
                && xmlTempMax.equals(jsonTempMax)
                && xmlTempFeelsLike.equals(jsonTempFeelsLike));
    }

    @Test
    public void sunRiseSet () {

        long jsonRise = modelMetric.getBody().getSys().getSunrise();
        long jsonSet = modelMetric.getBody().getSys().getSunset();

        String xmlRise = getXmlResponse("brest","sun").getAttribute("rise");
        String xmlSet = getXmlResponse("brest", "sun").getAttribute("set");

        log.info("Sunrise json: " + jsonRise + ", xml: " + xmlRise);
        log.info("Sunset json: " + jsonSet + ", xml: " + xmlSet);

        assertTrue(parseJsonDate(jsonRise).compareTo(parseXmlDate(xmlRise)) == 0
                && parseJsonDate(jsonSet).compareTo(parseXmlDate(xmlSet)) == 0);
    }

}

