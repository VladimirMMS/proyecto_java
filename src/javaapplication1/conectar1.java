
package javaapplication1;
import java.sql.*;



public class conectar1 {
  Connection conect = null;
  public Connection conexion() {
    String url = "jdbc:mysql://localhost:3306/facturacion";
    try {
        conect = DriverManager.getConnection(url, "root", "root");
        System.out.println("Database has been connected succesfully");
    } catch (SQLException e) {
      System.out.println("An error occurred while connecting to the database "+ e);
    } 
    return conect;
  }
}

