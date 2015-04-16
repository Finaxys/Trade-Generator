package generals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.TradeEvent;

public class OutputXML extends Output {
    private static final Logger LOGGER = Logger.getLogger(OutputXML.class
            .getName());

    public OutputXML()
    {
        super();
        extension = "xml";
    }
    
    public void writeTrade(TradeEvent trade)
    {
        writer.write("<trade>" + System.lineSeparator());
        for (TradeEvent.Node node : trade.getNodes())
            writeXMLNode(node);
        writer.write("</trade>" + System.lineSeparator());
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

        writeTrade(trade);

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

        writer.write("<trades>" + System.lineSeparator());
        for (TradeEvent trade : tradeEvents)
            writeTrade(trade);
        writer.write("</trades>" + System.lineSeparator());

        writer.close();
        tradeEvents.clear();
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
