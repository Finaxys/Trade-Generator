package HBaseTools;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;

import java.io.IOException;

/**
 * Created by cramo on 15/04/15.
 */
public interface IHBaseConnection {
    public HConnection connect() throws IOException;

    public void disconnect() throws IOException;

    public Configuration getConfiguration();
}
