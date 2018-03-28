import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class HTTPRequest {

	private final String USER_AGENT = "Mozilla/5.0";
	
	
	public JSONObject sendGet(String url)
	{
		JSONObject jo = null;
		try {
			URL urlObject = new URL(url);
			try {
				HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", USER_AGENT);
				
				int reponseCode = con.getResponseCode();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				
				Object obj = JSONValue.parse(response.toString());

				jo =(JSONObject)obj;
				
				in.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;
		
	}
}
