/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import java.sql.Connection;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osboxes
 */
public interface TiendaInterface {
     public void loadJDBC();
    public void connect();
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
    public String getProvincia();
    public void setProvincia(String provincia);
    public String getEmail();
    public void setEmail(String email);
    public String getTelefono();
    public void setTelefono(String telefono);
    public int getEmpresaId();
    public void setEmpresaId(int empresaid);
    public void displayAllTienda(DefaultTableModel model); 
    public void addTienda() ;
    public void deleteTienda(DefaultTableModel model, int selectedRow);
    public void updateTienda(DefaultTableModel model, int selectedRow);
    public void findTiendaById(DefaultTableModel model);
    public void setConn(Connection conn);
    public Connection getConn();

}