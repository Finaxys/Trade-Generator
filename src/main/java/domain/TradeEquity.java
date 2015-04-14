package domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import domain.Referential.Counterpart;

public class TradeEquity extends TradeEvent {
    private double price;
    private int quantity;
    private Referential.Product product;
    private Referential.Depositary depositary;
    private Referential.Trader trader;

    public TradeEquity(TradeGenerator instrument, String reference, Way way,
            Date date, Counterpart counterpart, Book book) {
        super(reference, way, date, counterpart, book, instrument);
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Referential.Product getProduct() {
        return product;
    }

    public void setProduct(Referential.Product product) {
        this.product = product;
    }

    public Referential.Depositary getDepositary() {
        return depositary;
    }

    public void setDepositary(Referential.Depositary depositary) {
        this.depositary = depositary;
    }

    public Referential.Trader getTrader() {
        return trader;
    }

    public void setTrader(Referential.Trader trader) {
        this.trader = trader;
    }

}