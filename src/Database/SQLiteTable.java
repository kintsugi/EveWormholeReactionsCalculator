package Database;
import java.sql.*;
import java.util.ArrayList;
public class SQLiteTable {
    
    private final ArrayList<String> columns;
    private final ArrayList<ArrayList<String>> rows;
    
    public SQLiteTable(Connection db, String tableName) {
        columns = new ArrayList(); rows = new ArrayList<>();
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            //Retrieve the column names.
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
                columns.add(rsmd.getColumnName(i));
            //Get each value from the table.
            while(rs.next()) {
                ArrayList<String> newRow = new ArrayList();
                for(String s : columns)
                    newRow.add(rs.getString(s));                    
                rows.add(newRow);
            }
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    public int getNumColumns() {
        return columns.size();
    }
    
    public int getNumRows() {
        return rows.size();
    }
    
    public ArrayList<String> getColumnNames() {
        return columns;
    }
    //Get all the values in the requested column.
    public ArrayList<String> getColumn(String columnName) {
        ArrayList<String> ret = new ArrayList(); int columnNumber;
        for(columnNumber = 0; columnNumber < columns.size(); columnNumber++) {
            if(columnName.equalsIgnoreCase(columns.get(columnNumber)))
                break;
        }
        for(ArrayList<String> row : rows)
            ret.add(row.get(columnNumber));
        return ret;
    }
    //Return the requested row.
    public ArrayList<String> getRow(int row) {
        return rows.get(row);
    }
    //Gets the value in column (name specified by variable column), where the value in the column (name specified by where) equals the value in the variable equals.
    public String getWhere(String column, String where, String equals) {
        int desiredColumnNumber; int whereColumnNumber;
        for(desiredColumnNumber = 0; desiredColumnNumber < columns.size(); desiredColumnNumber++) {
            if(column.equalsIgnoreCase(columns.get(desiredColumnNumber)))
                break;
        }
        for(whereColumnNumber = 0; whereColumnNumber < columns.size(); whereColumnNumber++) {
            if(where.equalsIgnoreCase(columns.get(whereColumnNumber)))
                break;
        }
        for (ArrayList<String> row : rows) {
                if(equals.equalsIgnoreCase(row.get(whereColumnNumber)))
                    return row.get(desiredColumnNumber);
        }
        return "No matching cell";
    }
    
}
