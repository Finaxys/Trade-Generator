package generals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import domain.Referential;
import domain.TradeEvent;

public class Report {
	Referential ref;
	public static List<TradeEvent> liste;

	private final static String		OUTPUT_PATH = "report.csv";
	private final static String		OUTPUT_ENCODING = "UTF-8";
	private static PrintWriter	writer;
	private static Report INSTANCE = new Report();

	private Report()
	{	
		super();	

		Report.liste=new ArrayList<TradeEvent>();
		try {
			writer = new PrintWriter(OUTPUT_PATH,OUTPUT_ENCODING);
			writer.write("Date" +"," +"BussinessUnit"+","+"Portfolio" +"," +"Book" +","+"Instrument" +","+"Sens" +","+"Nombre de transactions"+","+"Montant engage" +" EU"+","+System.lineSeparator());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

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
	}
	public static String printDate(Date d){
		return MessageFormat.format("{0}-{1}-{2}", d.getDate(), d.getMonth(), d.getYear());
	}
	public static void report(ArrayList<TradeEvent> lists,int j){

		File check = new File(OUTPUT_PATH );

		if (!check.exists())
			check.getParentFile().mkdirs();

		try {
			TradeEvent te;
			te=lists.get(0);
			int nombreTransaction=1;
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
					nombreTransaction++;
					writeCSTrade(te,nombreTransaction,montant);

					if (!lists.get(i).getWay().name().equalsIgnoreCase("sell"))
					{
					    MS=MS+montant;
					    NS=NS+nombreTransaction;
					}
					else
					{
					        ME=ME+montant;
					        NE=NE+nombreTransaction;
					}
				}
				{
					if (lists.get(i).compareTo(te)==0)
					{	
						montant=montant+lists.get(i).getAmount();
						nombreTransaction++;
					}else
					{	writeCSTrade(te, nombreTransaction, montant);

					if (!lists.get(i).getWay().name().equalsIgnoreCase("sell"))
					{
					    MS=MS+montant;
					    NS=NS+nombreTransaction;
					}
					else
					{
					    ME=ME+montant;
					    NE=NE+nombreTransaction;
					}
					nombreTransaction=1;
					montant=lists.get(i).getAmount();

					}

					te=lists.get(i);
				}
				i++;
			}

			NS=NS+NE;

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
