
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Inventary {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void loadJDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e);
        }
    }

    public void conectar() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventario", "VsCode", "2458");
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e);
        }
    }
    
}
