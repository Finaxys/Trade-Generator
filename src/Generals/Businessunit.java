package Generals;

import java.util.ArrayList;
import java.util.Iterator;

	public class Businessunit
	{
		public String name;
		public double ratio;
		public ArrayList<Instrument> lins;
		public ArrayList<Portfolio> lpor;
        public Instrument getInstrument(String str)
        {    
        	boolean sortie;    	 
        	Iterator<Instrument> it = lins.iterator();
        	 
       	while (it.hasNext()&&sortie==false) {
       	       Instrument s = it.next();
       	       if (s.name==str){
       	    	   
       	    	   sortie=true;
       	       }
       	}
       	       
			return s ;
       	       
       	}
        	
        
		public Businessunit(String name, double ratio, ArrayList<Instrument> lins, ArrayList<Portfolio> lpor)
		{
			super();
			this.name = name;
			this.ratio = ratio;
			this.lins = lins;
			this.lpor = lpor;
		}
	}

