package Generals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Trader;

public class Tradeloan extends TradeEvent
{
	
	private Date valueDate;
	private Date maturityDate;
	private Double national;	
	private Indexation index;
	private String ISIN;	
	private Referential.Depositary depositary;
	private Referential.Counterpart counterpart;
	private Typetaux rate;
	private Referential.Trader trader;
	private Referential.Currency currency;
	private Double rateValue;
	private Double spread;
	private Term term;
	private BaseCalcul basecalcul;
	private Boolean is_stp;

	public Tradeloan(Instrument instrument, Book book, Date date, int amount,
			Way way, Typetaux rate, Depositary depositary,
			Counterpart counterpart, Trader trader, Currency currency,
			Double rateValue, Term term, BaseCalcul basecalcul)
	{
		super(book, date,way, amount);
	
		this.depositary = depositary;
		this.counterpart = counterpart;

		this.rate = rate;
		this.trader = trader;
		this.currency = currency;
		this.rateValue = rateValue;
		this.term = term;
		this.basecalcul = basecalcul;
		this.setInstrument(instrument);
	}

	@Override
	public ArrayList<Node> getNodes()
	{
		// TODO Auto-generated method stub
		nodes = new ArrayList<Node>();

		addNode(nodes, "business", book.pt.bu.name, null);
		addNode(nodes, "portfolio", book.pt.name, null);
		addNode(nodes, "book", book.name, null);
		addNode(nodes, "way", getWay().equals(Way.BUY) ? "BUY" : "SELL", null);
		addNode(nodes, "type", "loandepo", null);
		addNode(nodes, "amount", Float.toString(amount), null);
		addNode(nodes, "depositary", depositary.code, null);
		addNode(nodes, "counterpart", counterpart.code, null);
		addNode(nodes, "rate", rate.name(), null);
		addNode(nodes, "trader", trader.codeptf, null);
		addNode(nodes, "currency", currency.name, null);
		addNode(nodes, "rateValue", Double.toString(rateValue), null);
		addNode(nodes, "term", term.name(), null);
		addNode(nodes, "basecalcul", basecalcul.name(), null);
		return (nodes);
	}
}
