package basic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.rodionov.apitests.models.RootModel;

public class CurrentWeatherClient {

    private final RestTemplate template = new RestTemplate();

    public ResponseEntity<RootModel> getCurrentWeatherInJsonMetric (String city){
        return template
                .getForEntity(new CurrentUrl.Builder(city)
                .current()
                .inMetric()
                .build(), RootModel.class);
    }

    public ResponseEntity<RootModel> getCurrentWeatherInJsonStandard (String city) {
        return template
                .getForEntity(new CurrentUrl.Builder(city)
                .current()
                .build(), RootModel.class);
    }

    public ResponseEntity<String> getCurrentWeatherInXml (String city) {
        return template
                .getForEntity(new CurrentUrl.Builder(city)
                        .current()
                        .inXml()
                        .inMetric()
                        .build(), String.class);
    }

    public ResponseEntity<String> getWeatherInJsonForFiveDays (String city) {
        return template
                .getForEntity(new CurrentUrl.Builder(city)
                        .forFiveDays()
                        .inMetric()
                        .build(), String.class);
    }

}
