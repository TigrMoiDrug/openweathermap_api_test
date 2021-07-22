import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


public class Test1 extends BasicTest{
// сравнение температуры в (Цельсий и Кельвин)
    @Test
    public void celsiusAndKelvin (){

        BigDecimal expected = getToUrl(getStandardUrl()).getMain().getTemp();
        BigDecimal actual = celsiusToKelvin(getToUrl(getMetricUrl()).getMain().getTemp());

        log.info("Code : " + getToUrl(getStandardUrl()).getCod());

        log.info("From metric system : " + actual);
        log.info("From standard system : " + expected);

        assertEquals(0, expected.compareTo(actual));
    }

}
