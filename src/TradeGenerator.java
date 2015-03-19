import java.awt.List;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


interface CbReferential {
	public void init(Referential ref);
	public void execute(Referential ref, Element elem);
}

// TO REMOVE
class General{}

interface CbGeneral {
	public void init(General ref);
	public void execute(General ref, Element elem);
}

public class TradeGenerator
{	
	public static void main(String[] args)
	{
		Referential ref = new Referential();
	
		
		// Load Static Informations
		
		loadReferential(ref, "instruments.xml", "instrument", new CbReferential() {
			public void init(Referential ref) {
				ref.Instruments = new ArrayList<Instrument>();		
			}
			
			public void execute(Referential ref, Element eElement) {
				Instrument instrument = new Instrument();
				instrument.isin =  getContent(eElement, "isin");;
				instrument.country =  getContent(eElement, "country");;
				instrument.price = Float.parseFloat(getContent(eElement, "price"));
				instrument.type =  getContent(eElement, "type");
				instrument.libelle =  getContent(eElement, "libelle");
				ref.Instruments.add(instrument);
			}
		});
		
		loadReferential(ref, "counterparts.xml", "counterpart", new CbReferential() {
			public void init(Referential ref) {
				ref.Counterparts = new ArrayList<Counterpart>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Counterpart counterpart = new Counterpart();
				counterpart.code =  getContent(eElement, "code");
				counterpart.name =  getContent(eElement, "name");
				ref.Counterparts.add(counterpart);
			}
		});
		
		loadReferential(ref, "depositaries.xml", "depositary", new CbReferential() {
			public void init(Referential ref) {
				ref.Depositaries = new ArrayList<Depositary>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Depositary depositary = new Depositary();
				depositary.code =  getContent(eElement, "code");
				depositary.libelle =  getContent(eElement, "libelle");
				ref.Depositaries.add(depositary);
			}
		});
		
		loadReferential(ref, "currencies.xml", "currency", new CbReferential() {
			public void init(Referential ref) {
				ref.Currencies = new ArrayList<Currency>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Currency currency = new Currency();
				currency.code =  getContent(eElement, "code");
				currency.country =  getContent(eElement, "country");
				currency.name =  getContent(eElement, "name");
				ref.Currencies.add(currency);
			}
		});
		
		loadReferential(ref, "portfolios.xml", "portfolio", new CbReferential() {
			public void init(Referential ref) {
				ref.Portfolios = new ArrayList<Portfolio>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Portfolio portfolio = new Portfolio();
				portfolio.codeptf =  getContent(eElement, "codeptf");
				portfolio.country = getContent(eElement, "country");
				portfolio.type =  getContent(eElement, "type");
				ref.Portfolios.add(portfolio);
			}
		});
		
//		loadReferential(ref, "traders.xml", "trader", new CbReferential() {
//			public void init(Referential ref) {
//				ref.Traders = new ArrayList<Trader>();
//			}
//			
//			public void execute(Referential ref, Element eElement) {
//				Trader trader = new Trader();
//				trader.codeptf = getContent(eElement, "codeptf");
//				trader.name =  getContent(eElement, "name");
//				ref.Traders.add(trader);
//			}
//		});

//		Instrument instrument = ref.getRandomElement(ref.Instruments);
//		System.out.println("-- Instrument");
//		System.out.println("ISIN : " +  instrument.isin);
//		System.out.println("LIBELLE : " + instrument.libelle);
//		System.out.println("PAYS : " + instrument.country);
//		System.out.println("PRIX : " + instrument.price);
//		System.out.println("TYPE : " + instrument.type);
		
		// Load General Settings
		loadGeneralSettings();
	}
	
	static public String getContent(Element elem, String name)
	{
		return (elem.getElementsByTagName(name).item(0).getTextContent());
	}
	
	static public void loadReferential(Referential ref, String filename, String elem, CbReferential cb)
	{
	  try
	  {
		File fXmlFile = new File("referential/" + filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
	 
		doc.getDocumentElement().normalize();
		
		cb.init(ref);
	 
		NodeList nList = doc.getElementsByTagName(elem);
	 
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) nNode;
				
				cb.execute(ref, eElement);
			}
		}
	  } catch (Exception e) {
		e.printStackTrace();
	  }
	}
	
	// TODO CHECK
  static public void loadGeneralSettings()
  {
	  try
	  {
		File fXmlFile = new File("params/generalinfs.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		// Get general setting bank
		NodeList settings = doc.getElementsByTagName("general_settings");
		Element setting = (Element) settings.item(0);

		// Get businessunits
		NodeList businessunits = doc.getElementsByTagName("businessunit");
		for (int temp = 0; temp < businessunits.getLength(); temp++)
		{
			Node businessunit = businessunits.item(temp);

			if (businessunit.getNodeType() != Node.ELEMENT_NODE)
				continue;

			Element ebusinessunit = (Element) businessunit;
			
			System.out.println("-- BusinessUnit : " + ebusinessunit.getAttribute("name"));
			
			// Get instruments
			NodeList instruments = ebusinessunit.getElementsByTagName("instrument");
			for (int iins = 0; iins < instruments.getLength(); iins++)
			{
				Element eins = (Element) instruments.item(iins);
				System.out.println("Instrument >> " + eins.getAttribute("name"));
			}
			
			// Get portfolios
			NodeList portfolios = ebusinessunit.getElementsByTagName("portfolio");
			for (int ipf = 0; ipf < portfolios.getLength(); ipf++)
			{
				Node portfolio = portfolios.item(ipf);
				if (portfolio.getNodeType() != Node.ELEMENT_NODE)
					continue;
				Element eportfolio = (Element) portfolio;

				System.out.println("Portfolio >> " + eportfolio.getAttribute("name"));
				
				// Get books
				NodeList books = eportfolio.getElementsByTagName("book");
				for (int ibook = 0; ibook < books.getLength(); ibook++)
				{
					Element ebook = (Element) books.item(ibook);
					System.out.println("Book >> " + ebook.getAttribute("name"));
				}
			}
		}
	  } catch (Exception e) {
		e.printStackTrace();
	  }
  }
}