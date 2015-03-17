import java.util.List;

class Referentiel
{
	public List<Brokers> Broker;
	public List<Currencies> Currencie;
	public List<Depositaries> Depositarie;
	public List<Instruments> Instrument;
	public List<Traders> Trader;
	public List<Wallets> Wallet;
}

class Brokers
{
	public String code;
	public String name;
}

class Currencies
{
	public String code;
	public String name;
	public String country;
}

class Depositaries
{
	public String code;
	public String libelle;
}

class Instruments
{
	public String type;
	public String isin;
	public String libelle;
	public String pays;
	public float prix;
}

class Traders
{
	public String name;
	public String codeptf;
}

class Wallets
{
	public String name;
	public String codeptf;
}