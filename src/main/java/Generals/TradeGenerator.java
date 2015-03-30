package Generals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class TradeGenerator
{
	public static void main(String[] args)
	{
		Devise dev5=new Devise("france","eur");
		Devise dev4=new Devise("france1","eur");
		Devise dev3=new Devise("france1","eur1");
		Devise dev2=new Devise("france1","eur");
		Devise dev1=new Devise("france","eur1");
		
		ArrayList<Devise> lt=new ArrayList<Devise>();
		lt.add(dev1);
		lt.add(dev3);
		lt.add(dev5);
		lt.add(dev2);
		lt.add(dev4);
		ArrayList<Devise> slt=new ArrayList<Devise>();
		
	
		
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

		int counter = 0;
		for (j = 0; j <= simulate_days; j++)
		{
			for (Businessunit bu : gen.bu)
				for (Portfolio port : bu.lpor)
					for (Book b : port.lb)
					{
						if (b.ins.size() > 0 &&
							(b.ins.get(0).name.equalsIgnoreCase("equity") || b.ins.get(0).name.equalsIgnoreCase("loandepo")))
						{
							Instrument t = b.ins.get(0);
							amount_per_book = (int) (gen.total_buget * bu.ratio / 1000);
							
							t.generate(b, amount_per_book, j);
						}
					}

			OutputManager.getInstance().outputTrades();
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println((float) estimatedTime * 100000 / 1000 / 60 / 60);
		System.out.println("Done");
		Collections.sort(Report.getInstance().liste);
//		Report.report(Report.getInstance().liste);
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