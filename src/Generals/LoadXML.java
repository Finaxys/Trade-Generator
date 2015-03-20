package Generals;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class LoadXML
{
	interface CbReferential {
		public void init(Referential ref);
		public void execute(Referential ref, Element elem);
	}
	
	public Referential ref;
	
	LoadXML(Referential ref)
	{
		this.ref = ref;
	}
	
	
	public String getContent(Element elem, String name)
	{
		return (elem.getElementsByTagName(name).item(0).getTextContent());
	}
	
	public void loadAll()
	{
		// Load Static Informations
		loadReferential("counterparts.xml", "counterpart", new CbReferential() {
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

		loadReferential("products.xml", "product", new CbReferential() {
			public void init(Referential ref) {
				ref.Products = new ArrayList<Referential.Product>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Referential.Product product = ref.new Product();
				product.name =  getContent(eElement, "type");
				product.type =  getContent(eElement, "type");
				product.isin =  getContent(eElement, "isin");
				product.libelle =  getContent(eElement, "libelle");
				product.country =  getContent(eElement, "country");
				product.price =  Float.parseFloat(getContent(eElement, "price"));
				ref.Products.add(product);
			}
		});
		
		loadReferential("depositaries.xml", "depositary", new CbReferential() {
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
		
		loadReferential("portfolios.xml", "portfolio", new CbReferential() {
			public void init(Referential ref) {
				ref.Portfolios = new ArrayList<Referential.Portfolio>();
			}
			
			public void execute(Referential ref, Element eElement) {
				Referential.Portfolio portfolio = ref.new Portfolio();
				portfolio.codeptf = getContent(eElement, "codeptf");
				portfolio.country = getContent(eElement, "country");
				portfolio.type =  getContent(eElement, "type");
				ref.Portfolios.add(portfolio);
			}
		});
		
		loadGeneralSettings();
		loadTraders();
	}
	
	public void loadReferential(String filename, String elem, CbReferential cb)
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
	
  public Generals loadGeneralSettings()
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
  
  public void loadTraders()
  {
	  try
	  {
		  ArrayList<Referential.Currency> currencies = new ArrayList<Referential.Currency>();
		  ArrayList<Referential.Instrument>	instruments = new ArrayList<Referential.Instrument>();
		  ArrayList<Referential.Trader>	traders = new ArrayList<Referential.Trader>();
		 
		  
		  File fXmlFile = new File("referential/traders.xml");
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  Document doc = dBuilder.parse(fXmlFile);
		  doc.getDocumentElement().normalize();


			  // Get currencies
			  NodeList ncurrencies = doc.getElementsByTagName("currency");
			  for (int icurrencies = 0; icurrencies < ncurrencies.getLength(); icurrencies++)
			  {
				  Element ecurrency = (Element) ncurrencies.item(icurrencies);
				  if (((Node) ecurrency).getNodeType() != Node.ELEMENT_NODE)
					  continue;
				  

				  System.out.println("currency >> " + ecurrency.getAttribute("name"));

				  // Get instruments
				  NodeList ninstruments = ecurrency.getElementsByTagName("instrument");
				  for (int iinstru = 0; iinstru < ninstruments.getLength(); iinstru++)
				  {
					  Element einstrument = (Element) ninstruments.item(iinstru);
					  if (((Node) einstrument).getNodeType() != Node.ELEMENT_NODE)
						  continue;
  

					  System.out.println("Instru >> " + einstrument.getAttribute("name"));
					  
					// Get traders
					  NodeList ntraders = einstrument.getElementsByTagName("trader");
					  for (int itrader = 0; itrader < ntraders.getLength(); itrader++)
					  {
						  Element etrader = (Element) ntraders.item(itrader);
						  if (((Node) etrader).getNodeType() != Node.ELEMENT_NODE)
							  continue;

						  traders.add(new Referential.Trader(etrader.getAttribute("name"), etrader.getAttribute("codeptf")));
						  ref.Traders = traders;
						  
						  System.out.println("Trader >> " + etrader.getAttribute("name"));
					  }
					  
					  instruments.add(ref.new Instrument(einstrument.getAttribute("name")));
					  ref.Instruments = instruments;
				  }
				  
				  currencies.add(ref.new Currency(ecurrency.getAttribute("code"), ecurrency.getAttribute("name"), ecurrency.getAttribute("country")));
				  ref.Currencies = currencies;
			  }
			  
			  
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
  }
  
}

