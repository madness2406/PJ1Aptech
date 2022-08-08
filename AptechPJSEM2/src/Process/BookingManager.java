/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import Database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class BookingManager {

    public static boolean Add(String renderId, String lenderId, String day,
            String status, String note) {
        DBConnection dbconn = new DBConnection();
        String sql;
        if (!note.equals("")) {
            sql = "Insert into booking (RenderId,LenderId,ExpiredDay,Status,Deposit,Note)"
                    + " values (N'" + renderId + "',N'" + lenderId + "',N'"
                    + day + "',N'" + status + "','0" + "',N'" + note + "')";
        } else {
            sql = "Insert into booking (RenderId,LenderId,ExpiredDay,Status,Deposit)"
                    + " values (N'" + renderId + "',N'" + lenderId + "',N'"
                    + day + "',N'" + status + "','0')";
        }
        return dbconn.UpdateData(sql);

    }

    public static boolean Edit(String renderId, String day, String status, String note) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update booking Set RenderId = N'" + renderId + "', ExpiredDay = N'"
                + day + "', Status = N'" + status + "', Note = N'" + note + "')";
        return dbConn.UpdateData(sql);
    }

    public static boolean Delete(String bookingId) {
        String sql = "Delete From booking Where BookingId ='" + bookingId + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(sql);
    }

    public static boolean BookingToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select * From booking");
            String[] row = new String[8];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(5);
                row[4] = rs.getString(4);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                dfTableModel.addRow(row);
            }
            return true;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static int Count(String tableName, String columnName1, String value1, String coloumnName2, String value2) {
        String sql;
        if (value1.length() == 0 && columnName1.length() == 0
                && value2.length() == 0 && coloumnName2.length() == 0) {
            sql = "Select COUNT(*) from " + tableName;
        } else {
            sql = "Select COUNT(*) from " + tableName + " Where " + columnName1 + " = N'" + value1 + "' And "
                    + coloumnName2 + " = N'" + value2 + "'";
        }

        DBConnection dbConn = new DBConnection();
        ResultSet rs = dbConn.GetData(sql);
        try {
            if (rs.next()) {
                int count = Integer.parseInt(rs.getString(1));
                return count;
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }

    public static int Count(String tableName, String columnName, String value) {
        String sql;
        if (value.length() == 0 && columnName.length() == 0) {
            sql = "Select COUNT(*) from " + tableName;
        } else {
            sql = "Select COUNT(*) from " + tableName + " Where " + columnName + " = N'" + value + "'";
        }

        DBConnection dbConn = new DBConnection();
        ResultSet rs = dbConn.GetData(sql);
        try {
            if (rs.next()) {
                int count = Integer.parseInt(rs.getString(1));
                return count;
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }

    public static String TotalMoney(String bookingId) {
        String qr = "Select SUM(Money) From bookingdetail"
                + " Where BookBookingId = '" + bookingId + "'";
        DBConnection dbConn = new DBConnection();
        ResultSet rs = dbConn.GetData(qr);
        try {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return "0.0";
        }
        return "0.0";
    }

    public static String TotalDeposits(Float money, int interest) {
        return String.valueOf(money * interest / 100);
    }
}
