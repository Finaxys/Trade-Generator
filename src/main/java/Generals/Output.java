package Generals;

import java.util.ArrayList;

public class Output
{
	public enum OutputFormat
	{
		CSV, JSON, XML
	}

	public enum Layer
	{
		FS, MQ, HBASE
	}

	public OutputFormat format;
	public String path;
	public ArrayList<Instrument> instruments;
	public Boolean isStp;
	public Layer layer;
	public ArrayList<TradeEvent> te = new ArrayList<TradeEvent>();
	public int	id;
	static int	counter = 0;

	public void addTradeEvent(TradeEvent tn)
	{   Report.add(tn);
		te.add(tn);
	}

	Output(String f, String path, ArrayList<Instrument> ins, Boolean stp,
			String lay)
	{
		id = counter++;
		format = OutputFormat.valueOf(f.toUpperCase());
		this.path = path;
		instruments = ins;
		isStp = stp;
		layer = Layer.valueOf(lay);
	}
}
