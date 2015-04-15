package Model;

/**
 * Created by cramo on 15/04/15.
 */
public class Data {
    private String columnFamily  ;
    private String qualifier ;
    private String value ;

    public Data(String columnFamily, String qualifier) {
        this.columnFamily = columnFamily;
        this.qualifier = qualifier;
    }

    public Data(String columnFamily, String qualifier, String value) {
        this.columnFamily = columnFamily;
        this.qualifier = qualifier;
        this.value = value;
    }


    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
