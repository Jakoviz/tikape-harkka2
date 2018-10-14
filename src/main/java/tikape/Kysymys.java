package tikape;

public class Kysymys {
    private String kysymysteksti;
    private String aihe;
    private String kurssi;

    public Kysymys(String kysymysteksti, String aihe, String kurssi) {
        this.kysymysteksti = kysymysteksti;
		this.aihe = aihe;
		this.kurssi = kurssi;
    }

    public String getNimi() {
        return kysymysteksti;
    }

    public void setNimi(String nimi) {
        this.kysymysteksti = nimi;
    }
}
