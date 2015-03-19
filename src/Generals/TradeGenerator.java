package Generals;

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
				ref.Instruments = new ArrayList<Referential.Instrument>();		
			}
			
			public void execute(Referential ref, Element eElement) {
				Referential.Instrument instrument = ref.new Instrument();
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
				ref.Counterparts = new ArrayList<Referential.Counterpart>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Referential.Counterpart counterpart = ref.new Counterpart();
				counterpart.code =  getContent(eElement, "code");
				counterpart.name =  getContent(eElement, "name");
				ref.Counterparts.add(counterpart);
			}
		});
		
		loadReferential(ref, "depositaries.xml", "depositary", new CbReferential() {
			public void init(Referential ref) {
				ref.Depositaries = new ArrayList<Referential.Depositary>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Referential.Depositary depositary = ref.new Depositary();
				depositary.code =  getContent(eElement, "code");
				depositary.libelle =  getContent(eElement, "libelle");
				ref.Depositaries.add(depositary);
			}
		});
		
		loadReferential(ref, "currencies.xml", "currency", new CbReferential() {
			public void init(Referential ref) {
				ref.Currencies = new ArrayList<Referential.Currency>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Referential.Currency currency = ref.new Currency();
				currency.code =  getContent(eElement, "code");
				currency.country =  getContent(eElement, "country");
				currency.name =  getContent(eElement, "name");
				ref.Currencies.add(currency);
			}
		});
		
		loadReferential(ref, "portfolios.xml", "portfolio", new CbReferential() {
			public void init(Referential ref) {
				ref.Portfolios = new ArrayList<Referential.Portfolio>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Referential.Portfolio portfolio = ref.new Portfolio();
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
		Generals gen = loadGeneralSettings();
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
	
  static public Generals loadGeneralSettings()
  {
	  try
	  {
		  ArrayList<Instrument>	instruments = new ArrayList<Instrument>();
		  ArrayList<Portfolio>	portfolios = new ArrayList<Portfolio>();
		  ArrayList<Businessunit>	businessunits = new ArrayList<Businessunit>();
		  ArrayList<Book>	books = new ArrayList<Book>();

		  File fXmlFile = new File("params/generalinfs.xml");
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  Document doc = dBuilder.parse(fXmlFile);
		  doc.getDocumentElement().normalize();

		  // Get general setting bank
		  NodeList settings = doc.getElementsByTagName("general_settings");
		  Element esetting = (Element) settings.item(0);

		  // Get businessunits
		  NodeList nbusinessunits = doc.getElementsByTagName("businessunit");
		  for (int ibu = 0; ibu < nbusinessunits.getLength(); ibu++)
		  {
			  Element ebusinessunit = (Element) nbusinessunits.item(ibu);

			  if (((Node) ebusinessunit).getNodeType() != Node.ELEMENT_NODE)
				  continue;

			  System.out.println("-- BusinessUnit : " + ebusinessunit.getAttribute("name"));

			  // Get instruments
			  NodeList ninstruments = ebusinessunit.getElementsByTagName("instrument");
			  for (int iins = 0; iins < ninstruments.getLength(); iins++)
			  {
				  Element eins = (Element) ninstruments.item(iins);
				  if (((Node) eins).getNodeType() != Node.ELEMENT_NODE)
					  continue; 

				  // Manage all instrument (Only equity for now)
				  if (eins.getAttribute("name") != "equity")
					  continue;

				  Equity equity = new Equity();
				  equity.name = "equity";
				  equity.owncountry = Integer.parseInt(getContent(eins, "owncountry"));
				  equity.Partsell = Integer.parseInt(getContent(eins, "partsell"));
				  equity.is_stp = Boolean.parseBoolean(getContent(eins, "isstp"));
				  equity.repartition_tolerance = Integer.parseInt(getContent(eins, "tolerancerep"));
				  equity.volumetry = Integer.parseInt(getContent(eins, "volumetry"));
				  equity.volumetry_tolerance = Integer.parseInt(getContent(eins, "volumetrytolerance"));
				  instruments.add(equity);

				  System.out.println("Instrument >> " + eins.getAttribute("name"));
			  }

			  // Get portfolios
			  NodeList nportfolios = ebusinessunit.getElementsByTagName("portfolio");
			  for (int ipf = 0; ipf < nportfolios.getLength(); ipf++)
			  {
				  Element eportfolio = (Element) nportfolios.item(ipf);
				  if (((Node) eportfolio).getNodeType() != Node.ELEMENT_NODE)
					  continue;

				  System.out.println("Portfolio >> " + eportfolio.getAttribute("name"));

				  // Get books
				  NodeList nbooks = eportfolio.getElementsByTagName("book");
				  for (int ibook = 0; ibook < nbooks.getLength(); ibook++)
				  {
					  Element ebook = (Element) nbooks.item(ibook);
					  if (((Node) ebook).getNodeType() != Node.ELEMENT_NODE)
						  continue;

					  books.add(new Book(ebook.getAttribute("name"), ebook.getAttribute("currency"), ebook.getAttribute("instrument")));

					  System.out.println("Book >> " + ebook.getAttribute("name"));
				  }
				  
				  portfolios.add(new Portfolio(eportfolio.getAttribute("name"), Integer.parseInt(eportfolio.getAttribute("ratio")), books));
			  }
			  
			  businessunits.add(new Businessunit(ebusinessunit.getAttribute("name"), Integer.parseInt(ebusinessunit.getAttribute("ratio")), instruments, portfolios));
		  }
		  
		  return new Generals(getContent(esetting, "bank_name"), Integer.parseInt(getContent(esetting, "total_budget")), getContent(esetting, "owncountry"), businessunits);
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  return null;
  }
}