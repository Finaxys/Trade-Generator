package Generals;

import java.util.ArrayList;
import java.util.List;

public class Businessunit
{
	public String name;
	public int ratio;
	public List<Instrument> lins;
	public List<Portfolio> lpor;
	public List<Output> lop;
	public Instrument		main_instrument;

	public Instrument getInstrument(String ins_str)
	{
		for (Instrument ins : lins)
		{
			if (ins.name.equalsIgnoreCase(ins_str))
				return (ins);
		}
		return (null);
	}

	public Businessunit(String name, int ratio, Instrument mainins, List<Output> lop,
			List<Instrument> lins, List<Portfolio> lpor)
	{
		super();
		this.main_instrument = mainins;
		this.name = name;
		this.ratio = ratio;
		this.lins = lins;
		this.lpor = lpor;
		this.lop = lop;
	}
	
	public int getMainInstrumentCount()
	{
		int	cnt = 0;
		
		for (Portfolio port : lpor)
			for (Book book : port.lb)
				if (book.ins.contains(main_instrument))
					++cnt;

		return (cnt);
	}
}
