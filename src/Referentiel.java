import java.util.List;

class Referential
{
	public List<Counterpart> Counterparts;
	public List<Currency> Currencies;
	public List<Depositary> Depositaries;
	public List<Instrument> Instruments;
	public List<Trader> Traders;
	public List<Wallet> Wallets;
}

class Counterpart
{
	public String code;
	public String name;
}

class Currency
{
	public String code;
	public String name;
	public String country;
}

class Depositary
{
	public String code;
	public String libelle;
}

class Instrument
{
	public String type;
	public String isin;
	public String libelle;
	public String country;
	public float price;
}

class Trader
{
	public String name;
	public String codeptf;
}

class Wallet
{
	public String type;
	public String country;
	public String codeptf;
}