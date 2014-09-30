package javashitsszzzea;

import java.util.List;

public class Meetstations {
	private List<Waterstanden> WATERSTANDEN;
	
	Meetstations(List<Waterstanden> WATERSTANDEN){
		this.setWATERSTANDEN(WATERSTANDEN);
	}

	public List<Waterstanden> getWATERSTANDEN() {
		return WATERSTANDEN;
	}

	public void setWATERSTANDEN(List<Waterstanden> WATERSTANDEN) {
		this.WATERSTANDEN = WATERSTANDEN;
	}
}
