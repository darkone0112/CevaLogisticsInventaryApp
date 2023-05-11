package fabricante;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osboxes
 */
public interface FabricanteInterface {
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
    public String getPagina();
    public void setPagina(String pagina);
    
    public void displayAllFabricante(DefaultTableModel model); 
    
    public void addFabricante() ;
    public void deleteFabricante(DefaultTableModel model, int selectedRow);
    public void updateFabricante(DefaultTableModel model, int selectedRow);
    public void findFabricanteById(DefaultTableModel model);
    public void setConn(Connection conn);
    public Connection getConn();
}
