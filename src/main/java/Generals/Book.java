package Generals;

import java.util.List;

import Generals.Referential.Currency;

public class Book
{
	private String name;
	private List<Currency> currencies;
	private List<TradeGenerator> generators;
	private Portfolio portFolios;

	public Book(String name, List<Currency> currencies, List<TradeGenerator> generators)
	{
		this.name = name;
		this.currencies = currencies;
		this.generators = generators;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies)
	{
		this.currencies = currencies;
	}

	public List<TradeGenerator> getGenerators()
	{
		return generators;
	}

	public void setGenerators(List<TradeGenerator> generators)
	{
		this.generators = generators;
	}

	public Portfolio getPortFolios()
	{
		return portFolios;
	}

	public void setPortFolios(Portfolio portFolios)
	{
		this.portFolios = portFolios;
	}
	
}