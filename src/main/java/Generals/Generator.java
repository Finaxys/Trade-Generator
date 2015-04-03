package Generals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Generator
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

//		int simulate_days = Integer.parseInt(args[0]);
//		int amount_per_book;
//		int j;
//		int dis;
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		for (j = 0; j <= simulate_days; j++)
//		{
//			for (Businessunit bu : gen.bu)
//			{
//				dis = bu.getMainInstrumentCount();
//
//				for (TradeGenerator tgen : bu.getGenerators())
//					tgen.init((int) (gen.budget * bu.getRatio() / (dis * 1000)));
//
//				for (Portfolio port : bu.getPortfolios())
//				{
//
//					for (Book b : port.getLb())
//					{
//						if (b.getGenerators().size() > 0 && (b.getGenerators().contains(bu.getMainInstrument())))
//						{
//							amount_per_book = (int) (gen.budget * bu.getRatio() / (dis * 1000));
//							for (int i = 0; i < b.getGenerators().size(); i++)
//							{
//								TradeGenerator t = b.getGenerators().get(i);
//
//								if (!(t.equals(bu.getMainInstrument())))
//								{
//									t.generate(b, t.getMontant(), calendar.getTime());
//								}
//								{
//									t.generate(b, amount_per_book, calendar.getTime());
//								}
//
//							}
//						}
//						else
//						{
//							if (j == 0)
//								System.out.println("book mal rangé: " + b.getName());
//						}
//					}
//
//				}
//			}
//
//			calendar.add(Calendar.DATE, 1);
//			OutputManager.getInstance().outputTrades();
//		}
		
		

		// List of instrument available
		List<TradeGenerator>	generators = new ArrayList<TradeGenerator>();
		int						days = Integer.parseInt(args[0]);
		Calendar				calendar = Calendar.getInstance();
		Book					book;
		TradeEvent				trade;
		calendar.setTime(new Date());
		for (int day = 0; day < days; ++day)
		{
			for (Businessunit bu : gen.bu)
			{	
				// Init generators available
				generators.addAll(bu.getGenerators());

				// Init Instrument Generator
				for (TradeGenerator tgen : generators)
					tgen.init(43);

				// While there is still a generator with a volumetry > 0
				while (generators.size() > 0)
				{
					// Generate random generator & currency
					TradeGenerator tgen = ref.getRandomElement(generators);
					Referential.Currency cur = ref.getRandomElement(ref.currencies);
					book = null;
					
					// Get matching book
					for (Portfolio portfolio : bu.getPortfolios())
						for (Book sbook : portfolio.getLb())
							if (sbook.getGenerators().contains(tgen) && sbook.getCurrencies().contains(cur))
								book = sbook;
					
					// Not found
					if (book == null)
						continue;

					// Generate trade and set properties
					trade = tgen.generate(book, 0, calendar.getTime());
					
					// Manage output
					TradeGenerator.tradeGenerated(trade);
					
					// Check if Generator volumetry full
					if (tgen.getTradeGenerated() >= tgen.getVolumetry())
						generators.remove(tgen);
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