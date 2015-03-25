package Generals;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class OutputManager {
	static private OutputManager 	instance = new OutputManager();
	static private PrintWriter		writer;
	
	private OutputManager() {}

	static public OutputManager getInstance()
	{
		return (instance);
	}
	
	static public void outputTrades()
	{
		ArrayList<TradeEvent>	te_remaining;

		try {

			for (Businessunit bu : Generals.getInstance().bu)
				for (Output output : bu.lop)
				{
					writer = new PrintWriter(output.path, "UTF-8");
					te_remaining = new ArrayList<TradeEvent>();

					for (TradeEvent trade : bu.te)
						if (output.instrument.contains(trade.instrument))
							outputTrade(output, trade);
						else
							te_remaining.add(trade);
					
					bu.te = te_remaining;
					writer.close();
				}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
	
	static public void outputTrade(Output output, TradeEvent trade)
	{
		if (output.format.equals(Output.OutputFormat.XML))
		{
			writer.write("<trade>" + System.lineSeparator());
			for (TradeEvent.Node node : trade.getNodes())
				writeXMLNode(node);
			writer.write("</trade>" + System.lineSeparator());
		}
		else if (output.format.equals(Output.OutputFormat.CSV))
		{

		}
	}
		
	static private void writeXMLNode(TradeEvent.Node node)
	{
		// Check if there is nodes inside node -> recursion
		if (node.nodes != null)
		{
			writer.write("<" + node.name + ">" + System.lineSeparator());
			for (TradeEvent.Node n : node.nodes)
				writeXMLNode(n);
			writer.write("</" + node.name + ">" + System.lineSeparator());
		}
		// Only simple node -> print it
		else
			writer.write("<" + node.name + ">" + node.value + "</" + node.name + ">" + System.lineSeparator());
	}
}
