package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Referential.Counterpart;

public abstract class TradeEvent implements Comparable<TradeEvent> {
    protected static long counter = 0;
    protected long id;
    protected Way way;
    protected Date date;
    protected Referential.Counterpart counterpart;
    protected Book book;
    protected Referential.Portfolio portfolio;
    protected TradeGenerator instrument;
    protected List<Node> nodes;

    public TradeEvent(String reference, Way way, Date date,
            Counterpart counterpart, Book book, TradeGenerator instrument) {
        super();
        this.id = counter++;
        this.way = way;
        this.date = date;
        this.instrument = instrument;
        this.counterpart = counterpart;
        this.book = book;
    }

    public abstract double getAmount();

    public List<Node> getNodes() {
        nodes = new ArrayList<Node>();
        return nodes;
    }

    public void addNode(List<Node> root, String name, String value,
            List<Node> nodes) {
        Node node = new Node();
        node.name = name;
        node.value = value;
        node.nodes = nodes;
        root.add(node);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(TradeEvent trade) {
        if (!(this.getDate().equals(trade.getDate())))
            return (int) (this.getDate().getTime() - trade.getDate().getTime());
        if (!this.getBook().getPortFolios().getName()
                .equalsIgnoreCase(trade.getBook().getPortFolios().getName()))
            return this.getBook().getPortFolios().getName()
                    .compareTo(trade.getBook().getPortFolios().getName());

        if (!this.getBook().getName()
                .equalsIgnoreCase(trade.getBook().getName()))
            return this.getBook().getName()
                    .compareTo(trade.getBook().getName());
        if (!this.instrument.getName().equalsIgnoreCase(
                trade.getInstrument().getName()))
            return this.instrument.getName().compareTo(
                    trade.getInstrument().getName());
        return this.getWay().name().compareTo(trade.getWay().name());
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public TradeGenerator getInstrument() {
        return instrument;
    }

    public void setInstrument(TradeGenerator instrument) {
        this.instrument = instrument;
    }

    public Way getWay() {
        return way;
    }

    public void setWay(Way way) {
        this.way = way;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public class Node {
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public List<Node> getNodes() {
            return nodes;
        }
        public void setNodes(List<Node> nodes) {
            this.nodes = nodes;
        }
        private String name;
        private String value; // If empty take list of nodes
        private List<Node> nodes;
    }

    public static long getCounter() {
        return counter;
    }

    public static void setCounter(long counter) {
        TradeEvent.counter = counter;
    }

    public Referential.Counterpart getCounterpart() {
        return counterpart;
    }

    public void setCounterpart(Referential.Counterpart counterpart) {
        this.counterpart = counterpart;
    }

    public Referential.Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Referential.Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}