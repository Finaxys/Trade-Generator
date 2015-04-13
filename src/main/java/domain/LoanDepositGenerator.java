package domain;

import generals.Generals;
import generals.Indexation;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LoanDepositGenerator extends TradeGenerator {
    public int partSell;
    public int ownCountry;
    public int repartitionTolerance;
    public float rateValue;
    public int rateValueTolerance;
    public int partRateVariable;
    private int amountPerDay;
    private List<Integer> loanPerTrade;
    private List<Locality> listLocality;
    private List<Way> listWay;
    private List<RateType> listRatetype;

    public LoanDepositGenerator() {
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void init(int amount) {
        super.init(amount);

        double rand1, rand2;
        double toleredVolumetry;
        Random random = new Random();
        amountPerDay = amount;
        rand1 = this.repartitionTolerance * 2 * (random.nextDouble() - 0.5)
                / 100;
        rand2 = this.volumetryTolerance * 2 * (random.nextDouble() - 0.5) / 100;
        amountPerDay = (int) (amountPerDay + rand1 * amountPerDay);

        // calculation of number of trades to distribute per day
        toleredVolumetry = (1 - rand2) * volumetry;
        roundedVolumetry = (int) toleredVolumetry;
        loanPerTrade = sparseMoney(roundedVolumetry, amountPerDay);
        listLocality = tableaubin(roundedVolumetry, this.ownCountry,
                Locality.class);
        listWay = tableaubin(roundedVolumetry, this.partSell, Way.class);
        listRatetype = tableaubin(roundedVolumetry, this.partRateVariable,
                RateType.class);

    }

    @Override
    public TradeEvent generate(Book book, Date date) {
        super.generate(book, date);

        Referential ref = Referential.getInstance();
        Generals generals = Generals.getInstance();

        // declaration des tirages au sort sous contrainte
        Referential.Depositary d1;
        Referential.Counterpart c1;
        Referential.Trader tr1;
        Referential.Currency cur1;

        d1 = ref.getRandomElement(ref.getDepositaries());

        c1 = ref.getRandomElement(ref.getCounterparts());

        if (listLocality.get(0).toString() == "NATIONAL")
            cur1 = ref.subList(ref.getCurrencies(), "country",
                    generals.getOwnCountry()).get(0);
        else
            cur1 = ref.getRandomElement(ref.subList(ref.getCurrencies(),
                    "country", generals.getOwnCountry()));

        tr1 = ref.getTrader(ref, cur1.getCode(), "loandepo");

        TradeLoan tl = new TradeLoan(this, "reference", listWay.get(0), date,
                date, c1, book, date, date, (double) 0,
                TradeGenerator.randEnum(Indexation.class), "isin?",
                TradeGenerator.randEnum(RateType.class),
                (double) this.rateValue, (double) this.rateValue / 2,
                TradeGenerator.randEnum(Term.class),
                TradeGenerator.randEnum(BaseCalcul.class), loanPerTrade.get(0),
                cur1, d1, tr1);

        listLocality.remove(0);
        listWay.remove(0);
        listRatetype.remove(0);
        loanPerTrade.remove(0);

        return tl;
    }
}
