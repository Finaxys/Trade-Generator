package Generals;

import java.util.List;

import Generals.Referential.Currency;

public class Book
{
	public String name;
	public List<Currency> cur;
	public List<Instrument> ins;
	public Portfolio pt;

	public Book(String name, List<Currency> cur, List<Instrument> ins)
	{
		this.name = name;
		this.cur = cur;
		this.ins = ins;
	}
}