/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import Database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class EmployeeManager {
    
    public static boolean Add(String userName, String password, String email,
            String phoneNo) {
        DBConnection dbconn = new DBConnection();
        String sql;        
            sql = "Insert into account (UserName, Password, Email, PhoneNo, Role)"
                    + " values (N'" + userName + "',N'" + password + "',N'"                    
                    + email + "',N'" + phoneNo + "')";
       
        return dbconn.UpdateData(sql);

    }
    
    public static boolean Edit(String id,String userName, String password, String email,
            String phoneNo) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update account Set UserName = N'" + userName + "', Password = N'" + password + "', Email = N'"
                + email + "', PhoneNo = N'" + phoneNo +  "'"                             
                + " Where AccountId = '" + id + "'";
        return dbConn.UpdateData(sql);
    }
    
    public static boolean Delete(String accountId) {
        String sql = "Delete From account Where AccountId = '" + accountId + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(sql);
    }
    
    public static boolean AccountToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select UserName, Email, PhoneNo From account");
            String[] row = new String[3];
            while (rs.next()) {
                row[0] = rs.getString(2);
                row[1] = rs.getString(4);
                row[2] = rs.getString(5);  
                row[3] = rs.getString(1);
                dfTableModel.addRow(row);
            }
            return true;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static int Count(String tableName, String columnName1, String value1) {
        String sql;
        if (value1.length() == 0 && columnName1.length() == 0 ) {
            sql = "Select COUNT(*) from " + tableName;
        } else {
            sql = "Select COUNT(*) from " + tableName + " Where " + columnName1 + " = N'" + value1 +  "'";
        }

        DBConnection dbConn = new DBConnection();
        ResultSet rs = dbConn.GetData(sql);
        try {
            if (rs.next()) {
                int count = Integer.parseInt(rs.getString(1));
                return count;
            }
        } catch (NumberFormatException | SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return -1;
        }
        return -1;
    }
}
