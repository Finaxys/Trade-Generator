package domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

//import org.apache.hadoop.hbase.util.Bytes;

public enum Term {

    OVERNIGHT("0D"), TOMNEXT("1D"), SPOT("2D"), ONE_WEEK("1W"), TWO_WEEKS("2W"), ONE_MONTH(
            "1M"), TWO_MONTHS("2M"), THREE_MONTHS("3M"), SIX_MONTHS("6M"), TWELVE_MONTHS(
            "12M");

    private static final Map<String, Term> BY_CODE_MAP = new LinkedHashMap<String, Term>();
    private String shortCode;

    static {
        for (Term rae : Term.values()) {
            BY_CODE_MAP.put(rae.shortCode, rae);
        }
    }

    private Term(String shortCode) {
        this.shortCode = shortCode;
    }

    public static Term getRandom() {
        Random random = new Random();
        return Term.values()[random.nextInt(Term.values().length - 1)];
    }

    @Override
    public String toString() {
        return shortCode;
    }

    public static boolean isTerm(String value) {
        if (value == null)
            return false;
        return BY_CODE_MAP.keySet().contains(value);
    }

    public static Term forCode(String code) {
        return BY_CODE_MAP.get(code);
    }

    public String getShortCode() {
        return shortCode;
    }

    // public static Term convert(byte[] value) {
    // if (value == null || value.length == 0) return null;
    // return Term.forCode(Bytes.toString(value));
    // }
    //
    // public static byte[] toBytes(Term value) {
    // return Bytes.toBytes(value.shortCode);
    // }
}
