package Generals;
import java.util.ArrayList;
import java.util.Collections;
public class Report {




Referential ref;
ArrayList<TradeEvent> liste;

private Report()
{	
	super();	
	this.liste=new ArrayList<TradeEvent>();
		
}

private static Report INSTANCE = new Report();

public static Report getInstance()
{	
	return INSTANCE;
}

public static void add(TradeEvent te){
	
	getInstance().liste.add(te);
}

public static ArrayList<TradeEvent> ConcatSortOutput(){
	ArrayList<TradeEvent> AllOutput= new ArrayList<TradeEvent>(); 
//	
//	for (Businessunit bu:gen.bu)
//	{
//	 for (Output output: bu.lop)
//	 {
//		 for (TradeEvent te: output.te)
//		 {   
//			 AllOutput.add(te);
//		 }
//	 }
//	}
	Collections.sort(AllOutput);
	
	return (AllOutput);
}	
//
//public ArrayList<TradeEvent> filtre(ArrayList<TradeEvent> t,int p,String str)
//{  ArrayList<TradeEvent> Al=new ArrayList<TradeEvent>();
//   for (TradeEvent te: t)
//	  
//	   {
//	   
//	   try {
//		if (te.getClass().getFields()[p].get(te).equals(str))
//			   {Al.add(te);}
//	} catch (IllegalArgumentException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IllegalAccessException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (SecurityException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//		  
//	   
//	   };
//return Al;
//}

public static void report(ArrayList<TradeEvent> lists){
	
	TradeEvent te=lists.get(0);
	Collections.sort(lists);
	int nombre_transaction=1;

	float montant=te.amount;
	int i=0;
	
while (i<lists.size())
{   if (i==lists.size()-1)
	{
	montant=montant+lists.get(i).amount;
	nombre_transaction++;
	System.out.println("Date: "+te.date + "Path: "+te.book.pt.bu.name + "/ "+te.book.pt.name+"/ "+te.book.name +" Sens: "+te.way.name()+"/n"+"Nombre d'opération: "+nombre_transaction+"/n"+"Somme transférée: "+montant);
	}
    {
    	if (i!=0)
    	{
    		if (lists.get(i).compareTo(te)==0)
    		{
    			montant=montant+lists.get(i).amount;
    			nombre_transaction++;
    		}
    		{
    			System.out.println("Date: "+te.date + "Path: "+te.book.pt.bu.name + "/ "+te.book.pt.name+"/ "+te.book.name +" Sens: "+te.way.name()+"/n"+"Nombre d'opération: "+nombre_transaction+"/n"+"Somme transférée: "+montant);
    			nombre_transaction=1;
    			montant=lists.get(i).amount;
    		}
	
	
    	}
	
	
	te=lists.get(i);
    }
	i++;
	
}
	//System.out.println(Integer.toString(index));

//private ArrayList<ArrayList<TradeEvent>> sort(ArrayList<TradeEvent> Lte,
//		ArrayList<ArrayList<TradeEvent>> classeur) {
//	for( TradeEvent te: Lte)
//	{
//		sortunity(te,classeur);
//	}
//	return classeur;
//	
//}
//
//private void sortunity(TradeEvent te, ArrayList<ArrayList<TradeEvent>> classeur) {
//	switch (te.instrument.name){ 
//	case "Equity": 
//	classeur.get(0).add(te);
//	break;
//	case "LoanDeposit":
//	classeur.get(1).add(te);
//	break;
//	default:
//	break;
//	}
//	
//	
//}


	
}
}
