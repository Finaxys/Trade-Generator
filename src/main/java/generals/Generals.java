package generals;

import java.util.List;

import domain.Businessunit;

public class Generals
{

	public String	bank_name;
	public int		budget;
	public String	owncountry;
	public List<Businessunit> bu;

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
			String owncountry, List<Businessunit> bu)
	{
		this.bank_name = bankname;
		this.budget = budget;
		this.owncountry = owncountry;
		this.bu = bu;
	}
}