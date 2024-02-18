package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    public static final String QUERY_CREATE = "INSERT INTO registration (studentid, termid, crn) VALUES (?,?,?)";
    public static final String QUERY_DROP = "DELETE FROM registration WHERE studentid=? AND termid=? AND crn=?";
    public static final String QUERY_WITHDRAW = "DELETE FROM registration WHERE studentid=? AND termid=?";
    public static final String QUERY_LIST = "SELECT * FROM registration WHERE studentid=? AND termid=? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                 ps = conn.prepareStatement(QUERY_CREATE);

                // Set the values for the parameters in the SQL statement
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);

                int updateCount = ps.executeUpdate(); // Execute the SQL statement

                // If at least one row is updated in the database, set the result flag to true
                if (updateCount > 0) {
                    result = true;
                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_DROP);

                // Set the values for the parameters in the SQL statement
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);

                int updateCount = ps.executeUpdate(); // Execute the SQL statement and get the number of rows affected

                // If at least one row is updated in the database, set the result flag to true
                if (updateCount > 0) {
                    result = true;
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                 ps = conn.prepareStatement(QUERY_WITHDRAW);

                // Set the values for the parameters in the SQL statement
                ps.setInt(1, studentid);
                ps.setInt(2, termid);

                int updateCount = ps.executeUpdate(); // Execute the SQL statement and get the number of rows affected

                // If at least one row is updated in the database, set the result flag to true
                if (updateCount > 0) {
                    result = true;
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_LIST);

                // Set the values for the parameters in the SQL statement
                ps.setInt(1, studentid);
                ps.setInt(2, termid);

                boolean hasresults = ps.execute(); // Execute the SQL statement and check if it has results

                if (hasresults) {
                    rs = ps.getResultSet(); // Get the ResultSet from the executed SQL statement
                    result = DAOUtility.getResultSetAsJson(rs); // Convert the ResultSet to a JSON string using utility method
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}
