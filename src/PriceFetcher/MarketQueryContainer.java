package PriceFetcher;
import java.io.IOException;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.net.URL;

public class MarketQueryContainer {
    
    private final String name;
    private final int id;
    private double price;
    
    public MarketQueryContainer(String newName, int newID) {
        name = newName; id = newID; Update();
    }
    
    public void Update() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            EveCentralHandler handler = new EveCentralHandler();
            URL link =  new URL(composeQuery());
            saxParser.parse(new InputSource(link.openStream()), handler);
            price = handler.getPrice();
        } catch (ParserConfigurationException | SAXException | IOException e) {
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
        return "http://api.eve-central.com/api/marketstat?typeid=" + id + "&usesystem=30000142";
    }
}
