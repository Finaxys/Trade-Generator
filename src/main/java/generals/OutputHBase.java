package generals;

import DAO.HBaseDAO;
import HBaseTools.HBaseMiniConnection;
import HBaseTools.IHBaseConnection;
import Model.Data;
import Model.HRow;
import domain.TradeEvent;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by cramo on 15/04/15.
 */
/*
public class OutputHBase extends Output{

    private static HBaseDAO dao;


    private static void connection() throws IOException
    {
        IHBaseConnection hbaseconnection=new HBaseMiniConnection("/tmp/configuration.xml") ;
        HBaseDAO dao = new HBaseDAO(hbaseconnection)  ;
        dao.connect();
    }
    public static void createTable() throws IOException
    {
        connection();
        String[] ColumnFamilies=new String[] {"cf1"};
        dao.createTable("Trades", ColumnFamilies);
        dao.disconnect();
    }

    /public static void putData(TradeEvent tradeEvent) throws IOException
    {
        connection();
        Data[] data =new Data[]{new Data("cf1", Integer.toString(tradeEvent.getId(), )

    public void outputTrade(TradeEvent trade)
    {

        List<TradeEvent.Node> nodes = trade.getNodes();

        //put

        for (TradeEvent.Node node : nodes)
            try {
                putData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        writer.write(node.getName() + ",");
        writer.write(System.lineSeparator());

        writer.close();
    }

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
}*/

