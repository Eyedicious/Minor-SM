package javashitsszzzea;

public class Vaartuigen {
	String id, mmsi,naam;
	BuurtPositie Positie;
	
	public Vaartuigen(String id, String mmsi, BuurtPositie Positie, String naam) {
		this.id = id;
		this.mmsi = mmsi;
		this.Positie = Positie;
		this.naam = naam;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public BuurtPositie getPositie() {
		return Positie;
	}

	public void setPositie(BuurtPositie positie) {
		Positie = positie;
	}
	
	public void setNaam(String naam){
		this.naam = naam;
	}
	
	public String getNaam(){
		return naam;
	}
	
}
