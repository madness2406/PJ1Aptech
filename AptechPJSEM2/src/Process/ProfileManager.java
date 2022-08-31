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
public class ProfileManager {  

    public static String GetData(String table, String columnDisplay, String columnConstraint, String value) {
        try {
            String dm = null;
            DBConnection dbConn = new DBConnection();
            String qr = "Select " + columnDisplay + " From " + table + " Where " + columnConstraint + " = N'" + value + "'";
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
    
    public static boolean Edit(String id, String password, String email,
            String phoneNo) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update account Set Password = N'" + password + "', Email = N'"
                + email + "', PhoneNo = N'" + phoneNo +  "'"                             
                + " Where AccountId = '" + id + "'";
        return dbConn.UpdateData(sql);
    }
    
    public static boolean Edit(String id, String email, String phoneNo) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update account Set Email = N'" + email + "', PhoneNo = N'" + phoneNo +  "'"                             
                + " Where AccountId = '" + id + "'";
        return dbConn.UpdateData(sql);
    }
}
