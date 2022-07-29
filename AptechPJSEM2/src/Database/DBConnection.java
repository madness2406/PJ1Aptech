/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import java.sql.*;


public class DBConnection {
    String dbName="booklibrary"; // Tên Cơ sở Dữ liệu mà mình tạo trong mySQL
    
     String connString = "jdbc:mysql://localhost:3307/"+dbName; // tạo chuỗi kết nối tới CSDL
     public Connection getConnection() // Hàm kết nối tới CSDL
     {
         Connection connection=null; // Tạo ra một biến kết nối 
         try {
             connection =DriverManager.getConnection(connString,"root",""); // Công việc kết nối
             
             System.out.println("Connect successful");
             return connection;
         } catch (SQLException e) 
         {
              System.out.println("Connect not successful");
         }
         return null;
     }
       public ResultSet getData(String query)
       {
           Connection con =getConnection();
           if(con== null )
           {
               CloseConnection(con);
               return null;
        
            }
           Statement stmt;
           try {
               stmt=con.createStatement();
               ResultSet rs =stmt.executeQuery(query);
               return rs;
               
           } catch (Exception e) {
               e.printStackTrace();
               CloseConnection(con);
               return null;
           }
    
        }
       public boolean updateData(String query)
       {
           Connection con =getConnection();
           if(con==null)
           {
               CloseConnection(con);
               return false;
           }
           Statement stmt;
           try {
               stmt=con.createStatement();
               stmt.executeUpdate(query);
               return true;
               
           } catch (Exception e) {
               e.printStackTrace();
               
               return false;
           }
           
       }


    private void CloseConnection(Connection con) {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
