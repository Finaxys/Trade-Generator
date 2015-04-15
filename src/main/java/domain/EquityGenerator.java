package domain;

import generals.Generals;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class EquityGenerator extends TradeGenerator {
    private int partSell;
    private int ownCountry;
    private int budgetTolerance;
    private List<Integer> loanPerTrade;
    private List<Locality> localities;
    private List<Way> ways;
    private int amountPerDay;
    
    @Override
    public void init(int amount) {
        super.init(amount);

        amountPerDay = amount;
        double rdmBudgetTolerance;
        double rdmVolumetryTolerance;
        double toleredVolumetry;
        Random random = new Random();

        rdmBudgetTolerance = this.getBudgetTolerance() * 2
                * (random.nextDouble() - 0.5) / 100;
        rdmVolumetryTolerance = getVolumetryTolerance() * 2
                * (random.nextDouble() - 0.5) / 100;

        amountPerDay += rdmBudgetTolerance * amountPerDay;

        // calculation of number of trades to distribute per day
        toleredVolumetry = (1 - rdmVolumetryTolerance) * volumetry;
        roundedVolumetry = (int) toleredVolumetry;

        loanPerTrade = sparseMoney(roundedVolumetry, amountPerDay);

        localities = TradeGenerator.tableaubin(roundedVolumetry,
                this.getOwnCountry(), Locality.class);
        ways = TradeGenerator.tableaubin(roundedVolumetry, this.getPartSell(),
                Way.class);
    }

    @Override
    public TradeEvent generate(Book book, Date date) {
        super.generate(book, date);

        Referential ref = Referential.getInstance();
        Generals generals = Generals.getInstance();

        // declaration des tirages au sort sous contrainte
        Referential.Depositary depositary;
        Referential.Counterpart counterpart;
        Referential.Trader trader;
        Referential.Product product;
        Referential.Currency currency = null;

        List<Referential.Product> equities = ref.subList(ref.getProducts(),
                "type", "EQUITY");

        // tirage au sort sous contrainte
        depositary = ref.getRandomElement(ref.getDepositaries());
        counterpart = ref.getRandomElement(ref.getCounterparts());
        currency = ref.getRandomElement(ref.getCurrencies());
        trader = ref.getTrader(ref, currency.getCode(), "equity");
        if (localities.get(0).toString() == "NATIONAL")
            currency = ref.subList(ref.getCurrencies(), "country",
                    generals.getOwnCountry()).get(0);
        else
            currency = ref.getRandomElement(ref.subList(ref.getCurrencies(),
                    "country", generals.getOwnCountry()));
        product = ref.getRandomElement(equities);

        float randToleranceQuantities;

        double randomquantity;
        // sharing of amount per trade
        randToleranceQuantities = (float) Math.random();
        Random random = new Random();
        // set random price -+3%
        randomquantity = 6 * (random.nextDouble() - 0.5);
        float price = product.getPrice();

        price = (float) (price * (1 + randomquantity / 100));
        int quantity = (int) (randToleranceQuantities * loanPerTrade.get(0) / price);
        TradeEquity tradeEquity = new TradeEquity(this, ways.get(0), date,
                counterpart, book);
        tradeEquity.setProduct(product);
        tradeEquity.setBook(book);
        tradeEquity.setDepositary(depositary);
        tradeEquity.setTrader(trader);
        tradeEquity.setPrice(price);
        tradeEquity.setQuantity(quantity);
        localities.remove(0);
        ways.remove(0);
        loanPerTrade.remove(0);

        return tradeEquity;
    }

    public int getOwnCountry() {
        return ownCountry;
    }

    public void setOwnCountry(int ownCountry) {
        this.ownCountry = ownCountry;
    }

    public int getPartSell() {
        return partSell;
    }

    public void setPartSell(int partSell) {
        this.partSell = partSell;
    }

    public double getBudgetTolerance() {
        return budgetTolerance;
    }

    public void setBudgetTolerance(int btol) {
        this.budgetTolerance = btol;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
