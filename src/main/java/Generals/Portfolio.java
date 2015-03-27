package Generals;

import java.util.ArrayList;

public class Portfolio
{
	public String name;
	public ArrayList<Book> lb;
	public Businessunit bu;

	public Portfolio(String nom, ArrayList<Book> lb)
	{
		this.name = nom;
		this.lb = lb;
	}
}
