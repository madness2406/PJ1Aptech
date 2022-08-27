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
 * @author phamtrunghieu
 */
public class StudentManager {

    public static boolean StudentToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select Id, Name, Gender, DOB, POB, PhoneNo From student");
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

    public static boolean Add(String name, String gender, String DOB, String POB, String PhoneNo) {
        DBConnection dbconn = new DBConnection();
        String sql;
        sql = "Insert into student (Name,Gender,DOB,POB,PhoneNo)"
                + " values (N'" + name + "',N'" + gender + "',N'"
                + DOB + "',N'" + POB + "',N'" + PhoneNo + "')";
        return dbconn.UpdateData(sql);

    }

}
