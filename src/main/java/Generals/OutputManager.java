package Generals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OutputManager
{
	static private OutputManager	instance = new OutputManager();
	static private PrintWriter		writer;
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
	
	private PrintWriter getWriter(Output output, TradeEvent trade) throws FileNotFoundException, UnsupportedEncodingException
	{
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");

		String path = OUTPUT_PATH + "stp" + trade.getId() + "-" + formater.format(trade.getDate()) +
					"." + output.getFormat().toString().toLowerCase();

		
		return (new PrintWriter(path, OUTPUT_ENCODING));
	}
	
	private PrintWriter getWriter(Output output) throws FileNotFoundException, UnsupportedEncodingException
	{
		String date = "";
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");

		if (output.getTrades().size() > 0)
			date = formater.format(output.getTrades().get(0).getDate());

		String path = OUTPUT_PATH + "batch" + output.getId() + "-" + date + "." + output.getFormat().toString().toLowerCase();
		
		return (new PrintWriter(path, OUTPUT_ENCODING));
	}

	public void outputTrades()
	{
		try
		{
			for (Businessunit bu : Generals.getInstance().bu)
				for (Output output : bu.getOutputs())
				{
					writer = getWriter(output);

					outputByFormat(output);

					writer.close();

					output.getTrades().clear();
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
		if (output.getFormat() == Output.OutputFormat.XML)
			for (TradeEvent trade : output.getTrades())
				writeXMLTrade(trade);
		else if (output.getFormat() == Output.OutputFormat.CSV)
			manageCSV(output);
	}

	private void manageCSV(Output output)
	{
		List<Class<? extends TradeEvent>>	trade_class = new ArrayList<Class<? extends TradeEvent>>();
		List<String>						header = new ArrayList<String>();

		// Get header
		for (TradeEvent trade : output.getTrades())
		{
			if (!trade_class.contains(trade.getClass()))
			{
				trade_class.add(trade.getClass());

				for (TradeEvent.Node node : trade.getNodes())
					if (!header.contains(node.name))
						header.add(node.name);
			}
		}
		
		for (String str : header)
			System.out.println(str);

		System.out.println("---");

		for (Instrument ins : output.getInstruments())
		{
			List<TradeEvent> te_remaining = new ArrayList<TradeEvent>();
			boolean first = true;

			for (TradeEvent trade : output.getTrades())
			{
				// Only one instrument at a time to keep them together
				if (trade.getInstrument() != ins)
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

				List<TradeEvent.Node> nodes = trade.getNodes();

				for (TradeEvent.Node node : nodes)
					writer.write(node.value + ",");
				writer.write(System.lineSeparator());
			}

			output.setTrades(te_remaining);
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
		List<TradeEvent.Node> nodes = trade.getNodes();

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
		if (output.getFormat().equals(Output.OutputFormat.XML))
			writeXMLTrade(trade);
		else if (output.getFormat().equals(Output.OutputFormat.CSV))
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
