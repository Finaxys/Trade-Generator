package Generals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
public class Report {




Referential ref;
public static ArrayList<TradeEvent> liste;

private Report()
{	
	super();	
	Report.liste=new ArrayList<TradeEvent>();
		
}

private static Report INSTANCE = new Report();

public static Report getInstance()
{	
	return INSTANCE;
}

public static void add(TradeEvent te){
	
	Report.liste.add(te);
}

public static void ConcatSortOutput(){

	
	Collections.sort(Report.liste);
}	


public static void report(ArrayList<TradeEvent> lists,int j){	
	
		File ff=new File("report"); // définir l'arborescence
		try {
			ff.createNewFile();
		
		final FileWriter ffw=new FileWriter(ff);

		TradeEvent te;
		te=lists.get(0);
		int nombre_transaction=1;
		int ntt=1;
		float MS=0;
		float ME=0;
		int NE=0;
		int NS=0;
		float montant=te.amount;
		int i=0;
		
		while (i<lists.size())
		{   	
			if (i==lists.size()-1)
			{
			montant=montant+lists.get(i).amount;
			nombre_transaction++;
			ffw.write("Date: "+te.date + "Path: "+te.book.pt.bu.name + "/ "+te.book.pt.name+"/ "+te.book.name +" Instrument: "+te.instrument+" Sens: "+te.way.name());
			ffw.write("\r\n ");
			ffw.write("Nombre d'opération: "+nombre_transaction);
			ffw.write("\r\n ");
			ffw.write("Somme transférée: "+montant+"EU");
			ffw.write("\r\n ");
			ffw.write("\r\n ");
			if (!lists.get(i).way.name().equalsIgnoreCase("sell")) {MS=MS+montant;NS=NS+nombre_transaction;}else{ME=ME+montant;NE=NE+nombre_transaction;}
			}
			{
				if (lists.get(i).compareTo(te)==0)
				{	
					montant=montant+lists.get(i).amount;
					nombre_transaction++;
				}else
				{
					ffw.write("Date: "+te.date + " Path: "+te.book.pt.bu.name + "/ "+te.book.pt.name+"/ "+te.book.name +" Instrument: "+te.instrument.name+" Sens: "+te.way.name());
					ffw.write("\r\n ");
					ffw.write("Nombre d'opération: "+nombre_transaction);
					ffw.write("\r\n ");
					ffw.write("Somme transférée: "+montant+"EU");
					ffw.write("\r\n ");
					ffw.write("\r\n ");
					if (!lists.get(i).way.name().equalsIgnoreCase("sell")) {MS=MS+montant;NS=NS+nombre_transaction;}else{ME=ME+montant;NE=NE+nombre_transaction;}
					nombre_transaction=1;
					montant=lists.get(i).amount; 
					
				}
				
			te=lists.get(i);
			}
		i++;
		}
		ffw.write("\r\n ");
		ffw.write("\r\n ");
		NS=NS+NE;
		ffw.write("Nombre d'opération moyen : "+NS/j);ffw.write("\r\n ");
		ffw.write("Montant sortant moyen par jour : "+ME/j+"EU");ffw.write("\r\n ");
		ffw.write("Montant entrant moyen par jour : "+MS/j+"EU");
		ffw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}


}
