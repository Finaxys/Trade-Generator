package Generals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Portfolio;
import Generals.Referential.Product;
import Generals.Referential.Trader;

public class TradeEquity extends TradeEvent
{	
	private double 						price;
	private int							quantity;
	private Referential.Product			product;
	protected Referential.Depositary 	depositary;
	protected Referential.Trader 		trader;
	


	@Override
	public List<Node> getNodes()
	{
		nodes = new ArrayList<Node>();
		addNode(nodes, "business", book.getPortFolios().getBu().getName(), null);
		addNode(nodes, "portfolio", book.getPortFolios().getName(), null);
		addNode(nodes, "book", book.getName(), null);
		addNode(nodes, "way", getWay().equals(Way.BUY) ? "BUY" : "SELL", null);
		addNode(nodes, "type", "equity", null);
		addNode(nodes, "product", product.libelle, null);
		addNode(nodes, "quantity", Integer.toString(quantity), null);
		addNode(nodes, "price", Double.toString(price), null);
		addNode(nodes, "trader", trader.codeptf, null);
		return (nodes);
	}



	public TradeEquity(String reference, Way way, Date date, Date tradeDate,
			Counterpart counterpart,Book book, double price, int quantity,
			Product product, Depositary depositary) {
		super(reference, way, date, tradeDate, counterpart, book);
		this.price = price;
		this.quantity = quantity;
		this.product = product;
		this.depositary = depositary;
	}




}