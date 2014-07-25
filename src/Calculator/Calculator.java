package Calculator;
import java.util.ArrayList;
import Database.ProgramDatabase;
import Database.SQLiteTable;
import PriceFetcher.PriceFetcher;
import PriceFetcher.Item;

/*
    Equations used:
        Revenue Per Hour:
            Polymer Unit Value * Polymer Produced in One Reaction
        Profit Per Hour
            Revenue Per Hour - Reaction Material Value
        Volume Mined Per Hour:
            (3600/cycleTime) * m3PerCycle
        Gas Per Hour of Mining:
            Volume Mined Per Hour/Gas Volume Per Unit(m3)
        Reactions per Hour of Mining:
            1 / ((Gas #1 Per Hour of Mining / Gas #1 Required Reaction Quantity) + ((Gas #2 Per Hour of Mining / Gas #2 Required Reaction Quantity))
*/

public class Calculator {
    //Reaction revenue per hour.
    public double revenuePerHour(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher();
        Item polymer = prices.getItem(reactionName);
        SQLiteTable reactions = db.getReactions();
        //Multiply the quantity of polymer per batch multipled by the polymer value to get the revenue per hour of running the reaction.
        return Integer.parseInt(reactions.getWhere("BATCH", "NAME", reactionName)) * polymer.getPrice();
    }
    //Profit per hour of running the reaction.
    public double profitPerHour(String reactionName) {
        ProgramDatabase db = new ProgramDatabase(); PriceFetcher prices = new PriceFetcher(); ArrayList<Item> recipe = new ArrayList(); 
        SQLiteTable recipeTable = db.getRecipe(reactionName);
        ArrayList<String> recipeNames = recipeTable.getColumn("NAME");
        //Find the total material value by summing the value of each material.
        double totalMaterialValue = 0;
        for(String s : recipeNames) {
            recipe.add(prices.getItem(s));
        }
        for(Item material : recipe) {
            totalMaterialValue += material.getPrice() * Integer.parseInt(recipeTable.getWhere("QUANTITY", "NAME", material.getName()));
        }
        //Return revenue per hour minus the total material value as the profit per hour.
        return revenuePerHour(reactionName) - totalMaterialValue;
    }
    
    public double iskEfficiency(String reactionName, double cycleTime, double m3PerCycle) {
        //ISK efficiency is revenue per hour of mining for one reaction.
        return (revenuePerHour(reactionName)) * ( time(reactionName, cycleTime, m3PerCycle));
    }
    
    public double timeEfficiency(String reactionName, double cycleTime, double m3PerCycle) {
        //Time efficiency is reaction profit per hour of mining for one reaction.
        //For example, a reaction has a huge profit margin, but takes many hours to gather the materials, so it is not worth mining for it.
        return (profitPerHour(reactionName)) * ( time(reactionName, cycleTime, m3PerCycle));
    }
    //Number of potential reactions from gathering materials for 1 hour.
    public double time(String reactionName, double cycleTime, double m3PerCycle) {
        ProgramDatabase db = new ProgramDatabase();
        SQLiteTable recipeTable = db.getRecipe(reactionName);
        ArrayList<String> recipeNames = recipeTable.getColumn("NAME");
        /*
            This finds the amount of reactions are possible with one hour of gathering materials.
            The equation is 1/((First Gas Quantity/First Gas Quantity Per Hour of mining
        */
        double volumePerHour = (3600/cycleTime) * m3PerCycle;
        double g1PerHour = volumePerHour / Double.parseDouble(recipeTable.getWhere("VOLUME", "NAME", recipeNames.get(0)));
        double g2PerHour = volumePerHour / Double.parseDouble(recipeTable.getWhere("VOLUME", "NAME", recipeNames.get(1)));
        return 1 / ((Integer.parseInt(recipeTable.getWhere("QUANTITY", "NAME", recipeNames.get(0))) / g1PerHour) + (Integer.parseInt(recipeTable.getWhere("QUANTITY", "NAME", recipeNames.get(1))) / g2PerHour));
    }
    
}
