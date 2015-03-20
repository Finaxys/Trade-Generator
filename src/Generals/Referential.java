package Generals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Referential
{
	public List<Counterpart> Counterparts;
	public List<Currency> Currencies;
	public List<Depositary> Depositaries;
	public List<Instrument> Instruments;
	public List<Trader> Traders;
	public List<Portfolio> Portfolios;
	
	public <T> T getRandomElement(List<T> list)
	{
        Random randomGenerator = new Random();
        return (list.get(randomGenerator.nextInt(list.size())));
	}

	public class Counterpart
	{
		public String code;
		public String name;
	}

	public class Currency
	{
		public String code;
		public String name;
		public String country;
		
		public Currency(){}
		public Currency(String code, String name, String country)
		{
			this.code = code;
			this.name = name;
			this.country = country;
		}
	}

	public class Depositary
	{
		public String code;
		public String libelle;
	}

	public class Instrument
	{
		public String name;
		public String type;
		public String isin;
		public String libelle;
		public String country;
		public float price;
		
		public Instrument(){}
		public Instrument (String name)
		{
			this.name = name;
		}
	}

	static public class Trader
	{
		public String name;
		public String codeptf;
		
		public Trader (String name, String codeptf)
		{
			this.name = name;
			this.codeptf = codeptf;
		}
	}

	public class Portfolio
	{
		public String type;
		public String country;
		public String codeptf;
	}
}