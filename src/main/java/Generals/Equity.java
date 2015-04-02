package Generals;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Equity extends Instrument
{
	private int partSell;
	private int ownCountry;
	private int volumetry;
	private double volumetryTolerance;
	private double repartitionTolerance;
	private Boolean isStp;

	@Override
	public void generate(Book book, int amount, Date date)
	{
		Referential ref = Referential.getInstance();
		Generals generals = Generals.getInstance();
		int amountPerDay = amount;
		double rand1, rand2;
		double toleredVolumetry;
		Random random = new Random();

		rand1 = this.getRepartitionTolerance() * 2 * (random.nextDouble() - 0.5)
				/ 100;
		rand2 = this.getVolumetryTolerance() * 2 * (random.nextDouble() - 0.5) / 100;

		amountPerDay += rand1 * amountPerDay;

		// calculation of number of trades to distribute per day
		toleredVolumetry = (1 - rand2) * getVolumetry();
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
		
		 List<Referential.Product> Listequity=ref.subList(ref.products,
		 "type", "EQUITY");
		 t1 = Instrument.tableaubin(roundedVolume,
		 this.getOwnCountry(),Locality.class);
		 t2 = Instrument.tableaubin(roundedVolume, this.getPartSell(),Way.class);

		for (int i = 0; i < roundedVolume; i++)
		{

			// sharing of amount per trade
			randToleranceQuantities = (float) Math.random();

			// set random price -+3%
			randomquantity = 6 * (random.nextDouble() - 0.5);

			// tirage au sort sous contrainte

			d = ref.getRandomElement(ref.depositaries);
			c = ref.getRandomElement(ref.counterparts);
			cur = ref.getRandomElement(ref.currencies);
			tr = ref.getTrader(ref, cur.country, "equity");

			if (t1.get(i).toString() == "NATIONAL")
			{
				cur = ref.subList(ref.currencies, "country", generals.owncountry).get(0);
			}
			{
				cur = ref.getRandomElement(ref.exList(ref.currencies, "country", generals.owncountry));
			}
			pro = ref.getRandomElement(Listequity);
			port = ref.getRandomElement(ref.portfolios);
			price = pro.price;
			// price=price*1/cur.change*Refential.Currency.this.getCurrencybycountry("country").change;

			price = (float) (price * (1 + randomquantity / 100));
			quantity = (int) (randToleranceQuantities * Loanpertrade.get(i) / price);
			TradeEquity tq1 = new TradeEquity(this, book, date, t2.get(i), quantity * price, quantity, d, c, tr, pro, cur, port);
			tradeGenerated(tq1);

		}
	}

	public int getOwnCountry() {
		return ownCountry;
	}

	public void setOwnCountry(int ownCountry) {
		this.ownCountry = ownCountry;
	}

	public int getPartSell() {
		return partSell;
	}

	public void setPartSell(int partSell) {
		this.partSell = partSell;
	}

	public double getRepartitionTolerance() {
		return repartitionTolerance;
	}

	public void setRepartitionTolerance(double repartitionTolerance) {
		this.repartitionTolerance = repartitionTolerance;
	}

	public int getVolumetry() {
		return volumetry;
	}

	public void setVolumetry(int volumetry) {
		this.volumetry = volumetry;
	}

	public double getVolumetryTolerance() {
		return volumetryTolerance;
	}

	public void setVolumetryTolerance(double volumetryTolerance) {
		this.volumetryTolerance = volumetryTolerance;
	}
}
