package Generals;

import java.util.List;

public class Businessunit
{
	private String name;
	private int ratio;
	private List<TradeGenerator> generators;
	private List<Portfolio> portfolios;
	private List<Output> outputs;
	private TradeGenerator mainInstrument;

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

	public List<TradeGenerator> getGenerators()
	{
		return generators;
	}

	public void setGenerators(List<TradeGenerator> generators)
	{
		this.generators = generators;
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

	public TradeGenerator getMainInstrument()
	{
		return mainInstrument;
	}

	public void setMainInstrument(TradeGenerator mainInstrument)
	{
		this.mainInstrument = mainInstrument;
	}

	public TradeGenerator getGenerator(String gen_str)
	{
		for (TradeGenerator gen : generators)
		{
			if (gen.getName().equalsIgnoreCase(gen_str))
				return (gen);
		}
		return (null);
	}

	public Businessunit(String name, int ratio, TradeGenerator main_instrument, List<Output> outputs,
			List<TradeGenerator> instruments, List<Portfolio> portfolios)
	{
		super();
		this.mainInstrument = main_instrument;
		this.name = name;
		this.ratio = ratio;
		this.generators = instruments;
		this.portfolios = portfolios;
		this.outputs = outputs;
	}
	
	public int getMainInstrumentCount()
	{
		int	cnt = 0;
		
		for (Portfolio port : portfolios)
			for (Book book : port.getLb())
				if (book.getGenerators().contains(mainInstrument))
					++cnt;

		return (cnt);
	}
}
