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

    public double iskEfficiency(String reactionName, double cycleTime, double m3PerCycle) {
        //ISK Efficiency: revenue per hour of mining for one reaction.
        return (revenuePerHour(reactionName)) * ( time(reactionName, cycleTime, m3PerCycle));
    }
    
    public double timeEfficiency(String reactionName, double cycleTime, double m3PerCycle) {
        //Time Efficiency: reaction profit per hour of mining for one reaction.
        return (profitPerHour(reactionName)) * ( time(reactionName, cycleTime, m3PerCycle));
    }
    
    public double time(String reactionName, double cycleTime, double m3PerCycle) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        
        SQLiteTable recipeTable = db.getRecipe(reactionName);
        ArrayList<String> recipeNames = recipeTable.getColumn("NAME");
        double volumePerHour = ((3600/cycleTime) * (m3PerCycle));
        double g1PerHour = volumePerHour / Double.parseDouble(recipeTable.getWhere("VOLUME", "NAME", recipeNames.get(0)));
        double g2PerHour = volumePerHour / Double.parseDouble(recipeTable.getWhere("VOLUME", "NAME", recipeNames.get(1)));
        return 1 / ((Integer.parseInt(recipeTable.getWhere("QUANTITY", "NAME", recipeNames.get(0))) / g1PerHour) + (Integer.parseInt(recipeTable.getWhere("QUANTITY", "NAME", recipeNames.get(1))) / g2PerHour));

    }
    
}
