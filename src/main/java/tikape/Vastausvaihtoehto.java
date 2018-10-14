/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

public class Vastausvaihtoehto {
    private int id;
    private int kysymys_id;
    private String vastaus; 
    private boolean oikein;

    public Vastausvaihtoehto(int id, int kysymys_id, String vastaus, boolean oikein) {
	this.id = id;
	this.kysymys_id = kysymys_id;
	this.vastaus = vastaus;
	this.oikein = oikein;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getKysymys_id() {
	return kysymys_id;
    }

    public void setKysymys_id(int kysymys_id) {
	this.kysymys_id = kysymys_id;
    }
    
    public void setOikein(boolean oikein) {
	this.oikein = oikein;
    }

    public boolean isOikein() {
	return oikein;
    }

    public String getVastaus() {
	return vastaus;
    }

    public void setVastaus(String vastaus) {
	this.vastaus = vastaus;
    }
}
