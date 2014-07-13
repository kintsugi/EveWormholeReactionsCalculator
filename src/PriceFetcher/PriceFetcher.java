package PriceFetcher;
import java.util.List;
import java.util.ArrayList;
import Databases.ProgramDatabase;

public class PriceFetcher {
    
    private final List<MarketQueryContainer> items;
    
    public PriceFetcher() {
        items = new ArrayList();
        ProgramDatabase db = new ProgramDatabase();
        List<String> itemNames = db.getItemNames();
        List<String> itemIDs = db.getItemIDs();
        for(int i = 0; i < itemNames.size(); i++) {
            items.add(new MarketQueryContainer(itemNames.get(i), Integer.parseInt(itemIDs.get(i))));
        }
    }
    
    public double getPrice(int id) {
        for(MarketQueryContainer item : items) {
            if(id == item.getID()) {
                item.Update();
                return item.getPrice();
            }
        }
        return 0;
    }
    
    public double getPrice(String name) {
        for(MarketQueryContainer item : items) {
            if(name.equalsIgnoreCase(item.getName())) {
                item.Update();
                return item.getPrice();
            }
        }
        return 0;
    }
    
}
