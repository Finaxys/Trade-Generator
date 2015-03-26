package Generals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Generals.Referential.Portfolio;


public class LoanDeposit extends Instrument {
//http://www.fimarkets.com/pages/pret_emprunt_de_titres.php
//type de taux (fixe/variable), base de calcul, valeur du taux, devise, durée
public String devise;
public int Partloan;
public int owncountry;
public int volumetry;
public double volumetry_tolerance;
public double repartition_tolerance;
public float valeur_taux;
public int tolerance_taux_var;
public int part_taux_variable;
public int durée;
BaseCalcul basecalcul;
private Class Locality;

public LoanDeposit(String devise, int partloan, int owncountry, int volumetry,
		double volumetry_tolerance, double repartition_tolerance,
		int valeur_taux, int tolerance_taux_var, int part_taux_variable,
		int durée, Boolean is_stp)  {
	super();
    this.devise=devise;
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
public void generate(Book book, int amount, int date) 
{
	Referential ref = Referential.getInstance();
	Generals generals = Generals.getInstance();		
	
	int amountPerDay = amount;
	double rand1, rand2;
	double toleredVolumetry;
	Random	random = new Random();
   
	rand1 = this.repartition_tolerance * 2 * (random.nextDouble()-0.5)/100;
	rand2 = this.volumetry_tolerance * 2 * (random.nextDouble()-0.5)/100;
	amountPerDay += rand1 * amountPerDay;
	
	//calculation of number of trades to distribute per day
	toleredVolumetry = (1-rand2) * volumetry;
	int roundedVolume = (int) toleredVolumetry;				
	List<Integer> Loanpertrade=Sparsemoney(roundedVolume, amountPerDay);		 
	
	//List<Way> t;
	
	//declaration des tirages au sort sous contrainte
	Referential.Depositary d1;
	Referential.Counterpart c1;
	Referential.Trader tr1;
//	Referential.Product pro1,pro2;
	Referential.Currency cur1;
//	Referential.Portfolio port1,port2;

	List<Locality> t1 = tableaubin(roundedVolume, this.owncountry,Locality.class);
	List<Way> t2 = tableaubin(roundedVolume, this.Partloan,Way.class);
	List<Typetaux> t3=  tableaubin(roundedVolume, this.part_taux_variable,Typetaux.class);
	
	
	
	for (int i = 0; i < roundedVolume; i = i + 1) 
	{

		//sharing of amount per trade
//		randToleranceQuantities = (float)Math.random();
		
		//set random price -+3%
//		randomquantity1 = 6 * (random.nextDouble() - 0.5);
//		randomquantity2 = 6 * (random.nextDouble() - 0.5);
		//tirage au sort sous contrainte
        
		d1 = ref.getRandomElement(ref.Depositaries);
		
		c1 = ref.getRandomElement(ref.Counterparts);
		
		tr1 = ref.getRandomElement(ref.Traders);
	
		cur1 = ref.getRandomElement(ref.Currencies);
		
		
		
		//Portfolio port1 = ref.getRandomElement(ref.Portfolios);
		


		
         
		Tradeloan tl = new Tradeloan(this, book,date,Loanpertrade.get(i),t2.get(i),t3.get(i),d1,c1,tr1,cur1,valeur_taux*random.nextFloat(),Term.getRandom(),BaseCalcul.getRandom());
		tradeGenerated(tl);
		
	}
}




	
}
