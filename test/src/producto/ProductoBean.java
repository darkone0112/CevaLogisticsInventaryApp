/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producto;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author osboxes
 */
public class ProductoBean implements ProductoInterface{
    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int fabricanteid;

    private Connection conn;
    private Statement statement;
    private ResultSet resultSet;

    public void loadJDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e);
        }
    }

    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionEmpresaDB", "VsCode", "2458");
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e);
        }
    }

    public void addProducto() {
        JTextField textFieldNombre = new JTextField();
        JTextField textFieldDescripcion = new JTextField();
        JTextField textFieldPrecio = new JTextField();
        JTextField textFieldFabricanteID = new JTextField();


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



            panel.add(new javax.swing.JLabel("Nombre:"));
            panel.add(textFieldNombre);
            panel.add(new javax.swing.JLabel("Descripcion:"));
            panel.add(textFieldDescripcion);
            panel.add(new javax.swing.JLabel("Precio:"));
            panel.add(textFieldPrecio);
            panel.add(new javax.swing.JLabel("Fabricanteid:"));
            panel.add(textFieldFabricanteID);

            int result = JOptionPane.showConfirmDialog(null, panel, "Agregar producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
                try {
                    statement = conn.createStatement();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                setNombre(textFieldNombre.getText());
                setDescripcion(textFieldDescripcion.getText());
                setPrecio(new BigDecimal(textFieldPrecio.getText()));
                setFabricanteId(Integer.parseInt(textFieldFabricanteID.getText()));

                String sql = "INSERT INTO PRODUCTO (nombre, descripcion, precio, fabricanteid) " +
                             "VALUES ('" + getNombre() + "','" + getDescripcion() + "','" + getPrecio() + "','" + getFabricanteId() + "')";
                try {
                    System.out.println("ok");
                    statement.executeUpdate(sql);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println("Producto added successfully.");
                JOptionPane.showMessageDialog(null, "Producto agregado con éxito");
            }
        }
        public void updateProducto(DefaultTableModel model, int selectedRow) {
            JTextField textFieldProductoID = new JTextField(String.valueOf(model.getValueAt(selectedRow, 0)));
            JTextField textFieldNombre = new JTextField(String.valueOf(model.getValueAt(selectedRow, 1)));
            JTextField textFieldDescripcion = new JTextField(String.valueOf(model.getValueAt(selectedRow, 2)));
            JTextField textFieldPrecio = new JTextField(String.valueOf(model.getValueAt(selectedRow, 3)));
            JTextField textFieldFabricanteID = new JTextField(String.valueOf(model.getValueAt(selectedRow, 4)));



            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new javax.swing.JLabel("Producto ID:"));
            panel.add(textFieldProductoID);
            panel.add(new javax.swing.JLabel("Nombre:"));
            panel.add(textFieldNombre);
            panel.add(new javax.swing.JLabel("Descripcion:"));
            panel.add(textFieldDescripcion);
            panel.add(new javax.swing.JLabel("Precio:"));
            panel.add(textFieldPrecio);
            panel.add(new javax.swing.JLabel("Fabricanteid:"));
            panel.add(textFieldFabricanteID);


            int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar fabricante", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION){
                    try {
                        statement = conn.createStatement();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setId(Integer.parseInt(textFieldProductoID.getText()));
                    setNombre(textFieldNombre.getText());
                    setDescripcion(textFieldDescripcion.getText());
                    setPrecio(new BigDecimal(textFieldPrecio.getText()));
                    setFabricanteId(Integer.parseInt(textFieldFabricanteID.getText()));

                    String sql = "UPDATE PRODUCTO SET nombre='" + getNombre() + "', descripcion='" + getDescripcion() + "', precio='" + getPrecio() + "', fabricanteid='" + getFabricanteId() + "' WHERE ID='" + Integer.parseInt(textFieldProductoID.getText()) + "';";
                    try {
                    statement.executeUpdate(sql);
                    } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                    model.setValueAt(getId(), selectedRow, 0);
                    model.setValueAt(getNombre(), selectedRow, 1);
                    model.setValueAt(getDescripcion(), selectedRow, 2);
                    model.setValueAt(getPrecio(), selectedRow, 3);
                    model.setValueAt(getFabricanteId(), selectedRow, 4);

                    }
                    }

                    public void deleteProducto(DefaultTableModel model, int selectedRow) {
                        if (selectedRow == -1) {
                            // No row is selected
                            JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected employee?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            // Delete the employee
                            int selectedEmployeeId = (Integer) model.getValueAt(selectedRow, 0);
                            try {
                                String sql = "DELETE FROM PRODUCTO WHERE id = ?";
                                PreparedStatement statement = conn.prepareStatement(sql);
                                statement.setInt(1, selectedEmployeeId);
                                statement.executeUpdate();
                                model.removeRow(selectedRow);
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, "Error deleting PRODUCTO: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }finally {
                                try {
                                    statement.close();
                                } catch (SQLException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                        }
    public void findProductoById(DefaultTableModel model) {
        JTextField textIdField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new javax.swing.JLabel("Id que quiere buscar"));
        panel.add(textIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            try {
                statement = conn.createStatement();
                String query = "SELECT * from PRODUCTO where id = "+ Integer.parseInt(textIdField.getText())+ ";";

                ResultSet rs = statement.executeQuery(query);
                model.setRowCount(0);
                System.out.println(rs);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("fabricanteid")
                    });
                }
                rs.close();
        } catch(SQLException e) {
            System.out.println("Error buscando Producto:" + e);
        }

        }
    }
    public void displayAllProducto(DefaultTableModel model) {
        try {
            String query = "SELECT * FROM PRODUCTO";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            model.setRowCount(0);
            System.out.println(rs);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBigDecimal("precio"),
                    rs.getInt("fabricanteid"),

                });
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error displaying employees: " + e);
        }
    }


    public ProductoBean() {
    }
    public ProductoBean(int id,String nombre, String descripcion, BigDecimal precio, int fabricanteid) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fabricanteid = fabricanteid;
        this.conn = conn;
        this.statement = statement;
        this.resultSet = resultSet;

    }
     public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getFabricanteId() {
        return fabricanteid;
    }
    public void setFabricanteId(int fabricanteid) {
        this.fabricanteid = fabricanteid;
    }

    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public Statement getStatement() {
        return statement;
    }
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    public ResultSet getResultSet() {
        return resultSet;
    }
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    //
}
