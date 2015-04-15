package Model;

/**
 * Created by cramo on 15/04/15.
 */
public class HRow {
    private String tableName  ;
    private String rowKey  ;
    private Data data[]  ;

    public HRow(String tableName, String rowKey, Data[] data) {
        this.tableName = tableName;
        this.rowKey = rowKey;
        this.data = data;
    }

    public Data[] getData() {
        return data;
    }

    public String getTableName() {
        return tableName;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }
}
