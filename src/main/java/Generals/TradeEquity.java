package Generals;

import java.util.ArrayList;
import java.util.Date;

public class TradeEquity extends TradeEvent
{
	int quantity;
	
	Referential.Depositary depositary;
	Referential.Counterpart counterpart;
	Referential.Trader trader;
	Referential.Product product;

	public TradeEquity(Instrument instrument, Book book, Date date, Way way,
			float amount, int quantity, Referential.Depositary d1,
			Referential.Counterpart c1, Referential.Trader tr1,
			Referential.Product pro1, Referential.Currency cur1,
			Referential.Portfolio port1)
	{
		super(book, date, way,amount);
	
		this.quantity = quantity;
		this.depositary = d1;
		this.counterpart = c1;
		this.trader = tr1;
		this.product = pro1;
		this.currency = cur1;
		this.portfolio = port1;
		this.instrument = instrument;
	}

	@Override
	public ArrayList<Node> getNodes()
	{
		nodes = new ArrayList<Node>();

		addNode(nodes, "business", book.pt.bu.name, null);
		addNode(nodes, "portfolio", book.pt.name, null);
		addNode(nodes, "book", book.name, null);
		addNode(nodes, "way", way.equals(Way.BUY) ? "BUY" : "sell", null);
		addNode(nodes, "type", "equity", null);
		addNode(nodes, "product", product.libelle, null);
		addNode(nodes, "quantity", Integer.toString(quantity), null);
		addNode(nodes, "price", Float.toString(amount), null);
		addNode(nodes, "currency", currency.code, null);
		addNode(nodes, "trader", trader.codeptf, null);
		return (nodes);
	}
}