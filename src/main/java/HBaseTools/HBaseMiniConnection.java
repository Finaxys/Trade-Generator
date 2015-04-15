package HBaseTools;


import HBaseTools.IHBaseConnection;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

import java.io.FileInputStream;
import java.io.IOException;

public class HBaseMiniConnection implements IHBaseConnection {

    private String path ;
    private Configuration configuration;
    private HConnection connection;


    public HBaseMiniConnection(String filePath){
        path=filePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HConnection connect() throws IOException {

        setConfiguration(loadConfiguration());
        setConnection(HConnectionManager.createConnection(getConfiguration()));
        return getConnection();

    }

    public void disconnect() throws IOException {

        getConnection().close();
    }

    public Configuration loadConfiguration() throws IOException{
        Configuration conf = new Configuration();
        conf.addResource(new FileInputStream(path));
        return conf ;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public HConnection getConnection() {
        return connection;
    }

    public void setConnection(HConnection connection) {
        this.connection = connection;
    }
}
