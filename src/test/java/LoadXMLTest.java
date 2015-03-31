import Generals.CustomParsingException;
import Generals.Generals;
import Generals.LoadXML;
import Generals.Referential;
import junit.framework.TestCase;

public class LoadXMLTest extends TestCase
{
	private LoadXML loadxml;
	private Referential ref = Referential.getInstance();
	private Generals gen = Generals.getInstance();

	public LoadXMLTest(String testMethodName)
	{
		super(testMethodName);
	}

	/*
	 * public void test() throws Exception { fail("test à échouer"); }
	 */

	protected void setUp() throws Exception
	{
		super.setUp();
		loadxml = new LoadXML();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
		loadxml = null;
	}

	public void testLoadXML()
	{
		assertNotNull("L'instance est créée", loadxml);
	}

	public void testInit()
	{
		try
		{
			LoadXML.init(ref);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage().toString());
		}
	}

	public void testLoadGeneralSettings() throws CustomParsingException
	{
		LoadXML.setPathGeneralInfs("src/test/java/generalinfs.xml");
		testInit();
		System.out.println("Verification of the values contained in the lists match the generalinfs file.");
		// bu
		for (int indexBu = 0; indexBu < gen.bu.size(); indexBu++)
		{
			System.out.println("Business unit " + (indexBu + 1) + " : " + gen.bu.get(indexBu).name.toString());
			for (int indexInstru = 0; indexInstru < gen.bu.get(indexBu).lins.size(); indexInstru++)
				System.out.println("Instrument " + (indexInstru + 1) + " : " + gen.bu.get(indexBu).lins.get(indexInstru).name.toString());
			for (int indexPort = 0; indexPort < gen.bu.get(indexBu).lpor.size(); indexPort++)
			{
				System.out.println("Portfolio " + (indexPort + 1) + " : " + gen.bu.get(indexBu).lpor.get(indexPort).name.toString());
				for (int indexBook = 0; indexBook < gen.bu.get(indexBu).lpor.get(indexPort).lb.size(); indexBook++)
				{
					System.out.println("Book " + (indexBook + 1) + " : " + gen.bu.get(indexBu).lpor.get(indexPort).lb.get(indexBook).name.toString());
					for (int indexIns = 0; indexIns < gen.bu.get(indexBu).lpor.get(indexPort).lb.get(indexBook).ins.size(); indexIns++)
						System.out.println("Filtre InstruBook " + (indexIns + 1) + " : "
								+ gen.bu.get(indexBu).lpor.get(indexPort).lb.get(indexBook).ins.get(indexIns).name);
					for (int indexCur = 0; indexCur < gen.bu.get(indexBu).lpor.get(indexPort).lb.get(indexBook).cur.size(); indexCur++)
						System.out.println("Filtre CurrencyBook " + (indexCur + 1) + " : "
								+ gen.bu.get(indexBu).lpor.get(indexPort).lb.get(indexBook).cur.get(indexCur).code);
				}

			}
		}
	}

	/*public void testLoadTrader() throws Exception
	{
		testInit();
		System.out.println("Check if values of the trader file are recovered.");
		for (int indexCur = 0; indexCur < ref.Currencies.size(); indexCur++)
		{
			System.out.println("Currency " + (indexCur + 1) + " : " + ref.Currencies.get(indexCur).code + " " + ref.Currencies.get(indexCur).name + " "
					+ ref.Currencies.get(indexCur).country);
			for (int indexIns = 0; indexIns < ref.Currencies.get(indexCur).Instruments.size(); indexCur++)
			{
				System.out.println("Instruments " + (indexIns + 1) + ref.Currencies.get(indexCur).Instruments.get(indexIns).name);

			}
		}
	}*/

	/*
	 * public void testLoadTraders() { LoadXML.loadTraders(); }
	 */

}
