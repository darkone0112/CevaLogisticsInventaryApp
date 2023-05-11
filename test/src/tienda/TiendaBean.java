package tienda;

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
import javax.swing.table.DefaultTableModel;/**
 *
 * @author osboxes
 */
public class TiendaBean implements TiendaInterface{
    private int id;
  private String nombre;
    private String direccion;
    private int cp;
    private String pais;
    private String provincia;
    private String email;
    private String telefono;
    private int empresaid;
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

    public void addTienda() {
        JTextField textFieldNombre = new JTextField();
        JTextField textFieldDireccion = new JTextField();
        JTextField textFieldCp = new JTextField();
        JTextField textFieldPais = new JTextField();
        JTextField textFieldProvincia = new JTextField();
        JTextField textFieldEmail = new JTextField();
        JTextField textFieldTelefono = new JTextField();
        JTextField textFieldEmpresaId = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new javax.swing.JLabel("Nombre:"));
        panel.add(textFieldNombre);
        panel.add(new javax.swing.JLabel("Direccion:"));
        panel.add(textFieldDireccion);
        panel.add(new javax.swing.JLabel("CP:"));
        panel.add(textFieldCp);
        panel.add(new javax.swing.JLabel("Pais:"));
        panel.add(textFieldPais);
        panel.add(new javax.swing.JLabel("Provincia:"));
        panel.add(textFieldProvincia);
        panel.add(new javax.swing.JLabel("Email:"));
        panel.add(textFieldEmail);
        panel.add(new javax.swing.JLabel("Telefono:"));
        panel.add(textFieldTelefono);
        panel.add(new javax.swing.JLabel("EmpresaId:"));
        panel.add(textFieldEmpresaId);

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar tienda", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
                try {
                    statement = conn.createStatement();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                setNombre(textFieldNombre.getText());
                setDireccion(textFieldDireccion.getText());
                setCp(Integer.parseInt(textFieldCp.getText()));
                setPais(textFieldProvincia.getText());
                setProvincia(textFieldProvincia.getText());
                setEmail(textFieldEmail.getText());
                setTelefono(textFieldTelefono.getText());
                setEmpresaId(Integer.parseInt(textFieldEmpresaId.getText()));

                String sql = "INSERT INTO TIENDA (nombre, direccion, cp, pais, provincia, email, telefono, empresaid) " +
                             "VALUES ('" + getNombre() + "','" + getDireccion() + "','" + getCp() + "','" + getPais() + "','" + getProvincia() + "','" + getEmail() + "','" + getTelefono() +  "','" + getEmpresaId() + "')";
                try {
                    System.out.println("ok");
                    statement.executeUpdate(sql);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println("Tienda added successfully.");
                JOptionPane.showMessageDialog(null, "Tienda agregado con éxito");
            }
        }
     public void updateTienda(DefaultTableModel model, int selectedRow) {
            JTextField textFieldTiendaID = new JTextField(String.valueOf(model.getValueAt(selectedRow, 0)));
            JTextField textFieldNombre = new JTextField(String.valueOf(model.getValueAt(selectedRow, 1)));
            JTextField textFieldDireccion = new JTextField(String.valueOf(model.getValueAt(selectedRow, 2)));
            JTextField textFieldCp = new JTextField(String.valueOf(model.getValueAt(selectedRow, 3)));
            JTextField textFieldPais = new JTextField(String.valueOf(model.getValueAt(selectedRow, 4)));
            JTextField textFieldProvincia = new JTextField(String.valueOf(model.getValueAt(selectedRow, 5)));
            JTextField textFieldEmail = new JTextField(String.valueOf(model.getValueAt(selectedRow, 6)));
            JTextField textFieldTelefono = new JTextField(String.valueOf(model.getValueAt(selectedRow, 7)));
            JTextField textFieldEmpresaId = new JTextField(String.valueOf(model.getValueAt(selectedRow, 8)));



            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new javax.swing.JLabel("Empleado ID:"));
            panel.add(textFieldTiendaID);
            panel.add(new javax.swing.JLabel("Nombre:"));
            panel.add(textFieldNombre);
            panel.add(new javax.swing.JLabel("Direccion:"));
            panel.add(textFieldDireccion);
            panel.add(new javax.swing.JLabel("Cp:"));
            panel.add(textFieldCp);
            panel.add(new javax.swing.JLabel("Pais:"));
            panel.add(textFieldPais);
            panel.add(new javax.swing.JLabel("Provincia:"));
            panel.add(textFieldProvincia);
            panel.add(new javax.swing.JLabel("Email:"));
            panel.add(textFieldEmail);
            panel.add(new javax.swing.JLabel("Telefono:"));
            panel.add(textFieldTelefono);

