package Generals;

import java.util.ArrayList;

public class Tradeequity extends TradeEvent 
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
	public ArrayList<Node> getNodes() {
		nodes = new ArrayList<Node>();

		addNode(nodes, "business", book.pt.bu.name, null);
		addNode(nodes, "portfolio", book.pt.name, null);
		addNode(nodes, "book", book.name, null);
		addNode(nodes, "way", way.equals(way.BUY) ? "BUY" : "sell", null);
		addNode(nodes, "type", "equity", null);
		addNode(nodes, "product", product.libelle, null);
		addNode(nodes, "quantity", Integer.toString(quantity), null);
		addNode(nodes, "price", Float.toString(prix), null);
		addNode(nodes, "currency", currency.code, null);
		addNode(nodes, "trader", trader.codeptf, null);
		return (nodes);
	}
}