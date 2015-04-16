package generals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.TradeEvent;

public class OutputCSV extends Output {
    private static final Logger LOGGER = Logger.getLogger(OutputCSV.class
            .getName());
    
    public OutputCSV() {
        super();
        extension = "csv";
    }

    @Override
    public void outputTrade(TradeEvent trade)
    {
        try
        {
            writer = getWriter(trade);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not open file", e);
            return ;
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, "Wrong encoding", e);
            return ;
        }

        List<TradeEvent.Node> nodes = trade.getNodes();

        for (TradeEvent.Node node : nodes)
            writer.write(node.getName() + ",");
        writer.write(System.lineSeparator());
        for (TradeEvent.Node node : nodes)
            writer.write(node.getValue() + ",");
        writer.write(System.lineSeparator());

        writer.close();
    }

    @Override
    public void outputTrades()
    {
        if (tradeEvents.isEmpty())
            return;

        try
        {
            writer = getWriter(tradeEvents.get(0).getDate());
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not open file", e);
            return ;
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, "Wrong encoding", e);
            return ;
        }

        List<Class<? extends TradeEvent>> tradeClass = new ArrayList<Class<? extends TradeEvent>>();
        List<String> header = new ArrayList<String>();

        // Get header
        for (TradeEvent trade : tradeEvents) {
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

        for (TradeEvent trade : tradeEvents) {
            List<TradeEvent.Node> nodes = trade.getNodes();

            // Check each field of header - if not present : empty ','
            for (String field : header) {
                for (TradeEvent.Node node : nodes)
                    if (node.getName().equals(field)) {
                        writer.write("" + node.getValue());
                        break;
                    }

                writer.write(",");
            }

            writer.write(System.lineSeparator());
        }

        writer.close();
        tradeEvents.clear();
    }
        
}
