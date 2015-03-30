package Generals;

import java.util.ArrayList;

public abstract class TradeEvent implements Comparable<TradeEvent>
{
	Way  way;
	static long counter = 0;
	long id;
	Referential.Currency currency;
	Referential.Portfolio portfolio;
	public Book book;
	public Integer date;
	public Instrument instrument;
	protected ArrayList<Node> nodes;
	float amount;
	
	
	public class Node
	{
		public String name;
		public String value; // If empty take list of nodes
		public ArrayList<Node> nodes;
	}


	public TradeEvent(Book book, int date, Way way, float amount)
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
		 if (!(this.date==trade.date))
	            return this.date.compareTo(trade.date);
	     if (!this.book.pt.bu.name.equalsIgnoreCase(trade.book.pt.bu.name))
	            return this.book.pt.bu.name.compareTo(trade.book.pt.bu.name);
	     if (!this.book.pt.name.equalsIgnoreCase(trade.book.pt.name))
	            return this.book.pt.name.compareTo(trade.book.pt.name);
	     if (!this.book.name.equalsIgnoreCase(trade.book.name))
	            return this.book.name.compareTo(trade.book.name);
	     if (!this.instrument.name.equalsIgnoreCase(trade.instrument.name))
	            return this.instrument.name.compareTo(trade.instrument.name);
	     return this.way.compareTo(trade.way);
	}
	
}
