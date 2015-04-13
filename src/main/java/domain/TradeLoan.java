package domain;

import generals.Indexation;

import java.util.Date;
import java.util.List;

import domain.Referential.Counterpart;
import domain.Referential.Currency;
import domain.Referential.Depositary;
import domain.Referential.Trader;

public class TradeLoan extends TradeEvent
{
	private Date 				valueDate;
	private Date 				maturityDate;
	private Double 				national;	
	private Indexation			index;
	private String 				isin;	
	private RateType 			rate;
	private Double 				rateValue;
	private Double 				spread;
	private Term				term;
	private BaseCalcul			basecalcul;
	private double				amount;				
	protected Referential.Currency 		currency;
	protected Referential.Depositary 	depositary;
	protected Referential.Trader 		trader;

	public TradeLoan(TradeGenerator instrument, String reference, Way way, Date date, Date tradeDate,
			Counterpart counterpart, Book book, Date valueDate,
			Date maturityDate, Double national, Indexation index, String isin,
			RateType rate, Double rateValue, Double spread, Term term,
			BaseCalcul basecalcul, double amount,
			Currency currency, Depositary depositary, Trader trader) 
	{
		super(reference, way, date, tradeDate, counterpart, book, instrument);
		this.valueDate = valueDate;
		this.maturityDate = maturityDate;
		this.national = national;
		this.index = index;
		this.isin = isin;
		this.rate = rate;
		this.rateValue = rateValue;
		this.spread = spread;
		this.term = term;
		this.basecalcul = basecalcul;
		this.amount = amount;
		this.currency = currency;
		this.depositary = depositary;
		this.trader = trader;
	}

	@Override
	public double getAmount()
	{
	    return amount;
	}

	@Override
	public List<Node> getNodes()
	{
		nodes = super.getNodes();

		addNode(nodes, "businessUnit", book.getPortFolios().getBu().getName(), null);
		addNode(nodes, "portfolio", book.getPortFolios().getName(), null);
		addNode(nodes, "book", book.getName(), null);
		addNode(nodes, "way", getWay().equals(Way.BUY) ? "BUY" : "SELL", null);
		addNode(nodes, "type", "loandepo", null);
		addNode(nodes, "amount", Double.toString(this.amount), null);
		addNode(nodes, "rate", rate.name(), null);
		addNode(nodes, "trader", trader.getCode(), null);
		addNode(nodes, "currency", currency.getName(), null);
		addNode(nodes, "rateValue", Double.toString(rateValue), null);
		addNode(nodes, "term", term.name(), null);
		addNode(nodes, "basecalcul", basecalcul.name(), null);
		addNode(nodes, "spread", Double.toString(spread), null);
		addNode(nodes, "valueDate", valueDate.toString(), null);
		addNode(nodes, "maturityDate", maturityDate.toString(), null);
		addNode(nodes, "Depositary", depositary.getCode(), null);
		addNode(nodes, "trader", trader.getName(), null);
		addNode(nodes, "ISIN", isin, null);
		addNode(nodes, "indexation", index.name(), null);
		addNode(nodes, "spread", spread.toString(), null);
		addNode(nodes, "national", national.toString(), null);
		
		return nodes;
	}
}
