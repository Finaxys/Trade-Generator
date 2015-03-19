package Generals;


class GeneralSettings
{
	public String bankSystem;
	public int totalBudget;
	public int equity;
	public int derivative;
	public int mutualFunds;
	public int bond;
	public int debt;
	public int swaps;
	public int cds;
	public int forex;
	public int repo;
	public int loan;
}

class DeskSettings
{
	public String name;
}

class EquitySettings extends DeskSettings
{
	public int toleranceRep;
	public int partsell;
	public int volumetry;
	public int toleranceVolumetry;
	public Boolean isstop;
}

class Configuration
{
	public String pathStp;
	public String pathBatch;
}