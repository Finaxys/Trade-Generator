package Generals;

import java.util.ArrayList;

public abstract class TradeEvent
{
	public class Node
	{
		public String name;
		public String value; // If empty take list of nodes
		public ArrayList<Node> nodes;
	}

	static long counter = 0;
	long id;
	Referential.Currency currency;
	Referential.Portfolio portfolio;
	public Book book;
	public int date;
	public Instrument instrument;
	protected ArrayList<Node> nodes;

	public TradeEvent(Book book, int date)
	{
		id = ++counter;
		this.book = book;
		this.date = date;
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
}
