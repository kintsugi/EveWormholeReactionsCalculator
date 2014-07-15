package Database;
import java.sql.*;
import java.util.ArrayList;


public class ProgramDatabase {
    
    static private SQLiteTable itemTable;
    static private SQLiteTable reactionsTable;
    static private SQLiteTable userDataTable;
    static private SQLiteTable recipeTableIndex;
    static private ArrayList<SQLiteTable> recipeTables;
    
    static public void load() {
        Connection dataDB = null; Connection recipeDB = null;
        try {
            Class.forName("org.sqlite.JDBC");
            dataDB = DriverManager.getConnection("jdbc:sqlite:data/data.db");
        } catch(ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        try {
            Class.forName("org.sqlite.JDBC");
            recipeDB = DriverManager.getConnection("jdbc:sqlite:data/recipe.db");
        } catch(ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );  
        }
        itemTable = new SQLiteTable(dataDB, "items");
        reactionsTable = new SQLiteTable(dataDB, "reactions");
        userDataTable = new SQLiteTable(dataDB, "userdata");
        recipeTableIndex = new SQLiteTable(recipeDB, "tableindex");
        recipeTables = new ArrayList();
        for(int i = 0; i < recipeTableIndex.getNumRows(); i++) {
            recipeTables.add(new SQLiteTable(recipeDB, recipeTableIndex.getWhere("TABLENAME", "ID", Integer.toString(i + 1))));
        }
    }
    
    public SQLiteTable getItemTable() {
        return itemTable;
    }
    
    public SQLiteTable getReactions() {
        return reactionsTable;
    }
    
    public SQLiteTable getRecipeTableIndex() {
        return recipeTableIndex;
    }
    
    public ArrayList<SQLiteTable> getRecipes() {
        return recipeTables;
    }
    
    public SQLiteTable getRecipe(String reactionName) {
        String ID = recipeTableIndex.getWhere("ID", "REACTIONNAME", reactionName);
        return recipeTables.get(Integer.parseInt(ID) - 1);
    }
    
    public SQLiteTable getUserData(String dataName) {
        return userDataTable;
    }
}
