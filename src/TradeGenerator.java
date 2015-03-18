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


interface CallbackParser {
	public void init(Referential ref);
	public void execute(Referential ref, Element elem);
}

public class TradeGenerator
{	
	public static void main(String[] args)
	{
		Referential ref = new Referential();
	
		
		// Load Static Informations
		
		loadReferential(ref, "instruments.xml", "instrument", new CallbackParser() {
			public void init(Referential ref) {
				ref.Instruments = new ArrayList<Instrument>();		
			}
			
			public void execute(Referential ref, Element eElement) {
				Instrument instrument = new Instrument();
				instrument.isin = eElement.getElementsByTagName("isin").item(0).getTextContent();
				instrument.country = eElement.getElementsByTagName("country").item(0).getTextContent();
				instrument.price = Float.parseFloat(eElement.getElementsByTagName("price").item(0).getTextContent());
				instrument.type = eElement.getElementsByTagName("type").item(0).getTextContent();
				instrument.libelle = eElement.getElementsByTagName("libelle").item(0).getTextContent();
				ref.Instruments.add(instrument);
			}
		});
		
		loadReferential(ref, "counterparts.xml", "counterpart", new CallbackParser() {
			public void init(Referential ref) {
				ref.Counterparts = new ArrayList<Counterpart>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Counterpart counterpart = new Counterpart();
				counterpart.code = eElement.getElementsByTagName("code").item(0).getTextContent();
				counterpart.name = eElement.getElementsByTagName("name").item(0).getTextContent();
				ref.Counterparts.add(counterpart);
			}
		});
		
		loadReferential(ref, "depositaries.xml", "depositary", new CallbackParser() {
			public void init(Referential ref) {
				ref.Depositaries = new ArrayList<Depositary>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Depositary depositary = new Depositary();
				depositary.code = eElement.getElementsByTagName("code").item(0).getTextContent();
				depositary.libelle = eElement.getElementsByTagName("libelle").item(0).getTextContent();
				ref.Depositaries.add(depositary);
			}
		});
		
		loadReferential(ref, "currencies.xml", "currency", new CallbackParser() {
			public void init(Referential ref) {
				ref.Currencies = new ArrayList<Currency>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Currency currency = new Currency();
				currency.code = eElement.getElementsByTagName("code").item(0).getTextContent();
				currency.country = eElement.getElementsByTagName("country").item(0).getTextContent();
				currency.name = eElement.getElementsByTagName("name").item(0).getTextContent();
				ref.Currencies.add(currency);
			}
		});
		
		loadReferential(ref, "portfolios.xml", "portfolio", new CallbackParser() {
			public void init(Referential ref) {
				ref.Portfolios = new ArrayList<Portfolio>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Portfolio portfolio = new Portfolio();
				portfolio.codeptf = eElement.getElementsByTagName("codeptf").item(0).getTextContent();
				portfolio.country = eElement.getElementsByTagName("country").item(0).getTextContent();
				portfolio.type = eElement.getElementsByTagName("type").item(0).getTextContent();
				ref.Portfolios.add(portfolio);
			}
		});
		
		loadReferential(ref, "traders.xml", "trader", new CallbackParser() {
			public void init(Referential ref) {
				ref.Traders = new ArrayList<Trader>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Trader trader = new Trader();
				trader.codeptf = eElement.getElementsByTagName("codeptf").item(0).getTextContent();
				trader.name = eElement.getElementsByTagName("name").item(0).getTextContent();
				ref.Traders.add(trader);
			}
		});

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
	
	static public void loadReferential(Referential ref, String filename, String elem, CallbackParser cb)
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
	  
  static public void loadGeneralSettings()
  {
	  try
	  {
		File fXmlFile = new File("params/generalinfs.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
	 
		doc.getDocumentElement().normalize();
	 
		NodeList businessunits = doc.getElementsByTagName("businessunit");

		for (int temp = 0; temp < businessunits.getLength(); temp++)
		{
			Node businessunit = businessunits.item(temp);
			
			if (businessunit.getNodeType() == Node.ELEMENT_NODE)
			{
				Element ebusinessunit = (Element) businessunit;
				
				System.out.println("-- BusinessUnit : " + ebusinessunit.getAttribute("name"));
				
				NodeList portfolios = ebusinessunit.getElementsByTagName("portfolio");
				for (int temp2 = 0; temp2 < portfolios.getLength(); temp2++)
					System.out.println("Portfolio >> " + ((Element)portfolios.item(temp2)).getAttribute("name"));
				
//				System.out.println(((Element) nNode).getElementsByTagName("businessunit"));
			}
		}
	  } catch (Exception e) {
		e.printStackTrace();
	  }
  }
}