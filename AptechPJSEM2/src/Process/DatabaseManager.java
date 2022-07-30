/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Database.DBConnection;
import javax.management.Query;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Database.*;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Admin
 */
public class DatabaseManager {

    //Thực hiện thêm sửa xoá các chức năng
    public static boolean ThemKhachhang(String makh, String tenkh, String sdt) {
        DBConnection dbconn = new DBConnection();
        String sql = "Insert into Khachhang values ('" + makh + "',N'" + tenkh + "',N'" + sdt + "')";
        return dbconn.UpdateData(sql);

    }

    public static boolean SuaKhachhang(String makh, String tenkh, String sdt) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update Khachhang Set tenkh = N'" + tenkh + "', dthoai= N'" + sdt + "'  Where makh = '" + makh + "'  ";

        return dbConn.UpdateData(sql);
    }

    public static boolean XoaKhachhang(String makh) {
        String qr = "Delete From Khachhang Where makh = '" + makh + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(qr);
    }

    public static boolean KhachhangToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select * From Khachhang");
            String[] row = new String[3];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                dfTableModel.addRow(row);
            }
            return true;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean ThemHangHoa(String mahh, String tenhh, String quicach, String donvitinh, String dongia) {
        DBConnection dbconn = new DBConnection();
        String sql = "Insert into Hanghoa values ('" + mahh + "',N'" + tenhh + "',N'" + quicach + "',N'" + donvitinh + "',N'" + dongia + "')";
        return dbconn.UpdateData(sql);

    }

    public static boolean SuaHangHoa(String mahh, String tenhh, String quicach, String donvitinh, String dongia) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update Hanghoa Set tenhh = N'" + tenhh + "', quicach= N'" + quicach + "', dvitinh= N'" + donvitinh + "', dongia= N'" + dongia + "'  Where mahh = '" + mahh + "'  ";

        return dbConn.UpdateData(sql);
    }

    public static boolean XoaHangHoa(String mahh) {
        String sql = "Delete From Chitiethoadon Where mahh ='" + mahh + "'";
        String qr = "Delete From Hanghoa Where mahh = '" + mahh + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(qr);
    }

    public static boolean HangHoaToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select * From Hanghoa");
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
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean ThemHoaDon(String hdso, String ngay, String makh, String trigia) {
        String qr = "Insert Into HoaDon Values('" + hdso + "',N'" + ngay + "', N'" + makh + "', N'" + trigia + "')";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(qr) == true;
    }

    public static boolean SuaHoaDon(String hdso, String ngay, String makh, String trigia) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update Hoadon Set ngay = N'" + ngay + "', makh= N'" + makh + "', trigia= N'" + trigia + "'  Where hdso = '" + hdso + "'  ";

        return dbConn.UpdateData(sql);
    }

    public static boolean XoaHoadon(String hdso) {
        String sqlChitiet = "Delete From Chitiethoadon Where hdso = '" + hdso + "'";
        String sqlHoadon = "Delete From Hoadon Where hdso = '" + hdso + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(sqlHoadon);
    }

    public static boolean HoaDonToTable(JTable jTable) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select * From Hoadon");
            String[] row = new String[4];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);

                dfTableModel.addRow(row);
            }
            return true;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean ThemChiTietHoaDon(String hdso, String mahh, String soluong) {
        DBConnection dbconn = new DBConnection();
        String sql = "Insert into Chitiethoadon values ('" + hdso + "',N'" + mahh + "',N'" + soluong + "')";
        return dbconn.UpdateData(sql);

    }

    public static boolean SuaChiTietHoaDon(String hdso, String mahh, String soluong) {
        DBConnection dbConn = new DBConnection();
        String sql = "Update Chitiethoadon Set mahh = N'" + mahh + "', soluong= N'" + soluong + "'  Where hdso = '" + hdso + "'  ";

        return dbConn.UpdateData(sql);
    }

    public static boolean XoaChiTietHoaDon(String mahh, String hdso) {
        String qr = "Delete From Chitiethoadon Where mahh = '" + mahh + "' And hdso='" + hdso + "'";
        DBConnection dbConn = new DBConnection();
        return dbConn.UpdateData(qr);
    }

    public static boolean ChiTietHoaDonToTable(JTable jTable, String hdso) {
        try {
            DefaultTableModel dfTableModel = (DefaultTableModel) jTable.getModel();
            dfTableModel.setRowCount(0);
            DBConnection db = new DBConnection();
            ResultSet rs = db.GetData("Select * From Chitiethoadon Where hdso = '" + hdso + "'");
            String[] row = new String[3];
            while (rs.next()) {
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                dfTableModel.addRow(row);
            }
            return true;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static int Count(String tableName, String columnName, String id) {
        String sql;
        if (id.length() == 0 || columnName.length() == 0) {
            sql = "Select COUNT(*) from " + tableName;
        } else {
            sql = "Select COUNT(*) from " + tableName + " Where " + columnName + " = '" + id + "'  ";
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
 
    public static int CountCTHD1(String tableName, String columnName1, String id1, String columnName2, String id2) {
        String qr;
        if ((id1.length() == 0 || columnName1.length() == 0) && (id2.length() == 0 || columnName2.length() == 0)) {
            qr = "Select COUNT(*) from " + tableName;
        } else {
            qr = "Select COUNT(*) from " + tableName + " Where " + columnName1 + " = '" + id1 + "' And " + columnName2 + " = '" + id2 + "'";
        }

        DBConnection dbConn = new DBConnection();
        ResultSet rs = dbConn.GetData(qr);
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

    public static DefaultComboBoxModel LayMKH() throws SQLException {
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        DBConnection dbConn = new DBConnection();

        String qr = "Select makh from Khachhang";
        ResultSet rs = dbConn.GetData(qr);
        while (rs.next()) {
            String name = rs.getString(1);
            dm.addElement(name);
        }
        return dm;
    }

    public static DefaultComboBoxModel LayMHH() throws SQLException {
        DefaultComboBoxModel dm = new DefaultComboBoxModel();
        DBConnection dbConn = new DBConnection();

        String qr = "Select mahh from Hanghoa";
        ResultSet rs = dbConn.GetData(qr);
        while (rs.next()) {
            String name = rs.getString(1);
            dm.addElement(name);
        }
        return dm;
    }

    public static String LayGT(String chon, String mahh) throws SQLException {
        String dm = null;
        DBConnection dbConn = new DBConnection();
        String qr = "Select " + chon + " from Hanghoa where mahh = '" + mahh + "'";
        ResultSet rs = dbConn.GetData(qr);
        if (rs.next()) {
            dm = rs.getString(chon);
        }
        return dm;
    }

    public static Double triGia(String hdso) {
        Double a = 0.0;
        String qr = "Select SUM(Hanghoa.dongia * Chitiethoadon.soluong) From Hanghoa"
                + " INNER JOIN Chitiethoadon ON Hanghoa.mahh = Chitiethoadon.mahh Where Chitiethoadon.hdso = '" + hdso + "'";
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

}
