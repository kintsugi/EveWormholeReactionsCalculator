package Database;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class ProgramDatabase {
    
    private Connection dataDB;
    private Connection recipeDB;
    
    public ProgramDatabase() {
        dataDB = null; recipeDB = null;
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
    }
    
    public List<String> getItemNames() {
        List<String> ret = new ArrayList();
        try {
            Statement stmt = dataDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NAME FROM ITEMS");
            while(rs.next()) {
                ret.add(rs.getString("NAME"));
            }
            return ret;
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
        return ret;
    }
    
    public List<String> getItemIDs() {
        List<String> ret = new ArrayList();
        try {
            Statement stmt = dataDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ITEMID FROM ITEMS");
            while(rs.next()) {
                ret.add(rs.getString("ITEMID"));
            }
            return ret;
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
        return ret;
    }
    
    public List<String> getReactions() {
        List<String> ret = new ArrayList();
        try {
            Statement stmt = dataDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NAME FROM REACTIONS");
            while(rs.next()) {
                ret.add(rs.getString("NAME"));
            }
            return ret;
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }        
        return ret;
    }
    
    public String getFromReactions(String reactionName, String columnName) {
        try {
            Statement stmt = dataDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + columnName + " FROM REACTIONS WHERE NAME = '" + reactionName + "'");
            return rs.getString(columnName);
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() + " from '" + reactionName + "'" );
        }
        return "ProgramDatabase.getFromReactions Failed. params: '" + reactionName + "', '" + columnName + "'";
    }
    
    public List<String> getReactionRecipe(String reactionName) {
        List<String> ret = new ArrayList();
        try {
            Statement stmt = recipeDB.createStatement();  String tableName = null;
            ResultSet rs = stmt.executeQuery("SELECT TABLENAME FROM TABLEINDEX WHERE REACTIONNAME = '" + reactionName + "'");
            tableName = rs.getString("TABLENAME");
            rs = stmt.executeQuery("SELECT NAME FROM " + tableName);
            while(rs.next()) {
                ret.add(rs.getString("NAME"));
            }
            return ret;
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
        return ret;
    }
    
    public String getFromRecipe(String reactionName, String materialName, String columnName) {
        try {
            Statement stmt = recipeDB.createStatement(); String tableName = null;
            ResultSet rs = stmt.executeQuery("SELECT TABLENAME FROM TABLEINDEX WHERE REACTIONNAME = '" + reactionName + "'");
            tableName = rs.getString("TABLENAME");
            rs = stmt.executeQuery("SELECT " + columnName + " FROM " + tableName + " WHERE NAME = '" + materialName + "'");
            return rs.getString(columnName);
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() + " from '" + reactionName + "'" );
        }
        return "ProgramDatabase.getFromRecipe Failed. params: '" + reactionName + "', '" + columnName + "'";
    }
    
    public String getUserData(String dataName) {
        try {
            Statement stmt = dataDB.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VALUE FROM USERDATA WHERE NAME = '" + dataName + "'");
            return rs.getString("VALUE");
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
        return "ProgramDatabase.getUserData Failed. params: '" + dataName + "'";
    }
    
    public void setUserData(String dataName, String value) {
        try {
            Statement stmt = dataDB.createStatement();
            stmt.executeQuery("UPDATE USERDATA set VALUE = '" + value + "' where NAME = '" + dataName + "'");
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
