package generals;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import domain.TradeEvent;

import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
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

    private static long stackedPuts = 0;
    private static long flushedPuts = 0;
    private static boolean autoflush;
    private static long stackPuts;

    OutputHBase()
    {
        if (init)
            return;

        init = true;
        conf = HBaseConfiguration.create();

        String tableName = System.getProperty("hbase.table", "trades");
        String cfName = System.getProperty("hbase.cf", "cf");

        autoflush = Boolean.parseBoolean(System.getProperty("hbase.autoflush", "false"));
        stackPuts = Integer.parseInt(System.getProperty("hbase.stackputs", "1000"));

        Configuration conf = HBaseConfiguration.create() ;
        try {
            String minicluster = System.getProperty("hbase.conf.minicluster", "");
            if (!minicluster.isEmpty())
                conf.addResource(new FileInputStream(minicluster));
            else
            {
                conf.addResource(new File(System.getProperty("hbase.conf.core", "core-site.xml")).getAbsoluteFile().toURI().toURL());
                conf.addResource(new File(System.getProperty("hbase.conf.hbase", "hbase-site.xml")).getAbsoluteFile().toURI().toURL());
                conf.addResource(new File(System.getProperty("hbase.conf.hdfs", "hdfs-site.xml")).getAbsoluteFile().toURI().toURL());
            }
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "Could not get hbase configuration files", e);
            return;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not get minicluster configuration files", e);
            return;
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

        tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        try {
            tableDescriptor.addFamily(new HColumnDescriptor(cfName));
            admin.createTable(tableDescriptor);
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Table already created");
        }

        try {
            LOGGER.log(Level.INFO, "Getting table information");
            table = new HTable(conf, "trades");
//            AutoFlushing
            table.setAutoFlushTo(autoflush);
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
            else if (node.getValue().getClass().getSimpleName().equals("Float"))
                p.add(Bytes.toBytes("cf"), Bytes.toBytes(node.getName()), Bytes.toBytes((Float) node.getValue()));
            else if (node.getValue().getClass().getSimpleName().equals("Double"))
                p.add(Bytes.toBytes("cf"), Bytes.toBytes(node.getName()), Bytes.toBytes((Double) node.getValue()));
            else if (node.getValue().getClass().getSimpleName().equals("Character"))
                p.add(Bytes.toBytes("cf"), Bytes.toBytes(node.getName()), Bytes.toBytes((Character) node.getValue()));
            else if (node.getValue().getClass().getSimpleName().equals("Long"))
                p.add(Bytes.toBytes("cf"), Bytes.toBytes(node.getName()), Bytes.toBytes((Long) node.getValue()));
            else
                p.add(Bytes.toBytes("cf"), Bytes.toBytes(node.getName()), Bytes.toBytes((Integer) node.getValue()));

        }
        table.put(p);
    }

    public void outputTrade(TradeEvent trade)
    {

        try {
            putData(trade);
            ++stackedPuts;

            // Flushing every X
            if (!autoflush && stackedPuts > stackPuts)
                flushPuts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flushPuts(){
        try {
            table.flushCommits();
        } catch (InterruptedIOException e) {
            LOGGER.log(Level.SEVERE, "Could not flush table", e);
        } catch (RetriesExhaustedWithDetailsException e) {
            LOGGER.log(Level.SEVERE, "Could not flush table ", e);
        }

        flushedPuts += stackedPuts;
        stackedPuts = 0;
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

        try {
            table.flushCommits();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        } catch (RetriesExhaustedWithDetailsException e) {
            e.printStackTrace();
        }

        closed = true;
        try {
            table.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

