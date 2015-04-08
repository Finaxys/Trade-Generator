import generals.CustomParsingException;
import generals.Generals;
import generals.LoadXML;
import domain.Referential;
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
			for (int indexInstru = 0; indexInstru < gen.bu.get(indexBu).getGenerators().size(); indexInstru++)
				System.out.println("Instrument " + (indexInstru + 1) + " : " + gen.bu.get(indexBu).getGenerators().get(indexInstru).getName().toString());
			for (int indexPort = 0; indexPort < gen.bu.get(indexBu).getPortfolios().size(); indexPort++)
			{
				System.out.println("Portfolio " + (indexPort + 1) + " : " + gen.bu.get(indexBu).getPortfolios().get(indexPort).getName().toString());
				for (int indexBook = 0; indexBook < gen.bu.get(indexBu).getPortfolios().get(indexPort).getLb().size(); indexBook++)
				{
					System.out.println("Book " + (indexBook + 1) + " : " + gen.bu.get(indexBu).getPortfolios().get(indexPort).getLb().get(indexBook).getName().toString());
					for (int indexIns = 0; indexIns < gen.bu.get(indexBu).getPortfolios().get(indexPort).getLb().get(indexBook).getGenerators().size(); indexIns++)
						System.out.println("Filtre InstruBook " + (indexIns + 1) + " : "
								+ gen.bu.get(indexBu).getPortfolios().get(indexPort).getLb().get(indexBook).getGenerators().get(indexIns).getName());
					for (int indexCur = 0; indexCur < gen.bu.get(indexBu).getPortfolios().get(indexPort).getLb().get(indexBook).getCurrencies().size(); indexCur++)
						System.out.println("Filtre CurrencyBook " + (indexCur + 1) + " : "
								+ gen.bu.get(indexBu).getPortfolios().get(indexPort).getLb().get(indexBook).getCurrencies().get(indexCur).code);
				}

			}
		}
	}

	public void testLoadTrader() throws Exception
	{

		testInit();
		System.out.println("\nCheck if values of the trader file are recovered.");
		System.out.println("Size currencies = " + ref.currencies.size());
		for (int indexCur = 0; indexCur < ref.currencyTraders.size(); indexCur++)
		{
			System.out.println("Currency " + (indexCur + 1) + " : " + ref.currencyTraders.get(indexCur).code);
			System.out.println("Size instru = " + ref.currencyTraders.get(indexCur).Instruments.size());
			for (int indexIns = 0; indexIns < ref.currencyTraders.get(indexCur).Instruments.size(); indexIns++)
			{
				System.out.println("Instrument " + (indexIns + 1) + " " + ref.currencyTraders.get(indexCur).Instruments.get(indexIns).name);
				for (int indexTra = 0; indexTra < ref.currencyTraders.get(indexCur).Instruments.get(indexIns).Traders.size(); indexTra++)
					System.out.println("Trader " + (indexTra + 1) + " " + ref.currencyTraders.get(indexCur).Instruments.get(indexIns).Traders.get(indexTra).name
							+ " " + ref.currencyTraders.get(indexCur).Instruments.get(indexIns).Traders.get(indexTra).codeptf);
			}
		}
	}

}
