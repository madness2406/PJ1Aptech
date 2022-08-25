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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class BookingDetailManager {

    public static boolean Add(String bookingId, String bookId, String quantity,
            String money, String bookQuantity) {
        DBConnection dbconn = new DBConnection();
        String sql1, sql2;
        int _quantity = Integer.parseInt(quantity);
        int _bookQuantity = Integer.parseInt(bookQuantity);
        int result = _bookQuantity - _quantity;
        sql1 = "Insert into bookingdetail (BookBookingId,BookId,Quantity,Money)"
                + " values (N'" + bookingId + "',N'" + bookId + "',N'"
                + quantity + "',N'" + money + "')";
        sql2 = "Update book Set Quantity = N'" + result + "'"
                + " Where BookId = '" + bookId + "'";
        
        return dbconn.UpdateData(sql1, sql2);
    }
    
    public static boolean UpdateToBooking(String bookingId, String column1, String value1, String column2, String value2) {
        DBConnection dbconn = new DBConnection();
        String sql;
        sql = "Update booking Set "+ column1 + " = N'" + value1 + "', " + column2 +" = N'"
                + value2 + "' Where BookingId = '" + bookingId + "'";
        return dbconn.UpdateData(sql);
    }

    public static boolean Edit(String bookingId, String bookId, String newQuantity,
            String money, String bookQuantity, String oldQuantity) {
        DBConnection dbConn = new DBConnection();
        int _newQuantity = Integer.parseInt(newQuantity);
        int _oldQuantity = Integer.parseInt(oldQuantity);
        int _bookQuantity = Integer.parseInt(bookQuantity);
        int result = _bookQuantity - _newQuantity + _oldQuantity;
        String sql1 = "Update bookingdetail Set BookBookingId = N'" + bookingId + "', BookId = N'"
                + bookId + "', Quantity = N'" + newQuantity + "', Money = N'" + money + "'";
        String sql2 = "Update book Set Quantity = N'" + result + "'"
                + " Where BookId = '" + bookId + "'";
        return dbConn.UpdateData(sql1, sql2);
    }

    public static boolean Delete(String bookingId, String bookId, String quantity, String bookQuantity) {
        DBConnection dbConn = new DBConnection();
        int _quantity = Integer.parseInt(quantity);
        int _bookQuantity = Integer.parseInt(bookQuantity);
        int result = _quantity + _bookQuantity;
        String sql1 = "Delete From bookingdetail Where BookId ='" + bookId + "' And "
                + "BookBookingId = '" + bookingId + "'";
        String sql2 = "Update book Set Quantity = N'" + result + "'"
                + " Where BookId = '" + bookId + "'";
        return dbConn.UpdateData(sql1, sql2);
    }

    public static boolean BookingDetailToTable(JTable jTable, String bookingId) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select bkd.BookId,b.Name,bkd.Quantity,bkd.Money "
                    + "From bookingdetail bkd "
                    + "Inner Join book b "
                    + "On bkd.BookId = b.BookId "
                    + "Where BookBookingId = '" + bookingId + "'");
            String[] row = new String[4];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);

                dfTableModel.addRow(row);
            }
            return true;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean BookToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select BookId,Name,Author From book");
            String[] row = new String[3];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                dfTableModel.addRow(row);
            }
            return true;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean SearchBookToTable(JTable jTable, String constraint) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs;
            if (constraint.length() > 0) {
                rs = db.GetData("Select BookId,Name,Author From book Where (Name Like '%" + constraint + "%' Or Author Like '%" + constraint + "%')");
            } else {
                rs = db.GetData("Select BookId,Name,Author From book");
            }
            String[] row = new String[3];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                dfTableModel.addRow(row);
            }
            return dfTableModel.getRowCount() > 0;
        } catch (SQLException ex) {
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
        } catch (NumberFormatException | SQLException ex) {
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
        } catch (NumberFormatException | SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }

    public static String TotalValue(String aggregateFunction, String tableName, String columnName, String value) {
        String sql;
        sql = "Select " + aggregateFunction + " from " + tableName + " Where " + columnName + " = N'" + value + "'";

        DBConnection dbConn = new DBConnection();
        ResultSet rs = dbConn.GetData(sql);
        try {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (NumberFormatException | SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return "0.0";
        }

        return "0.0";
    }

    public static String GetData(String table, String columnDisplay) {
        try {
            String dm = null;
            DBConnection dbConn = new DBConnection();
            String qr = "Select " + columnDisplay + " From " + table;
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
}
