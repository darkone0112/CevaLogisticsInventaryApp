package empresa;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;

public interface EmpresaInterface {
    public void loadJDBC();
    public void connect();
    public ResultSet executeQuery(Connection conn, String query);
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
    public String getDireccion();
    public void setDireccion(String direccion);
    public int getCp();
    public void setCp(int cp);
    public String getPais();
    public void setPais(String pais);
    public String getEmail();
    public void setEmail(String email);
    public String getTelefono();
    public void setTelefono(String telefono);
    public void displayAllEmpresas(DefaultTableModel model);
    public void addEmpresa();
    public void deleteEmpresa(DefaultTableModel model, int selectedRow);
    public void updateEmpresa(DefaultTableModel model, int selectedRow);
    public void findEmpresaById(DefaultTableModel model);
    public void setConn(Connection conn);
    public Connection getConn();
}