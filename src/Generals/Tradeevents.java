package Generals;

public abstract class Tradeevents {
	public Book				book;
	public int				date;
	
	public Tradeevents(Book book, int date) {
		this.book = book;
		this.date = date;
	}
 
	public String toXml() { return ""; }
}
