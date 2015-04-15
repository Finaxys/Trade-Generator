package domain;

import generals.Output;
import generals.OutputManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class TradeGenerator {
    protected String name;
    protected int amount;
    protected int volumetry;
    protected int roundedVolumetry;
    protected int tradeGenerated = 0;
    protected int volumetryTolerance;
    protected int budgetTolerance;

    static int cnt = 0;

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }


    public void setVolumetry(int volumetry) {
        this.volumetry = volumetry;
    }

    public int getVolumetry() {
        return volumetry;
    }

    public void setVolumetryTolerance(int vt) {
        this.volumetryTolerance = vt;
    }

    public int getVolumetryTolerance() {
        return volumetryTolerance;
    }

    public int getRoundedVolumetry() {
        return roundedVolumetry;
    }

    public int getTradeGenerated() {
        return tradeGenerated;
    }

    public void init(int amountDay) {
        tradeGenerated = 0;
    }

    public TradeEvent generate(Book b, Date date) {
        ++tradeGenerated;

        return null;
    }

    public static List<Output> getOutputsFromTrade(TradeEvent trade) {
        List<Output> outputs = new ArrayList<Output>();

        for (Output op : trade.getBook().getPortFolios().getBu().getOutputs())
            if (op.getGenerators().contains(trade.getInstrument()))
                outputs.add(op);

        return outputs;
    }

    public static void tradeGenerated(TradeEvent trade) {
        List<Output> outputs = getOutputsFromTrade(trade);
        for (Output output : outputs)
            if (output.isStp())
                OutputManager.getInstance().outputTrade(output, trade);
            else
                output.addTradeEvent(trade);
    }

    public static <T extends Enum<T>> List<T> tableaubin(int size, int ratio,
            Class<T> e) {
        List<T> trueArray = new ArrayList<T>();
        int j, i;
        int national = (ratio * (size - 1)) / 100;
        T tp1 = e.getEnumConstants()[0];
        T tp2 = e.getEnumConstants()[1];

        for (i = 0; i < size; i++)
            trueArray.add(tp1);

        for (j = 0; j < national; j++)
            trueArray.set(j, tp2);

        Collections.shuffle(trueArray);

        return trueArray;
    }

    public static <T extends Enum<T>> T randEnum(Class<T> e) {
        Random rand = new Random();
        return e.getEnumConstants()[rand
                .nextInt(e.getEnumConstants().length - 1)];
    }

    public static List<Integer> sparseMoney(int volumetry, int montant)

    {
        List<Integer> ints = new ArrayList<Integer>();

        Random random = new Random();
        int somme = 0;
        int volumetryorder = Math.max((int) 0.1 * volumetry, 3);
        int randint = 0;

        for (int i = 0; i < volumetry; i++) {
            randint = random.nextInt(volumetryorder) + 5;
            somme = somme + randint;
            ints.add(randint);
        }
        for (int i = 0; i < volumetry; i++)
            ints.set(i, ints.get(i) * montant / somme);

        return ints;
    }

    @Override
    public boolean equals(Object ins) {
        if (!(ins instanceof TradeGenerator))
            return false;

        return ((TradeGenerator) ins).getName()
                .equalsIgnoreCase(this.getName());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public int getMontant() {
        return amount;
    }

    public void setMontant(int montant) {
        this.amount = montant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}