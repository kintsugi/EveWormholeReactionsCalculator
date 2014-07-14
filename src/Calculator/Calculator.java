package Calculator;
import java.util.List;
import java.util.ArrayList;
import Database.ProgramDatabase;
import PriceFetcher.PriceFetcher;
import PriceFetcher.Item;

public class Calculator {
    
    public Calculator() {
        
    }
    
    public double profitPerHour(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        Item polymer = prices.getItem(reactionName); List<String> recipeNames = db.getReactionRecipe(reactionName);
        List<Item> recipe = new ArrayList();
        double totalMaterialValue = 0;
        for(String s : recipeNames) {
            recipe.add(prices.getItem(s));
        }
        for(Item material : recipe) {
            totalMaterialValue += material.getPrice() * Integer.parseInt(db.getFromRecipe(reactionName, material.getName(), "QUANTITY"));
        }
        return polymer.getPrice() * Integer.parseInt(db.getFromReactions(reactionName, "BATCH")) - totalMaterialValue;
    }
    
    public double revenuePerHour(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        Item polymer = prices.getItem(reactionName);
        return polymer.getPrice() * Integer.parseInt(db.getFromReactions(reactionName, "BATCH"));
    }

    public double efficiency(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        Item polymer = prices.getItem(reactionName);
        List<String> recipeNames = db.getReactionRecipe(reactionName);
        double totalMaterialValue = 0;
        
        List<Item> recipe = new ArrayList();
        for(String s : recipeNames) {
            recipe.add(prices.getItem(s));
        }
        for(Item material : recipe) {
            totalMaterialValue += material.getPrice() * Integer.parseInt(db.getFromRecipe(reactionName, material.getName(), "QUANTITY"));
        }
        //Efficiency: Profit per hour of mining for one reaction.
        return (polymer.getPrice() * Double.parseDouble(db.getFromReactions(reactionName, "BATCH")) - totalMaterialValue ) / (1 / time(reactionName));
    }
    
    public double time(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        Item polymer = prices.getItem(reactionName);
        List<String> recipeNames = db.getReactionRecipe(reactionName);
        double volume1 = Double.parseDouble(db.getFromRecipe(reactionName, recipeNames.get(0), "VOLUME"));
        double volume2 = Double.parseDouble(db.getFromRecipe(reactionName, recipeNames.get(1), "VOLUME"));
        double cycleTime = Double.parseDouble(db.getUserData("Cycle Time"));
        double m3PerCycle = Double.parseDouble(db.getUserData("m3 Per Cycle"));
        double volumePerHour = ((3600/cycleTime) * (m3PerCycle));
        double g1PerHour = volumePerHour / volume1;
        double g2PerHour = volumePerHour / volume2;
        return 1 / ((Double.parseDouble(db.getFromRecipe(reactionName, recipeNames.get(0), "Quantity")) / g1PerHour) + (Double.parseDouble(db.getFromRecipe(reactionName, recipeNames.get(1), "Quantity")) / g2PerHour));

    }
    
}
