package Generals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class TradeGenerator
{
	protected String 	name;
	protected int		amount;
	protected int 		volumetry;
	protected int 		rounded_volumetry;
	protected int		trade_generated = 0;

	public int getVolumetry() {
		return rounded_volumetry;
	}

	public int getTradeGenerated() {
		return trade_generated;
	}

	public abstract void init(int amount_day);
	public TradeEvent generate(Book b, int amount, Date date)
	{
		++trade_generated;

		return null;
	}
	
	static public List<Output> getOutputsFromTrade(TradeEvent trade)
	{
		List<Output>	outputs = new ArrayList<Output>();

		for (Output op : trade.getBook().getPortFolios().getBu().getOutputs())
			if (op.getGenerators().contains(trade.getInstrument()))
				outputs.add(op);

		return (outputs);
	}
	static int cnt = 0;

	static public void tradeGenerated(TradeEvent trade)
	{
		Report.add(trade);

		List<Output> outputs = getOutputsFromTrade(trade);
		for (Output output : outputs)
			if (output.isStp())
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
		
		return T;
	}

	@Override
	public boolean equals(Object ins)
	{
		if (!(ins instanceof TradeGenerator))
			return (false);

		return (((TradeGenerator) ins).getName().equalsIgnoreCase(this.getName()));
	}

	public int getMontant() {
		return amount;
	}

	public void setMontant(int montant) {
		this.amount = montant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}