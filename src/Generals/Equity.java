package Generals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Equity extends Instrument {
	//public final String type="Equity";

	public int Partsell;
	public int owncountry;
	public int volumetry;
	public double volumetry_tolerance;
	public double repartition_tolerance;
	public Boolean is_stp;

	//private Date startDate;



	public static List<Boolean> tableaubin(int size, int ratio){
		List<Boolean> t1=new ArrayList<Boolean>(size);
		int j;
		int national = (ratio * size) / 100;
		
		for (int i = 0; i < size; ++i)
			t1.add(Boolean.FALSE);
		
		t1.set(0, Boolean.FALSE);

		for (j = 0; j <= national; j++)
			t1.set(j, Boolean.TRUE);

		Collections.shuffle(t1);

		return t1;
	}

	@Override
	public void generate(Generals g,Referential ref, int montant,int date) {

		int montant_total_jour=montant;//remplir avec general
		double rand1, rand2;
		double v;
		//calcul des  tol�rances
		//exemple 20==>random entre -0.2 et 0.2
		rand1= this.repartition_tolerance*2*(Math.random()-0.5)/100;
		rand2=this.volumetry_tolerance*2*(Math.random()-0.5)/100;
		//montant r�el (prise en compte de la tol�rance
		montant_total_jour+=rand1*montant_total_jour;
		//calcul du nombre de trade a distribuer par jour (entier pair)
		v=(1-rand2)*volumetry;
		int volume_arrondi=(int) v;
		if (volume_arrondi % 2 ==1)
		{
			volume_arrondi++;
		}
		// Date d=startDate;
		//DECLARATION des variables de la boucles while  
		//separer le montant en 2 
		float rand12;
		//double random pour la tol�rance des quantit�s 
		double randomquantity3;
		double randomquantity4;
		//montant a diviser chaque jour
		double to2tradeamount=montant_total_jour/volume_arrondi;
		//quantit� a calculer
		int quantity1,quantity2;
		//prix soumis a tolerance randomquantity
		float price1,price2;		
		//nombre de jour de simulation en dur
		//    int day_simulation=number;
		//    //start date
		//    int day_start=0;
		//   //le jour courant
		//    int numero_jour_courant=0;
		//les tableaux de distribution de sell/buy; National/international
		//List<boolean> t1,t2; 
		List<Boolean> t1;

		List<Boolean> t2;

		//declaration des tirages au sort sous contraintes
		Referential.Depositary d1,d2;
		Referential.Counterpart c1,c2;
		Referential.Trader tr1,tr2;
		Referential.Product pro1,pro2;
		Referential.Currency cur1,cur2;
		Referential.Portfolio port1,port2;


		//boucle while parcourant les jours de simulation
		//    while(numero_jour_courant<=day_simulation){
		//durant un jour:
		//calcul des tableaux de distribution

		t1= tableaubin(volume_arrondi,this.owncountry);

		t2= tableaubin(volume_arrondi,this.Partsell);

		//pour chaque bi-trade
		int i;
		for (i=0; i<volume_arrondi; i=i+2) {

			//partage du montantpertrade
			rand12=(float)Math.random();
			//set random price -+3%
			randomquantity3=6*(Math.random()-0.5);
			randomquantity4=6*(Math.random()-0.5);

			//tirage au sort sous contrainte

			d1=ref.getRandomElement(ref.Depositaries);
			d2=ref.getRandomElement(ref.Depositaries);
			c1=ref.getRandomElement(ref.Counterparts);
			c2=ref.getRandomElement(ref.Counterparts);
			tr1=ref.getRandomElement(ref.Traders);
			tr2=ref.getRandomElement(ref.Traders);
			pro1=ref.getRandomElement(ref.Products);
			pro2=ref.getRandomElement(ref.Products);
			cur1=ref.getRandomElement(ref.Currencies);
			cur2=ref.getRandomElement(ref.Currencies);
			port1=ref.getRandomElement(ref.Portfolios);
			port2=ref.getRandomElement(ref.Portfolios);


			price1=pro1.price;
			price2=pro2.price;
			price1=(float) (price1*(1+randomquantity3/100));
			price2=(float) (price2*(1+randomquantity4/100));
			quantity1=(int) (rand12*to2tradeamount/price1);
			quantity2=(int) (rand12*to2tradeamount/price2);


			Tradeequity tq1= new Tradeequity(date,t2.get(i),price1,quantity1,d1,c1,tr1,pro1,cur1,port1);
			Tradeequity tq2= new Tradeequity(date,t2.get(i+1),price2,quantity2,d2,c2,tr2,pro2,cur2,port2);
			
			System.out.println("Equity >> " + date + " / " + tq1.prix + " / " + tq1.quantity + " / " + tq1.port1.codeptf);

			// Tradeequity tq2=
			g.addTradevent(tq1);
			g.addTradevent(tq2);
		}

		//montant_total_jour += montant_total_jour*;


		//}

	}
}
