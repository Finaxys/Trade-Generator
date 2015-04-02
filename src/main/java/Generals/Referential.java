package Generals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Referential
{
	public List<Counterpart> counterparts;
	public List<Product> products;
	public List<Currency> currencies;
	public List<Depositary> depositaries;
	public List<Portfolio> portfolios;

	private Referential()
	{
	}

	private static Referential INSTANCE = new Referential();

	public static Referential getInstance()
	{
		return INSTANCE;
	}

	public <T> T getRandomElement(List<T> list)
	{
		Random randomGenerator = new Random();
		return (list.get(randomGenerator.nextInt(list.size())));
	}

	public <T> List<T> subList(List<T> list, String field, String filter)
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

	public <T> List<T> exList(List<T> list, String field, String filter)
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

	public Trader getTrader(Referential ref, String country, String nameIns)
	{
		for (Referential.Currency cur : ref.currencies)
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
		{
			for (int i=0;i<currencies.size();i++ )
				if (currencies.get(i).country==country)
					return currencies.get(i);
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

	public class Trader
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
