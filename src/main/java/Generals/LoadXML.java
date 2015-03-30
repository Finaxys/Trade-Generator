package Generals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.security.pkcs.ParsingException;
import Generals.Referential.Currency;

public class LoadXML
{
	interface CbReferential
	{
		public void init(Referential ref);

		public void execute(Referential ref, Element elem) throws CustomParsingException;
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
		return (content);
	}

	static public void init(Referential ref) throws CustomParsingException
	{
		_ref = ref;

		// Load Static Informations
		loadReferential("counterparts.xml", "counterpart", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.Counterparts = new ArrayList<Referential.Counterpart>();
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Counterpart counterpart = ref.new Counterpart();
				counterpart.code = getContent(eElement, "code");
				counterpart.name = getContent(eElement, "name");
				ref.Counterparts.add(counterpart);
			}
		});

		loadReferential("products.xml", "product", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.Products = new ArrayList<Referential.Product>();
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Product product = ref.new Product();
				product.name = getContent(eElement, "type");
				product.type = getContent(eElement, "type");
				product.isin = getContent(eElement, "isin");
				product.libelle = getContent(eElement, "libelle");
				product.country = getContent(eElement, "country");
				product.price = Float.parseFloat(getContent(eElement, "price"));
				ref.Products.add(product);
			}
		});

		loadReferential("depositaries.xml", "depositary", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.Depositaries = new ArrayList<Referential.Depositary>();
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Depositary depositary = ref.new Depositary();
				depositary.code = getContent(eElement, "code");
				depositary.libelle = getContent(eElement, "libelle");
				ref.Depositaries.add(depositary);
			}
		});

		loadReferential("portfolios.xml", "portfolio", new CbReferential()
		{
			public void init(Referential ref)
			{
				ref.Portfolios = new ArrayList<Referential.Portfolio>();
			}

			public void execute(Referential ref, Element eElement) throws CustomParsingException
			{
				Referential.Portfolio portfolio = ref.new Portfolio();
				portfolio.codeptf = getContent(eElement, "codeptf");
				portfolio.country = getContent(eElement, "country");
				portfolio.type = getContent(eElement, "type");
				ref.Portfolios.add(portfolio);
			}
		});

		try
		{
			loadTraders();
		} catch (Exception e)
		{
			throw new CustomParsingException("Traders :" + e.getMessage(), true);
		}
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
			File fXmlFile = new File("params/generalinfs.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			// Get general setting bank
			NodeList settings = doc.getElementsByTagName("generalSettings");
			Element esetting = (Element) settings.item(0);

			// Get businessunits
			ArrayList<Businessunit> businessunits = new ArrayList<Businessunit>();
			NodeList nbusinessunits = doc.getElementsByTagName("businessUnit");
			for (int ibu = 0; ibu < nbusinessunits.getLength(); ibu++)
			{
				ArrayList<Instrument> instruments = new ArrayList<Instrument>();
				ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
				ArrayList<Book> books = new ArrayList<Book>();
				ArrayList<Output> outputs = new ArrayList<Output>();

				Element ebusinessunit = (Element) nbusinessunits.item(ibu);

				if (((Node) ebusinessunit).getNodeType() != Node.ELEMENT_NODE)
					continue;

				// Get instruments
				getInstruments(ebusinessunit, instruments);

				// Get portfolios
				getPortfolios(ebusinessunit, portfolios, books, instruments);

				// Get outputs
				getOutputs(ebusinessunit, esetting, outputs, instruments);

				businessunits.add(new Businessunit(ebusinessunit
						.getAttribute("name"), Integer.parseInt(ebusinessunit
						.getAttribute("ratio")), outputs, instruments,
						portfolios));
			}

			Generals.getInstance().init(getContent(esetting, "name"),
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
			ArrayList<Referential.Currency> currencies = new ArrayList<Referential.Currency>();
			ArrayList<Referential.Instrument> instruments = new ArrayList<Referential.Instrument>();
			ArrayList<Referential.Trader> traders = new ArrayList<Referential.Trader>();

			File fXmlFile = new File("referential/traders.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
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

				// Get instruments
				NodeList ninstruments = ecurrency
						.getElementsByTagName("instrument");
				for (int iinstru = 0; iinstru < ninstruments.getLength(); iinstru++)
				{
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

						traders.add(new Referential.Trader(etrader
								.getAttribute("name"), etrader
								.getAttribute("codeptf")));
						_ref.Traders = traders;
					}

					instruments.add(_ref.new Instrument(einstrument
							.getAttribute("name")));
					_ref.Instruments = instruments;
				}

				currencies.add(_ref.new Currency(
						ecurrency.getAttribute("code"), ecurrency
								.getAttribute("name"), ecurrency
								.getAttribute("country")));
				_ref.Currencies = currencies;
			}
	}

	private static void getFilters(Element ebook,
			ArrayList<Instrument> binstruments,
			ArrayList<Currency> bcurrencies, ArrayList<Instrument> instruments)
	{
		NodeList nfilters = ebook.getElementsByTagName("filter");
		for (int ifilter = 0; ifilter < nfilters.getLength(); ifilter++)
		{
			Element efilter = (Element) nfilters.item(ifilter);

			List<String> vfilter = Arrays.asList(efilter.getAttribute("value")
					.split("\\s*,\\s*"));

			if (efilter.getAttribute("type").equalsIgnoreCase("instrument"))
			{
				for (String str : vfilter)
					for (Instrument ins : instruments)
						if (str.equals(ins.name))
							binstruments.add(ins);
			}
			else if (efilter.getAttribute("type").equalsIgnoreCase("currency"))
			{
				for (String str : vfilter)
					for (Currency cur : _ref.Currencies)
						if (str.equals(cur.code))
							bcurrencies.add(cur);
			}
		}
	}

	private static void getOutputs(Element ebusinessunit, Element esetting,
			ArrayList<Output> outputs, ArrayList<Instrument> instruments) throws CustomParsingException
	{
		NodeList noutputs = ebusinessunit.getElementsByTagName("output");
		for (int ipf = 0; ipf < noutputs.getLength(); ipf++)
		{
			Element eoutput = (Element) noutputs.item(ipf);
			if (((Node) eoutput).getNodeType() != Node.ELEMENT_NODE)
				continue;

			String sinst = getContent(eoutput, "instrument");
			ArrayList<Instrument> opins = new ArrayList<Instrument>();
			if (sinst.equalsIgnoreCase("all"))
				opins.addAll(instruments);
			else
			{
				ArrayList<String> sins = new ArrayList<String>(
						Arrays.asList(sinst.split("\\s*,\\s*")));

				// Get Instrument ref for each output
				for (String str : sins)
					for (Instrument inst : instruments)
						if (str.equals(inst.name))
						{
							opins.add(inst);
							break;
						}
			}
			outputs.add(new Output(getContent(esetting, "format"), getContent(
					eoutput, "path"), opins, Boolean.parseBoolean(getContent(
					eoutput, "isStp")), getContent(eoutput, "layer")));
		}
	}

	private static void getPortfolios(Element ebusinessunit,
			ArrayList<Portfolio> portfolios, ArrayList<Book> books,
			ArrayList<Instrument> instruments)
	{
		NodeList nportfolios = ebusinessunit.getElementsByTagName("portfolio");
		for (int ipf = 0; ipf < nportfolios.getLength(); ipf++)
		{
			Element eportfolio = (Element) nportfolios.item(ipf);
			if (((Node) eportfolio).getNodeType() != Node.ELEMENT_NODE)
				continue;

			getBooks(eportfolio, books, instruments);

			portfolios
					.add(new Portfolio(eportfolio.getAttribute("name"), books));
		}
	}

	private static void getBooks(Element eportfolio, ArrayList<Book> books,
			ArrayList<Instrument> instruments)
	{
		// Get books
		NodeList nbooks = eportfolio.getElementsByTagName("book");
		for (int ibook = 0; ibook < nbooks.getLength(); ibook++)
		{
			Element ebook = (Element) nbooks.item(ibook);
			if (((Node) ebook).getNodeType() != Node.ELEMENT_NODE)
				continue;

			ArrayList<Currency> bcurrencies = new ArrayList<Currency>();
			ArrayList<Instrument> binstruments = new ArrayList<Instrument>();

			// Get filters
			getFilters(ebook, binstruments, bcurrencies, instruments);

			books.add(new Book(ebook.getAttribute("name"), bcurrencies,
					binstruments));
		}
	}

	private static void getInstruments(Element ebusinessunit,
			ArrayList<Instrument> instruments) throws CustomParsingException
	{
		NodeList ninstruments = ebusinessunit
				.getElementsByTagName("instrument");
		for (int iins = 0; iins < ninstruments.getLength(); iins++)
		{
			Element eins = (Element) ninstruments.item(iins);
			if (((Node) eins).getNodeType() != Node.ELEMENT_NODE)
				continue;

			// Manage all instrument (Only equity for now)
			if (eins.getAttribute("name").equalsIgnoreCase("equity"))
			{
				Equity equity = new Equity();
				equity.name = "equity";
				equity.ownCountry = Integer.parseInt(getContent(eins, "ownCountry"));
				equity.partSell = Integer.parseInt(getContent(eins, "partSell"));
				equity.repartitionTolerance = Integer.parseInt(getContent(eins, "toleranceRep"));
				equity.volumetry = Integer.parseInt(getContent(eins, "volumetry"));
				equity.volumetryTolerance = Integer.parseInt(getContent(eins, "volumetryTolerance"));
				instruments.add(equity);
			}
			else if (eins.getAttribute("name").equalsIgnoreCase("loandepo"))
			{
				LoanDeposit loandepo = new LoanDeposit(Integer.parseInt(getContent(eins, "partLoan")), Integer.parseInt(getContent(eins, "ownCountry")), Integer.parseInt(getContent(eins, "volumetry")), 
						Integer.parseInt(getContent(eins, "volumetryTolerance")), Integer.parseInt(getContent(eins, "repartitionTolerance")),  Integer.parseInt(getContent(eins, "rateValue")), 
						Integer.parseInt(getContent(eins, "rateValueTolerance")), Integer.parseInt(getContent(eins, "partRateVariable")));
				loandepo.name = "loandepo";
				instruments.add(loandepo);
			}
		}
	}

	private static void crossReferences()
	{
		for (Businessunit bu : Generals.getInstance().bu)
		{
			// Set Ref BU/PORT for Books
			for (Portfolio pt : bu.lpor)
			{
				pt.bu = bu;

				for (Book b : pt.lb)
					b.pt = pt;
			}
		}
	}

}