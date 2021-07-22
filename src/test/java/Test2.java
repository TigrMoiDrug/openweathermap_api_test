import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test2 extends BasicTest {

    @Test
    public void Coordinates () {

        String xmlLon = getXmlResponse("coord").getAttribute("lon");
        String xmlLat = getXmlResponse("coord").getAttribute("lat");

        String jsonLon = Float.toString(getToUrl(getMetricUrl()).getCoord().getLon());
        String jsonLat = Float.toString(getToUrl(getMetricUrl()).getCoord().getLat());

        log.info("Lon from xml: "+ xmlLon +", lon from json: "+ jsonLon);
        log.info("Lat from xml: "+ xmlLat +", lat from json: "+ jsonLat);

        assertTrue(xmlLon.equals(jsonLon) && xmlLat.equals(jsonLat));
    }

    @Test
    public void windSpeed () {

        float xmlSpeed = Float.parseFloat(getXmlResponse("speed").getAttribute("value"));

        float jsonSpeed = getToUrl(getMetricUrl()).getWind().getSpeed();

        log.info("Wind speed from xml: "+ xmlSpeed +", from json: "+jsonSpeed);

        assertEquals(xmlSpeed, jsonSpeed);
    }

    @Test
    public void pressure () {

        String xmlPressure = getXmlResponse("pressure").getAttribute("value");

        String jsonPressure = Integer.toString(getToUrl(getMetricUrl()).getMain().getPressure());

        log.info("Pressure from xml: "+ xmlPressure +", from json: "+ jsonPressure);

        assertEquals(xmlPressure, jsonPressure);
    }

    @Test
    public void temperature () {

        String xmlTempValue = getXmlResponse("temperature").getAttribute("value");
        String xmlTempMin = getXmlResponse("temperature").getAttribute("min");
        String xmlTempMax = getXmlResponse("temperature").getAttribute("max");
        String xmlTempFeelsLike = getXmlResponse("feels_like").getAttribute("value");

        String jsonTempValue = getToUrl(getMetricUrl()).getMain().getTemp().toString();
        String jsonTempMin = getToUrl(getMetricUrl()).getMain().getTempMin().toString();
        String jsonTempMax = getToUrl(getMetricUrl()).getMain().getTempMax().toString();
        String jsonTempFeelsLike = getToUrl(getMetricUrl()).getMain().getFeelsLike().toString();

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

        long jsonRise = getToUrl(getMetricUrl()).getSys().getSunrise();
        long jsonSet = getToUrl(getMetricUrl()).getSys().getSunset();

        String xmlRise = getXmlResponse("sun").getAttribute("rise");
        String xmlSet = getXmlResponse("sun").getAttribute("set");

        log.info("Sunrise json: " + jsonRise + ", xml: " + xmlRise);
        log.info("Sunset json: " + jsonSet + ", xml: " + xmlSet);

        assertTrue(parseJsonDate(jsonRise).compareTo(parseXmlDate(xmlRise)) == 0
                && parseJsonDate(jsonSet).compareTo(parseXmlDate(xmlSet)) == 0);
    }

}

