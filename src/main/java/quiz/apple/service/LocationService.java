package quiz.apple.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
public class LocationService {
	final static String PROXY_ADDR = "proxy.cognizant.com";
	final static int PROXY_PORT = 6050;

	/**
	 * get longtitude and latitude by physical address
	 * 
	 * @param address
	 * @return output String as jsonobject
	 */
	public JSONObject getCoordinates(String address, String mapApiUrl, String appKey, String city) {
		try {
			URL url = null;
			URLConnection connection = null;
			String substituted = mapApiUrl.replace("apiKey", appKey).replace("inputAddr", address).replace("myCity", city);
			System.out.println("url: " + substituted);
			url = new URL(substituted);
			Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(PROXY_ADDR, PROXY_PORT));
			connection = url.openConnection(proxy);
			connection.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				//System.out.println("text%%%%%%%%: " + line);
				sb.append(line.trim());
			}
			in.close();
			return JSONObject.fromObject(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
