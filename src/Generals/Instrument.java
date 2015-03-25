package Generals;


public abstract class Instrument
{
	public Output		output;
	String 				name;

	public void  generate (Book b, int montant,int date){}

	@Override
	public boolean equals(Object ins)
	{
		if (!(ins instanceof Instrument))
			return (false);

		return (((Instrument)ins).name.equalsIgnoreCase(this.name));
	}

}