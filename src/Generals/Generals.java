package Generals;

import java.util.ArrayList;

	class Generals {
		public String nom_de_la_banque;
		public int total_buget; 
		public String owncountry;
		public ArrayList<Businessunit> bu;
		
		public Generals(String nom_de_la_banque, int total_buget, String owncountry,
		ArrayList<Businessunit> bu) {
			super();
			this.nom_de_la_banque = nom_de_la_banque;
			this.total_buget = total_buget;
			this.owncountry = owncountry;
			this.bu = bu;
		}
	}
 
 