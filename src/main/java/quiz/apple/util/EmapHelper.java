package quiz.apple.util;

import net.sf.json.JSONObject;

public class EmapHelper {
	public static double[] extractBaiduCoords(JSONObject obj) {
		double[] coord = new double[2];
		obj = obj.getJSONObject("result").getJSONObject("location");
		coord[0] = Double.valueOf(obj.getString("lng"));
		coord[1] = Double.valueOf(obj.getString("lat"));
		System.out.println("bd coord " + coord[0] + ", " + coord[1]);
		return coord;
	}

	public static double[] extractAutoNaviCoords(JSONObject obj) {
		String locStr = obj.getJSONArray("geocodes").getJSONObject(0).getString("location");
		double[] coord = new double[2];
		String[] coStr = locStr.split(",");
		coord[0] = Double.parseDouble(coStr[0]);
		coord[1] = Double.parseDouble(coStr[1]);
		System.out.println("autonavi coord " + coord[0] + ", " + coord[1]);
		return coord;
	}

	// convert coordinates from Baidu to autonavi
	public static double[] bdEncrypt(double bd_lon, double bd_lat) {
		final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double[] coord = new double[2];
		coord[0] = z * Math.cos(theta);
		coord[1] = z * Math.sin(theta);
		return coord;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * returns meters as unit of distance
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return distance in unit of meter
	 */
	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		final double EARTH_RADIUS = 6378.137;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(getDistance(123.507,41.813,123.402,41.817));
	}
}
