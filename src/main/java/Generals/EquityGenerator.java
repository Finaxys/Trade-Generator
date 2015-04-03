package Generals;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class EquityGenerator extends TradeGenerator
{
	private int 			partSell;
	private int 			ownCountry;
	private double 			volumetryTolerance;
	private double 			budgetTolerance;
	private List<Integer> 	Loanpertrade ;
	private List<Locality> 	localities;
	private List<Way> 		ways;
	private int 			amountPerDay;
	
	@Override
	public void init(int amount)
	{
		super.init(amount);

		amountPerDay = amount;
		double rdmBudgetTolerance;
		double rdmVolumetryTolerance;
		double toleredVolumetry;
		Random random = new Random();

		rdmBudgetTolerance = this.getBudgetTolerance() * 2 * (random.nextDouble() - 0.5) / 100;
		rdmVolumetryTolerance = this.getVolumetryTolerance() * 2 * (random.nextDouble() - 0.5) / 100;

		amountPerDay += rdmBudgetTolerance * amountPerDay;

		// calculation of number of trades to distribute per day
		toleredVolumetry = (1 - rdmVolumetryTolerance) * volumetry;
		rounded_volumetry = (int) toleredVolumetry;

		Loanpertrade = Sparsemoney(rounded_volumetry, amountPerDay);
		 
		localities = TradeGenerator.tableaubin(rounded_volumetry,
				this.getOwnCountry(),Locality.class);
		ways = TradeGenerator.tableaubin(rounded_volumetry, this.getPartSell(),Way.class);
	}

	@Override
	public TradeEvent generate(Book book, Date date)
	{
		super.generate(book,  date);

		Referential ref = Referential.getInstance();
		Generals generals = Generals.getInstance();

		// declaration des tirages au sort sous contrainte
		Referential.Depositary depositary;
		Referential.Counterpart counterpart;
		Referential.Trader trader;
		Referential.Product product;
		Referential.Currency currency = null;
		Referential.Portfolio portfolio;

		List<Referential.Product> Listequity = ref.subList(ref.products, "type", "EQUITY");

		// tirage au sort sous contrainte

		depositary = ref.getRandomElement(ref.depositaries);
		counterpart = ref.getRandomElement(ref.counterparts);
		currency = ref.getRandomElement(ref.currencies);
		trader = ref.getTrader(ref, currency.code, "equity");
		if (localities.get(0).toString() == "NATIONAL")
		{
			currency = ref.subList(ref.currencies, "country", generals.owncountry).get(0);
		}
		{
			currency = ref.getRandomElement(ref.exList(ref.currencies, "country", generals.owncountry));
		}
		product = ref.getRandomElement(Listequity);
		portfolio = ref.getRandomElement(ref.portfolios);

		float randToleranceQuantities;

		double randomquantity;
		// sharing of amount per trade
		randToleranceQuantities = (float) Math.random();
		Random random = new Random();
		// set random price -+3%
		randomquantity = 6 * (random.nextDouble() - 0.5);
		float price = product.price;
		// price=price*1/cur.change*Refential.Currency.this.getCurrencybycountry("country").change;

		price = (float) (price * (1 + randomquantity / 100));
		int quantity = (int) (randToleranceQuantities * Loanpertrade.get(0) / price);
		TradeEquity tradeEquity = new TradeEquity(this, "reference", ways.get(0), date, date, counterpart, book, price, quantity, product, depositary, trader);
		localities.remove(0);
		ways.remove(0);
		Loanpertrade.remove(0);
		
		return (tradeEquity);
	}

	public int getOwnCountry()
	{
		return ownCountry;
	}

	public void setOwnCountry(int ownCountry)
	{
		this.ownCountry = ownCountry;
	}

	public int getPartSell()
	{
		return partSell;
	}

	public void setPartSell(int partSell)
	{
		this.partSell = partSell;
	}

	public double getBudgetTolerance()
	{
		return budgetTolerance;
	}

	public void setBudgetTolerance(double budgetTolerance)
	{
		this.budgetTolerance = budgetTolerance;
	}

	public double getVolumetryTolerance()
	{
		return volumetryTolerance;
	}

	public void setVolumetryTolerance(double volumetryTolerance)
	{
		this.volumetryTolerance = volumetryTolerance;
	}
}
