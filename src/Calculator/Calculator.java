package Calculator;
import java.util.ArrayList;
import Database.ProgramDatabase;
import Database.SQLiteTable;
import PriceFetcher.PriceFetcher;
import PriceFetcher.Item;

public class Calculator {
    
    public double profitPerHour(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher(); ArrayList<Item> recipe = new ArrayList(); 
        Item polymer = prices.getItem(reactionName);
        SQLiteTable recipeTable = db.getRecipe(reactionName);
        ArrayList<String> recipeNames = recipeTable.getColumn("NAME");
        SQLiteTable reactions = db.getReactions();
        
        double totalMaterialValue = 0;
        for(String s : recipeNames) {
            recipe.add(prices.getItem(s));
        }
        for(Item material : recipe) {
            totalMaterialValue += material.getPrice() * Integer.parseInt(recipeTable.getWhere("QUANTITY", "NAME", material.getName()));
        }
        return Integer.parseInt(reactions.getWhere("BATCH", "NAME", reactionName)) * polymer.getPrice() - totalMaterialValue;
    }
    
    public double revenuePerHour(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        Item polymer = prices.getItem(reactionName);
        SQLiteTable reactions = db.getReactions();
        return polymer.getPrice() * Integer.parseInt(reactions.getWhere("BATCH", "NAME", reactionName));
    }

    public double efficiency(String reactionName, double cycleTime, double m3PerCycle) {
        //Efficiency: Profit per hour of mining for one reaction.
        return (profitPerHour(reactionName)) / (1 / time(reactionName, cycleTime, m3PerCycle));
    }
    
    public double time(String reactionName, double cycleTime, double m3PerCycle) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        
        SQLiteTable recipeTable = db.getRecipe(reactionName);
        ArrayList<String> recipeNames = recipeTable.getColumn("NAME");
        double volumePerHour = ((3600/cycleTime) * (m3PerCycle));
        double g1PerHour = volumePerHour / Double.parseDouble(recipeTable.getWhere("VOLUME", "NAME", recipeNames.get(0)));
        double g2PerHour = volumePerHour / Double.parseDouble(recipeTable.getWhere("VOLUME", "NAME", recipeNames.get(1)));
        return 1 / ((Double.parseDouble(recipeTable.getWhere("QUANTITY", "NAME", recipeNames.get(0))) / g1PerHour) + (Double.parseDouble(recipeTable.getWhere("QUANTITY", "NAME", recipeNames.get(1))) / g2PerHour));

    }
    
}
