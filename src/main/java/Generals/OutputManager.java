package Generals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class OutputManager
{
	static private OutputManager instance = new OutputManager();
	static private PrintWriter	writer;
	final static private String		OUTPUT_PATH = "trades/";
	final static private String		OUTPUT_ENCODING = "UTF-8";

	private OutputManager()
	{
		File check = new File(OUTPUT_PATH + "test");

		if (!check.exists())
			check.getParentFile().mkdirs();
	}

	static public OutputManager getInstance()
	{
		return (instance);
	}
	
	@SuppressWarnings("unused")
	private PrintWriter getWriter(Output output, TradeEvent trade) throws FileNotFoundException, UnsupportedEncodingException
	{
		String path = OUTPUT_PATH + Integer.toString(trade.date) + "-"
					+ trade.id + "."
					+ output.format.toString().toLowerCase();
		
		return (new PrintWriter(path, OUTPUT_ENCODING));
	}
	
	private PrintWriter getWriter(Output output) throws FileNotFoundException, UnsupportedEncodingException
	{
		String date = "";

		if (output.te.size() > 0)
			date = Integer.toString(output.te.get(0).date);

		String path = OUTPUT_PATH + "[" + output.id + "]-" + date + "." + output.format.toString().toLowerCase();
		
		return (new PrintWriter(path, OUTPUT_ENCODING));
	}

	public void outputTrades()
	{
		try
		{
			for (Businessunit bu : Generals.getInstance().bu)
				for (Output output : bu.lop)
				{
					writer = getWriter(output);

					outputByFormat(output);

					writer.close();

					output.te.clear();
				}
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
	}

	private void outputByFormat(Output output)
	{
		if (output.format == Output.OutputFormat.XML)
			for (TradeEvent trade : output.te)
				writeXMLTrade(trade);
		else if (output.format == Output.OutputFormat.CSV)
			manageCSV(output);
	}

	private void manageCSV(Output output)
	{
		for (Instrument ins : output.instruments)
		{
			ArrayList<TradeEvent> te_remaining = new ArrayList<TradeEvent>();
			boolean first = true;

			for (TradeEvent trade : output.te)
			{
				// Only one instrument at a time to keep them together
				if (trade.instrument != ins)
				{
					te_remaining.add(trade);
					continue;
				}

				// First one : display header fields
				if (first)
				{
					first = false;
					writeCSVTrade(trade);
					continue;
				}

				ArrayList<TradeEvent.Node> nodes = trade.getNodes();

				for (TradeEvent.Node node : nodes)
					writer.write(node.value + ",");
				writer.write(System.lineSeparator());
			}

			output.te = te_remaining;
			first = true;
		}
	}

	private void writeXMLTrade(TradeEvent trade)
	{
		writer.write("<trade>" + System.lineSeparator());
		for (TradeEvent.Node node : trade.getNodes())
			writeXMLNode(node);
		writer.write("</trade>" + System.lineSeparator());
	}

	private void writeCSVTrade(TradeEvent trade)
	{
		ArrayList<TradeEvent.Node> nodes = trade.getNodes();

		for (TradeEvent.Node node : nodes)
			writer.write(node.name + ",");
		writer.write(System.lineSeparator());
		for (TradeEvent.Node node : nodes)
			writer.write(node.value + ",");
		writer.write(System.lineSeparator());
	}

	public void outputTrade(Output output, TradeEvent trade)
	{
		try
		{
			writer = getWriter(output, trade);
			writeTrade(output, trade);
			writer.close();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
	}

	private void writeTrade(Output output, TradeEvent trade)
	{
		if (output.format.equals(Output.OutputFormat.XML))
			writeXMLTrade(trade);
		else if (output.format.equals(Output.OutputFormat.CSV))
			writeCSVTrade(trade);
	}

	private void writeXMLNode(TradeEvent.Node node)
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
			writer.write("<" + node.name + ">" + node.value + "</" + node.name
					+ ">" + System.lineSeparator());
	}
}
