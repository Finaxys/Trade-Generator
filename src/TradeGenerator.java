import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class TradeGenerator
{

	public static void main(String[] args)
	{
		Referential	ref = new Referential();
		
		loadReferential(ref);
		
		for (Instrument instrument : ref.Instruments) {
			 
			System.out.println("ISIN : " +  instrument.isin);
			System.out.println("LIBELLE : " + instrument.libelle);
			System.out.println("PAYS : " + instrument.country);
			System.out.println("PRIX : " + instrument.price);
			System.out.println("TYPE : " + instrument.type);
		}
	}
	
	public static void loadReferential(Referential ref)
	{
	  ref.Instruments = new ArrayList<Instrument>();
		
	  try
	  {
		File fXmlFile = new File("referential/instruments.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
	 
		doc.getDocumentElement().normalize();
	 
		NodeList nList = doc.getElementsByTagName("instrument");
	 
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) nNode;
				
				Instrument instrument = new Instrument();
				instrument.isin = eElement.getElementsByTagName("isin").item(0).getTextContent();
				instrument.country = eElement.getElementsByTagName("country").item(0).getTextContent();
				instrument.price = Float.parseFloat(eElement.getElementsByTagName("price").item(0).getTextContent());
				instrument.type = eElement.getElementsByTagName("type").item(0).getTextContent();
				instrument.libelle = eElement.getElementsByTagName("libelle").item(0).getTextContent();
				ref.Instruments.add(instrument);
			}
		}
	  } catch (Exception e) {
		e.printStackTrace();
	  }
	}
}