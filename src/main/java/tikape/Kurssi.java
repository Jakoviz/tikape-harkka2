/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

public class Kurssi {
	public String nimi;	

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public String getNimi() {
		return nimi;
	}

	public Kurssi(String nimi) {
		this.nimi = nimi;
	}
}
