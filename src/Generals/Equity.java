package Generals;

import java.util.ArrayList;

public class Equity implements Instrument {
	public int Partsell;
	public int owncountry;
	public int volumetry;
	public double volumetry_tolerance;
	public double repartition_tolerance;
    public Generals g;
	//private Date startDate;
    
    public static ArrayList<Integer> tableaubin(int a,int b){
    	ArrayList<Integer> T=new ArrayList<Integer>();
    	return T;
    }
	public void toxml() {
		// TODO Auto-generated method stub

	}
    
	public void generate() {
		int montant_total_jour=5;//remplir avec general
		double rand1, rand2;
		double v;
		//calcul des  tolérances
		//exemple 20==>random entre -0.2 et 0.2
		rand1= this.repartition_tolerance*2*(Math.random()-0.5)/100;
		rand2=this.volumetry_tolerance*2*(Math.random()-0.5)/100;
		montant_total_jour+=rand1*montant_total_jour;
		//calcul du nombre de trade a distribuer par jour (entier pair)
		v=rand2*volumetry;
		
		int volume_arrondi=(int) v;
		
        if (volume_arrondi % 2 ==1){
        	volume_arrondi++;
        	}
        
          // Date d=startDate;
           double rand12;
           double randomquantity3;
           double randomquantity4;
           double to2tradeamount=montant_total_jour/volume_arrondi;
           int quantity1,quantity2;
           int price1,price2;		
           int day_simulation=20;
           int numero_jour_courant=0;
           //hashtable ou arraylist
           while(numero_jour_courant<=day_simulation){
        	   //sert le pays  et way
        	   //pays
        	   ArrayList<Integer> t1,t2;  	  
        	   t1= tableaubin(volume_arrondi,this.owncountry);
        	   //way
        	   t2=tableaubin(volume_arrondi,this.Partsell);
				// counterpart CTP1;
				// devise D1;
				// trader T1;
				// portefeuille ptf1;
				// depositaire dpst1;
					//a paralleliser
				  int i;
						for (i=1; i<volume_arrondi; i=i+2) {
				
				//sert a partager les sous 	
						
				rand12=Math.random();
				//instrument1=new instrument(tableaubin1[i],"equity");
				//instrument2=new instrument(tableaubin1[i+1]);
				//
				//randomquantity3=6*(Math.random()-0.5);
				//randomquantity4=6*(Math.random()-0.5);
				//price1=instrument1.getprice();
				//price2=instrument2.getprice();
				//price1=price1*(1+randomquantity3/100);
				//price2=price2*(1+randomquantity4/100);
				//quantity1=(int)rand12*to2tradeamount/price1;
				//quantity2=(int)rand12*to2tradeamount/price2;
				//CTP1=set_ref.getcounterpart();
				//D1=instrument1.getdevise();
				//dpst1=set_ref.getdepositaire();
				//
				//ToXML(s...);
		}
		//montant_total_jour += montant_total_jour*;	
     }
	}
}
