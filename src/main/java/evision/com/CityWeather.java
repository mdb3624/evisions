package evision.com;

import org.apache.commons.lang3.StringUtils;

import evision.com.data.City;
import evision.com.data.CurrentWeather;
public class CityWeather {

	public static void main(String[] args) {
		if ( ! CityWeather.validArgs(args) ) {
			System.out.println("Usage: java -jar CityWeather zip=[zipcode]");
		} 
		
		City city = new City();
		OWA openWeatherAPI = new OWA("e2f53f8f77402ad5f198b0dce321fa00");
		CurrentWeather weather = openWeatherAPI.getCurrentWeather(args[0]);

		city.setName(weather.getCityName());
		city.setTemperature(weather.getTemperature());
		city.setLatitude(weather.getLatitude());
		city.setLongitude(weather.getLongitude());
		
		GMA googleMapAPI = new GMA(city,"AIzaSyAfPdydrRAIWHBPpyzyPNc2iD9tqld15sc"); // Needed for longiture/latiude
		city.setElevation(googleMapAPI.getelevation());
		city.setTimeZone(googleMapAPI.getTimeZoneName());
		
		System.out.printf("At the location %s, the temperature is %f, the timezone is %s, and the elevation is %s\n", city.getName(),
				city.getTemperature(), 
				city.getTimeZone(), 
				city.getElevation());
		
	}

	// Make sure that only 1 5 digit number has been sent in for processing.
	private static boolean validArgs(String[] args) {
		boolean result = true;
		if ( args.length == 0 || args.length > 1 ) {
			return false;
		}
		if ( args[0].length() != 5 ) {
			return false;
		}
		if ( ! StringUtils.isNumeric(args[0]) ) {
			return false;
		}
		return result;
	}
}
