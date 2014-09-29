package javashitsszzzea;

public class Positie {
	private double lat, lon;
	private int graden;
	private double snelheid;
	
	public Positie(double lat, double lon, int graden, double snelheid){
		this.lat = lat;
		this.lon = lon;
		this.graden = graden;
		this.snelheid = snelheid;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getGraden() {
		return graden;
	}

	public void setGraden(int graden) {
		this.graden = graden;
	}

	public double getSnelheid() {
		return snelheid;
	}

	public void setSnelheid(double snelheid) {
		this.snelheid = snelheid;
	}
}
