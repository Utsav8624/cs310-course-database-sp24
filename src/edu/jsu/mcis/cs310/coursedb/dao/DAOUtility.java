package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
       
        
        try {
        
            if (rs != null) {

            // Get metadata to retrieve information about the columns
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            
            // Iterate through each row in the ResultSet
            while (rs.next()) {
                JsonObject row = new JsonObject();
                
                // Iterate through each column in the row
                for (int i = 1; i <= columnCount; ++i) {
                    String columnname = metadata.getColumnName(i);// Get the column name
                    row.put(columnname, rs.getObject(i).toString());// Get the column value

                }
                // Add the row to the JsonArray
                records.add(row);
            }

            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}
