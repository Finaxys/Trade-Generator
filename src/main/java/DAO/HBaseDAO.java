package DAO;

import HBaseTools.IHBaseConnection;
import Model.Data;
import Model.HRow;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sr-readonly
 * Date: 4/15/15
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class HBaseDAO {


    protected IHBaseConnection HBaseConnection ;
    private Configuration Config;
    private HConnection connection;
    private HBaseAdmin admin ;


    public  HBaseDAO(IHBaseConnection HBaseConnection ) {


        this.HBaseConnection=HBaseConnection;
    }


    public void  connect( ) throws IOException {

        setConnection(HBaseConnection.connect());
        setConfig(HBaseConnection.getConfiguration());
        setAdmin(new HBaseAdmin(getConnection()));

    }

    public void disconnect() throws IOException {

        getHBaseConnection().disconnect();
    }

    public void createTable(String tableName ,String[] ColumnFamilies ) throws IOException {

        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

        for (String columnFamily : ColumnFamilies) {
            tableDescriptor.addFamily(new HColumnDescriptor((columnFamily)));
        }

        getAdmin().createTable(tableDescriptor);
    }



    public void putRow(HRow row) throws IOException {

        HTable table = new HTable(getConfig(), row.getTableName());
        Put p = new Put(Bytes.toBytes(row.getRowKey()));
        for (Data insert : row.getData()) {
            p.add(Bytes.toBytes(insert.getColumnFamily()), Bytes.toBytes(insert.getQualifier()),Bytes.toBytes(insert.getValue()));
        }
        table.put(p);
        table.close();


    }

    public void getRow(HRow row) throws IOException {

        HTable table = new HTable(getConfig(), row.getTableName());
        // Instantiating Get class
        Get g = new Get(Bytes.toBytes(row.getRowKey()));
        // Reading the data
        Result result = table.get(g);
        // Reading values from Result class object
        for (Data select : row.getData()) {
            byte[] value = result.getValue(Bytes.toBytes(select.getColumnFamily()), Bytes.toBytes(select.getQualifier()));
            System.out.println("data: " + Bytes.toString(value));
        }
        table.close();
    }


    public IHBaseConnection getHBaseConnection() {
        return HBaseConnection;
    }

    public void setHBaseConnection(IHBaseConnection HBaseConnection) {
        this.HBaseConnection = HBaseConnection;
    }


    public Configuration getConfig() {
        return Config;
    }

    public void setConfig(Configuration config) {
        this.Config = config;
    }

    public HConnection getConnection() {
        return connection;
    }

    public void setConnection(HConnection connection) {
        this.connection = connection;
    }

    public HBaseAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(HBaseAdmin admin) {
        this.admin = admin;
    }
}
