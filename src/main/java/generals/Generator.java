package generals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import domain.Book;
import domain.Businessunit;
import domain.Portfolio;
import domain.Referential;
import domain.TradeEvent;
import domain.TradeGenerator;

public class Generator
{
	public static void main(String[] args)
	{
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
				return;
			}
			System.out.println("Problem handled. Continuing operation. Fix it next time.");
		}

		long startTime = System.currentTimeMillis();

//		for (Businessunit bu : gen.bu)
//			for (Portfolio portfolio : bu.getPortfolios())
//				for (Book sbook : portfolio.getLb())
//					for (Referential.Currency cur : sbook.getCurrencies())
//						System.out.println(sbook.getName() +" >> " + cur.code);

		// List of instrument available
		List<TradeGenerator>	generators = new ArrayList<TradeGenerator>();
		int						days = gen.number_of_day;
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
					tgen.init(43000);

				// While there is still a generator with a volumetry > 0
				while (generators.size() > 0)
				{
					// Generate random generator & currency
					TradeGenerator tgen = ref.getRandomElement(generators);
					Referential.Currency cur = ref.getRandomElement(ref.getCurrencies());
					book = null;
					
					// Get matching book
					for (Portfolio portfolio : bu.getPortfolios())
						for (Book sbook : portfolio.getLb())
							if (sbook.getGenerators().contains(tgen) && sbook.getCurrencies().contains(cur))
							{
								book = sbook;
								break;
							}
					
					// Not found
					if (book == null)
						continue;

					// Generate trade and set properties
					trade = tgen.generate(book, calendar.getTime());
					
					// Manage output
					TradeGenerator.tradeGenerated(trade);
					
					
					// Check if Generator volumetry full
					if (tgen.getTradeGenerated() >= tgen.getRoundedVolumetry())
						generators.remove(tgen);
				}
			}

			calendar.add(Calendar.DATE, 1);
			OutputManager.getInstance().outputTrades();
		}

//		System.out.println(Report.liste.size());
//		Report.ConcatSortOutput();
//		Report.report(Report.liste, days);
//		System.out.println("Report done");

		// Estimation Stats
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println((float) estimatedTime * 100000 / 1000 / 60 / 60);
		System.out.println("OVER");
	}

	/*private static Date instrumentGenerator(TradeGenerator insrandom) {
		// TODO Auto-generated method stub
		return null;
	}

	private static LinkedList<TradeGenerator> Init(Businessunit bu) {
		// TODO Auto-generated method stub
		return null;
	}*/

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