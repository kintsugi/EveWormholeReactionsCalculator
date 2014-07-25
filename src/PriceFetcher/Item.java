package PriceFetcher;
import java.io.IOException;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.net.URL;

public class Item {
    
    private final String name;
    private final int id;
    private double price;
    
    public Item(String newName, int newID) {
        name = newName; id = newID; 
        try {
            //Query the price from the Eve Central Web API and parse it.
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            EveCentralHandler handler = new EveCentralHandler();
            URL link =  new URL(composeQuery());
            saxParser.parse(new InputSource(link.openStream()), handler);
            price = handler.getPrice();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            price = 0;
        }
    }
    
    public String getName() {
        return name;
    }
    
    public int getID() {
        return id;
    }
    
    public double getPrice() {
        return price;
    }
    
    private String composeQuery() {
        //System used is Jita.
        return "http://api.eve-central.com/api/marketstat?typeid=" + id + "&usesystem=30000142";
    }
}
