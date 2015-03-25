package Generals;

import java.util.ArrayList;

public class OutputManager {
	static private OutputManager instance = new OutputManager();
	
	private OutputManager() {}

	static public OutputManager getInstance()
	{
		return (instance);
	}
	
	static public void outputTrades(ArrayList<TradeEvent> trades)
	{
		for (TradeEvent trade : trades)
		{
		}
	}
	
	static public void outputTrade(TradeEvent trade)
	{
		
	}
}
