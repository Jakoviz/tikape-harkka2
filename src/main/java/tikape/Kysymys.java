package tikape;

public class Kysymys {
    private int id;
    private String kysymysteksti;
    private String aihe;
    private Kurssi kurssi;

    public Kysymys(int id, String kysymysteksti, String aihe, Kurssi kurssi) {
	this.id = id;
	this.kysymysteksti = kysymysteksti;
	this.aihe = aihe;
	this.kurssi = kurssi;
    }
    public void setId(int id) {
	this.id = id;
    }

    public int getId() {
	return id;
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
