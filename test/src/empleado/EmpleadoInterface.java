import javax.swing.table.DefaultTableModel;
public interface EmpleadoInterface {
    public void loadJDBC();

    public void connect();

    public void addEmpleado();

    public void updateEmpleado(DefaultTableModel model, int selectedRow);

    public void findEmpleadoById(DefaultTableModel model);

    public void deleteEmpleado(DefaultTableModel model, int selectedRow);

    public void displayAllEmployees(DefaultTableModel model);
}


    

