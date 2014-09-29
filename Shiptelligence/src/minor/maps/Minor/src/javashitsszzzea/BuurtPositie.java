package javashitsszzzea;

public class BuurtPositie {
	String afstand;
	int meters;
	public BuurtPositie(String afstand, int meters) {
		this.afstand = afstand;
		this.meters = meters;
	}
	public String getAfstand() {
		return afstand;
	}
	public void setAfstand(String afstand) {
		this.afstand = afstand;
	}
	public int getMeters() {
		return meters;
	}
	public void setMeters(int meters) {
		this.meters = meters;
	}
	
}
