package Generals;


public class TradeGenerator
{	
	public static void main(String[] args)
	{
		Referential ref = new Referential();	
		Generals gen = null;
		
		gen = LoadXML.init(ref);
		
        int simulate_days=Integer.parseInt(args[0]);
		
		int j;
		
	for (j=0;j<=simulate_days;j++)
	{	
		for (Businessunit bu : gen.bu)
		{
			for (Portfolio port :  bu.lpor)
			{
				for (Book b : port.lb)
				{
					if (b.name=="equity")
					{
						Instrument t=bu.lins.getInstrument(b.name);
						t.generate(gen, ref,gen.total_buget*bu.ratio*por.ratio*b.ratio, j);
						
					}  
					
					
					{
						
					}
				}
			}
		}
	  }
	}	
}