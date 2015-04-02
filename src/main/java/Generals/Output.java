package Generals;

import java.util.ArrayList;
import java.util.List;

public class Output
{
	private OutputFormat			format;
	private String					path;
	private List<Instrument>		instruments;
	private Boolean					isStp;
	private Layer 					layer;
	private List<TradeEvent>		te;
	private int						id;
	static 	int						counter = 0;

	public void addTradeEvent(TradeEvent tn)
	{   
		te.add(tn);
	}

	Output(String format, String path, List<Instrument> ins, Boolean stp, String lay)
	{
		this.te = new ArrayList<TradeEvent>();
		this.id = counter++;
		this.format = OutputFormat.valueOf(format.toUpperCase());
		this.path = path;
		this.instruments = ins;
		this.isStp = stp;
		this.layer = Layer.valueOf(lay);
	}

	public OutputFormat getFormat() {
		return format;
	}

	public String getPath() {
		return path;
	}

	public List<Instrument> getInstruments() {
		return instruments;
	}

	public Boolean isStp() {
		return isStp;
	}

	public Layer getLayer() {
		return layer;
	}

	public void	setTrades(List<TradeEvent> te) {
		this.te = te;
	}

	public List<TradeEvent> getTrades() {
		return te;
	}

	public int getId() {
		return id;
	}

	public enum OutputFormat
	{
		CSV, JSON, XML
	}

	public enum Layer
	{
		FS, MQ, HBASE
	}
}
