package Generals;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

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
			{// main instrument ratio
				// get nombre de book avec main instrument

				dis = bu.getMainInstrumentCount();
				for (Portfolio port : bu.getPortfolios())
				{

					for (Book b : port.getLb())
					{
						if (b.getIns().size() > 0 && (b.getIns().contains(bu.getMainInstrument())))
						{
							amount_per_book = (int) (gen.budget * bu.getRatio() / (dis * 1000));
							for (int i = 0; i < b.getIns().size(); i++)
							{
								Instrument t = b.getIns().get(i);
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
			calendar.add(Calendar.DATE, 1);
			OutputManager.getInstance().outputTrades();
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println((float) estimatedTime * 100000 / 1000 / 60 / 60);
		System.out.println("Done");
		Report.ConcatSortOutput();
		Report.report(Report.liste, simulate_days);
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
			writer.write("<" + node.name + ">" + node.value + "</" + node.name + ">" + System.lineSeparator());
	}
}