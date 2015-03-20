package Generals;


public class TradeGenerator
{	
	public static void main(String[] args)
	{
		Referential ref = new Referential();	
		Generals gen = null;

		gen = LoadXML.init(ref);

		int simulate_days = Integer.parseInt(args[0]);
		int amount_per_book;
		int j;

		for (j = 0; j <= simulate_days; j++)
			for (Businessunit bu : gen.bu)
				for (Portfolio port :  bu.lpor)
					for (Book b : port.lb)
					{
						if (b.ins.equalsIgnoreCase("equity"))
						{
							Instrument t = bu.getInstrument(b.ins);
							amount_per_book = (int) (gen.total_buget * bu.ratio * port.ratio * b.ratio / 1000000);

							t.generate(gen, ref, amount_per_book, j);
						}  
					}
	}	
}