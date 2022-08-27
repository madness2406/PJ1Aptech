/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import Database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class LoginManager {  

    public static String GetData(String table, String columnDisplay, String columnConstraint1, String value1, String columnConstraint2, String value2) {
        try {
            String dm = null;
            DBConnection dbConn = new DBConnection();
            String qr = "Select " + columnDisplay + " From " + table + " Where " + columnConstraint1 + " = N'" + value1
                    + "' And " + columnConstraint2 + " = N'" + value2 + "'";
            ResultSet rs = dbConn.GetData(qr);
            if (rs.next()) {
                dm = rs.getString(columnDisplay);
            }
            return dm;
        } catch (SQLException ex) {
            Logger.getLogger(BookingDetailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean CheckLogin(String table, String columnConstraint1, String value1, String columnConstraint2, String value2) {
        try {
            DBConnection dbConn = new DBConnection();
            String qr = "Select * From " + table + " Where " + columnConstraint1 + " = N'" + value1
                    + "' And " + columnConstraint2 + " = N'" + value2 + "'";
            ResultSet rs = dbConn.GetData(qr);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingDetailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
