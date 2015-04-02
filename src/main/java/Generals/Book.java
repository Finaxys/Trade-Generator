package Generals;

import java.util.List;

import Generals.Referential.Currency;

public class Book
{
	private String name;
	private List<Currency> currencies;
	private List<Instrument> instruments;
	private Portfolio portFolios;

	public Book(String name, List<Currency> currencies, List<Instrument> instruments)
	{
		this.name = name;
		this.currencies = currencies;
		this.instruments = instruments;
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

	public List<Instrument> getInstruments()
	{
		return instruments;
	}

	public void setInstruments(List<Instrument> instruments)
	{
		this.instruments = instruments;
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