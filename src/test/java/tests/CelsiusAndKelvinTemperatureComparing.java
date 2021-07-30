package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import basic.BasicTest;
import basic.CurrentWeatherClient;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.rodionov.apitests.models.RootModel;
import java.math.BigDecimal;

public class CelsiusAndKelvinTemperatureComparing extends BasicTest {
// сравнение температуры в (Цельсий и Кельвин)
    @Test
    public void celsiusAndKelvin (){

        ResponseEntity<RootModel>modelMetric = new CurrentWeatherClient()
                .getCurrentWeatherInJsonMetric( "brest");

        ResponseEntity<RootModel>modelStandard = new CurrentWeatherClient()
                .getCurrentWeatherInJsonStandard( "brest");

        BigDecimal expected = modelMetric.getBody().getMain().getTemp();
        BigDecimal actual = modelStandard.getBody().getMain().getTemp();

        log.info("Code : " + modelMetric.getStatusCode());
        log.info("Header : " + modelMetric.getHeaders());

        log.info("From metric system : " + actual);
        log.info("From standard system : " + expected);

        assertEquals(0, celsiusToKelvin(expected).compareTo(actual));
    }
}
