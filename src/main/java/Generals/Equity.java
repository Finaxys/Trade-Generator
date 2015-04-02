package Generals;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Equity extends Instrument
{
	public int partSell;
	public int ownCountry;
	public int volumetry;
	public double volumetryTolerance;
	public double repartitionTolerance;
	public Boolean isStp;

	@Override
	public void generate(Book book, int amount, Date date)
	{
		Referential ref = Referential.getInstance();
		Generals generals = Generals.getInstance();
		int amountPerDay = amount;
		double rand1, rand2;
		double toleredVolumetry;
		Random random = new Random();

		rand1 = this.repartitionTolerance * 2 * (random.nextDouble() - 0.5)
				/ 100;
		rand2 = this.volumetryTolerance * 2 * (random.nextDouble() - 0.5) / 100;
		amountPerDay += rand1 * amountPerDay;

		// calculation of number of trades to distribute per day
		toleredVolumetry = (1 - rand2) * volumetry;
		int roundedVolume = (int) toleredVolumetry;

		float randToleranceQuantities;

		double randomquantity;
		List<Integer> Loanpertrade = Sparsemoney(roundedVolume, amountPerDay);
		
		int quantity;
		float price;

		List<Locality> t1;
		List<Way> t2;

		// declaration des tirages au sort sous contrainte
		Referential.Depositary d;
		Referential.Counterpart c;
		Referential.Trader tr;
		Referential.Product pro;
		Referential.Currency cur = null;
		Referential.Portfolio port;
			
		 List<Referential.Product> Listequity=Referential.subList(ref.Products,
		 "type", "EQUITY");
		 t1 = Instrument.tableaubin(roundedVolume,
		 this.ownCountry,Locality.class);
		 t2 = Instrument.tableaubin(roundedVolume, this.partSell,Way.class);
		for (int i = 0; i < roundedVolume; i++)
		{

			// sharing of amount per trade
			randToleranceQuantities = (float) Math.random();
			
			// set random price -+3%
			randomquantity = 6 * (random.nextDouble() - 0.5);

			// tirage au sort sous contrainte

			d = Referential.getRandomElement(ref.Depositaries);
			c = Referential.getRandomElement(ref.Counterparts);
			cur = Referential.getRandomElement(ref.Currencies);
			tr = Referential.getTrader(ref, cur.country, "equity");

			if (t1.get(i).toString() == "NATIONAL")
			{
				cur=Referential.subList(ref.Currencies, "country",
						generals.owncountry).get(0);
			}
			{
				cur = Referential.getRandomElement(Referential.exList(ref.Currencies, "country",
						generals.owncountry));
			}
			pro = Referential.getRandomElement(Listequity);
			port = Referential.getRandomElement(ref.Portfolios);
			price = pro.price;
			//price=price*1/cur.change*Refential.Currency.this.getCurrencybycountry("country").change;

			price = (float) (price * (1 + randomquantity / 100));
			quantity = (int) (randToleranceQuantities * Loanpertrade.get(i) / price);
			TradeEquity tq1 = new TradeEquity(this, book, date, t2.get(i), quantity*price,
					quantity, d, c, tr, pro, cur, port);
			tradeGenerated(tq1);

		}
	}
}
