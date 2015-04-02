package Generals;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TradeGenerator
{
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();

		Referential ref = Referential.getInstance();
		Generals gen = Generals.getInstance();

		try
		{
			LoadXML.init(ref);
		} catch (CustomParsingException e)
		{
			System.out.println("Problem while parsing informations :");
			System.out.println(e.getMessage());
			if (e.aborting())
			{
				System.out.println("Aborting program");
				System.exit(-1);
			}
			System.out.println("Problem handled. Continuing operation. Fix it next time.");
		}

		int simulate_days = Integer.parseInt(args[0]);
		int amount_per_book;
		int j;
		int dis;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		for (j = 0; j <= simulate_days; j++)
		{	
			for (Businessunit bu : gen.bu)
			{//main instrument ratio
				//get nombre de book avec main instrument
				dis=bu.getMainInstrumentCount();
				for (Portfolio port : bu.lpor)
					{
					
					for (Book b : port.lb)
					{	

						if (b.ins.size() > 0 && (b.ins.contains(bu.main_instrument)))
							{	amount_per_book = (int) (gen.budget * bu.ratio / (dis*1000));
								for (int i=0;i<b.ins.size();i++)
								{
									Instrument t=b.ins.get(i);
									if (!(t.equals(bu.main_instrument)))
									{
									t.generate(b,t.getMontant(), calendar.getTime());
									}
									{
									t.generate(b, amount_per_book, calendar.getTime());
									}

								}

							}
						else
							{if (j==0)
							System.out.println("book mal rangé: "+b.name);
							}
					}
					
					}
				}
			calendar.add(Calendar.DATE, 1);
			OutputManager.getInstance().outputTrades();
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println((float) estimatedTime * 100000 / 1000 / 60 / 60);
		System.out.println("Done");
		Report.ConcatSortOutput();
		Report.report(Report.liste,simulate_days);
		System.out.println("Report done");
	}

	static void writeXMLNode(PrintWriter writer, TradeEvent.Node node)
	{
		// Check if there is nodes inside node -> recursion
		if (node.nodes != null)
		{
			writer.write("<" + node.name + ">" + System.lineSeparator());
			for (TradeEvent.Node n : node.nodes)
				writeXMLNode(writer, n);
			writer.write("</" + node.name + ">" + System.lineSeparator());
		}
		// Only simple node -> print it
		else
			writer.write("<" + node.name + ">" + node.value + "</" + node.name
					+ ">" + System.lineSeparator());
	}
}