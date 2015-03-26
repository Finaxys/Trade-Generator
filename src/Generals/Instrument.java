package Generals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public abstract class Instrument
{
	String 				name;

	public void  generate (Book b, int montant,int date){}
	
	static public Output getOutputFromTrade(TradeEvent trade)
	{
		for (Output op : trade.book.pt.bu.lop)
			if (op.instruments.contains(trade.instrument))
				return (op);

		return (null);
	}
	
	public void tradeGenerated(TradeEvent trade)
	{
		Output	output = getOutputFromTrade(trade);

		if (output.isStp)
			OutputManager.getInstance().outputTrade(output, trade);
		else
			output.addTradeEvent(trade);
	}

	public static <T extends Enum<T>> List<T> tableaubin(int size, int ratio, Class<T> e)
	{   
		List<T> TrueArray = new ArrayList<T>(size);
		int j;
		int national = (ratio * size) / 100;
		T tp1=e.getEnumConstants()[0];
		T tp2=e.getEnumConstants()[1];
		
		for (int i = 0; i < size; i++)
			TrueArray.set(i,tp1);
		
			
		for (j = 0; j <= national; j++)
			TrueArray.set(j, tp2);

		Collections.shuffle(TrueArray);

		return (TrueArray);
	}
	
	public static List<Integer> Sparsemoney (int volumetry,int montant)
	
	{
		List<Integer> T= new ArrayList<Integer>(volumetry);
		Random	random=new Random();
		int somme=0;
		int volumetryorder=(int)0.1*volumetry;
		int randint;
		
		for(int i=0;i<=T.size();i++)
		{
			randint = random.nextInt(volumetryorder);
            T.set(i, randint);		          
		}
		for(int i=0;i<=T.size();i++)				
			T.set(i,T.get(i)*montant/somme);	          
		
		return T;
	}
	@Override
	public boolean equals(Object ins)
	{
		if (!(ins instanceof Instrument))
			return (false);

		return (((Instrument)ins).name.equalsIgnoreCase(this.name));
	}

}