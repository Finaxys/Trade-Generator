package generals;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import domain.TradeEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OutputHBase extends Output
{
    private static final Logger LOGGER = Logger.getLogger(OutputHBase.class
            .getName());

    private static boolean init = false;
    private static boolean closed = false;
    private static Configuration conf;
    private static HConnection connection;
    private static HBaseAdmin admin;
    private static HTableDescriptor tableDescriptor;
    private static HTable table;

    OutputHBase()
    {
        if (init)
            return;

        init = true;
        conf = HBaseConfiguration.create();
        try {
            conf.addResource(new FileInputStream("/tmp/configuration"));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not get hbase configuration files", e);
            return ;
        }

        conf.reloadConfiguration();

        try {
            connection = HConnectionManager.createConnection(conf);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create Connection", e);
        }

        HBaseAdmin admin = null;
        try {
            admin = new HBaseAdmin(connection);
        } catch (MasterNotRunningException e) {
            LOGGER.log(Level.SEVERE, "Master server not running", e);
        } catch (ZooKeeperConnectionException e) {
            LOGGER.log(Level.SEVERE, "Could not connect to ZooKeeper", e);
        }

        tableDescriptor = new HTableDescriptor(TableName.valueOf("trades"));
        try {
            tableDescriptor.addFamily(new HColumnDescriptor("cf"));
            admin.createTable(tableDescriptor);
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Table already created");
        }

        try {
            LOGGER.log(Level.INFO, "Getting table information");
            table = new HTable(conf, "trades");
//            AutoFlushing
            table.setAutoFlushTo(true);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not get table " + "trades", e);
        }
    }

    public void putData(TradeEvent tradeEvent) throws IOException {
        Put p = new Put(Bytes.toBytes(tradeEvent.getId()));
        List<TradeEvent.Node> nodes = tradeEvent.getNodes();
        for (TradeEvent.Node node : nodes) {
            if (node.getValue().getClass().getSimpleName().equals("String"))
                p.add(Bytes.toBytes("cf"), Bytes.toBytes(node.getName()), Bytes.toBytes((String) node.getValue()));
            else
                p.add(Bytes.toBytes("cf"), Bytes.toBytes(node.getName()), Bytes.toBytes((Integer) node.getValue()));

        }
        table.put(p);
    }

    public void outputTrade(TradeEvent trade)
    {

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

    public void close()
    {
        if (!closed)
            return;

        closed = true;
        try {
            table.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

