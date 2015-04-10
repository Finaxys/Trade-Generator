package domain;

import generals.Generals;
import generals.Indexation;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LoanDepositGenerator extends TradeGenerator
{
	private int 				partSell;
	private int 				ownCountry;
	private int 				volumetryTolerance;
	private int 				repartitionTolerance;
	private float 				valueRate;
	private int 				partVariableRate;
	private List<Integer> 		loanPerTrade;
	private int 				amountPerDay;
	private List<Locality> 		listLocality;
	private List<Way>			listWay;
	private List<RateType> 		listRatetype;

	public LoanDepositGenerator(int partSell, int ownCountry,
			int volumetry, int volumetryTolerance,
			int repartitionTolerance, int valueRate,
			int tolerance_taux_var, int partVariableRate){
		super();
		this.partSell = partSell;
		this.ownCountry = ownCountry;
		this.volumetry = volumetry;
		this.volumetryTolerance = volumetryTolerance;
		this.repartitionTolerance = repartitionTolerance;
		this.valueRate = valueRate;
		this.partVariableRate = partVariableRate;
	}

	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
	
	@Override
	public void init(int amount)
	{		
		super.init(amount);

		double rand1, rand2;
		double toleredVolumetry;
		Random random = new Random();
		amountPerDay=amount;
		rand1 = this.repartitionTolerance * 2 * (random.nextDouble() - 0.5)
				/ 100;
		rand2 = this.volumetryTolerance * 2 * (random.nextDouble() - 0.5)
				/ 100;
		amountPerDay = (int) (amountPerDay + rand1 * amountPerDay);
		
		// calculation of number of trades to distribute per day
		toleredVolumetry = (1 - rand2) * volumetry;
		roundedVolumetry = (int) toleredVolumetry;	
		loanPerTrade = Sparsemoney(roundedVolumetry, amountPerDay);
		listLocality = tableaubin(roundedVolumetry, this.ownCountry,
				Locality.class);
		listWay = tableaubin(roundedVolumetry, this.partSell, Way.class);
		 listRatetype = tableaubin(roundedVolumetry, this.partVariableRate,
				RateType.class);
	
	}
	@Override
	public TradeEvent generate(Book book, Date date)
	{	
		super.generate(book, date);

		Referential ref = Referential.getInstance();
		Generals generals = Generals.getInstance();
		// List<Way> t;

		// declaration des tirages au sort sous contrainte
		Referential.Depositary d1;
		Referential.Counterpart c1;
		Referential.Trader tr1;
		Referential.Currency cur1;

		d1 = ref.getRandomElement(ref.depositaries);

		c1 = ref.getRandomElement(ref.counterparts);

		if (listLocality.get(0).toString() == "NATIONAL")
		{
			cur1 = ref.subList(ref.currencies, "country", generals.owncountry).get(0);
		}

		{
			cur1 = ref.getRandomElement(ref.exList(ref.currencies, "country", generals.owncountry));
		}

		//			float change=1/Referential.getInstance().getdevise(generals.owncountry).change*cur1.change;
		//			int trade=(int) ((int) Loanpertrade.get(i)*change);
		//			Tradeloan tl = new Tradeloan(this, book, date,Loanpertrade.get(i),


		tr1 = ref.getTrader(ref, cur1.code, "loandepo");

//		TradeLoan tl = new TradeLoan(this, "eference", listWay.get(0), date, date,
//				c1, book, date,
//				date,(double ) 45, Indexation.EIBOR, "isni",
//				RateType.ADJUSTABLE,(double )45, (double )45, Term.ONE_WEEK,
//				BaseCalcul.methode1, null, amount,
//				cur1, d1, tr1);
		TradeLoan tl = new TradeLoan(this, "reference", listWay.get(0), date, date,
				c1, book, date,
				date,(double) 0,TradeGenerator.randEnum(Indexation.class), "isin?",
				TradeGenerator.randEnum(RateType.class),(double) this.valueRate,(double) this.valueRate/2, TradeGenerator.randEnum(Term.class),
				TradeGenerator.randEnum(BaseCalcul.class), loanPerTrade.get(0),
				cur1, d1, tr1);

		listLocality.remove(0);
		listWay.remove(0);
		listRatetype.remove(0);
		loanPerTrade.remove(0);

		return tl;
	}
}


