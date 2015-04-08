package domain;

import java.util.List;

public class Portfolio
{
	private String name;
	private List<Book> lb;
	private Businessunit bu;

	public Portfolio(String nom, List<Book> lb)
	{
		this.setName(nom);
		this.setLb(lb);
	}

	public List<Book> getLb() {
		return lb;
	}

	public void setLb(List<Book> lb) {
		this.lb = lb;
	}

	public Businessunit getBu() {
		return bu;
	}

	public void setBu(Businessunit bu) {
		this.bu = bu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
