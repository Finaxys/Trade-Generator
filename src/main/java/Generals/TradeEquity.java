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
	private int					quantity;
	private Referential.Product	product;

	public TradeEquity(long id, Currency currency, Portfolio portfolio,
			Book book, Instrument instrument, List<Node> nodes, float amount,
			String reference, Way way, Date eventDate, Date tradeDate,
			ProductType producttype, Depositary depositary,
			Counterpart counterpart, Trader trader, int quantity,
			Product product2) {
		super(id, currency, portfolio, book, instrument, nodes, amount,
				reference, way, eventDate, tradeDate, producttype, depositary,
				counterpart, trader);
		this.quantity = quantity;
		product = product2;
	}

	@Override
	public List<Node> getNodes()
	{
		nodes = new ArrayList<Node>();

		addNode(nodes, "business", book.getPt().getBu().getName(), null);
		addNode(nodes, "portfolio", book.getPt().getName(), null);
		addNode(nodes, "book", book.getName(), null);
		addNode(nodes, "way", getWay().equals(Way.BUY) ? "BUY" : "SELL", null);
		addNode(nodes, "type", "equity", null);
		addNode(nodes, "product", product.libelle, null);
		addNode(nodes, "quantity", Integer.toString(quantity), null);
		addNode(nodes, "price", Float.toString(amount), null);
		addNode(nodes, "currency", currency.code, null);
		addNode(nodes, "trader", trader.codeptf, null);
		return (nodes);
	}
}