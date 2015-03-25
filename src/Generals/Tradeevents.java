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
	
	public Tradeevents(Book book, int date) {
		this.book = book;
		this.date = date;
	}
	
	public abstract ArrayList<Node>	getNodes();
	
	public String toXML() { return ""; }
	public String toCSV() { return ""; }
}
