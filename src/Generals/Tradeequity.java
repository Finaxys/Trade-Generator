package Generals;

public class Tradeequity extends Tradeevents 
{
	int date;
	float prix;
	int quantity;
	Way way;
	Referential.Depositary depositary;
	Referential.Counterpart counterpart;
	Referential.Trader trader;
	Referential.Product product;
	Referential.Currency currency;
	Referential.Portfolio portfolio;

	public Tradeequity(Book book, int date, Way way, float prix, int quantity,
			Referential.Depositary d1, Referential.Counterpart c1, Referential.Trader tr1, Referential.Product pro1,
			Referential.Currency cur1, Referential.Portfolio port1) 
	{
		super(book, date);
		this.way = way;
		this.prix = prix;
		this.quantity = quantity;
		this.depositary = d1;
		this.counterpart = c1;
		this.trader = tr1;
		this.product = pro1;
		this.currency = cur1;
		this.portfolio = port1;
	}

	@Override
	public String toXml() 
	{	
		StringBuilder Document = new StringBuilder();
		Document.append("<trade>\n<way>" + (way.equals(way.BUY) ? "BUY" : "sell") + "<way>\n");
		Document.append("<book>" + book.name + "</book>\n");
		Document.append("<type>equity</type>\n");
		Document.append("<product>" + product.libelle + "<product>\n");
		Document.append("<quantity>" + Integer.toString(quantity) + "<quantity>\n");
		Document.append("<price>" + Float.toString(prix) + "<price>\n");
		Document.append("<currency>" + currency.code + "<currency>\n");
		Document.append("<trader>" + trader.codeptf + "<trader>\n</trade>\n");			
		return Document.toString();
	}
}