package Generals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import Generals.Referential.Counterpart;
import Generals.Referential.Currency;
import Generals.Referential.Depositary;
import Generals.Referential.Trader;

public class Tradeloan extends Tradeevents {
	public int montant;
    Referential.Depositary depositary;
	Referential.Counterpart counterpart;
	Way way;
	Way taux;
	Referential.Trader trader;
	Referential.Currency devise;	
	public float valeur_taux;
	public Term durée;
	BaseCalcul basecalcul;
	public Boolean is_stp;
	
	public Tradeloan(Book book, int date, int montant,Way way, Way taux, Depositary depositary,
			Counterpart counterpart, Trader trader,
			Currency devise, float valeur_taux, Term durée,
			BaseCalcul basecalcul) {
		super(book, date);
		this.montant=montant;
		this.depositary = depositary;
		this.counterpart = counterpart;
		this.way = way;
		this.taux = taux;
		this.trader = trader;
		this.devise = devise;
		this.valeur_taux = valeur_taux;
		this.durée = durée;
		this.basecalcul = basecalcul;
		
	}



		




		@Override
		public String toXml() {
			

			
				
			return "eee";
		}
	}
