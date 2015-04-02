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
			System.out.println("Business unit " + (indexBu + 1) + " : " + gen.bu.get(indexBu).getName().toString());
			for (int indexInstru = 0; indexInstru < gen.bu.get(indexBu).getInstruments().size(); indexInstru++)
				System.out.println("Instrument " + (indexInstru + 1) + " : " + gen.bu.get(indexBu).getInstruments().get(indexInstru).getName().toString());
			for (int indexPort = 0; indexPort < gen.bu.get(indexBu).getPortfolios().size(); indexPort++)
			{
				System.out.println("Portfolio " + (indexPort + 1) + " : " + gen.bu.get(indexBu).getPortfolios().get(indexPort).name.toString());
				for (int indexBook = 0; indexBook < gen.bu.get(indexBu).getPortfolios().get(indexPort).lb.size(); indexBook++)
				{
					System.out.println("Book " + (indexBook + 1) + " : " + gen.bu.get(indexBu).getPortfolios().get(indexPort).lb.get(indexBook).name.toString());
					for (int indexIns = 0; indexIns < gen.bu.get(indexBu).getPortfolios().get(indexPort).lb.get(indexBook).ins.size(); indexIns++)
						System.out.println("Filtre InstruBook " + (indexIns + 1) + " : "
								+ gen.bu.get(indexBu).getPortfolios().get(indexPort).lb.get(indexBook).ins.get(indexIns).getName());
					for (int indexCur = 0; indexCur < gen.bu.get(indexBu).getPortfolios().get(indexPort).lb.get(indexBook).cur.size(); indexCur++)
						System.out.println("Filtre CurrencyBook " + (indexCur + 1) + " : "
								+ gen.bu.get(indexBu).getPortfolios().get(indexPort).lb.get(indexBook).cur.get(indexCur).code);
				}

			}
		}
	}

	public void testLoadTrader() throws Exception
	{

		testInit();
		System.out.println("\nCheck if values of the trader file are recovered.");
		System.out.println("Size currencies = " + ref.currencies.size());
		for (int indexCur = 0; indexCur < ref.currencies.size(); indexCur++)
		{
			System.out.println("Currency " + (indexCur + 1) + " : " + ref.currencies.get(indexCur).code + " " + ref.currencies.get(indexCur).name + " "
					+ ref.currencies.get(indexCur).country);
			System.out.println("Size instru = " + ref.currencies.get(indexCur).Instruments.size());
			for (int indexIns = 0; indexIns < ref.currencies.get(indexCur).Instruments.size(); indexIns++)
			{
				System.out.println("Instrument " + (indexIns + 1) + " " + ref.currencies.get(indexCur).Instruments.get(indexIns).name);
				for (int indexTra = 0; indexTra < ref.currencies.get(indexCur).Instruments.get(indexIns).Traders.size(); indexTra++)
					System.out.println("Trader " + (indexTra + 1) + " " + ref.currencies.get(indexCur).Instruments.get(indexIns).Traders.get(indexTra).name
							+ " " + ref.currencies.get(indexCur).Instruments.get(indexIns).Traders.get(indexTra).codeptf);
			}
		}
	}

}
