package inventario;

import java.math.BigDecimal;
import java.sql.Connection;

import javax.swing.table.DefaultTableModel;

public interface InventarioInterface {
    public void loadJDBC();
    public void connect();
    public void displayAllInventario(DefaultTableModel model);
    public void addInventario() ;
    public void findInventarioById(DefaultTableModel model);
    public void deleteInventario(DefaultTableModel model, int selectedRow);
    public void updateInventario(DefaultTableModel model, int selectedRow);
}