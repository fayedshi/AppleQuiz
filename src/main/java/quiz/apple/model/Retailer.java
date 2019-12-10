package quiz.apple.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Retailer {
	@Column(name = "loc_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int locId;
	@Column(name = "latitude")
	private double latitude;
	@Column(name = "longtitude")
	private double longtitude;
	@Column(name = "address")
	private String address;
	@Column(name = "sourceMap")
	private String sourceMap;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Retailer() {
	}

	// location may change in version
	public Retailer(double lngt, double lat, String addr, String srcMap) {
		this.latitude = lat;
		this.longtitude = lngt;
		this.address = addr;
		this.sourceMap = srcMap;
	}

	public int getLocId() {
		return locId;
	}

	public void setLocId(int locId) {
		this.locId = locId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public String getSourceMap() {
		return sourceMap;
	}

	public void setSourceMap(String sourceMap) {
		this.sourceMap = sourceMap;
	}
}
