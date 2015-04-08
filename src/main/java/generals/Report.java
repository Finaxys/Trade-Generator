package generals;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import domain.Referential;
import domain.TradeEvent;
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

public static void writeCSTrade(TradeEvent te, int nombre_op,double montant) throws FileNotFoundException, UnsupportedEncodingException
{
	
	String s=System.lineSeparator();
	
	String date = "";
	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	
	DecimalFormat df = new DecimalFormat() ;

	df.setRoundingMode(RoundingMode.HALF_UP);
	df.applyPattern("###.##");
	df.setMaximumFractionDigits( 2 ) ; //arrondi à 2 chiffres apres la virgules 
	String montantstr = df.format(montant);
	montantstr = montantstr.replaceAll(",", ".");

		date = formater.format(te.getDate());
	writer.write(date +","+te.getBook().getPortFolios().getBu().getName()+","+te.getBook().getPortFolios().getName() +","+te.getBook().getName() +","+te.getInstrument().getName()+","+te.getWay() +","+nombre_op+","+montantstr +","+ s);
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
public static String printDate(Date d){
	return MessageFormat.format("{0}-{1}-{2}", d.getDate(), d.getMonth(), d.getYear());
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
		double MS=0;
		double ME=0;
		int NE=0;
		int NS=0;
		double montant=te.getAmount();
		int i=0;
		
		while (i<lists.size())
		{   	
			if (i==lists.size()-1)
			{
			montant=montant+lists.get(i).getAmount();
			nombre_transaction++;
			writeCSTrade(te,nombre_transaction,montant);

			if (!lists.get(i).getWay().name().equalsIgnoreCase("sell")) {MS=MS+montant;NS=NS+nombre_transaction;}else{ME=ME+montant;NE=NE+nombre_transaction;}
			}
			{
				if (lists.get(i).compareTo(te)==0)
				{	
					montant=montant+lists.get(i).getAmount();
					nombre_transaction++;
				}else
				{	writeCSTrade(te,nombre_transaction,montant);
					
					if (!lists.get(i).getWay().name().equalsIgnoreCase("sell")) {MS=MS+montant;NS=NS+nombre_transaction;}else{ME=ME+montant;NE=NE+nombre_transaction;}
					nombre_transaction=1;
					montant=lists.get(i).getAmount();
					
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
