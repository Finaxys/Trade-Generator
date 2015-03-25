package Generals;


public abstract class Instrument
{
	String 				name;

	public void  generate (Book b, int montant,int date){}
	
	public void tradeGenerated(TradeEvent trade)
	{
		Generals.getInstance().te.add(trade);
	}

	@Override
	public boolean equals(Object ins)
	{
		if (!(ins instanceof Instrument))
			return (false);

		return (((Instrument)ins).name.equalsIgnoreCase(this.name));
	}

}