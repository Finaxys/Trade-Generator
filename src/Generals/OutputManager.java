package Generals;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class OutputManager {
	static private OutputManager instance = new OutputManager();
	
	private OutputManager() {}

	static public OutputManager getInstance()
	{
		return (instance);
	}
	
	static public void outputTrades()
	{
		PrintWriter		writer;

		try {

			for (Businessunit bu : Generals.getInstance().bu)
				for (Output output : bu.lop)
				{
					writer = new PrintWriter(output.path, "UTF-8");
//
//					for (TradeEvent trade : bu.te)
//					{
//						if (trade.)
//							outputTrade(trade);
//					}
				}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
	
	static public void outputTrade(TradeEvent trade)
	{
					
//					writer.write("<traders>" + System.lineSeparator());
//					for (TradeEvent trade : gen.te)
//					{
//					ArrayList<TradeEvent.Node>	nodes = trade.getNodes();
//					writer.write("<trade>" + System.lineSeparator());
//					for (TradeEvent.Node node : nodes)
//					writeXMLNode(writer, node);
//					writer.write("</trade>" + System.lineSeparator());
//					}
		
//					writer.write("</traders>");
//					writer.close();
	}
}
