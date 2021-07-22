import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.rodionov.apitests.models.RootModel;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class BasicTest {

    public Logger log = Logger.getLogger(String.valueOf(BasicTest.class));
    private String prefix = "https://api.openweathermap.org/data/2.5/weather?q=";
    private String metric = "&units=metric";
    private String brest = "brest";
    private String inXML = "&mode=xml";
    private RestTemplate restTemplate = new RestTemplate();

// перевоз из системы Цельсия в Кельвина
    public BigDecimal celsiusToKelvin (BigDecimal celsius){
        BigDecimal toKelvin = new BigDecimal("273.15");
        return celsius.add(toKelvin);
    }

// доступ к ключу в проперти файле "dev.properties"
    public String apiKey (){
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
// получение данных в метрической системе для бреста
    public String getMetricUrl() {
        return prefix + brest + metric + apiKey();
    }

// получение данных в стандартной системе для бреста
    public String getStandardUrl() {
        return prefix + brest + apiKey();
    }

// получение данных в метрической системе для бреста в XML
    public String getMetricUrlXml(){
        return prefix + brest + metric + inXML +apiKey();
    }

// get запрос для указанных URL
    public RootModel getToUrl(String url){
        return restTemplate.getForObject(url, RootModel.class);
    }

// доступ к корню XML
    public Element getXmlResponse(String tagName){
        Element element = null;

        try {
            String url = getMetricUrlXml();
            log.info(url);

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            System.out.println(conn.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(response.toString())));

            NodeList list = doc.getElementsByTagName(tagName);

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    element = (Element) node;
                }
            }
        } catch (Exception e) {

            System.out.println(e);
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

// получение json в виде строки
    public String getJsonArrayByCityName(String cityName){
        StringBuilder buffer = new StringBuilder();
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&units=metric"+apiKey());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            System.out.println("Code: "+conn.getResponseCode());

            if(conn.getResponseCode() == 200) {
                Scanner scan = new Scanner(url.openStream());
                while(scan.hasNext()) {
                    String temp = scan.nextLine();
                    buffer.append(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
//поиск по json документу
    public List<String> getFiveFeelsLike(String name) {

        List<String> feelsLike = new ArrayList<>();

        try {

            JsonNode arrNode = new ObjectMapper().readTree(getJsonArrayByCityName(name)).get("list");

            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    if (arrNode.get(i).get("dt_txt").toString().contains("15:00:00")) {
                        feelsLike.add(arrNode.get(i).get("main").get("feels_like").toString());
                    }
                }
            }
        }

        catch (JsonProcessingException e){
            System.out.println(e);
        }

        return feelsLike;
    }

    // Строка в дабл
    public double stringToDouble (String string){
        return Double.parseDouble(string);
    }

}
