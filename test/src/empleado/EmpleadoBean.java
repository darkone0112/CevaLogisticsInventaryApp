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

public class EmpleadoBean implements EmpleadoInterface{
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String fecna;
    private String email;
    private int empresaid;
    private int tiendaid;
    private String telefono;
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ceva", "VsCode", "2458");
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e);
        }
    }
    
    public void addEmpleado() {
        JTextField textFieldNombre = new JTextField();
        JTextField textFieldApellido = new JTextField();
        JTextField textFieldDNI = new JTextField();
        JTextField textFieldFECNA = new JTextField();
        JTextField textFieldEmail = new JTextField();
        JTextField textFieldEmpresaID = new JTextField();
        JTextField textFieldTiendaID = new JTextField();
        JTextField textFieldTelefono = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new javax.swing.JLabel("Nombre:"));
        panel.add(textFieldNombre);
        panel.add(new javax.swing.JLabel("Apellidos:"));
        panel.add(textFieldApellido);
        panel.add(new javax.swing.JLabel("DNI:"));
        panel.add(textFieldDNI);
        panel.add(new javax.swing.JLabel("Fecha de Nacimiento:"));
        panel.add(textFieldFECNA);
        panel.add(new javax.swing.JLabel("Email:"));
        panel.add(textFieldEmail);
        panel.add(new javax.swing.JLabel("Empresa ID:"));
        panel.add(textFieldEmpresaID);
        panel.add(new javax.swing.JLabel("Tienda ID:"));
        panel.add(textFieldTiendaID);

        panel.add(new javax.swing.JLabel("Telefono:"));
        panel.add(textFieldTelefono);
        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Empleado", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
                try {
                    statement = conn.createStatement();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                setNombre(textFieldNombre.getText());
                setApellido(textFieldApellido.getText());
                setDni(textFieldDNI.getText());
                setFecna(textFieldFECNA.getText());
                setEmail(textFieldEmail.getText());
                setEmpresaid(Integer.parseInt(textFieldEmpresaID.getText()));
                setTiendaid(Integer.parseInt(textFieldTiendaID.getText()));
                setTelefono(textFieldTelefono.getText());
                java.sql.Date date = java.sql.Date.valueOf(textFieldFECNA.getText());
                String sql = "INSERT INTO EMPLEADO (nombre, apellidos, dni, fecna, email, empresaid, tiendaid, telefono) " +
                             "VALUES ('" + getNombre() + "','" + getApellido() + "','" + getDni() + "','" + date + "','" + getEmail() + "','" + getEmpresaid() + "','" + getTiendaid() +  "','" + getTelefono() + "')";
                try {
                    System.out.println("ok");
                    statement.executeUpdate(sql);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println("Empleado added successfully.");
                JOptionPane.showMessageDialog(null, "Empleado agregado con éxito");
            }
        }
        public void updateEmpleado(DefaultTableModel model, int selectedRow) {
            JTextField textFieldEmpleadoID = new JTextField(String.valueOf(model.getValueAt(selectedRow, 0)));
            JTextField textFieldNombre = new JTextField(String.valueOf(model.getValueAt(selectedRow, 1)));
            JTextField textFieldApellido = new JTextField(String.valueOf(model.getValueAt(selectedRow, 2)));
            JTextField textFieldDNI = new JTextField(String.valueOf(model.getValueAt(selectedRow, 3)));
            JTextField textFieldFECNA = new JTextField(String.valueOf(model.getValueAt(selectedRow, 4)));
            JTextField textFieldEmail = new JTextField(String.valueOf(model.getValueAt(selectedRow, 5)));
            JTextField textFieldEmpresaID = new JTextField(String.valueOf(model.getValueAt(selectedRow, 6)));
            JTextField textFieldTiendaID = new JTextField(String.valueOf(model.getValueAt(selectedRow, 7)));
            JTextField textFieldTelefono = new JTextField(String.valueOf(model.getValueAt(selectedRow, 8)));

    
            
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new javax.swing.JLabel("Empleado ID:"));
            panel.add(textFieldEmpleadoID);
            panel.add(new javax.swing.JLabel("Nombre:"));
            panel.add(textFieldNombre);
            panel.add(new javax.swing.JLabel("Apellidos:"));
            panel.add(textFieldApellido);
            panel.add(new javax.swing.JLabel("DNI:"));
            panel.add(textFieldDNI);
            panel.add(new javax.swing.JLabel("Fecha de Nacimiento:"));
            panel.add(textFieldFECNA);
            panel.add(new javax.swing.JLabel("Email:"));
            panel.add(textFieldEmail);
            panel.add(new javax.swing.JLabel("Empresa ID:"));
            panel.add(textFieldEmpresaID);
            panel.add(new javax.swing.JLabel("Tienda ID:"));
            panel.add(textFieldTiendaID);
        
            panel.add(new javax.swing.JLabel("Telefono:"));
            panel.add(textFieldTelefono);
            int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Empleado", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION){
                    try {
                        statement = conn.createStatement();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setNombre(textFieldNombre.getText());
                    setApellido(textFieldApellido.getText());
                    setDni(textFieldDNI.getText());
                    setFecna(textFieldFECNA.getText());
                    setEmail(textFieldEmail.getText());
                    setEmpresaid(Integer.parseInt(textFieldEmpresaID.getText()));
                    setTiendaid(Integer.parseInt(textFieldTiendaID.getText()));
                    setTelefono(textFieldTelefono.getText());
                    String sql = "UPDATE EMPLEADO SET nombre='" + getNombre() + "', apellidos='" + getApellido() + "', dni='" + getDni() + "', fecna='" + getFecna() + "', email='" + getEmail() + "', empresaId=" + getEmpresaid() + ", tiendaId=" + getTiendaid() + ", telefono='" + getTelefono() + "' WHERE dni='" + textFieldDNI.getText() + "';";
                    try {
                    statement.executeUpdate(sql);
                    } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                    model.setValueAt(getId(), selectedRow, 0);
                    model.setValueAt(getNombre(), selectedRow, 1);
                    model.setValueAt(getApellido(), selectedRow, 2);
                    model.setValueAt(getDni(), selectedRow, 3);
                    model.setValueAt(getFecna(), selectedRow, 4);
                    model.setValueAt(getEmail(), selectedRow, 5);
                    model.setValueAt(getEmpresaid(), selectedRow, 6);
                    model.setValueAt(getTiendaid(), selectedRow, 7);
                    model.setValueAt(getTelefono(), selectedRow, 8);
                    }
                    }

                    public void deleteEmpleado(DefaultTableModel model, int selectedRow) {
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
                                String sql = "DELETE FROM EMPLEADO WHERE id = ?";
                                PreparedStatement statement = conn.prepareStatement(sql);
                                statement.setInt(1, selectedEmployeeId);
                                statement.executeUpdate();
                                model.removeRow(selectedRow);
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, "Error deleting employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                        public void findEmpleadoById(DefaultTableModel model) {
                            JTextField textIdField = new JTextField();
                    
                            JPanel panel = new JPanel();
                            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                            panel.add(new javax.swing.JLabel("Id que quiere buscar"));
                            panel.add(textIdField);
                    
                            int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Empresa", JOptionPane.OK_CANCEL_OPTION);
                            if(result == JOptionPane.OK_OPTION) {
                                try {
                                    statement = conn.createStatement();
                                    String query = "SELECT * from EMPLEADO where id = "+ Integer.parseInt(textIdField.getText())+ ";";
                    
                                    ResultSet rs = statement.executeQuery(query);
                                    model.setRowCount(0);
                                    System.out.println(rs);
                                    while (rs.next()) {
                                        model.addRow(new Object[]{
                                            rs.getInt("id"),
                                            rs.getString("nombre"),
                                            rs.getString("apellidos"),
                                            rs.getString("dni"),
                                            rs.getString("fecna"),
                                            rs.getString("email"),
                                            rs.getInt("empresaid"),
                                            rs.getInt("tiendaid"),
                                            rs.getString("telefono")
                                        });
                                    }
                                    rs.close();
                            } catch(SQLException e) {
                                System.out.println("Error buscando empleado:" + e);
                            }
                            /* return empleado; */
                        } 
                    
                            }
    public void displayAllEmployees(DefaultTableModel model) {
        try {
            String query = "SELECT * FROM EMPLEADO";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            model.setRowCount(0);
            System.out.println(rs);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("dni"),
                    rs.getString("fecna"),
                    rs.getString("email"),
                    rs.getInt("empresaid"),
                    rs.getInt("tiendaid"),
                    rs.getString("telefono")
                });
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error displaying employees: " + e);
        }
    }
    

    public EmpleadoBean() {
    }
    public EmpleadoBean(int id,String nombre, String apellido, String dni, String fecna, String email, int empresaid, int tiendaid, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecna = fecna;
        this.email = email;
        this.empresaid = empresaid;
        this.tiendaid = tiendaid;
        this.telefono = telefono;
        this.conn = conn;
        this.statement = statement;
        this.resultSet = resultSet;

    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getFecna() {
        return fecna;
    }
    public void setFecna(String fecna) {
        this.fecna = fecna;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getEmpresaid() {
        return empresaid;
    }
    public void setEmpresaid(int empresaid) {
        this.empresaid = empresaid;
    }
    public int getTiendaid() {
        return tiendaid;
    }
    public void setTiendaid(int tiendaid) {
        this.tiendaid = tiendaid;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    //

}
