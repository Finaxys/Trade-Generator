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


public static void report(ArrayList<TradeEvent> lists){	
	
		File ff=new File("C:\\Users\\finaxys\\Desktop\\workspace\\report"); // définir l'arborescence
		try {
			ff.createNewFile();
		
		final FileWriter ffw=new FileWriter(ff);

		TradeEvent te;
		te=lists.get(0);
		int nombre_transaction=1;
		float montant=te.amount;
		int i=0;
		while (i<lists.size())
		{   	
			if (i==lists.size()-1)
			{
			montant=montant+lists.get(i).amount;
			nombre_transaction++;
			ffw.write("Date: "+te.date + "Path: "+te.book.pt.bu.name + "/ "+te.book.pt.name+"/ "+te.book.name +" Sens: "+te.way.name());
			ffw.write("\r\n ");
			ffw.write("Nombre d'opération: "+nombre_transaction);
			ffw.write("\r\n ");
			ffw.write("Somme transférée: "+montant);
			ffw.write("\r\n ");
			}
			{
				if (lists.get(i).compareTo(te)==0)
				{
					montant=montant+lists.get(i).amount;
					nombre_transaction++;
				}else
				{
					ffw.write("Date: "+te.date + " Path: "+te.book.pt.bu.name + "/ "+te.book.pt.name+"/ "+te.book.name +" Sens: "+te.way.name());
					ffw.write("\r\n ");
					ffw.write("Nombre d'opération: "+nombre_transaction);
					ffw.write("\r\n ");
					ffw.write("Somme transférée: "+montant);
					ffw.write("\r\n ");
					nombre_transaction=1;
					montant=lists.get(i).amount;				
				}
				
			te=lists.get(i);
			}
		i++;
		}
		ffw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}


}
