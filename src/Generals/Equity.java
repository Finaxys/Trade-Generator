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
		
		double randomquantity1, randomquantity2;
		double to2tradeamount = (amountPerDay / roundedVolume);
		int quantity1, quantity2;
		float price1,price2;		 
		
		List<Way> t1;
		List<Way> t2;
		
		//declaration des tirages au sort sous contrainte
		Referential.Depositary d1,d2;
		Referential.Counterpart c1,c2;
		Referential.Trader tr1,tr2;
		Referential.Product pro1,pro2;
		Referential.Currency cur1,cur2;
		Referential.Portfolio port1,port2;

		t1 = tableaubin(roundedVolume, this.ownCountry);

		t2 = tableaubin(roundedVolume, this.partSell);
		
		for (int i = 0; i < roundedVolume; i = i + 2) 
		{

			//sharing of amount per trade
			randToleranceQuantities = (float)Math.random();
			
			//set random price -+3%
			randomquantity1 = 6 * (random.nextDouble() - 0.5);
			randomquantity2 = 6 * (random.nextDouble() - 0.5);
			//tirage au sort sous contrainte

			d1 = ref.getRandomElement(ref.Depositaries);
			d2 = ref.getRandomElement(ref.Depositaries);
			c1 = ref.getRandomElement(ref.Counterparts);
			c2 = ref.getRandomElement(ref.Counterparts);
			tr1 = ref.getRandomElement(ref.Traders);
			tr2 = ref.getRandomElement(ref.Traders);
			pro1 = ref.getRandomElement(ref.Products);
			pro2 = ref.getRandomElement(ref.Products);
			cur1 = ref.getRandomElement(ref.Currencies);
			cur2 = ref.getRandomElement(ref.Currencies);
			port1 = ref.getRandomElement(ref.Portfolios);
			port2 = ref.getRandomElement(ref.Portfolios);

			price1 = pro1.price;
			price2 = pro2.price;
			price1 = (float) (price1 * ( 1 + randomquantity1 / 100));
			price2 = (float) (price2 * (1 + randomquantity2 / 100));
			quantity1 = (int) (randToleranceQuantities * to2tradeamount / price1);
			quantity2 = (int) (randToleranceQuantities * to2tradeamount / price2);

			Tradeequity tq1 = new Tradeequity(book, date, t2.get(i), price1, quantity1, d1, c1, tr1, pro1, cur1, port1);
			Tradeequity tq2 = new Tradeequity(book, date, t2.get(i + 1), price2, quantity2, d2, c2, tr2, pro2, cur2, port2);

			generals.addTradevent(tq1);
			generals.addTradevent(tq2);
		}
	}
}
