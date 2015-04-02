package Generals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Trader;

public class Tradeloan extends TradeEvent
{
	
	Referential.Depositary depositary;
	Referential.Counterpart counterpart;

	Typetaux rate;
	Referential.Trader trader;
	Referential.Currency currency;
	public float rateValue;
	public Term term;
	BaseCalcul basecalcul;
	public Boolean is_stp;

	public Tradeloan(Instrument instrument, Book book, Date date, int amount,
			Way way, Typetaux rate, Depositary depositary,
			Counterpart counterpart, Trader trader, Currency currency,
			float rateValue, Term term, BaseCalcul basecalcul)
	{
		super(book, date,way, amount);
	
		this.depositary = depositary;
		this.counterpart = counterpart;

		this.rate = rate;
		this.trader = trader;
		this.currency = currency;
		this.rateValue = rateValue;
		this.term = term;
		this.basecalcul = basecalcul;
		this.instrument = instrument;
	}

	@Override
	public List<Node> getNodes()
	{
		// TODO Auto-generated method stub
		nodes = new ArrayList<Node>();

		addNode(nodes, "business", book.pt.bu.name, null);
		addNode(nodes, "portfolio", book.pt.name, null);
		addNode(nodes, "book", book.name, null);
		addNode(nodes, "way", way.equals(Way.BUY) ? "LOAN" : "DEPO", null);
		addNode(nodes, "type", "loandepo", null);
		addNode(nodes, "amount", Float.toString(amount), null);
		addNode(nodes, "depositary", depositary.code, null);
		addNode(nodes, "counterpart", counterpart.code, null);
		addNode(nodes, "rate", rate.name(), null);
		addNode(nodes, "trader", trader.codeptf, null);
		addNode(nodes, "currency", currency.name, null);
		addNode(nodes, "rateValue", Float.toString(rateValue), null);
		addNode(nodes, "term", term.name(), null);
		addNode(nodes, "basecalcul", basecalcul.name(), null);
		return (nodes);
	}
}
