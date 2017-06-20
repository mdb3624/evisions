package evision.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import evision.com.data.CurrentWeather;

public class OWA {
	private String apiKey;
	private final static String APISERVER = "http://api.openweathermap.org/data/2.5/weather?zip=";
	@SuppressWarnings("unused")
	private OWA() {
		;
	}

	public OWA(String apiKey) {
		this.apiKey = apiKey;
	}

	public CurrentWeather getCurrentWeather(String zipCode) {
		CurrentWeather weather = new CurrentWeather();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String URI = OWA.APISERVER + zipCode + "&units=Imperial&appid=" + apiKey;
		HttpGet httpGet = new HttpGet(URI);
		try {
			
			HttpResponse response = httpclient.execute (httpGet);
			response = httpclient.execute(httpGet);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			JSONParser parser = new JSONParser();
			JSONObject weatherArray = (JSONObject) parser.parse(br);

			weather.setCityName((String)weatherArray.get("name"));
			weather.setTemperature((Double)((JSONObject)weatherArray.get("main")).get("temp"));
			weather.setLatitude((Double)((JSONObject)weatherArray.get("coord")).get("lat"));
			weather.setLongitude((Double)((JSONObject)weatherArray.get("coord")).get("lon"));
			
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return weather;
	}
}
