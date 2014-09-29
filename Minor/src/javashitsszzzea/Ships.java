package javashitsszzzea;


public class Ships {
	private int mmsi;
	private Positie Positie;
	private String scheepsnaam;
	public Ships(int mmsi, Positie Positie, String scheepsnaam){
		this.mmsi = mmsi;
		this.Positie = Positie;
		this.scheepsnaam = scheepsnaam;
	}
	public int getMmsi() {
		return mmsi;
	}
	public void setMmsi(int mmsi) {
		this.mmsi = mmsi;
	}
	public Positie getPositie() {
		return Positie;
	}
	public void setPositie(Positie Positie) {
		this.Positie = Positie;
	}
	public String getScheepsnaam() {
		return scheepsnaam;
	}
	public void setScheepsnaam(String scheepsnaam) {
		this.scheepsnaam = scheepsnaam;
	}
}
