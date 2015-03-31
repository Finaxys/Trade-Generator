package Generals;

import java.util.ArrayList;
import java.util.Date;

public abstract class TradeEvent implements Comparable<TradeEvent>
{
	Way  way;
	static long counter = 0;
	long id;
	Referential.Currency currency;
	Referential.Portfolio portfolio;
	public Book book;
	public Date date;
	public Instrument instrument;
	protected ArrayList<Node> nodes;
	float amount;
	
	
	public class Node
	{
		public String name;
		public String value; // If empty take list of nodes
		public ArrayList<Node> nodes;
	}


	public TradeEvent(Book book, Date date, Way way, float amount)
	{
		id = ++counter;
		this.book = book;
		this.date = date;
		this.way = way;
		this.amount=amount;
	}

	public abstract ArrayList<Node> getNodes();

	public void addNode(ArrayList<Node> root, String name, String value,
			ArrayList<Node> nodes)
	{
		Node node = new Node();
		node.name = name;
		node.value = value;
		node.nodes = nodes;
		root.add(node);
	}
	
	public int compareTo(TradeEvent trade) 
	{
		 if (!(this.date.equals(trade.date)))
	         return (int) (this.date.getTime()-trade.date.getTime());
//	     if (!this.book.pt.bu.name.equalsIgnoreCase(trade.book.pt.bu.name))
//	    	 return 1;
//	     if (!this.book.pt.name.equalsIgnoreCase(trade.book.pt.name))
//	    	 return 1;
	     if (!this.book.name.equalsIgnoreCase(trade.book.name))
	    	 return this.book.name.compareTo(trade.book.name);
	     if (!this.instrument.name.equalsIgnoreCase(trade.instrument.name))
	    	 return this.instrument.name.compareTo(trade.instrument.name);
	     return (this.way.name().compareTo(trade.way.name()));
//	    	return 0; 
	     }
	    	
	   
	}
		
