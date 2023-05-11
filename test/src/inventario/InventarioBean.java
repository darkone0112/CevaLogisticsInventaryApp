package inventario;

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

public class InventarioBean implements InventarioInterface {

    private int id;
    private String producto;
    private BigDecimal precio;
    private String tienda;
    private int cantidad;

    private Connection conn;
    private Statement statement;
    private ResultSet resultSet;


    @Override
    public void loadJDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e);
        }
    }

    @Override
    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionEmpresaDB", "VsCode", "2458");
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e);
        }
    }

    @Override
    public void addInventario() {
        JTextField textFieldProducto = new JTextField();
        JTextField textFieldTienda = new JTextField();
        JTextField textFieldCantidad = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new javax.swing.JLabel("Producto:"));
        panel.add(textFieldProducto);
        panel.add(new javax.swing.JLabel("Tienda:"));
        panel.add(textFieldTienda);
        panel.add(new javax.swing.JLabel("Cantidad:"));
        panel.add(textFieldCantidad);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar al inventario", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION){
            try {
                statement = conn.createStatement();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            setProducto(textFieldProducto.getText());
            setTienda(textFieldTienda.getText());
            setCantidad(Integer.parseInt(textFieldCantidad.getText()));

            String sql = "insert into INVENTARIO (productoid, tiendaid, cantidad) values ((select producto.id from producto where nombre = '"+ getProducto() +"'), (select tienda.id from tienda where nombre= '"+getTienda()+"'), "+ getCantidad() +")";
            try {
                System.out.println("ok");
                statement.executeUpdate(sql);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("Inentario added successfully.");
            JOptionPane.showMessageDialog(null, "Inventario agregado con éxito");
        }
    }

    @Override
    public void displayAllInventario(DefaultTableModel model) {
        try {
            String query = "select i.id as 'id', p.nombre as 'producto', p.precio as 'precio', t.nombre as 'tienda',i.cantidad as 'cantidad' from INVENTARIO i, PRODUCTO p , TIENDA t where (p.id = productoid and t.id= tiendaid)";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            model.setRowCount(0);
            System.out.println(rs);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("producto"),
                    rs.getBigDecimal("precio"),
                    rs.getString("tienda"),
                    rs.getInt("cantidad")

                });
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error displaying employees: " + e);
        }
    }
    @Override
    public void updateInventario(DefaultTableModel model, int selectedRow) {
        JTextField textFieldInventarioId = new JTextField(String.valueOf(model.getValueAt(selectedRow, 0)));
        JTextField textFieldProducto = new JTextField(String.valueOf(model.getValueAt(selectedRow, 1)));
        JTextField textFieldTienda = new JTextField(String.valueOf(model.getValueAt(selectedRow, 3)));
        JTextField textFieldCantidad = new JTextField(String.valueOf(model.getValueAt(selectedRow, 4)));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new javax.swing.JLabel("Inventario Id:"));
        panel.add(textFieldInventarioId);
        panel.add(new javax.swing.JLabel("Producto:"));
        panel.add(textFieldProducto);
        panel.add(new javax.swing.JLabel("Tienda:"));
        panel.add(textFieldTienda);
        panel.add(new javax.swing.JLabel("Cantidad:"));
        panel.add(textFieldCantidad);

        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Inventario", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION){
            try {
                statement = conn.createStatement();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            setId(Integer.parseInt(textFieldInventarioId.getText()));
            setProducto(textFieldProducto.getText());
            setTienda(textFieldTienda.getText());
            setCantidad(Integer.parseInt(textFieldCantidad.getText()));

            String sql = "update INVENTARIO set productoid=(select id from producto where nombre= '"+getProducto() +"'), tiendaid= (select id from tienda where nombre= '"+getTienda()+"'), cantidad="+getCantidad()+" where id='"+getId()+"'";
            try {
            statement.executeUpdate(sql);
            } catch (SQLException e) {
            e.printStackTrace();
            }
            model.setValueAt(getId(), selectedRow, 0);
            model.setValueAt(getProducto(), selectedRow, 1);
            model.setValueAt(getTienda(), selectedRow, 2);
            model.setValueAt(getCantidad(), selectedRow, 3);

            }
    }
    @Override
    public void findInventarioById(DefaultTableModel model) {
        JTextField textIdField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new javax.swing.JLabel("Id que quiere buscar"));
        panel.add(textIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Buscar Inventario", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            try {
                statement = conn.createStatement();
                String query = "select i.id as 'id', p.nombre as 'producto', p.precio as 'precio', t.nombre as 'tienda',i.cantidad as 'cantidad' from INVENTARIO i, PRODUCTO p , TIENDA t where (p.id = productoid and t.id= tiendaid) and t.id="+ Integer.parseInt(textIdField.getText())+ ";";

                ResultSet rs = statement.executeQuery(query);
                model.setRowCount(0);
                System.out.println(rs);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("producto"),
                        rs.getBigDecimal("precio"),
                        rs.getString("tienda"),
                        rs.getInt("cantidad")
                    });
                }
                rs.close();
        } catch(SQLException e) {
            System.out.println("Error buscando Inventario:" + e);
        }

        }
    }
    @Override
    public void deleteInventario(DefaultTableModel model, int selectedRow) {
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
                String sql = "DELETE FROM INVENTARIO WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, selectedEmployeeId);
                statement.executeUpdate();
                model.removeRow(selectedRow);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting Inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public InventarioBean() {
    }
    public InventarioBean(int id,String producto, BigDecimal precio ,String tienda, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.precio = precio;
        this.tienda = tienda;
        this.cantidad = cantidad;
        this.conn = conn;
        this.statement = statement;
        this.resultSet = resultSet;

    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}