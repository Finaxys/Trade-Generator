package Generals;

import java.util.ArrayList;

	public class Portfolio
	{

		public String name;
		public double ratio;
		public ArrayList<Book> lb;

		public Portfolio(String nom, double ratio, ArrayList<Book> lb){
			this.name=nom;
			this.ratio=ratio;
			this.lb=lb;
		}
	}

