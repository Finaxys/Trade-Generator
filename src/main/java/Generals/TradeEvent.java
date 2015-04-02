package Generals;

import java.util.Date;
import java.util.List;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Portfolio;
import Generals.Referential.Trader;

public abstract class TradeEvent implements Comparable<TradeEvent>
{
	private static long 			counter = 0;
	private long 					id;
	protected Referential.Currency 	currency;
	protected Referential.Portfolio portfolio;
	protected Book 					book;
	protected Instrument 			instrument;
	protected List<Node> 			nodes;
	protected float 				amount;
	private String 					reference;
	private Way 					way;
	private Date 					date;
	private Date 					tradeDate;
	private ProductType 			product;
	private Referential.Depositary 	depositary;
	protected Referential.Counterpart counterpart;
	protected Referential.Trader 		trader;

	public TradeEvent(long id, Currency currency, Portfolio portfolio,
			Book book, Instrument instrument, List<Node> nodes, float amount,
			String reference, Way way, Date eventDate, Date tradeDate,
			ProductType product, Depositary depositary,
			Counterpart counterpart, Trader trader) {
		super();
		setId(++counter);
		this.currency = currency;
		this.portfolio = portfolio;
		this.book = book;
		this.instrument = instrument;
		this.nodes = nodes;
		this.amount = amount;
		this.reference = reference;
		this.way = way;
		this.date = eventDate;
		this.tradeDate = tradeDate;
		this.product = product;
		this.depositary = depositary;
		this.counterpart = counterpart;
		this.trader = trader;
	}

	public class Node
	{
		public String 				name;
		public String 				value; // If empty take list of nodes
		public List<Node> 			nodes;
	}

	public abstract List<Node> getNodes();

	public void addNode(List<Node> root, String name, String value,
			List<Node> nodes)
	{
		Node node = new Node();
		node.name = name;
		node.value = value;
		node.nodes = nodes;
		root.add(node);
	}
	
	public int compareTo(TradeEvent trade) 
	{
		 if (!(this.getDate().equals(trade.getDate())))
	         return (int) (this.getDate().getTime()-trade.getDate().getTime());
//	     if (!this.book.pt.bu.name.equalsIgnoreCase(trade.book.pt.bu.name))
//	    	 return 1;
	     if (!this.getBook().getPt().getName().equalsIgnoreCase(trade.getBook().getPt().getName()))
	    	 return this.getBook().getPt().getName().compareTo(trade.getBook().getPt().getName());
	     if (!this.getBook().getName().equalsIgnoreCase(trade.getBook().getName()))
	    	 return this.getBook().getName().compareTo(trade.getBook().getName());
	     if (!this.instrument.getName().equalsIgnoreCase(trade.getInstrument().getName()))
	    	 return this.instrument.getName().compareTo(trade.getInstrument().getName());
	     return (this.getWay().name().compareTo(trade.getWay().name()));
//	    	return 0; 
	     }

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Way getWay() {
		return way;
	}

	public void setWay(Way way) {
		this.way = way;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	    	
	   
	}
		

