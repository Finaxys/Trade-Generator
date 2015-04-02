package Generals;

import java.util.Date;
import java.util.List;

public abstract class TradeEvent implements Comparable<TradeEvent>
{

	private static long counter = 0;
	private long id;
	protected Referential.Currency currency;
	protected Referential.Portfolio portfolio;
	protected Book book;
	private Date date;
	protected Instrument instrument;
	protected List<Node> nodes;
	protected float amount;
	private String reference;
	private Way way;
	private Date eventDate;
	private Date tradeDate;
	private String counterpart;
	private ProductType product;


	
	public class Node
	{
		public String name;
		public String value; // If empty take list of nodes
		public List<Node> nodes;
	}


	public TradeEvent(Book book, Date date, Way way, float amount)
	{
		setId(++counter);
		this.setBook(book);
		this.setDate(date);
		this.setWay(way);
		this.amount=amount;
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
	     if (!this.getBook().pt.name.equalsIgnoreCase(trade.getBook().pt.name))
	    	 return this.getBook().pt.name.compareTo(trade.getBook().pt.name);
	     if (!this.getBook().name.equalsIgnoreCase(trade.getBook().name))
	    	 return this.getBook().name.compareTo(trade.getBook().name);
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
		

