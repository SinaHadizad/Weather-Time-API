import org.jibble.pircbot.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyBot extends PircBot
{
	public MyBot() {
        this.setName("Sina");
    }
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
            String city = null;
            city = message.toString();
        	    sendMessage(channel, startWebRequest(city));
            sendMessage(channel, startWebRequest2(city));
	}
	
	
	static String startWebRequest(String city){
		String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=26aa1d90a24c98fad4beaac70ddbf274";
		StringBuilder result = new StringBuilder(); //will hold the java String after converting from JSON 
		try {	
			URL url = new URL(weatherURL); //the url we want to parse
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //Used to make a single connection to that URL
			conn.setRequestMethod("GET"); //The Type of request we want to make
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); //pass in the instance of HttpURLConnection 
			String line;
		    	while ((line = rd.readLine()) != null) { //turn BufferedReader value into type String
		    		result.append(line);
		    	}
		    rd.close();
		    
		    JsonElement jelement = new JsonParser().parse(result.toString());
	        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
	        
	        JsonObject tempObject = MasterWeatherObject.getAsJsonObject("main"); //type this exactly as it appears in the JSON response
	        String temp = tempObject.get("temp").getAsString();
	        
	        String output = null;
	        output = "Temperature in " + city + " is currently: " + temp + " kelvin";

	        return output;
		}
		catch(Exception e){return "Error! Exception: " + e;}
	}
	
	static String startWebRequest2(String city){
		String weatherURL = "https://www.amdoren.com/api/timezone.php?api_key=L6txmLSm7KC5AHrd6snyVRQHXKUdis&loc="+city;
		StringBuilder result = new StringBuilder(); //will hold the java String after converting from JSON 
		try {	
			URL url = new URL(weatherURL); //the url we want to parse
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //Used to make a single connection to that URL
			conn.setRequestMethod("GET"); //The Type of request we want to make
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); //pass in the instance of HttpURLConnection 
			String line;
		    	while ((line = rd.readLine()) != null) { //turn BufferedReader value into type String
		    		result.append(line);
		    	}
		    rd.close();
		    
		    JsonElement jelement = new JsonParser().parse(result.toString());
	        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
		    
		    String timeObject = MasterWeatherObject.get("time").getAsString();
		
	        String output = null;
	        output = "General date and time in " + city + " is currently: " + timeObject;

	        return output;
		}
		catch(Exception e){return "Error! Exception: " + e;}
	}
}