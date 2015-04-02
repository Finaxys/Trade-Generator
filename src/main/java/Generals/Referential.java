package Generals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Referential
{
	public List<Counterpart> Counterparts;
	public List<Product> Products;
	public List<Currency> Currencies;
	public List<Depositary> Depositaries;

	public List<Portfolio> Portfolios;

	private Referential()
	{
	}

	private static Referential INSTANCE = new Referential();

	public static Referential getInstance()
	{
		return INSTANCE;
	}

	public static <T> T getRandomElement(List<T> list)
	{
		Random randomGenerator = new Random();
		return (list.get(randomGenerator.nextInt(list.size())));
	}

	public static <T> List<T> subList(List<T> list, String field, String filter)
	{
		List<T> subT = new ArrayList<T>();
		for (T te : list)
		{
			try
			{
				if (te.getClass().getField(field).get(te).equals(filter))
					subT.add((T) te);
			}
			catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return (subT);
	}

	public static <T> List<T> exList(List<T> list, String field, String filter)
	{
		List<T> subT = new ArrayList<T>();
		for (T te : list)
		{
			try
			{
				if (!te.getClass().getField(field).get(te).equals(filter))
					subT.add((T) te);
			}
			catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return (subT);
	}

	public static Trader getTrader(Referential ref, String country, String nameIns)
	{
		for (Referential.Currency cur : ref.Currencies)
			if (cur.country.equals(country))
				for (Referential.Instrument ins : cur.Instruments)
					if (ins.name.equals(nameIns))
						return getRandomElement(ins.Traders);

		return (null);
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
		public List<Instrument> Instruments;
		public float change;

		public Currency()
		{
		}

		public Currency(String code, String name, String country)
		{
			this.code = code;
			this.name = name;
			this.country = country;
		}
		
		public  Currency getCurrencybycountry(String country)
		{ for (int i=0;i<Currencies.size();i++ )
			if (Currencies.get(i).country==country)
			{
				return Currencies.get(i);
				
			}
		return null;
		
		}
	}

	public class Product
	{
		public String name;
		public String type;
		public String isin;
		public String libelle;
		public String country;
		public float price;
	}

	public class Depositary
	{
		public String code;
		public String libelle;
	}

	public class Instrument
	{
		public String name;
		public List<Trader> Traders;

		public Instrument()
		{
		}

		public Instrument(String name)
		{
			this.name = name;
		}
	}

	static public class Trader
	{
		public String name;
		public String codeptf;

		public Trader(String name, String codeptf)
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
