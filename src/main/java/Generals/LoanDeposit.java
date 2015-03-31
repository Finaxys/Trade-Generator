package Generals;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class LoanDeposit extends Instrument
{
	// type de taux (fixe/variable), base de calcul, valeur du taux, devise,
	// durée
	public String devise;
	public int Partloan;
	public int owncountry;
	public int volumetry;
	public int volumetry_tolerance;
	public int repartition_tolerance;
	public float valeur_taux;
	public int tolerance_taux_var;
	public int part_taux_variable;
	public int durée;
	BaseCalcul basecalcul;

	public LoanDeposit(int partloan, int owncountry,
			int volumetry, int volumetry_tolerance,
			int repartition_tolerance, int valeur_taux,
			int tolerance_taux_var, int part_taux_variable)
	{
		super();
		this.devise = "EUR";
		Partloan = partloan;
		this.owncountry = owncountry;
		this.volumetry = volumetry;
		this.volumetry_tolerance = volumetry_tolerance;
		this.repartition_tolerance = repartition_tolerance;
		this.valeur_taux = valeur_taux;
		this.tolerance_taux_var = tolerance_taux_var;
		this.part_taux_variable = part_taux_variable;
	}

	@Override
	public void generate(Book book, int amount, Date date)
	{
		Referential ref = Referential.getInstance();
		Generals generals = Generals.getInstance();

		int amountPerDay = amount;
		double rand1, rand2;
		double toleredVolumetry;
		Random random = new Random();

		rand1 = this.repartition_tolerance * 2 * (random.nextDouble() - 0.5)
				/ 100;
		rand2 = this.volumetry_tolerance * 2 * (random.nextDouble() - 0.5)
				/ 100;
		amountPerDay += rand1 * amountPerDay;
		
		// calculation of number of trades to distribute per day
		toleredVolumetry = (1 - rand2) * volumetry;
		int roundedVolume = (int) toleredVolumetry;	



	
		List<Integer> Loanpertrade = Sparsemoney(roundedVolume, amountPerDay);

		// List<Way> t;

		// declaration des tirages au sort sous contrainte
		Referential.Depositary d1;
		Referential.Counterpart c1;
		Referential.Trader tr1;
		Referential.Currency cur1;

		List<Locality> t1 = tableaubin(roundedVolume, this.owncountry,
				Locality.class);
		List<Way> t2 = tableaubin(roundedVolume, this.Partloan, Way.class);
		List<Typetaux> t3 = tableaubin(roundedVolume, this.part_taux_variable,
				Typetaux.class);
		
		
		for (int i = 0; i < roundedVolume; i = i + 1)
		{

			d1 = ref.getRandomElement(ref.Depositaries);

			c1 = ref.getRandomElement(ref.Counterparts);

			tr1 = ref.getRandomElement(ref.Traders);

			if (t1.get(i).toString() == "NATIONAL")
			{
				cur1 = ref.subList(ref.Currencies, "country",
						generals.owncountry).get(0);
			}

			{
				cur1 = ref.getRandomElement(ref.exList(ref.Currencies,
						"country", generals.owncountry));
			}
			
			Tradeloan tl = new Tradeloan(this, book, date, Loanpertrade.get(i),
					t2.get(i), t3.get(i), d1, c1, tr1, cur1, valeur_taux
							* random.nextFloat(), Term.getRandom(),
					BaseCalcul.getRandom());
			tradeGenerated(tl);

		}
	}

}
