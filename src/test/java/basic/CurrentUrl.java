package basic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CurrentUrl {

    public static String apiKey (){

        String apiKeyField = "&appid=";
        String propertiesName = "src/test/resources/dev.properties";
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(propertiesName));
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

        return apiKeyField + prop.getProperty("API_KEY");
    }

    public static class Builder {

        private String prefix = "";
        private String metric = "";
        private String city;
        private String inXml = "";

        public Builder(String city) {
            this.city = city;
        }

        public Builder inMetric() {
            this.metric = "&units=metric";
            return this;
        }

        public Builder current (){
            this.prefix = "https://api.openweathermap.org/data/2.5/weather?q=";
            return this;
        }

        public Builder forFiveDays (){
            this.prefix = "https://api.openweathermap.org/data/2.5/forecast?q=";
            return this;
        }

        public Builder inXml() {
            this.inXml = "&mode=xml";
            return this ;
        }

        public String build() {
            return this.prefix + this.city + this.metric + this.inXml + apiKey();
        }
    }

    private CurrentUrl() {
    }

}
