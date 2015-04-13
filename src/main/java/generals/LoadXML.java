package generals;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import domain.Book;
import domain.Businessunit;
import domain.EquityGenerator;
import domain.LoanDepositGenerator;
import domain.Portfolio;
import domain.Referential;
import domain.TradeGenerator;
import domain.Referential.Currency;

public class LoadXML
{
	private static ApplicationContext context = null;

	interface CbReferential
	{
		public void init(Referential ref);

		public void execute(Referential ref, Element elem) throws CustomParsingException;
	}
	
	private static String pathGeneralInfs;
	
	public static String getPathGeneralInfs()
	{
		return pathGeneralInfs;
	}
	
	public static void setPathGeneralInfs(String pathGeneralsInfs)
	{
		pathGeneralInfs = pathGeneralsInfs;
	}

	private static Referential _ref;

	static public String getContent(Element elem, String name) throws CustomParsingException
	{
		String content;
		try
		{
			content = elem.getElementsByTagName(name).item(0).getTextContent();
		} catch (Exception e)
		{
			throw new CustomParsingException("Name tag invalid: " + name, true);
		}
		return content;
	}

	static public String getOptContent(Element elem, String name, String opt)
	{
		String content;
		try
		{
			content = elem.getElementsByTagName(name).item(0).getTextContent();
		} catch (Exception e)
		{
			return (opt);
		}
		return content;
	}


	
	static public void init(Referential ref) throws CustomParsingException
	{
		_ref = ref;
		try
		{
			context = new ClassPathXmlApplicationContext("file:spring.xml");
		} catch (Exception e)
		{
			System.out.println("Couldn't init spring modules. Using default generators.");
		}

		// Load Static Informations
		loadReferential("counterparts.xml", "counterpart", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.setCounterparts(new ArrayList<Referential.Counterpart>());
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Counterpart counterpart = ref.new Counterpart();
				counterpart.setCode(getContent(eElement, "code"));
				counterpart.setName(getContent(eElement, "name"));
				ref.getCounterparts().add(counterpart);
			}
		});

		loadReferential("products.xml", "product", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.setProducts(new ArrayList<Referential.Product>());
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Product product = ref.new Product();
				product.setName(getContent(eElement, "type"));
				product.setType(getContent(eElement, "type"));
				product.setIsin(getContent(eElement, "isin"));
				product.setLibelle(getContent(eElement, "libelle"));
				product.setCountry(getContent(eElement, "country"));
				product.setPrice(Float.parseFloat(getContent(eElement, "price")));
				ref.getProducts().add(product);
			}
		});

		loadReferential("depositaries.xml", "depositary", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.setDepositaries(new ArrayList<Referential.Depositary>());
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Depositary depositary = ref.new Depositary();
				depositary.setCode(getContent(eElement, "code"));
				depositary.setLibelle(getContent(eElement, "libelle"));
				ref.getDepositaries().add(depositary);
			}
		});

		loadReferential("portfolios.xml", "portfolio", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.setPortfolios(new ArrayList<Referential.Portfolio>());
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Portfolio portfolio = ref.new Portfolio();
				portfolio.setCode(getContent(eElement, "codeptf"));
				portfolio.setCountry(getContent(eElement, "country"));
				portfolio.setType(getContent(eElement, "type"));
				ref.getPortfolios().add(portfolio);
			}
		});
		
		loadReferential("currencies.xml", "currency", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.setCurrencies(new ArrayList<Referential.Currency>());
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Currency currency = ref.new Currency();
				currency.setName(getContent(eElement, "name"));
				currency.setCountry(getContent(eElement, "country"));
				currency.setCode(getContent(eElement, "code"));
				ref.getCurrencies().add(currency);
			}
		});

		try
		{
			loadTraders();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new CustomParsingException("Traders :" + e.getMessage(), true);
		}
		setPathGeneralInfs("params/generalinfs.xml");
		loadGeneralSettings();
	}

	static public void loadReferential(String filename, String elem,
			CbReferential cb) throws CustomParsingException
	{
		try
		{
			File fXmlFile = new File("referential/" + filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			cb.init(_ref);

			NodeList nList = doc.getElementsByTagName(elem);

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;

					cb.execute(_ref, eElement);
				}
			}
		}
		catch (Exception e)
		{
			throw new CustomParsingException("Referential : " + e.getMessage(), true);
		}
	}

	static public void loadGeneralSettings() throws CustomParsingException
	{
		try
		{
			File fXmlFile = new File(getPathGeneralInfs());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			// Get general setting bank
			NodeList settings = doc.getElementsByTagName("generalSettings");
			Element esetting = (Element) settings.item(0);

			// Get businessunits
			List<Businessunit> businessunits = new ArrayList<Businessunit>();
			NodeList nbusinessunits = doc.getElementsByTagName("businessUnit");
			for (int ibu = 0; ibu < nbusinessunits.getLength(); ibu++)
			{
				List<TradeGenerator> generators = new ArrayList<TradeGenerator>();
				List<Portfolio> portfolios = new ArrayList<Portfolio>();
				List<Output> outputs = new ArrayList<Output>();

				Element ebusinessunit = (Element) nbusinessunits.item(ibu);

				if (((Node) ebusinessunit).getNodeType() != Node.ELEMENT_NODE)
					continue;

				// Get instruments
				getInstruments(ebusinessunit, generators);

				// Get portfolios
				getPortfolios(ebusinessunit, portfolios, generators);

				// Get outputs
				getOutputs(ebusinessunit, esetting, outputs, generators);
				
				// Get Main Instrument
				TradeGenerator	main_ins = null;
				for (TradeGenerator gen : generators)
				{
					if (gen.getName().equalsIgnoreCase(ebusinessunit.getAttribute("instrument")))
					{
						main_ins = gen;
						break;
					}
				}
				if (main_ins == null)
					throw new CustomParsingException("Business unit missing main instrument", true);

				businessunits.add(new Businessunit(ebusinessunit.getAttribute("name"), Integer.parseInt(ebusinessunit
						.getAttribute("ratio")), main_ins, outputs, generators,
						portfolios));
			}

			Generals.getInstance().init(Integer.parseInt(getContent(esetting, "numberOfDay")), getContent(esetting, "name"),
					Integer.parseInt(getContent(esetting, "budget")),
					getContent(esetting, "ownCountry"), businessunits);
		}
		catch (Exception e)
		{
			throw new CustomParsingException("Settings : " + e.getMessage(), true);
		}

		// Add cross references
		crossReferences();
	}

	static public void loadTraders() throws Exception
	{
		File fXmlFile = new File("referential/traders.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		List<Referential.CurrencyTrader> currencyTraders = new ArrayList<Referential.CurrencyTrader>();
		
		// Get currencies
		NodeList ncurrencies = doc.getElementsByTagName("currency");
		for (int icurrencies = 0; icurrencies < ncurrencies.getLength(); icurrencies++)
		{
			List<Referential.InstrumentTrader> instruments = new ArrayList<Referential.InstrumentTrader>();

			Element ecurrency = (Element) ncurrencies.item(icurrencies);
			if (((Node) ecurrency).getNodeType() != Node.ELEMENT_NODE)
				continue;

			// Get instruments
			NodeList ninstruments = ecurrency
					.getElementsByTagName("instrument");
			for (int iinstru = 0; iinstru < ninstruments.getLength(); iinstru++)
			{
				List<Referential.Trader> traders = new ArrayList<Referential.Trader>();

				Element einstrument = (Element) ninstruments.item(iinstru);
				if (((Node) einstrument).getNodeType() != Node.ELEMENT_NODE)
					continue;

				// Get traders
				NodeList ntraders = einstrument
						.getElementsByTagName("trader");
				for (int itrader = 0; itrader < ntraders.getLength(); itrader++)
				{
					Element etrader = (Element) ntraders.item(itrader);
					if (((Node) etrader).getNodeType() != Node.ELEMENT_NODE)
						continue;

					traders.add(_ref.new Trader(etrader.getAttribute("name"), etrader.getAttribute("codeptf")));
				}

				Referential.InstrumentTrader instrument = _ref.new InstrumentTrader(einstrument.getAttribute("name"));
				instrument.setTraders(traders);
				instruments.add(instrument);
			}

			Referential.CurrencyTrader currencyTrader = _ref.new CurrencyTrader(ecurrency.getAttribute("code"));
			currencyTrader.setInstruments(instruments);
			currencyTraders.add(currencyTrader);
		}
		_ref.setCurrencyTraders(currencyTraders);
	}

	private static void getFilters(Element ebook,
			List<TradeGenerator> bgenerators,
			List<Currency> bcurrencies, List<TradeGenerator> generators)
	{
		NodeList nfilters = ebook.getElementsByTagName("filter");
		for (int ifilter = 0; ifilter < nfilters.getLength(); ifilter++)
		{
			Element efilter = (Element) nfilters.item(ifilter);

			if (efilter.getAttribute("value").equalsIgnoreCase("all"))
			{
				if (efilter.getAttribute("type").equalsIgnoreCase("instrument"))
					bgenerators.addAll(generators);
				else if (efilter.getAttribute("type").equalsIgnoreCase("currency"))
					bcurrencies.addAll(_ref.getCurrencies());
				continue;
			}

			List<String> vfilter = Arrays.asList(efilter.getAttribute("value")
					.split("\\s*,\\s*"));

			if (efilter.getAttribute("type").equalsIgnoreCase("instrument"))
			{
				for (String str : vfilter)
					for (TradeGenerator gen : generators)
						if (str.equals(gen.getName()))
							bgenerators.add(gen);
			}
			else if (efilter.getAttribute("type").equalsIgnoreCase("currency"))
			{
				for (String str : vfilter)
					for (Currency cur : _ref.getCurrencies())
						if (str.equals(cur.getCode()))
							bcurrencies.add(cur);
			}
		}
	}

	private static void getOutputs(Element ebusinessunit, Element esetting,
			List<Output> outputs, List<TradeGenerator> generators) throws CustomParsingException
	{
		NodeList noutputs = ebusinessunit.getElementsByTagName("output");
		for (int ipf = 0; ipf < noutputs.getLength(); ipf++)
		{
			Element eoutput = (Element) noutputs.item(ipf);
			if (((Node) eoutput).getNodeType() != Node.ELEMENT_NODE)
				continue;

			String sinst = getContent(eoutput, "instrument");
			List<TradeGenerator> opins = new ArrayList<TradeGenerator>();
			if (sinst.equalsIgnoreCase("all"))
				opins.addAll(generators);
			else
			{
				List<String> sins = new ArrayList<String>(
						Arrays.asList(sinst.split("\\s*,\\s*")));

				// Get Instrument ref for each output
				for (String str : sins)
					for (TradeGenerator gen : generators)
						if (str.equals(gen.getName()))
						{
							opins.add(gen);
							break;
						}
			}
			outputs.add(new Output(getContent(eoutput, "format"), getContent(
					eoutput, "path"), opins, Boolean.parseBoolean(getContent(
							eoutput, "isStp")), getContent(eoutput, "layer")));
		}
	}

	private static void getPortfolios(Element ebusinessunit,
			List<Portfolio> portfolios, List<TradeGenerator> generators)
	{
		NodeList nportfolios = ebusinessunit.getElementsByTagName("portfolio");
		for (int ipf = 0; ipf < nportfolios.getLength(); ipf++)
		{
			Element eportfolio = (Element) nportfolios.item(ipf);
			if (((Node) eportfolio).getNodeType() != Node.ELEMENT_NODE)
				continue;

			List<Book> books = new ArrayList<Book>();
			getBooks(eportfolio, books, generators);

			portfolios.add(new Portfolio(eportfolio.getAttribute("name"), books));
		}
	}

	private static void getBooks(Element eportfolio, List<Book> books, List<TradeGenerator> generators)
	{
		// Get books
		NodeList nbooks = eportfolio.getElementsByTagName("book");
		for (int ibook = 0; ibook < nbooks.getLength(); ibook++)
		{
			Element ebook = (Element) nbooks.item(ibook);
			if (((Node) ebook).getNodeType() != Node.ELEMENT_NODE)
				continue;

			List<Currency> bcurrencies = new ArrayList<Currency>();
			List<TradeGenerator> bgenerators = new ArrayList<TradeGenerator>();

			// Get filters
			getFilters(ebook, bgenerators, bcurrencies, generators);
		
			if (bcurrencies.size() == 0 || bgenerators.size() == 0)
				System.out.println("Book " + ebook.getAttribute("name") + " : invalid filters");

			books.add(new Book(ebook.getAttribute("name"), bcurrencies,
					bgenerators));
		}
	}

	private static void getInstruments(Element ebusinessunit,
			List<TradeGenerator> instruments) throws CustomParsingException
	{
		NodeList ninstruments = ebusinessunit
				.getElementsByTagName("instrument");
		for (int iins = 0; iins < ninstruments.getLength(); iins++)
		{
			Element eins = (Element) ninstruments.item(iins);
			if (((Node) eins).getNodeType() != Node.ELEMENT_NODE)
				continue;

			String name = eins.getAttribute("name");
			if (!name.equalsIgnoreCase("equity") && !name.equalsIgnoreCase("loandepo"))
				continue; 

			// Get custom or default generator
			String implementation = getOptContent(eins, "implementation", "");
			TradeGenerator generator = null;
			if (implementation != null && !implementation.isEmpty())
				generator = (TradeGenerator) context.getBean(implementation);
			else
			{
				if (eins.getAttribute("name").equalsIgnoreCase("equity"))
					generator = new EquityGenerator();
				else if (eins.getAttribute("name").equalsIgnoreCase("loandepo"))
					generator = new LoanDepositGenerator();
				else
					continue ;
			}
			
			// Set all fields by name
			generator.name = name;
			NodeList nfields = eins.getChildNodes();
			for (int ifield = 0; ifield < nfields.getLength(); ifield++)
			{
				Node nfield = nfields.item(ifield);
				try {
					Field field = generator.getClass().getField(nfield.getNodeName());
					try {
						System.out.println(name + " >> " + nfield.getNodeName());
						if (field.getType() == Integer.TYPE)
							field.set(generator, Integer.parseInt(nfield.getFirstChild().getNodeValue()));
						else if (field.getType().toString().equals(String.class.toString()))
							field.set(generator, nfield.getFirstChild().getNodeValue());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}

			instruments.add(generator);
		}
	}

	private static void crossReferences()
	{
		for (Businessunit bu : Generals.getInstance().bu)
		{
			// Set Ref BU/PORT for Books
			for (Portfolio pt : bu.getPortfolios())
			{
				pt.setBu(bu);

				for (Book b : pt.getLb())
					b.setPortFolios(pt);
			}
		}
	}

}