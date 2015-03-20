package Generals;

import java.util.ArrayList;
import java.util.Iterator;

public class Businessunit
{
	public String name;
	public int ratio;
	public ArrayList<Instrument> lins;
	public ArrayList<Portfolio> lpor;

	public Instrument getInstrument(String ins_str)
	{    
		for (Instrument ins : lins)
			if (ins.name.equalsIgnoreCase(ins_str))
				return (ins);

		return (null);
	}

	public Businessunit(String name, int ratio, ArrayList<Instrument> lins, ArrayList<Portfolio> lpor)
	{
		super();
		this.name = name;
		this.ratio = ratio;
		this.lins = lins;
		this.lpor = lpor;
	}
}