            panel.add(new javax.swing.JLabel("EmpresaId:"));
            panel.add(textFieldEmpresaId);
            int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Tienda", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION){
                    try {
                        statement = conn.createStatement();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setId(Integer.parseInt(textFieldTiendaID.getText()));
                    setNombre(textFieldNombre.getText());
                    setDireccion(textFieldDireccion.getText());
                    setCp(Integer.parseInt(textFieldCp.getText()));
                    setPais(textFieldPais.getText());
                    setProvincia(textFieldProvincia.getText());
                    setEmail(textFieldEmail.getText());
                    setTelefono(textFieldTelefono.getText());
                    setEmpresaId(Integer.parseInt(textFieldEmpresaId.getText()));

                    String sql = "UPDATE TIENDA SET nombre='" + getNombre() + "', direccion='" + getDireccion() + "', cp='" + getCp() + "', pais='" + getPais() + "', provincia='" + getProvincia() + "', email=" + getEmail() + ", Telefono=" + getTelefono() + ", EMPRESAID='" + getEmpresaId() + "' WHERE ID='" + Integer.parseInt(textFieldTiendaID.getText()) + "';";
                    try {
                    statement.executeUpdate(sql);
                    } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                    model.setValueAt(getId(), selectedRow, 0);
                    model.setValueAt(getNombre(), selectedRow, 1);
                    model.setValueAt(getDireccion(), selectedRow, 2);
                    model.setValueAt(getCp(), selectedRow, 3);
                    model.setValueAt(getPais(), selectedRow, 4);
                    model.setValueAt(getProvincia(), selectedRow, 5);
                    model.setValueAt(getEmail(), selectedRow, 6);
                    model.setValueAt(getTelefono(), selectedRow, 7);
                    model.setValueAt(getEmpresaId(), selectedRow, 8);
                    }
                    }

    public void deleteTienda(DefaultTableModel model, int selectedRow) {
                        if (selectedRow == -1) {
                            // No row is selected
                            JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected Tienda?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            // Delete the employee
                            int selectedTiendaId = (Integer) model.getValueAt(selectedRow, 0);
                            try {
                                String sql = "DELETE FROM TIENDA WHERE id = ?";
                                PreparedStatement statement = conn.prepareStatement(sql);
                                statement.setInt(1, selectedTiendaId);
                                statement.executeUpdate();
                                model.removeRow(selectedRow);
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, "Error deleting tienda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        public void findTiendaById(DefaultTableModel model) {
            JTextField textIdField = new JTextField();
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new javax.swing.JLabel("Id que quiere buscar"));
            panel.add(textIdField);
    
            int result = JOptionPane.showConfirmDialog(null, panel, "BuscarTienda", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.OK_OPTION) {
                try {
                    statement = conn.createStatement();
                    String query = "SELECT * from TIENDA where id = "+ Integer.parseInt(textIdField.getText())+ ";";
    
                    ResultSet rs = statement.executeQuery(query);
                    model.setRowCount(0);
                    System.out.println(rs);
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("cp"),
                            rs.getString("provincia"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getString("empresaId"),
                        });
                    }
                    rs.close();
            } catch(SQLException e) {
                System.out.println("Error buscando empresa:" + e);
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
    public void displayAllTienda(DefaultTableModel model) {
        try {
            String query = "SELECT * FROM TIENDA";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            model.setRowCount(0);
            System.out.println(rs);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getInt("cp"),
                    rs.getString("pais"),
                    rs.getString("provincia"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getInt("empresaid")

                });
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error displaying TIENDA: " + e);
        }
    }


    public TiendaBean() {
    }
    public TiendaBean(String nombre, String direccion, int cp, String pais, String provincia, String email, String telefono, int empresaid) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cp = cp;
        this.pais = pais;
        this.provincia=provincia;
        this.email = email;
        this.telefono = telefono;
        this.empresaid = empresaid;
        this.conn = conn;
        this.statement = statement;
        this.resultSet = resultSet;

    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
    public int getCp(){
        return cp;
    }
    public void setCp(int cp){
        this.cp=cp;
    }
    public String getPais(){
        return pais;
    }
    public void setPais(String pais){
        this.pais=pais;
    }
    public String getProvincia(){
        return provincia;
    }
    public void setProvincia(String provincia){
        this.provincia=provincia;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public int getEmpresaId(){
        return empresaid;
    }
    public void setEmpresaId(int empresaid){
        this.empresaid=empresaid;
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
}