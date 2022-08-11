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
public class BookManager {

    public static boolean Add(String name, String author, String categoryId,
            String publishYear, String quantity, String price, String creatorId, String note) {
        DBConnection dbconn = new DBConnection();
        String sql;
        if (!note.equals("")) {
            sql = "Insert into book (Name,Author,CategoryId,PublishYear,Quantity,Price,CreatorId,Note)"
                    + " values (N'" + name + "',N'" + author + "',N'"
                    + categoryId + "',N'" + publishYear + "',N'" + quantity + "',N'"
                    + price + "',N'" + creatorId + "',N'" + note + "')";
        }
        else{
            sql = "Insert into book (Name,Author,CategoryId,PublishYear,Quantity,Price,CreatorId)"
                    + " values (N'" + name + "',N'" + author + "',N'"
                    + categoryId + "',N'" + publishYear + "',N'" + quantity + "',N'"
                    + price + "',N'" + creatorId + "')";
        }
        return dbconn.UpdateData(sql);

    }

    public static boolean Edit(String id, String name, String author, String categoryId,
            String publishYear, String quantity, String price, String modifyDate, String changerId, String note) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update book Set Name = N'" + name + "', Author = N'" + author + "', CategoryId = N'"
                + categoryId + "', Publishyear = N'" + publishYear + "', Quantity = N'" + quantity
                + "', Price = N'" + price + "', ModifiedTime = N'" + modifyDate + "', ChangerId = N'" + changerId
                + "', Note = N'" + note + "'"
                + " Where BookId = '" + id + "'";
        return dbConn.UpdateData(sql);
    }

    public static boolean Delete1(String bookId) {
        String sql = "Delete From book Where BookId = '" + bookId + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(sql);
    }

    public static boolean BookToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select * From book");
            String[] row = new String[8];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(5);
                row[4] = rs.getString(4);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(10);
                dfTableModel.addRow(row);
            }
            return true;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }
    public static int Count(String tableName, String columnName1,String coloumnName2, String value1, String value2) {
        String sql;
        if (value1.length() == 0 && columnName1.length() == 0 &&
            value2.length() == 0 && coloumnName2.length() == 0) {
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
            return -1;
        }
        return -1;
    }

    public static DefaultComboBoxModel GetCategoryId() throws SQLException {
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        DBConnection dbConn = new DBConnection();

        String qr = "Select CategoryId from category";
        ResultSet rs = dbConn.GetData(qr);
        while (rs.next()) {
            String name = rs.getString(1);
            dm.addElement(name);
        }
        return dm;
    }
}
