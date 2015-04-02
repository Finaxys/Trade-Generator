package Generals;

import java.util.List;

public class Businessunit
{
	private String name;
	private int ratio;
	private List<Instrument> listInstrument;
	private List<Portfolio> listPortfolio;
	private List<Output> listOutput;
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

	public List<Instrument> getListInstrument()
	{
		return listInstrument;
	}

	public void setListInstrument(List<Instrument> listInstrument)
	{
		this.listInstrument = listInstrument;
	}

	public List<Portfolio> getListPortfolio()
	{
		return listPortfolio;
	}

	public void setListPortfolio(List<Portfolio> listPortfolio)
	{
		this.listPortfolio = listPortfolio;
	}

	public List<Output> getListOutput()
	{
		return listOutput;
	}

	public void setListOutput(List<Output> listOutput)
	{
		this.listOutput = listOutput;
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
		for (Instrument ins : listInstrument)
		{
			if (ins.name.equalsIgnoreCase(ins_str))
				return (ins);
		}
		return (null);
	}

	public Businessunit(String name, int ratio, Instrument mainInstrument, List<Output> listOutput,
			List<Instrument> listInstrument, List<Portfolio> listPortfolio)
	{
		super();
		this.mainInstrument = mainInstrument;
		this.name = name;
		this.ratio = ratio;
		this.listInstrument = listInstrument;
		this.listPortfolio = listPortfolio;
		this.listOutput = listOutput;
	}
	
	public int getMainInstrumentCount()
	{
		int	cnt = 0;
		
		for (Portfolio port : listPortfolio)
			for (Book book : port.lb)
				if (book.ins.contains(mainInstrument))
					++cnt;

		return (cnt);
	}
}
