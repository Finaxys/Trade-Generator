package Generals;

import java.util.List;

import Generals.Referential.Currency;

public class Book
{
	private String name;
	private List<Currency> cur;
	private List<Instrument> ins;
	private Portfolio pt;

	public Book(String name, List<Currency> cur, List<Instrument> ins)
	{
		this.setName(name);
		this.cur = cur;
		this.setIns(ins);
	}

	public List<Instrument> getIns() {
		return ins;
	}

	public void setIns(List<Instrument> ins) {
		this.ins = ins;
	}

	public Portfolio getPt() {
		return pt;
	}

	public void setPt(Portfolio pt) {
		this.pt = pt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}