package basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class BasicTest {

    public Logger log = Logger.getLogger(String.valueOf(BasicTest.class));
    private RestTemplate restTemplate = new RestTemplate();

    // перевоз из системы Цельсия в Кельвина
    public BigDecimal celsiusToKelvin (BigDecimal celsius){
        BigDecimal toKelvin = new BigDecimal("273.15");
        return celsius.add(toKelvin);
    }

// доступ к корню XML
    public Element getXmlResponse(String city, String tagName){

        ResponseEntity<String> entity = new CurrentWeatherClient()
                .getCurrentWeatherInXml(city);

        Element element = null;

        try {

            log.info("Code : " + entity.getStatusCode());
            log.info("Header : " + entity.getHeaders());

            Document doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource
                            (new StringReader(Objects.requireNonNull(entity
                                    .getBody()))));

            NodeList list = doc.getElementsByTagName(tagName);

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    element = (Element) node;
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException ex){
                log.info("ParserConfigurationException: " + ex);
        }
        return element;
    }

// форматирование даты из Юникс формата в стандартный
    public String parseJsonDate (long mills) {
        Date date = new Date (mills*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        return sdf.format(date);
    }

// удаление символа "Т" из даты
    public String parseXmlDate (String dateToFormat) {
        return dateToFormat.replace("T", " ");
    }

//поиск по json документу
    public List<String> getFiveFeelsLike(String name) {
        ResponseEntity<String> entity = new CurrentWeatherClient()
                .getWeatherInJsonForFiveDays(name);

        List<String> feelsLike = new ArrayList<>();

        try {
            log.info("Code : " + entity.getStatusCode());
            log.info("Header : " + entity.getHeaders());

            JsonNode arrNode = new ObjectMapper()
                    .readTree(entity
                            .getBody())
                    .get("list");


            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    if (arrNode.get(i).get("dt_txt").toString().contains("15:00:00")) {
                        feelsLike.add(arrNode.get(i).get("main").get("feels_like").toString());
                    }
                }
            }
        }

        catch (JsonProcessingException ignored){

        }
        return feelsLike;
    }

    // Строка в дабл
    public double stringToDouble (String string){
        return Double.parseDouble(string);
    }

}
