package Generals;

import java.util.List;

public class Portfolio
{
	public String name;
	public List<Book> lb;
	public Businessunit bu;

	public Portfolio(String nom, List<Book> lb)
	{
		this.name = nom;
		this.lb = lb;
	}
}
