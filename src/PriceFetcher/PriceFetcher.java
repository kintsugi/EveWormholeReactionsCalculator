package PriceFetcher;
import java.util.ArrayList;
import Database.ProgramDatabase;
import Database.SQLiteTable;

public class PriceFetcher {
    
    private static ArrayList<Item> items;
    
    static public void load() {
        items = new ArrayList();
        ProgramDatabase db = new ProgramDatabase();
        SQLiteTable itemTable = db.getItemTable();
        ArrayList<String> itemNames = itemTable.getColumn("NAME");
        ArrayList<String> itemIDs = itemTable.getColumn("ITEMID");
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
