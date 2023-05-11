package fabricante;
/**
 *
 * @author osboxes
 */
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osboxes
 */
public class FabricanteBean implements FabricanteInterface{
    private int id;
    private String nombre;
    private String direccion;
    private int cp;
    private String pais;
    private String email;
    private String telefono;
    private String pagina;
    private java.sql.Connection conn;
    private java.sql.Statement statement;
    
    public void loadJDBC(){
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
    public FabricanteBean(){
    }
    public FabricanteBean(int id, String nombre,String direccion,int cp, String pais, String email,String telefono,String pagina){
        this.id= id;
        this.nombre= nombre;
        this.direccion= direccion;
        this.cp= cp;
        this.pais= pais;
        this.email= email;
        this.telefono = telefono;
        this.pagina = pagina;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getDireccion(){
        return nombre;
    }
    public void setDireccion(String direccion){
        this.nombre=direccion;
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
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono=telefono;
    }
    public String getPagina(){
        return telefono;
    }
    public void setPagina(String pagina){
        this.pagina=pagina;
    }
    public Connection getConn() {
        return conn;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet executeQuery(Connection conn, String query) {
        
        statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return rs;
    }
    public void displayAllFabricante(DefaultTableModel model) {
        try {
            String query = "SELECT * FROM FABRICANTE";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("DIRECCION"),
                    rs.getInt("CP"),
                    rs.getString("PAIS"),
                    rs.getString("EMAIL"),
                    rs.getString("TELEFONO"),
                    rs.getString("PAGINA_WEB"),
                });
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e);
        }
    }
    public void addFabricante() {
        JTextField idField = new JTextField();
        JTextField nombreField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField paisField = new JTextField();
        JTextField cpField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField paginaField = new JTextField();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("id:"));
        panel.add(idField);
        panel.add(new JLabel("nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("direccion:"));
        panel.add(direccionField);
        panel.add(new JLabel("cp:"));
        panel.add(cpField);
        panel.add(new JLabel("pais:"));
        panel.add(paisField);
        panel.add(new JLabel("email:"));
        panel.add(emailField);
        panel.add(new JLabel("telefono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("pagina:"));
        panel.add(paginaField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Fabricante", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                statement = conn.createStatement();
                setId(Integer.parseInt(idField.getText()));
                setNombre(nombreField.getText());
                setDireccion(direccionField.getText());
                setCp(Integer.parseInt(cpField.getText()));
                setPais(paisField.getText());
                setEmail(emailField.getText());
                setTelefono(telefonoField.getText());
                setPagina(paginaField.getText());
                String query = "INSERT INTO FABRICANTE (ID, NOMBRE, DIRECCION, CP,PAIS,EMAIL,TELEFONO,PAGINA_WEB) VALUES ('" + getId() + "', '" + getNombre() + "','" + getDireccion() + "','" + getCp() + "', '" + getPais() + "','" + getEmail() + "','" + getTelefono() +"','" + getPagina() +"')";
                statement.executeUpdate(query);
                System.out.println("Frabricante added successfully.");
                System.out.println("hola"+getId()+getNombre()+getDireccion()+getCp()+getPais()+getEmail()+getTelefono());
            } catch (SQLException e) {
                System.out.println("Error adding videogame: " + e);
            }
        
        }
    }
    public void deleteFabricante(DefaultTableModel model, int selectedRow) {

        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(null, "Estas seguro de que deseas borrar el siguiente Fabricante?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Delete the employee
            int selectedEmpresa = (Integer) model.getValueAt(selectedRow, 0);
            try {
                String sql = "DELETE FROM FABRICANTE WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, selectedEmpresa);
                statement.executeUpdate();
                model.removeRow(selectedRow);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public void updateFabricante(DefaultTableModel model, int selectedRow) {
        JTextField idField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 0)));
        JTextField nombreField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 1)));
        JTextField direccionField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 2)));
        JTextField cpField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 3)));
        JTextField paisField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 4)));
        JTextField emailField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 5)));
        JTextField telefonoField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 6)));
        JTextField paginaField = new JTextField(String.valueOf(model.getValueAt(selectedRow, 7)));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Id:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Direccion:"));
        panel.add(direccionField);
        panel.add(new JLabel("Cp:"));
        panel.add(cpField);
        panel.add(new JLabel("Pais:"));
        panel.add(paisField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Telefono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Pagina Web:"));
        panel.add(paginaField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Actualizar Fabricante", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                statement = conn.createStatement();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            setId(Integer.parseInt(idField.getText()));
            setNombre(nombreField.getText());
            setDireccion(direccionField.getText());
            setCp(Integer.parseInt(cpField.getText()));
            setPais(paisField.getText());
            setEmail(emailField.getText());
            setTelefono(telefonoField.getText());
            setPagina(paginaField.getText());
            String query = "UPDATE FABRICANTE SET NOMBRE = '" + getNombre() + "', DIRECCION = '" + getDireccion() + "', CP = '" + getCp() + "',PAIS ='" + getPais() + "',EMAIL = '"+getEmail()+"', TELEFONO = '"+getTelefono()+"', PAGINA_WEB = '" + getPagina() + "' WHERE  ID = '" + Integer.parseInt(idField.getText()) + "'";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
            // TODO Auto-generated catch block
                e.printStackTrace();
            }
            model.setValueAt(getId(), selectedRow, 0);
            model.setValueAt(getNombre(), selectedRow, 1);
            model.setValueAt(getDireccion(), selectedRow, 2);
            model.setValueAt(getCp(), selectedRow, 3);
            model.setValueAt(getPais(), selectedRow, 4);
            model.setValueAt(getEmail(), selectedRow, 5);
            model.setValueAt(getTelefono(), selectedRow, 6);
        }
    }
    public void findFabricanteById(DefaultTableModel model) {
        JTextField textIdField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new javax.swing.JLabel("Id que quiere buscar"));
        panel.add(textIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "BuscarEmpresa", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            try {
                statement = conn.createStatement();
                String query = "SELECT * from FABRICANTE where id = "+ Integer.parseInt(textIdField.getText())+ ";";

                ResultSet rs = statement.executeQuery(query);
                model.setRowCount(0);
                System.out.println(rs);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("cp"),
                        rs.getString("pais"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("PAGINA_WEB"),
                    });
                }
                rs.close();
        } catch(SQLException e) {
            System.out.println("Error buscando empresa:" + e);
        }
    } 
        }
}
    
