package producto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigDecimal;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osboxes
 */
public interface ProductoInterface {
    public void loadJDBC();
    public void connect();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public BigDecimal getPrecio();
    public void setPrecio(BigDecimal precio);
    public int getFabricanteId();
    public void setFabricanteId(int fabricanteid);
    public void displayAllProducto(DefaultTableModel model); 
    
    public void addProducto() ;
    public void findProductoById(DefaultTableModel model);
    public void deleteProducto(DefaultTableModel model, int selectedRow);
    public void updateProducto(DefaultTableModel model, int selectedRow);
    public void setConn(Connection conn);
    public Connection getConn();  
}
