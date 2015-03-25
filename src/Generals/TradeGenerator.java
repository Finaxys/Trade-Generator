package Generals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;


public class TradeGenerator
{	
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();

		Referential ref = Referential.getInstance();	
		Generals gen = Generals.getInstance();

		LoadXML.init(ref);

		int simulate_days = Integer.parseInt(args[0]);
		int amount_per_book;
		int j;

		for (j = 0; j <= simulate_days; j++)
			for (Businessunit bu : gen.bu)
				for (Portfolio port :  bu.lpor)
					for (Book b : port.lb)
					{
						if (b.ins.equalsIgnoreCase("equity"))
						{
							Instrument t = bu.getInstrument(b.ins);
							amount_per_book = (int) (gen.total_buget * bu.ratio / 1000);

							t.generate(b, amount_per_book, j);
						}  
					}
		
		
		// Create file BATCH MODE
		PrintWriter writer;
		try {
			writer = new PrintWriter("trades.xml", "UTF-8");

			writer.write("<traders>\n");

			for (Tradeevents trade : gen.te)
				writer.write(trade.toXML());
			
			writer.write("</traders>");
			writer.close();

			System.out.println("Done");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println((float) estimatedTime * 100000 / 1000 / 60 / 60);
	}	
}