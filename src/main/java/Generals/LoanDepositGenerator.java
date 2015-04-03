package Generals;

import java.util.Date;
import java.util.List;
import java.util.Random;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Trader;

public class LoanDepositGenerator extends TradeGenerator
{
	private int 				PartSell;
	private int 				owncountry;
	private int 				volumetry_tolerance;
	private int 				repartition_tolerance;
	private float 				valeur_taux;
	private int 				tolerance_taux_var;
	private int 				part_taux_variable;
	private int 				durée;
	private BaseCalcul 			basecalcul;
	private String 				devise;
	private List<Integer> 		Loanpertrade;
	private int 				amountPerDay;
	private List<Locality> 		listLocality;
	private List<Way>			listWay;
	private List<RateType> 		listRatetype;
	private int 				index;

	public LoanDepositGenerator(int partloan, int owncountry,
			int volumetry, int volumetry_tolerance,
			int repartition_tolerance, int valeur_taux,
			int tolerance_taux_var, int part_taux_variable){
		super();
		this.devise = "EUR";
		this.PartSell = partloan;
		this.owncountry = owncountry;
		this.volumetry = volumetry;
		this.volumetry_tolerance = volumetry_tolerance;
		this.repartition_tolerance = repartition_tolerance;
		this.valeur_taux = valeur_taux;
		this.tolerance_taux_var = tolerance_taux_var;
		this.part_taux_variable = part_taux_variable;
	}

	public void init(int amount)
	{		
		super.init(amount);

		double rand1, rand2;
		double toleredVolumetry;
		Random random = new Random();
		amountPerDay=amount;
		rand1 = this.repartition_tolerance * 2 * (random.nextDouble() - 0.5)
				/ 100;
		rand2 = this.volumetry_tolerance * 2 * (random.nextDouble() - 0.5)
				/ 100;
		amountPerDay = (int) (amountPerDay + rand1 * amountPerDay);
		
		// calculation of number of trades to distribute per day
		toleredVolumetry = (1 - rand2) * volumetry;
		rounded_volumetry = (int) toleredVolumetry;	
		Loanpertrade = Sparsemoney(rounded_volumetry, amountPerDay);
		listLocality = tableaubin(rounded_volumetry, this.owncountry,
				Locality.class);
		listWay = tableaubin(rounded_volumetry, this.PartSell, Way.class);
		 listRatetype = tableaubin(rounded_volumetry, this.part_taux_variable,
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


		tr1 = ref.getTrader(ref, cur1.country, "loandepo");

//		TradeLoan tl = new TradeLoan(this, "eference", listWay.get(0), date, date,
//				c1, book, date,
//				date,(double ) 45, Indexation.EIBOR, "isni",
//				RateType.ADJUSTABLE,(double )45, (double )45, Term.ONE_WEEK,
//				BaseCalcul.methode1, null, amount,
//				cur1, d1, tr1);
		TradeLoan tl = new TradeLoan(this, "reference", listWay.get(0), date, date,
				c1, book, date,
				date,(double) 0,TradeGenerator.randEnum(Indexation.class), "isin?",
				TradeGenerator.randEnum(RateType.class),(double) this.valeur_taux,(double) this.valeur_taux/2, TradeGenerator.randEnum(Term.class),
				TradeGenerator.randEnum(BaseCalcul.class), Loanpertrade.get(0),
				cur1, d1, tr1);

		listLocality.remove(0);
		listWay.remove(0);
		listRatetype.remove(0);
		Loanpertrade.remove(0);

		return (tl);
	}
}


