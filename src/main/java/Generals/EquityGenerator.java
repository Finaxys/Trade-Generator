package Generals;

import java.util.Date;
import java.util.List;
import java.util.Random;

import Generals.Referential.Counterpart;
import Generals.Referential.Depositary;
import Generals.Referential.Portfolio;
import Generals.Referential.Product;
import Generals.Referential.Trader;
import Generals.TradeEvent.Node;

public class EquityGenerator extends TradeGenerator
{
	private int 		partSell;
	private int 		ownCountry;
	private int 		volumetry;
	private double 		volumetryTolerance;
	private double 		repartitionTolerance;
	private Boolean 	isStp;
	private int  roundedVolumetry;
	private List<Integer> Loanpertrade ;
	private List<Locality> t1;
	private List<Way> t2;
	private int amountPerDay;
	
	@Override
	public void init(int amount)
	{

		amountPerDay = amount;
		double rand1, rand2;
		double toleredVolumetry;
		Random random = new Random();

		rand1 = this.getRepartitionTolerance() * 2 * (random.nextDouble() - 0.5)
				/ 100;
		rand2 = this.getVolumetryTolerance() * 2 * (random.nextDouble() - 0.5) / 100;

		amountPerDay += rand1 * amountPerDay;

		// calculation of number of trades to distribute per day
		toleredVolumetry = (1 - rand2) * getVolumetry();
		 this.roundedVolumetry = (int) toleredVolumetry;

		float randToleranceQuantities;

		double randomquantity;
		List<Integer> Loanpertrade = Sparsemoney(roundedVolumetry, amountPerDay);

		int quantity;
		float price;

		List<Locality> t1;
		List<Way> t2;
		t1 = Instrument.tableaubin(roundedVolumetry,
				 this.getOwnCountry(),Locality.class);
				 t2 = Instrument.tableaubin(roundedVolumetry, this.getPartSell(),Way.class);
				
	}

	@Override
	public void generate(Book book, int amount, Date date)
	{
		Referential ref = Referential.getInstance();
		Generals generals = Generals.getInstance();

		// declaration des tirages au sort sous contrainte
		Referential.Depositary d;
		Referential.Counterpart c;
		Referential.Trader tr;
		Referential.Product pro;
		Referential.Currency cur = null;
		Referential.Portfolio port;
		
		 List<Referential.Product> Listequity=ref.subList(ref.products,
		 "type", "EQUITY");
		 t1 = TradeGenerator.tableaubin(this.roundedVolumetry,
		 this.getOwnCountry(),Locality.class);
		 t2 = TradeGenerator.tableaubin(roundedVolumetry, this.getPartSell(),Way.class);


		 // tirage au sort sous contrainte

		 d = ref.getRandomElement(ref.depositaries);
		 c = ref.getRandomElement(ref.counterparts);
		 cur = ref.getRandomElement(ref.currencies);
		 tr = ref.getTrader(ref, cur.country, "equity");

		 if (t1.get(0).toString() == "NATIONAL")
		 {
			 cur = ref.subList(ref.currencies, "country", generals.owncountry).get(0);
		 }
		 {
			 cur = ref.getRandomElement(ref.exList(ref.currencies, "country", generals.owncountry));
		 }
		 pro = ref.getRandomElement(Listequity);
		 port = ref.getRandomElement(ref.portfolios);
		 
		 float randToleranceQuantities;

		 double randomquantity;
		 // sharing of amount per trade
		 randToleranceQuantities = (float) Math.random();
		 Random random = new Random();
		 // set random price -+3%
		 randomquantity = 6 * (random.nextDouble() - 0.5);
		 float price = pro.price;
		 // price=price*1/cur.change*Refential.Currency.this.getCurrencybycountry("country").change;

		 price = (float) (price * (1 + randomquantity / 100));
		 int quantity = (int) (randToleranceQuantities * Loanpertrade.get(0) / price);
		 TradeEquity tq1 = new TradeEquity(this, "reference",t2.get(0), date, date, c, book, price,quantity,pro,d,tr);
		 tradeGenerated(tq1);
		 t1.remove(0);
		 t2.remove(0);
		 Loanpertrade.remove(0);
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
