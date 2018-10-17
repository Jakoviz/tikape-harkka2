package tikape;

import java.util.*;

public class Kysymys {
    private int id;
    private String kysymysteksti;
    private String aihe;
    private Kurssi kurssi;
    private List<Vastausvaihtoehto> vastausvaihtoehdot;

	public void setVastausvaihtoehdot(List<Vastausvaihtoehto> vastausvaihtoehdot) {
		this.vastausvaihtoehdot = vastausvaihtoehdot;
	}

	public List<Vastausvaihtoehto> getVastausvaihtoehdot() {
		return vastausvaihtoehdot;
	}

    public Kysymys(int id, String kysymysteksti, String aihe, Kurssi kurssi, List<Vastausvaihtoehto> vastausvaihtoehdot) {
		this.id = id;
		this.kysymysteksti = kysymysteksti;
		this.aihe = aihe;
		this.kurssi = kurssi;
		this.vastausvaihtoehdot = vastausvaihtoehdot;
	}

	public void addVastausvaihtoehto(Vastausvaihtoehto vastausvaihtoehto) {
		this.vastausvaihtoehdot.add(vastausvaihtoehto);
	}

    public void setId(int id) {
	this.id = id;
    }

    public int getId() {
		return this.id;
    }
    
    public void setKurssi(Kurssi kurssi) {
	this.kurssi = kurssi;
    }

    public Kurssi getKurssi() {
	return kurssi;
    }

    public void setKysymysteksti(String kysymysteksti) {
	    this.kysymysteksti = kysymysteksti;
    }

    public void setAihe(String aihe) {
	    this.aihe = aihe;
    }

    public String getKysymysteksti() {
	    return kysymysteksti;
    }

    public String getAihe() {
	    return aihe;
    }

}
