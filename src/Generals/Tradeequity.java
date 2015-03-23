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


public class Tradeequity implements Tradeevents {

	int date;
	Boolean buy;
	float prix;
	int quantity;
	Referential.Depositary d1;
	Referential.Counterpart c1;
	Referential.Trader tr1;
	Referential.Product pro1;
	Referential.Currency cur1;
	Referential.Portfolio port1;

	public Tradeequity(int date, Boolean buy, float prix, int quantity,
			Referential.Depositary d1, Referential.Counterpart c1, Referential.Trader tr1, Referential.Product pro1,
			Referential.Currency cur1, Referential.Portfolio port1) {
		super();
		this.date = date;
		this.buy = buy;
		this.prix = prix;
		this.quantity = quantity;
		this.d1 = d1;
		this.c1 = c1;
		this.tr1 = tr1;
		this.pro1 = pro1;
		this.cur1 = cur1;
		this.port1 = port1;
	}

	public String toXml() {
		
		StringBuilder Document = new StringBuilder();

		Document.append("<trade>\n<way>" + (buy.equals(Boolean.TRUE) ? "buy" : "sell") + "<way>\n");
		Document.append("<type>equity</type>\n");
		Document.append("<product>" + pro1.libelle + "<product>\n");
		Document.append("<quantity>" + Integer.toString(quantity) + "<quantity>\n");
		Document.append("<price>" + Float.toString(prix) + "<price>\n");
		Document.append("<currency>" + cur1.code + "<currency>\n");
		Document.append("<trader>" + tr1.codeptf + "<trader>\n</trade>\n");
			
		return Document.toString();
	}
}