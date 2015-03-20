package Generals;


public class TradeGenerator
{	
	public static void main(String[] args)
	{
		Referential ref = new Referential();	
		
		LoadXML load = new LoadXML(ref);
		load.loadAll();		
	}	
}