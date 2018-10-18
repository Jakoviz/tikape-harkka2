/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape;

import java.util.*;

public class Kurssi {
	private String nimi;	
	private int id;
	private List<Kysymys> kysymykset; 

	public Kurssi(String nimi, int id) {
		this.nimi = nimi;
		this.id = id;
		this.kysymykset = new LinkedList<Kysymys>();
	}

	public void setKysymykset(List<Kysymys> kysymykset) {
		this.kysymykset = kysymykset;
	}

	public List<Kysymys> getKysymykset() {
		return kysymykset;
	}

	public void addKysymys(Kysymys kysymys) {
		kysymykset.add(kysymys);
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
