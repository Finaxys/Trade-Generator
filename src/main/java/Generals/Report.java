package Generals;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
public class Report {




Referential ref;
public static ArrayList<TradeEvent> liste;

final static private String		OUTPUT_PATH = "report.csv";
final static private String		OUTPUT_ENCODING = "UTF-8";
static private PrintWriter	writer;
private Report()
{	
	super();	
	
	Report.liste=new ArrayList<TradeEvent>();
	try {
		writer = new PrintWriter(OUTPUT_PATH,OUTPUT_ENCODING);
		writer.write("Date" +"," +"BussinessUnit"+","+"Portfolio" +"," +"Book" +","+"Instrument" +","+"Sens" +","+"Nombre de transactions"+","+"Montant engage" +" EU"+","+System.lineSeparator());
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
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

public static void writeCSTrade(TradeEvent te, int nombre_op,float montant) throws FileNotFoundException, UnsupportedEncodingException
{
	
	String s=System.lineSeparator();
	

	writer.write(te.getDate() +","+te.getBook().getPortFolios().getBu().getName()+","+te.getBook().getPortFolios().getName() +","+te.getBook().getName() +","+te.getInstrument()+","+te.getWay() +","+nombre_op+","+montant +","+ s);
//	writer.write(System.lineSeparator());
//	writer.write(te.date.toString() +",");
//	writer.write(System.lineSeparator());
//	writer.write("Portfolio" +",");
//	writer.write(System.lineSeparator());
//	writer.write(te.book.pt.name +",");
//	writer.write(System.lineSeparator());
//	writer.write("book" +",");
//	writer.write(System.lineSeparator());
//	writer.write(te.book.name +",");
//	writer.write(System.lineSeparator());
//	writer.write("Instrument" +",");
//	writer.write(System.lineSeparator());
//	writer.write(te.instrument.name +",");
//	writer.write(System.lineSeparator());
//	writer.write("Sens" +",");
//	writer.write(System.lineSeparator());
//	writer.write(te.way +",");
//	writer.write(System.lineSeparator());
//	writer.write("Nombre de transactions" +",");
//	writer.write(System.lineSeparator());
//	writer.write(nombre_op +",");
//	writer.write(System.lineSeparator());
//	writer.write("Montant engagé" +",");
//	writer.write(System.lineSeparator());
//	writer.write(montant +",");

	
}
public static void report(ArrayList<TradeEvent> lists,int j){
	
	File check = new File(OUTPUT_PATH );
	
	if (!check.exists())
		check.getParentFile().mkdirs();
    
		try {
//			ff.createNewFile();
//		
//		final FileWriter ffw=new FileWriter(ff);

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
			writeCSTrade(te,nombre_transaction,montant);

			if (!lists.get(i).getWay().name().equalsIgnoreCase("sell")) {MS=MS+montant;NS=NS+nombre_transaction;}else{ME=ME+montant;NE=NE+nombre_transaction;}
			}
			{
				if (lists.get(i).compareTo(te)==0)
				{	
					montant=montant+lists.get(i).amount;
					nombre_transaction++;
				}else
				{	writeCSTrade(te,nombre_transaction,montant);
					
					if (!lists.get(i).getWay().name().equalsIgnoreCase("sell")) {MS=MS+montant;NS=NS+nombre_transaction;}else{ME=ME+montant;NE=NE+nombre_transaction;}
					nombre_transaction=1;
					montant=lists.get(i).amount; 
					
				}
				
			te=lists.get(i);
			}
		i++;
		}

		NS=NS+NE;
		

		writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}


}
