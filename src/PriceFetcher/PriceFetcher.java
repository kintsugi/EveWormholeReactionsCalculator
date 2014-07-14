package PriceFetcher;
import java.util.List;
import java.util.ArrayList;
import Database.ProgramDatabase;

public class PriceFetcher {
    
    private final List<Item> items;
    
    public PriceFetcher() {
        items = new ArrayList();
        ProgramDatabase db = new ProgramDatabase();
        List<String> itemNames = db.getItemNames();
        List<String> itemIDs = db.getItemIDs();
        for(int i = 0; i < itemNames.size(); i++) {
            items.add(new Item(itemNames.get(i), Integer.parseInt(itemIDs.get(i))));
        }
    }
    
    public Item getItem(int id) {
        for(Item item : items) {
            if(id == item.getID()) { 
                return item;
            }
        }
        System.err.println("No item matching ID of '" + id + "'");
        return null;
    }
    
    public Item getItem(String name) {
        for(Item item : items) {
            if(name.equalsIgnoreCase(item.getName())) { 
                return item;
            }
        }
        System.err.println("No item matching name of '" + name + "'");
        return null;
    }
    
}
