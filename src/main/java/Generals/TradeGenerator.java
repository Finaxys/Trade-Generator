package Generals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class TradeGenerator
{
	private String 	name;
	private int		amount;

	public abstract void init();
	public abstract void generate(Book b, int amount, Date date);

	static public Output getOutputFromTrade(TradeEvent trade)
	{
		for (Output op : trade.getBook().getPortFolios().getBu().getOutputs())
			if (op.getInstruments().contains(trade.getInstrument()))
				return (op);

<<<<<<< HEAD
		return (null);
	}
=======
		int simulate_days = Integer.parseInt(args[0]);
		int amount_per_book;
		int j;
		int dis;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		for (j = 0; j <= simulate_days; j++)
		{
			for (Businessunit bu : gen.bu)
			{
				dis = bu.getMainInstrumentCount();

				for (Portfolio port : bu.getPortfolios())
				{

					for (Book b : port.getLb())
					{
						if (b.getInstruments().size() > 0 && (b.getInstruments().contains(bu.getMainInstrument())))
						{
							amount_per_book = (int) (gen.budget * bu.getRatio() / (dis * 1000));
							for (int i = 0; i < b.getInstruments().size(); i++)
							{
								Instrument t = b.getInstruments().get(i);

								if (!(t.equals(bu.getMainInstrument())))
								{
									t.generate(b, t.getMontant(), calendar.getTime());
								}
								{
									t.generate(b, amount_per_book, calendar.getTime());
								}

							}
						}
						else
						{
							if (j == 0)
								System.out.println("book mal rangé: " + b.getName());
						}
					}

				}
			}

			// List of instrument available
			List<Instrument> instruments = new ArrayList<Instrument>();
			for (Businessunit bu : gen.bu)
			{	
				// Init instruments available
				instruments.addAll(bu.getInstruments());
				
				// Init Instrument Generator
				for (Instrument ins : instruments)
					ins.init();
				
				// While there is still an instrument with a volumetry > 0
				while (instruments.size() > 0)
				{
					// Get random instrument & currency
					
					// Find appropriate book
					
					// We found one -> generate trade
					
					// Set trade attributes

					// Instrument is full -> remove from list
					
					
//					Instrument insrandom=getrandomins();
//					Currency currandom=getrandomdevise();
//					Book book= match(bu,insrandom,currandom);
//
//					insrandom.generate(book, bu.getMainInstrument().getMontant(), instrumentGeneratorinsrandom));
				}
			}


			
			
			
			
			calendar.add(Calendar.DATE, 1);
			OutputManager.getInstance().outputTrades();
		}
>>>>>>> 1a2037b17e89cbad7222b7b0a2d8641f4517c40b

	static int cnt = 0;

	public void tradeGenerated(TradeEvent trade)
	{
		Output output = getOutputFromTrade(trade);
		Report.add(trade);
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