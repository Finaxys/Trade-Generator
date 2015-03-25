package Generals;

import java.util.ArrayList;
import java.util.Iterator;

public class Businessunit
{
	public String name;
	public int ratio;
	public ArrayList<Instrument> lins;
	public ArrayList<Portfolio> lpor;
	public ArrayList<Output>	lop;
	public ArrayList<TradeEvent> te;
	
	public void addTradeEvent(TradeEvent tn){ te.add(tn);}

	public Instrument getInstrument(String ins_str)
	{    
		for (Instrument ins : lins)
		{
			if (ins.name.equalsIgnoreCase(ins_str))
				return (ins);
		}
		System.out.println("Seek ins " + ins_str);
		return (null);
	}

	public Businessunit(String name, int ratio, ArrayList<Output> lop, ArrayList<Instrument> lins, ArrayList<Portfolio> lpor)
	{
		super();
		this.te = new ArrayList<TradeEvent>();
		this.name = name;
		this.ratio = ratio;
		this.lins = lins;
		this.lpor = lpor;
		this.lop = lop;
	}
}

