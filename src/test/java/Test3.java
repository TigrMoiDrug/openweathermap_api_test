import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test3 extends BasicTest{
    @ParameterizedTest
    @ValueSource(strings = {"brest", "grodno", "gomel", "vitebsk", "mogilev", "minsk"})
    public void feelsLikeLessThanThirtyFive (String name) {
        int counter = 0;
        for(int i = 0; i < getFiveFeelsLike(name).size(); i++  ){
            if (stringToDouble(getFiveFeelsLike(name).get(i)) <= 35){
                counter = 0;
            }
            else {
                counter++;
            }
        }
        assertEquals(0, counter);
    }


}
