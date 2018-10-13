package tikape;

public class Kysymys {
    private String kysymysteksti;

    public Kysymys(String nimi) {
        this.kysymysteksti = nimi;
    }

    public String getNimi() {
        return kysymysteksti;
    }

    public void setNimi(String nimi) {
        this.kysymysteksti = nimi;
    }
}
