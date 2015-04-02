package Generals;

import java.util.List;

public class Businessunit
{
	private String name;
	private int ratio;
	private List<Instrument> instruments;
	private List<Portfolio> portfolios;
	private List<Output> outputs;
	private Instrument mainInstrument;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getRatio()
	{
		return ratio;
	}

	public void setRatio(int ratio)
	{
		this.ratio = ratio;
	}

	public List<Instrument> getInstruments()
	{
		return instruments;
	}

	public void setInstruments(List<Instrument> instruments)
	{
		this.instruments = instruments;
	}

	public List<Portfolio> getPortfolios()
	{
		return portfolios;
	}

	public void setPortfolios(List<Portfolio> portfolios)
	{
		this.portfolios = portfolios;
	}

	public List<Output> getOutputs()
	{
		return outputs;
	}

	public void setOutputs(List<Output> outputs)
	{
		this.outputs = outputs;
	}

	public Instrument getMainInstrument()
	{
		return mainInstrument;
	}

	public void setMainInstrument(Instrument mainInstrument)
	{
		this.mainInstrument = mainInstrument;
	}

	public Instrument getInstrument(String ins_str)
	{
		for (Instrument ins : instruments)
		{
			if (ins.name.equalsIgnoreCase(ins_str))
				return (ins);
		}
		return (null);
	}

	public Businessunit(String name, int ratio, Instrument main_instrument, List<Output> outputs,
			List<Instrument> instruments, List<Portfolio> portfolios)
	{
		super();
		this.mainInstrument = main_instrument;
		this.name = name;
		this.ratio = ratio;
		this.instruments = instruments;
		this.portfolios = portfolios;
		this.outputs = outputs;
	}
	
	public int getMainInstrumentCount()
	{
		int	cnt = 0;
		
		for (Portfolio port : portfolios)
			for (Book book : port.lb)
				if (book.ins.contains(mainInstrument))
					++cnt;

		return (cnt);
	}
}
