package Generals;

import java.util.ArrayList;

	public abstract class Instrument
	{
		String name;
		public void  generate (int montant,int date){}
		
		@Override
		public boolean equals(Object ins)
		{
			if (!(ins instanceof Instrument))
				return (false);

			return (((Instrument)ins).name.equalsIgnoreCase(this.name));
		}
	
	}
