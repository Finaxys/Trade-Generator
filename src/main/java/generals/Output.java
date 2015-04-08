package generals;

import java.util.ArrayList;
import java.util.List;

import domain.TradeEvent;
import domain.TradeGenerator;

public class Output
{
	private OutputFormat			format;
	private String					path;
	private List<TradeGenerator>	generators;
	private Boolean					isStp;
	private Layer 					layer;
	private List<TradeEvent>		te;
	private int						id;
	static 	int						counter = 0;

	public void addTradeEvent(TradeEvent tn)
	{   
		te.add(tn);
	}

	Output(String format, String path, List<TradeGenerator> gen, Boolean stp, String lay)
	{
		this.te = new ArrayList<TradeEvent>();
		this.id = counter++;
		this.format = OutputFormat.valueOf(format.toUpperCase());
		this.path = path;
		this.generators = gen;
		this.isStp = stp;
		this.layer = Layer.valueOf(lay);
	}

	public OutputFormat getFormat() {
		return format;
	}

	public String getPath() {
		return path;
	}

	public List<TradeGenerator> getGenerators() {
		return generators;
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
