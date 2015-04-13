package domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import domain.Referential.Counterpart;
import domain.Referential.Depositary;
import domain.Referential.Product;
import domain.Referential.Trader;

public class TradeEquity extends TradeEvent {
    private double price;
    private int quantity;
    private Referential.Product product;
    protected Referential.Depositary depositary;
    protected Referential.Trader trader;

    public TradeEquity(TradeGenerator instrument, String reference, Way way,
            Date date, Date tradeDate, Counterpart counterpart, Book book,
            double price, int quantity, Product product, Depositary depositary,
            Trader trader) {
        super(reference, way, date, tradeDate, counterpart, book, instrument);
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.depositary = depositary;
        this.trader = trader;
    }

    @Override
    public double getAmount() {
        return price;
    }

    @Override
    public List<Node> getNodes() {
        nodes = super.getNodes();

        addNode(nodes, "businessUnit", book.getPortFolios().getBu().getName(),
                null);
        addNode(nodes, "portfolio", book.getPortFolios().getName(), null);
        addNode(nodes, "book", book.getName(), null);
        addNode(nodes, "way", getWay().equals(Way.BUY) ? "BUY" : "SELL", null);
        addNode(nodes, "type", "equity", null);
        addNode(nodes, "product", product.getLibelle(), null);
        addNode(nodes, "quantity", Integer.toString(quantity), null);
        addNode(nodes, "price",
                BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP)
                        .toString(), null);
        addNode(nodes, "counterpart", counterpart.getCode(), null);
        addNode(nodes, "product", product.getName(), null);
        addNode(nodes, "depositary", depositary.toString(), null);
        addNode(nodes, "trader", trader.getName(), null);

        return nodes;
    }
}