package Generals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Instrument
{
	String name;

	public void generate(Book b, int montant, int date)
	{
	}

	static public Output getOutputFromTrade(TradeEvent trade)
	{
		for (Output op : trade.book.pt.bu.lop)
			if (op.instruments.contains(trade.instrument))
				return (op);

		return (null);
	}

	static int cnt = 0;

	public void tradeGenerated(TradeEvent trade)
	{
		Output output = getOutputFromTrade(trade);

		if (output.isStp)
			OutputManager.getInstance().outputTrade(output, trade);
		else
			output.addTradeEvent(trade);
	}
	

	public static <T extends Enum<T>> List<T> tableaubin(int size, int ratio,
			Class<T> e)
	{
		List<T> TrueArray = new ArrayList<T>();
		int j, i;
		int national = (ratio * (size - 1)) / 100;
		T tp1 = e.getEnumConstants()[0];
		T tp2 = e.getEnumConstants()[1];

		for (i = 0; i < size; i++)
		TrueArray.add(tp1);

		for (j = 0; j < national; j++)
			TrueArray.set(j, tp2);

		Collections.shuffle(TrueArray);

		return (TrueArray);
	}

	public static List<Integer> Sparsemoney(int volumetry, int montant)

	{
		List<Integer> T = new ArrayList<Integer>();
		
		Random random = new Random();
		int somme = 0;
		int volumetryorder = Math.max((int) 0.1 * volumetry,3);
		int randint=0;

		for (int i = 0; i < volumetry; i++)
		{		
			randint = random.nextInt(volumetryorder);
			somme=somme+randint;
			T.add(randint);
		}
		for (int i = 0; i < volumetry; i++)
			T.set(i, T.get(i) * montant / somme);
		
		System.out.println();
		return T;
	}

	@Override
	public boolean equals(Object ins)
	{
		if (!(ins instanceof Instrument))
			return (false);

		return (((Instrument) ins).name.equalsIgnoreCase(this.name));
	}

}