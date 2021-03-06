/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {
    private static boolean cityException = false;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="temp_info"
    private Text temp_info; // Value injected by FXMLLoader

    @FXML // fx:id="temp_feel"
    private Text temp_feel; // Value injected by FXMLLoader

    @FXML // fx:id="temp_max"
    private Text temp_max; // Value injected by FXMLLoader

    @FXML // fx:id="temp_min"
    private Text temp_min; // Value injected by FXMLLoader

    @FXML // fx:id="pressure"
    private Text pressure; // Value injected by FXMLLoader

    @FXML // fx:id="city"
    private TextField city; // Value injected by FXMLLoader

    @FXML // fx:id="getData"
    private Button getData; // Value injected by FXMLLoader

    @FXML
    private Text humidity;

    @FXML
    private Text wind;

    @FXML
    private Text windDirection;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                if (cityException) {
                    temp_info.setText("City not found!");
                    temp_feel.setText("");
                    temp_max.setText("");
                    temp_min.setText("");
                    pressure.setText("");
                    humidity.setText("");
                    wind.setText("");
                    windDirection.setText("");
                    cityException = false;
                } else {
                    String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=a4fdc930384b5412ee7414f32f793310&units=metric");
                    if (!output.isEmpty()) {
                        JSONObject obj = new JSONObject(output);
                        temp_info.setText("??????????????????????: " + obj.getJSONObject("main").getDouble("temp")+" C");
                        temp_feel.setText("??????????????????: " + obj.getJSONObject("main").getDouble("feels_like")+" C");
                        temp_max.setText("????????????????: " + obj.getJSONObject("main").getDouble("temp_max")+" C");
                        temp_min.setText("??????????????: " + obj.getJSONObject("main").getDouble("temp_min")+" C");
                        String pressureString = String.format("%.1f", obj.getJSONObject("main").getDouble("pressure")*755/1025);
                        pressure.setText("????????????????: " + pressureString+" ????. ????. ????.");
                        humidity.setText("??????????????????: " + obj.getJSONObject("main").getDouble("humidity")+ " %");
                        wind.setText("??????????: "+ obj.getJSONObject("wind").getDouble("speed")+ " ??/?? ");
                        windDirection.setText("??????????????????????: "+ direction(obj.getJSONObject("wind").getDouble("deg")));
                    }
                }
            }
        });

    }

    private static String getUrlContent(String urlAddress){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
            reader.close();
        } catch (Exception e) {
            cityException = true;

        }
        return content.toString();
    }
    private static String direction(Double d) {
        if (d >= 0 && d < 23 ) return "??";
        if (d >= 23 && d < 23 + 45 ) return "????";
        if (d >= 23 + 45 && d < 23 + 2*45 ) return "??";
        if (d >= 23 + 2*45 && d < 23 + 3*45 ) return "????";
        if (d >= 23 + 3*45 && d < 23 + 4*45 ) return "??";
        if (d >= 23 + 4*45 && d < 23 + 5*45 ) return "????";
        if (d >= 23 + 5*45 && d < 23 + 6*45 ) return "??";
        if (d >= 23 + 6*45 && d < 23 + 7*45 ) return "????";
        if (d >= 23 + 7*45 && d <= 360) return "??";
    return null;
    }

}
