/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

public class Kurssi {
	public String nimi;	
	private int id;

	public Kurssi(String nimi, int id) {
		this.nimi = nimi;
		this.id = id;
	}
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public String getNimi() {
		return nimi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
