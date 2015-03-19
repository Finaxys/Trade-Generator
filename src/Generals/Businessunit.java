package Generals;

import java.util.ArrayList;

	public class Businessunit
	{

		public String name;
		public double ratio;
		public ArrayList<Instrument> lins;
		public ArrayList<Portfolio> lpor;

		public Businessunit(String name, double ratio, ArrayList<Instrument> lins, ArrayList<Portfolio> lpor)
		{
			super();
			this.name = name;
			this.ratio = ratio;
			this.lins = lins;
			this.lpor = lpor;
		}
	}

