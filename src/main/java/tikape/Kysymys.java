package tikape;

public class Kysymys {
    private String kysymysteksti;
    private String aihe;
    private Kurssi kurssi;

    public Kysymys(String kysymysteksti, String aihe, Kurssi kurssi) {
	this.kysymysteksti = kysymysteksti;
	this.aihe = aihe;
	this.kurssi = kurssi;
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
