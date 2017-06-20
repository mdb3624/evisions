package evision.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import evision.com.data.City;

public class GMA {

	private String apiKey;
	private final static String APISERVER = "https://maps.googleapis.com/maps/api/";
	
	private City city;

	@SuppressWarnings("unused")
	private GMA() {
		;
	}

	public GMA(City city, String apiKey) {
		this.apiKey = apiKey;
		this.city = city;
	}

	public String getTimeZoneName() {
		String result = "";
		
		String URI = GMA.APISERVER + "timezone/json?location=" + Double.toString(city.getLatitude()) + "," 
				+ Double.toString(city.getLongitude()) + "&timestamp=1458000000&key=" + apiKey;

		JSONObject object = getData(URI);
		result = (String) object.get("timeZoneName");
		return result;
		
	}
	
	public double getelevation() {
		double result = -99.9;
		
		String URI = GMA.APISERVER + "elevation/json?locations=" + Double.toString(city.getLatitude()) + "," 
				+ Double.toString(city.getLongitude()) + "&timestamp=1458000000&key=" + apiKey;

		JSONObject object = getData(URI);
		JSONArray array = (JSONArray) object.get("results");
		
		for ( Object obj : array ) {
			JSONObject jObject = (JSONObject) obj;
			result = (Double)jObject.get("elevation");
		}
		return result;
		
	}

	private JSONObject getData(String URL) {
		JSONObject object = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(URL);
		try {
			
			HttpResponse response = httpclient.execute (httpGet);
			response = httpclient.execute(httpGet);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			JSONParser parser = new JSONParser();
			object = (JSONObject) parser.parse(br);

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

		return object;
	}
}
