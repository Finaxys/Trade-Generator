package Generals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EntryPoint
{
	public static void main(String[] args)
	{
		// Stat
		long startTime = System.currentTimeMillis();

		Referential ref = Referential.getInstance();
		Generals gen = Generals.getInstance();

		try
		{
			LoadXML.init(ref);
		}
		catch (CustomParsingException e)
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
			{
				dis = bu.getMainInstrumentCount();

				for (Portfolio port : bu.getPortfolios())
				{

					for (Book b : port.getLb())
					{
						if (b.getGenerators().size() > 0 && (b.getGenerators().contains(bu.getMainInstrument())))
						{
							amount_per_book = (int) (gen.budget * bu.getRatio() / (dis * 1000));
							for (int i = 0; i < b.getGenerators().size(); i++)
							{
								TradeGenerator t = b.getGenerators().get(i);

								if (!(t.equals(bu.getMainInstrument())))
								{
									t.generate(b, t.getMontant(), calendar.getTime());
								}
								{
									t.generate(b, amount_per_book, calendar.getTime());
								}

							}
						}
						else
						{
							if (j == 0)
								System.out.println("book mal rangé: " + b.getName());
						}
					}

				}
			}

			// List of instrument available
			List<TradeGenerator> generators = new ArrayList<TradeGenerator>();
			for (Businessunit bu : gen.bu)
			{	
				// Init instruments available
				generators.addAll(bu.getGenerators());
				
				// Init Instrument Generator
				
				// While there is still an instrument with a volumetry > 0
				while(instrumentgenerator!=null)
				{
					TradeGenerator insrandom=getrandomins();
					Currency currandom=getrandomdevise();
					Book book= match(bu,insrandom,currandom);

					insrandom.generate(book, bu.getMainInstrument().getMontant(), instrumentGenerator(insrandom));
				}
			}


			
			
			
			
			calendar.add(Calendar.DATE, 1);
			OutputManager.getInstance().outputTrades();
		}

		Report.ConcatSortOutput();
//		Report.report(Report.liste, simulate_days);
//		System.out.println("Report done");

				// Estimation Stats
				long estimatedTime = System.currentTimeMillis() - startTime;
				System.out.println((float) estimatedTime * 100000 / 1000 / 60 / 60);
				System.out.println("Done");
	}
	
	private static Date instrumentGenerator(TradeGenerator insrandom) {
		// TODO Auto-generated method stub
		return null;
	}

	private static LinkedList<TradeGenerator> Init(Businessunit bu) {
		// TODO Auto-generated method stub
		return null;
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
			writer.write("<" + node.name + ">" + node.value + "</" + node.name + ">" + System.lineSeparator());
	}
}