package Generals;

import java.util.ArrayList;

public class Generals
{

	public String	bank_name;
	public int		budget;
	public String	owncountry;
	public ArrayList<Businessunit> bu;

	private Generals()
	{
		super();
	}

	private static Generals INSTANCE = new Generals();

	public static Generals getInstance()
	{
		return INSTANCE;
	}

	public void init(String bankname, int budget,
			String owncountry, ArrayList<Businessunit> bu)
	{
		this.bank_name = bankname;
		this.budget = budget;
		this.owncountry = owncountry;
		this.bu = bu;
	}
}