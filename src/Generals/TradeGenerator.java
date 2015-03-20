package Generals;

import java.awt.List;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TradeGenerator
{	
	public static void main(String[] args)
	{
		Referential ref = new Referential();	
		
		LoadXML load = new LoadXML(ref);
		load.loadAll();		
	}	
}