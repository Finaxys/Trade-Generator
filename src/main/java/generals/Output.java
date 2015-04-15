package generals;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import domain.TradeEvent;
import domain.TradeGenerator;

public abstract class Output {
    protected static final Logger LOGGER = Logger.getLogger(Output.class
            .getName());
    protected static PrintWriter writer;

    protected OutputFormat format;
    protected String path;
    protected List<TradeGenerator> generators;
    protected Boolean isStp;
    protected Layer layer;
    protected List<TradeEvent> tradeEvents;
    protected int id;
    static protected SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

    protected static int counter = 0;

    Output(String format, String path, List<TradeGenerator> gen, Boolean stp,
            String lay) {
        this.tradeEvents = new ArrayList<TradeEvent>();
        this.id = counter++;
        this.format = OutputFormat.valueOf(format.toUpperCase());
        this.path = path;
        this.generators = gen;
        this.isStp = stp;
        this.layer = Layer.valueOf(lay);
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

    public OutputFormat getFormat() {
        return format;
    }

    public String getPath() {
        return path;
    }

    public List<TradeGenerator> getGenerators() {
        return generators;
    }

    public Boolean isStp() {
        return isStp;
    }

    public Layer getLayer() {
        return layer;
    }

    public int getId() {
        return id;
    }

    public enum OutputFormat {
        CSV, JSON, XML
    }

    public enum Layer {
        FS, MQ, HBASE
    }
}
