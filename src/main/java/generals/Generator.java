package generals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Book;
import domain.Businessunit;
import domain.Portfolio;
import domain.Referential;
import domain.TradeEvent;
import domain.TradeGenerator;

public class Generator
{
	private static final Logger LOGGER = Logger.getLogger(Generator.class.getName());
	
	private Generator()
	{
	    
	}

	public static void main(String[] args)
	{
		Referential ref = Referential.getInstance();
		Generals gen = Generals.getInstance();

		try
		{
			LoadXML.init();
		}
		catch (CustomParsingException e)
		{
			LOGGER.log(Level.WARNING, "Custom exception caught", e);

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

		// List of instrument available
		List<TradeGenerator>	generators = new ArrayList<TradeGenerator>();
		int						days = gen.getNumberOfDay();
		Calendar				calendar = Calendar.getInstance();
		Book					book;
		TradeEvent				trade;
		calendar.setTime(new Date());
		for (int day = 0; day < days; ++day)
		{
			for (Businessunit bu : gen.getBusinessunits())
			{	
				// Init generators available
				generators.addAll(bu.getGenerators());

				// Init Instrument Generator
				for (TradeGenerator tgen : generators)
					tgen.init(43000);

				// While there is still a generator with a volumetry > 0
				while (!generators.isEmpty())
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
}