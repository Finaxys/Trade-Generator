package Generals;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Instrument;
import Generals.Referential.Portfolio;
import Generals.Referential.Trader;

public class Tradeequity implements Tradeevents {

	int date;
	Boolean Sens;
	float prix;
	int quantity;
	 Referential.Depositary d1;
	    Referential.Counterpart c1;
	    Referential.Trader tr1;
	    Referential.Instrument iins1;
	    Referential.Currency cur1;
	    Referential.Portfolio port1;
	
	
	

	public Tradeequity(int date, Boolean sens, float prix, int quantity,
				Depositary d1, Counterpart c1, Trader tr1, Referential.Instrument iins1,
				Currency cur1, Portfolio port1) {
			super();
			this.date = date;
			Sens = sens;
			this.prix = prix;
			this.quantity = quantity;
			this.d1 = d1;
			this.c1 = c1;
			this.tr1 = tr1;
			this.iins1 = iins1;
			this.cur1 = cur1;
			this.port1 = port1;
		}




	public void toXml() {
		// TODO Auto-generated method stub		
	}
}
