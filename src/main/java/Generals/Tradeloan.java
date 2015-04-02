package Generals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Portfolio;
import Generals.Referential.Trader;

public class Tradeloan extends TradeEvent
{


	private Date 						valueDate;
	private Date 						maturityDate;
	private Double 						national;	
	private Indexation					index;
	private String 						ISIN;	
	private RateType 					rate;
	private Double 						rateValue;
	private Double 						spread;
	private Term term;
	private BaseCalcul basecalcul;
	private Boolean is_stp;










	public Tradeloan(long id, Currency currency, Portfolio portfolio,
			Book book, Instrument instrument, List<Node> nodes, float amount,
			String reference, Way way, Date eventDate, Date tradeDate,
			ProductType product, Depositary depositary,
			Counterpart counterpart, Trader trader, Date valueDate,
			Date maturityDate, Double national, Indexation index, String iSIN,
			RateType rate, Double rateValue, Double spread, Term term,
			BaseCalcul basecalcul, Boolean is_stp) {
		super(id, currency, portfolio, book, instrument, nodes, amount,
				reference, way, eventDate, tradeDate, product, depositary,
				counterpart, trader);
		this.valueDate = valueDate;
		this.maturityDate = maturityDate;
		this.national = national;
		this.index = index;
		ISIN = iSIN;
		this.rate = rate;
		this.rateValue = rateValue;
		this.spread = spread;
		this.term = term;
		this.basecalcul = basecalcul;
		this.is_stp = is_stp;
	}










	@Override
	public List<Node> getNodes()
	{
		// TODO Auto-generated method stub
		nodes = new ArrayList<Node>();

		addNode(nodes, "business", book.getPt().getBu().getName(), null);
		addNode(nodes, "portfolio", book.getPt().getName(), null);
		addNode(nodes, "book", book.getName(), null);
		addNode(nodes, "way", getWay().equals(Way.BUY) ? "BUY" : "SELL", null);
		addNode(nodes, "type", "loandepo", null);
		addNode(nodes, "amount", Float.toString(amount), null);
		addNode(nodes, "rate", rate.name(), null);
		addNode(nodes, "trader", trader.codeptf, null);
		addNode(nodes, "currency", currency.name, null);
		addNode(nodes, "rateValue", Double.toString(rateValue), null);
		addNode(nodes, "term", term.name(), null);
		addNode(nodes, "basecalcul", basecalcul.name(), null);
		addNode(nodes, "spread", Double.toString(spread), null);
		addNode(nodes, "valueDate", basecalcul.name(), null);
		addNode(nodes, "basecalcul", basecalcul.name(), null);
		return (nodes);
	}
}
