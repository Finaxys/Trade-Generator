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
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;

		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element trade = doc.createElement("trade");
			doc.appendChild(trade);

			// append child elements to root element
			trade.setAttribute("id", "XXX");
			trade.appendChild(getTradeElement(doc, trade, "way", buy.equals(Boolean.TRUE) ? "buy" : "sell"));
			trade.appendChild(getTradeElement(doc, trade, "type", "equity"));
			trade.appendChild(getTradeElement(doc, trade, "product", pro1.libelle));
			trade.appendChild(getTradeElement(doc, trade, "quantity", Integer.toString(quantity)));
			trade.appendChild(getTradeElement(doc, trade, "price", Float.toString(prix)));
			trade.appendChild(getTradeElement(doc, trade, "currency", cur1.code));
			trade.appendChild(getTradeElement(doc, trade, "trader", tr1.codeptf));
			Calendar c = new GregorianCalendar();
			c.add(Calendar.DAY_OF_MONTH, date);
			Date d = c.getTime();
			trade.appendChild(getTradeElement(doc, trade, "tradedate", d.getDay() + "/" + d.getDate() + "/" + d.getYear()));

			// output DOM XML to console 
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); 
			DOMSource source = new DOMSource(doc);
			StreamResult xml = new StreamResult(new StringWriter());
			transformer.transform(source, xml);

			return (xml.getWriter().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return ("");
	}
	 
	    // utility method to create text node
	    private static Node getTradeElement(Document doc, Element element, String name, String value) {
	        Element node = doc.createElement(name);
	        node.appendChild(doc.createTextNode(value));
	        return node;
	    }
}