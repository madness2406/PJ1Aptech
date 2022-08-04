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
            sql = "Insert into booking (RenderId,LenderId,ExpiredDay,Status,Note)"
                    + " values (N'" + renderId + "',N'" + lenderId + "',N'"
                    + day + "',N'" + status + "',N'" + note + "')";
        }
        else{
            sql = "Insert into booking (RenderId,LenderId,ExpiredDay,Status)"
                    + " values (N'" + renderId + "',N'" + lenderId + "',N'"
                    + day + "',N'" + status + "')";
        }
        return dbconn.UpdateData(sql);

    }

    public static boolean Edit(String renderId, String lenderId, String day,
            String status, String note) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update booking Set RenderId = N'" + renderId + "', LenderId = N'" + lenderId + "', ExpiredDay = N'"
                + day + "', Status = N'" + status + "', Note = N'" + note + ")";
        return dbConn.UpdateData(sql);
    }

    public static boolean Delete(String bookingId) {
        String sql = "Delete From booking Where BookingId ='" + bookingId + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(sql);
    }

    public static boolean BookToTable(JTable jTable) {
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
                row[7] = rs.getString(9);
                dfTableModel.addRow(row);
            }
            return true;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
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
            return -1;
        }
        return -1;
    }

    public static Double TotalMoney(String bookingId) {
        Double a = 0.0;
        String qr = "Select SUM(bkd.Quantity * b.Price) From booking bk"
                 + " INNER JOIN bookingdetail bkd ON bk.BookingId = bkd.BookBookingId"
                 + " INNER JOIN book b ON bkd.BookId = b.BookId"
                 + " Where bk.BookingId = '" + bookingId + "'";
        DBConnection dbConn = new DBConnection();
        ResultSet rs = dbConn.GetData(qr);
        try {
            if (rs.next()) {
                a = Double.parseDouble(rs.getString(1));
                return a;
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return a;
        }
        return a;
    }
    
    public static Double TotalDeposits(Double money, int interest){
        return money*interest/100;
    }
}
