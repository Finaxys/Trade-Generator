package generals;

import DAO.HBaseDAO;
//import HBaseTools.IHBaseConnection;
import HBaseTools.HBaseMiniConnection;
import HBaseTools.IHBaseConnection;
import Model.Data;
//import Model.HRow;
import Model.HRow;
import domain.TradeEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OutputHBase extends Output
{
    private static final Logger LOGGER = Logger.getLogger(OutputHBase.class
            .getName());

    private static IHBaseConnection hbaseconnection = new HBaseMiniConnection("/tmp/configuration.xml") ;
    private static HBaseDAO dao = new HBaseDAO(hbaseconnection)  ;

    private static void connection() throws IOException
    {
        dao.connect();
    }

    public static void createTable() throws IOException
    {
        connection();

        String[] ColumnFamilies=new String[] {"cf1"};
        dao.createTable("Trades", ColumnFamilies);

        dao.disconnect();
    }

    //mettre tout les qualifiers dans un seul put
    public static void putData(TradeEvent tradeEvent) throws IOException {
        connection();

        List<Data> data = new ArrayList<Data>();
        for (TradeEvent.Node node : tradeEvent.getNodes())
            data.add(new Data("cf1", node.getName(), (String)node.getValue()));
        HRow row = new HRow("Trades", Long.toString(tradeEvent.getId()), (Data[]) data.toArray());
        dao.putRow(row);

        dao.disconnect();
    }

    public void outputTrade(TradeEvent trade)
    {
        List<TradeEvent.Node> nodes = trade.getNodes();

        try {
            putData(trade);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outputTrades() {
        if (tradeEvents.isEmpty())
            return;

        for (TradeEvent trade : tradeEvents)
            outputTrade(trade);

        tradeEvents.clear();
    }
}

