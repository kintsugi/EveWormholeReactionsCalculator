package PriceFetcher;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class EveCentralHandler extends DefaultHandler {
    
    private double price;
    private boolean isSell;
    private boolean isSellMin;
    
    public EveCentralHandler() {
        price = 0; isSell = false; isSellMin = false;
    }
    
    public double getPrice() {
        return price;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //Set the flags to true when <sell> <min> is reached.
        if (qName.equalsIgnoreCase("sell"))
            isSell = true;
        if (qName.equalsIgnoreCase("min") && isSell)
            isSellMin = true;
    }
        
    @Override
    public void characters (char ch[], int start, int length) throws SAXException {
        //When both flags are true, retrieve the value, then reset the flags.
        if(isSell && isSellMin) {
            price = Double.parseDouble(new String(ch, start, length));
            isSell = false; isSellMin = false;
        }
    }
}
        
