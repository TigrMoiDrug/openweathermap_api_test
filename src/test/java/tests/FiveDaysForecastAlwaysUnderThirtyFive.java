package tests;

import basic.BasicTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiveDaysForecastAlwaysUnderThirtyFive extends BasicTest {
    @ParameterizedTest
    @ValueSource(strings = {"brest", "grodno", "gomel", "vitebsk", "mogilev", "minsk"})
    public void feelsLikeLessThanThirtyFive (String name) {
        int counter = 0;
        List<String> temp = getFiveFeelsLike(name);


        for (String s : temp) {
            if (stringToDouble(s) <= 35) {
                counter++;
            } else {
                counter--;
            }
        }
        assertEquals(5, counter);
    }

}
