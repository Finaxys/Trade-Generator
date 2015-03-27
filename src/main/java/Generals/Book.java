package Generals;

import java.util.ArrayList;

import Generals.Referential.Currency;

public class Book
{
	public String name;
	public ArrayList<Currency> cur;
	public ArrayList<Instrument> ins;
	public Portfolio pt;

	public Book(String name, ArrayList<Currency> cur, ArrayList<Instrument> ins)
	{
		this.name = name;
		this.cur = cur;
		this.ins = ins;
	}
}