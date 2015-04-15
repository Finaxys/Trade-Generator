package domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Referential {
    private static final Logger LOGGER = Logger.getLogger(Referential.class
            .getName());
    private static Referential INSTANCE = new Referential();

    private List<Counterpart> counterparts = null;
    private List<Product> products = null;
    private List<Currency> currencies = null;
    private List<CurrencyTrader> currencyTraders = null;
    private List<Depositary> depositaries = null;
    private List<Portfolio> portfolios = null;

    private Referential() {
    }

    public static Referential getInstance() {
        return INSTANCE;
    }

    public <T> T getRandomElement(List<T> list) {
        Random randomGenerator = new Random();
        return list.get(randomGenerator.nextInt(list.size()));
    }

    public <T> List<T> subList(List<T> list, String fieldname, String filter) {
        List<T> subT = new ArrayList<T>();
        try {
            for (T te : list) {
                Field field = te.getClass().getDeclaredField(fieldname);
                field.setAccessible(true);

                if (field.get(te).equals(filter))
                    subT.add((T) te);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Field unknown", e);
        }

        return subT;
    }

    public Trader getTrader(Referential ref, String code, String nameIns) {
        for (Referential.CurrencyTrader cur : ref.currencyTraders)
            if (cur.code.equals(code))
                for (Referential.InstrumentTrader ins : cur.getInstruments())
                    if (ins.name.equals(nameIns))
                        return getRandomElement(ins.getTraders());

        return null;
    }

    public class Counterpart {
        private String code;
        private String name;

        public void setCode(String code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

    public class Currency {
        private String code;
        private String name;
        private String country;
        private float change;

        public Currency() {
        }

        public Currency(String code, String name, String country) {
            this.code = code;
            this.name = name;
            this.country = country;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setChange(Float change) {
            this.change = change;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public float getChange() {
            return change;
        }

        public Currency getCurrencybycountry(String country) {
            for (int i = 0; i < currencies.size(); i++)
                if (currencies.get(i).country.equals(country))
                    return currencies.get(i);
            return null;
        }
    }

    public class CurrencyTrader {
        private String code;
        private List<InstrumentTrader> instruments;

        public CurrencyTrader() {
        }

        public CurrencyTrader(String code) {
            this.code = code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setInstruments(List<InstrumentTrader> instruments) {
            this.instruments = instruments;
        }

        public String getCode() {
            return code;
        }

        public List<InstrumentTrader> getInstruments() {
            return instruments;
        }

    }

    public class Product {
        private String name;
        private String type;
        private String isin;
        private String libelle;
        private String country;
        private float price;

        public void setType(String type) {
            this.type = type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setIsin(String isin) {
            this.isin = isin;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public String getLibelle() {
            return libelle;
        }

        public String getIsin() {
            return isin;
        }

        public float getPrice() {
            return price;
        }

    }

    public class Depositary {
        private String code;
        private String libelle;

        public void setCode(String code) {
            this.code = code;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public String getCode() {
            return code;
        }

        public String getLibelle() {
            return libelle;
        }

    }

    public class InstrumentTrader {
        private String name;
        private List<Trader> traders;

        public InstrumentTrader() {
        }

        public InstrumentTrader(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public List<Trader> getTraders() {
            return traders;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTraders(List<Trader> traders) {
            this.traders = traders;
        }
    }

    public class Trader {
        private String name;
        private String codeptf;

        public Trader(String name, String codeptf) {
            this.name = name;
            this.codeptf = codeptf;
        }

        public String getCode() {
            return codeptf;
        }

        public String getName() {
            return name;
        }

        public void setCode(String code) {
            this.codeptf = code;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Portfolio {
        private String type;
        private String country;
        private String codeptf;

        public void setCode(String code) {
            this.codeptf = code;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCode() {
            return codeptf;
        }

        public String getType() {
            return type;
        }

        public String getCountry() {
            return country;
        }

    }

    public List<Counterpart> getCounterparts() {
        return counterparts;
    }

    public void setCounterparts(List<Counterpart> counterparts) {
        this.counterparts = counterparts;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<CurrencyTrader> getCurrencyTraders() {
        return currencyTraders;
    }

    public void setCurrencyTraders(List<CurrencyTrader> currencyTraders) {
        this.currencyTraders = currencyTraders;
    }

    public List<Depositary> getDepositaries() {
        return depositaries;
    }

    public void setDepositaries(List<Depositary> depositaries) {
        this.depositaries = depositaries;
    }

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }
}
