package Generals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;


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
						if (b.ins.size() > 0  && b.ins.get(0).name.equalsIgnoreCase("equity"))
						{
							Instrument t = b.ins.get(0);
							amount_per_book = (int) (gen.total_buget * bu.ratio / 1000);

							t.generate(b, amount_per_book, j);
						}  
					}
		
		
		// Create file BATCH MODE
		PrintWriter writer;
		try {
			writer = new PrintWriter("trades.xml", "UTF-8");

			writer.write("<traders>" + System.lineSeparator());

			for (Tradeevents trade : gen.te)
			{
				ArrayList<Tradeevents.Node>	nodes = trade.getNodes();
				writer.write("<trade>" + System.lineSeparator());
				for (Tradeevents.Node node : nodes)
					writeXMLNode(writer, node);
				writer.write("</trade>" + System.lineSeparator());
			}
			
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
	
	static void writeXMLNode(PrintWriter writer, Tradeevents.Node node)
	{
		// Check if there is nodes inside node -> recursion
		if (node.nodes != null)
		{
			writer.write("<" + node.name + ">" + System.lineSeparator());
			for (Tradeevents.Node n : node.nodes)
				writeXMLNode(writer, n);
			writer.write("</" + node.name + ">" + System.lineSeparator());
		}
		// Only simple node -> print it
		else
			writer.write("<" + node.name + ">" + node.value + "</" + node.name + ">" + System.lineSeparator());
	}
}