import generals.Generator;

import java.util.ArrayList;

import domain.Referential;
import domain.Referential.Trader;
import junit.framework.TestCase;

public class ReferentialTest extends TestCase {
    private static Referential ref = Referential.getInstance();

    public static void testGetRandomElement() {
        ArrayList<Integer> l = new ArrayList<Integer>();
        l.add(11);
        l.add(12);
        l.add(13);
        int result = ref.getRandomElement(l);
        System.out.println(result);
    }

    public static void testGetTrader() {
        String instrument = new String("equity");
        String code = new String("EUR");

        Generator.main(new String[] { "10" });
        Trader trader = ref.getTrader(ref, code, instrument);
        if (trader == null)
            System.out.println("trader is null");
        else {
            System.out.println("name trader : " + trader.getName());
            System.out.println("codeptf trader : " + trader.getCode());
        }

    }
}
