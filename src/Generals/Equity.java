package Generals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Equity extends Instrument 
{
	public int partSell;
	public int ownCountry;
	public int volumetry;
	public double volumetryTolerance;
	public double repartitionTolerance;
	public Boolean isStp;

	public static List<Way> tableaubin(int size, int ratio)
	{
		List<Way> BuyArray = new ArrayList<Way>(size);
		int j;
		int national = (ratio * size) / 100;
		
		for (int i = 0; i < size; ++i)
			BuyArray.add(Way.SELL);
		
		BuyArray.set(0, Way.SELL);

		for (j = 0; j <= national; j++)
			BuyArray.set(j, Way.BUY);

		Collections.shuffle(BuyArray);

		return (BuyArray);
	}

	@Override
	public void generate(Book book, int amount, int date) 
	{
		Referential ref = Referential.getInstance();
		Generals generals = Generals.getInstance();		
		int amountPerDay = amount;
		double rand1, rand2;
		double toleredVolumetry;
		Random	random = new Random();
		
		rand1 = this.repartitionTolerance * 2 * (random.nextDouble()-0.5)/100;
		rand2 = this.volumetryTolerance * 2 * (random.nextDouble()-0.5)/100;
		amountPerDay += rand1 * amountPerDay;
		
		//calculation of number of trades to distribute per day
		toleredVolumetry = (1-rand2) * volumetry;
		int roundedVolume = (int) toleredVolumetry;
		if (roundedVolume % 2 == 1)
			roundedVolume++;
 
		float randToleranceQuantities;
		
		double randomquantity;
		double to2tradeamount = (amountPerDay / roundedVolume);
		int quantity;
		float price;		 
		
		List<Way> t1,t2;
		
		
		//declaration des tirages au sort sous contrainte
		Referential.Depositary d;
		Referential.Counterpart c;
		Referential.Trader tr;
		Referential.Product pro;
		Referential.Currency cur;
		Referential.Portfolio port;
		List<Referential.Product> Listequity=ref.subList(ref.Products, "type", "Equity");
		t1 = tableaubin(roundedVolume, this.ownCountry);
		t2 = tableaubin(roundedVolume, this.partSell);
		for (int i = 0; i < roundedVolume;i++) 
		{

			//sharing of amount per trade
			randToleranceQuantities = (float)Math.random();
			
			//set random price -+3%
			randomquantity = 6 * (random.nextDouble() - 0.5);
			
			//tirage au sort sous contrainte

			d = ref.getRandomElement(ref.Depositaries);
			c = ref.getRandomElement(ref.Counterparts);
			tr = ref.getRandomElement(ref.Traders);
			
			if (t1.get(i).toString()=="NATIONAL")
			{ 
				cur=ref.subList(ref.Currencies, "Country", generals.owncountry).get(0);
			}
			{
				cur = ref.getRandomElement(ref.exList(ref.Currencies, "Country", generals.owncountry));
			}
		    
			pro = ref.getRandomElement(Listequity);
			port = ref.getRandomElement(ref.Portfolios);
			price = pro.price;
			price = (float) (price * ( 1 + randomquantity/ 100));
			quantity = (int) (randToleranceQuantities * to2tradeamount / price);
			TradeEquity tq1 = new TradeEquity(this, book, date, t2.get(i), price, quantity, d, c, tr, pro, cur, port);
			tradeGenerated(tq1);
			
		}
	}
}
