/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import Database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class CategoryManager {

    public static boolean Add(String name, String creatorId, String note) {
        DBConnection dbconn = new DBConnection();
        String sql;
        if (!note.equals("")) {
            sql = "Insert Into category (Name,CreatorId,Note)"
                    + " values (N'" + name + "',N'" + creatorId + "',N'" + note + "')";
        }
        else{
            sql = "Insert into category (Name,CreatorId)"
                    + " values (N'" + name + "',N'" + creatorId + "')";
        }
        return dbconn.UpdateData(sql);

    }

    public static boolean Edit(String id, String name, String modifyDate, String changerId, String note) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update category Set Name = N'" + name + "', ModifyTime = N'" + modifyDate + "', ChangerId = N'" + changerId
                + "', Description = N'" + note + "'"
                + " Where CategoryId = '" + id + "'";
        return dbConn.UpdateData(sql);
    }
    
    public static boolean Delete(String categoryId) {
        String sql = "Delete From category Where CategoryId = '" + categoryId + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(sql);
    }

    public static boolean CategoryToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select CategoryId,Name,CreatedTime,ModifyTime,Description From category");
            String[] row = new String[5];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                dfTableModel.addRow(row);
            }
            return true;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean SearchCategoryToTable(JTable jTable, String constraint) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs;
            if (constraint.length() > 0) {
                rs = db.GetData("Select CategoryId,Name,CreatedTime,ModifyTime,Description "
                        + "From category Where ("
                        + "Name Like '%" + constraint + "%')");
            } else {
                rs = db.GetData("Select CategoryId,Name,CreatedTime,ModifyTime,Description From category");
            }
            String[] row = new String[5];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                dfTableModel.addRow(row);
            }
            return dfTableModel.getRowCount() > 0;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static int Count(String tableName, String columnName, String value) {
        String sql;
            sql = "Select COUNT(*) from " + tableName + " Where " + columnName + " = N'" + value + "'";
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
