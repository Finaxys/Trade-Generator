package generals;

import java.util.List;

import domain.Businessunit;

public class Generals {
    private static Generals INSTANCE = new Generals();

    private int numberOfDay;
    private String bankName;
    private int budget;
    private String ownCountry;
    private List<Businessunit> businessunits;

    private Generals() {
        super();
    }

    public static Generals getInstance() {
        return INSTANCE;
    }

    public void init(int nbday, String bankname, int budget, String owncountry,
            List<Businessunit> bu) {
        this.numberOfDay = nbday;
        this.bankName = bankname;
        this.budget = budget;
        this.ownCountry = owncountry;
        this.businessunits = bu;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public String getBankName() {
        return bankName;
    }

    public int getBudget() {
        return budget;
    }

    public String getOwnCountry() {
        return ownCountry;
    }

    public List<Businessunit> getBusinessunits() {
        return businessunits;
    }

}