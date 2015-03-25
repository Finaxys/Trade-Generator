package Generals;

import java.util.ArrayList;

public abstract class Tradeevents {
	public class Node
	{
		public String			name;
		public String			value; // If empty take list of nodes
		public ArrayList<Node>	nodes;
	}

	public Book				book;
	public int				date;
	protected ArrayList<Node>	nodes;
	
	public Tradeevents(Book book, int date) {
		this.book = book;
		this.date = date;
	}
	
	public abstract ArrayList<Node>	getNodes();
	
	public void addNode(ArrayList<Node> root, String name, String value, ArrayList<Node> nodes)
	{
		Node node = new Node();
		node.name = name;
		node.value = value;
		node.nodes = nodes;
		root.add(node);
	}
}
