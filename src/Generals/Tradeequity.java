package Generals;

public class Tradeequity implements Tradeevents {

	int date;
	//date datedone;
	//int heure;
	//Trader trader;
	boolean Sens;
	Book book;
	int prix;
	int quantity;
	
	public Tradeequity(int date, boolean sens, Book book, int prix, int quantity) {
		super();
		this.date = date;
		Sens = sens;
		this.book = book;
		this.prix = prix;
		this.quantity = quantity;
	}

	public void toXml() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
