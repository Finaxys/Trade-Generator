package generals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Businessunit;
import domain.TradeEvent;

public class OutputManager {
    private static final Logger LOGGER = Logger.getLogger(OutputManager.class
            .getName());

    private static OutputManager instance = new OutputManager();
    private static PrintWriter writer;
    private final static String OUTPUT_PATH = "trades/";
    private final static String OUTPUT_ENCODING = "UTF-8";

    private OutputManager() {
        File check = new File(OUTPUT_PATH + "test");

        if (!check.exists())
            check.getParentFile().mkdirs();
    }

    public static OutputManager getInstance() {
        return instance;
    }

    private PrintWriter getWriter(Output output, TradeEvent trade)
            throws FileNotFoundException, UnsupportedEncodingException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        String path = OUTPUT_PATH + "stp" + trade.getId() + "-"
                + formater.format(trade.getDate()) + "."
                + output.getFormat().toString().toLowerCase();

        return new PrintWriter(path, OUTPUT_ENCODING);
    }

    private PrintWriter getWriter(Output output) throws FileNotFoundException,
            UnsupportedEncodingException {
        String date = "";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        if (!output.getTrades().isEmpty())
            date = formater.format(output.getTrades().get(0).getDate());

        String path = OUTPUT_PATH + "batch" + output.getId() + "-" + date + "."
                + output.getFormat().toString().toLowerCase();

        return new PrintWriter(path, OUTPUT_ENCODING);
    }

    public void outputTrades() {
        try {
            for (Businessunit bu : Generals.getInstance().getBusinessunits())
                for (Output output : bu.getOutputs()) {
                    writer = getWriter(output);

                    outputByFormat(output);

                    writer.close();

                    output.getTrades().clear();
                }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not open file", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, "Wrong encoding", e);
        }
    }

    private void outputByFormat(Output output) {
        if (output.getFormat() == Output.OutputFormat.XML)
            for (TradeEvent trade : output.getTrades()) {
                writer.write("<trades>" + System.lineSeparator());
                writeXMLTrade(trade);
                writer.write("</trades>" + System.lineSeparator());
            }
        else if (output.getFormat() == Output.OutputFormat.CSV)
            manageCSV(output);
    }

    private void manageCSV(Output output) {
        List<Class<? extends TradeEvent>> tradeClass = new ArrayList<Class<? extends TradeEvent>>();
        List<String> header = new ArrayList<String>();

        // Get header
        for (TradeEvent trade : output.getTrades()) {
            if (!tradeClass.contains(trade.getClass())) {
                tradeClass.add(trade.getClass());

                for (TradeEvent.Node node : trade.getNodes())
                    if (!header.contains(node.getName()))
                        header.add(node.getName());
            }
        }

        // Header
        for (String field : header)
            writer.write(field + ",");
        writer.write(System.lineSeparator());

        for (TradeEvent trade : output.getTrades()) {
            List<TradeEvent.Node> nodes = trade.getNodes();

            // Check each field of header - if not present : empty ','
            for (String field : header) {
                for (TradeEvent.Node node : nodes)
                    if (node.getName().equals(field)) {
                        writer.write(node.getValue());
                        break;
                    }

                writer.write(",");
            }

            writer.write(System.lineSeparator());
        }
    }

    private void writeXMLTrade(TradeEvent trade) {
        writer.write("<trade>" + System.lineSeparator());
        for (TradeEvent.Node node : trade.getNodes())
            writeXMLNode(node);
        writer.write("</trade>" + System.lineSeparator());
    }

    private void writeCSVTrade(TradeEvent trade) {
        List<TradeEvent.Node> nodes = trade.getNodes();

        for (TradeEvent.Node node : nodes)
            writer.write(node.getName() + ",");
        writer.write(System.lineSeparator());
        for (TradeEvent.Node node : nodes)
            writer.write(node.getValue() + ",");
        writer.write(System.lineSeparator());
    }

    public void outputTrade(Output output, TradeEvent trade) {
        try {
            writer = getWriter(output, trade);
            writeTrade(output, trade);
            writer.close();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not open file", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, "Wrong encoding", e);
        }
    }

    private void writeTrade(Output output, TradeEvent trade) {
        if (output.getFormat().equals(Output.OutputFormat.XML))
            writeXMLTrade(trade);
        else if (output.getFormat().equals(Output.OutputFormat.CSV))
            writeCSVTrade(trade);
    }

    private void writeXMLNode(TradeEvent.Node node) {
        // Check if there is nodes inside node -> recursion
        if (node.getNodes() != null) {
            writer.write("<" + node.getName() + ">" + System.lineSeparator());
            for (TradeEvent.Node n : node.getNodes())
                writeXMLNode(n);
            writer.write("</" + node.getName() + ">" + System.lineSeparator());
        }
        // Only simple node -> print it
        else
            writer.write("<" + node.getName() + ">" + node.getValue() + "</" + node.getName()
                    + ">" + System.lineSeparator());
    }
}
