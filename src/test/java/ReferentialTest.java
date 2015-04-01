import java.util.ArrayList;

import Generals.Referential;
import Generals.Referential.Trader;
import Generals.TradeGenerator;
import junit.framework.TestCase;

public class ReferentialTest extends TestCase
{
	private static Referential ref = Referential.getInstance();
	
	
	public static void testGetRandomElement()
	{
		ArrayList<Integer>l = new ArrayList<Integer>();
		l.add(11);
		l.add(12);
		l.add(13);
		int result = Referential.getRandomElement(l);
		System.out.println(result);
	}
	
	public static void testGetTrader()
	{
		String instrument = new String("equity");
		String country = new String("CN");

		TradeGenerator.main(new String[] {"10"});
		Trader trader = Referential.getTrader(ref, country, instrument);
		if (trader == null)
			System.out.println("trader is null");
		System.out.println("name trader : " + trader.name);
	}
}
