import Generals.Businessunit;
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

	/*
	 * public void testInit() {
	 * 
	 * loadxml.init(ref); }
	 */

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
		System.out.println("Verification of the values contained in the lists.");
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
					System.out.println("Instrument " + (indexBook + 1) + " : " + gen.bu.get(indexBu).lpor.get(indexPort).lb.get(indexBook).name.toString());
			}
		}
		// instruments
		// portfolios
		// outputs
	}

	/*
	 * public void testLoadTraders() { LoadXML.loadTraders(); }
	 */

	/*
	 * public void testGetContent() { assertEquals("Contenu de getcontent",
	 * "pouet", loadxml.getContent(elem, name)()); }
	 * 
	 * public void testSetNom() { personne.setNom("nom2");
	 * assertEquals("Est ce que nom est correct", "nom2", personne.getNom());
	 * try { personne.setNom(null);
	 * fail("IllegalArgumentException non levée avec la propriété nom à null");
	 * } catch (IllegalArgumentException iae) { // ignorer l'exception puisque
	 * le test est OK (l'exception est levée) } }
	 * 
	 * public void testGetPrenom() {
	 * assertEquals("Est ce que prenom est correct", "prenom1",
	 * personne.getPrenom()); }
	 * 
	 * public void testSetPrenom() { personne.setPrenom("prenom2");
	 * assertEquals("Est ce que prenom est correct", "prenom2",
	 * personne.getPrenom()); } }
	 */
}
// chargement des settings
// création de trades
// fichier corrompu
// pas de fichier
