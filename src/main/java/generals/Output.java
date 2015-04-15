package generals;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.TradeEvent;
import domain.TradeGenerator;

public abstract class Output {
    protected static PrintWriter writer;

    protected Format format;
    protected String path;
    protected List<TradeGenerator> generators;
    protected Boolean isStp;
    protected Layer layer;
    protected List<TradeEvent> tradeEvents;
    protected int id;
    static protected SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

    protected static int counter = 0;

    public Output() 
    {
        this.tradeEvents = new ArrayList<TradeEvent>();
        this.id = counter++;
    }

    protected PrintWriter getWriter(TradeEvent trade)
            throws FileNotFoundException, UnsupportedEncodingException {
        String path = OutputManager.OUTPUT_PATH + "stp" + trade.getId() + "-"
                + formater.format(trade.getDate()) + "."
                + format.toString().toLowerCase();

        return new PrintWriter(path, OutputManager.OUTPUT_ENCODING);
    }

    protected PrintWriter getWriter(Date date) throws FileNotFoundException,
            UnsupportedEncodingException {
        String path = OutputManager.OUTPUT_PATH + "batch" + id + "-" + formater.format(date) + "."
                + format.toString().toLowerCase();

        return new PrintWriter(path, OutputManager.OUTPUT_ENCODING);
    }


    public void addTradeEvent(TradeEvent te) {
        if (isStp)
            outputTrade(te);
        else
            tradeEvents.add(te);
    }
    
    public abstract void outputTrade(TradeEvent te);

    public abstract void outputTrades();

    public List<TradeGenerator> getGenerators() {
        return generators;
    }

    public void setGenerators(List<TradeGenerator> gen) {
        this.generators = gen;
    }

    public int getId() {
        return id;
    }

    public enum Format {
        CSV, JSON, XML, HBASE
    }

    public enum Layer {
        FS, MQ, HBASE
    }

    public Boolean getIsStp() {
        return isStp;
    }

    public void setIsStp(Boolean isStp) {
        this.isStp = isStp;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }
}
