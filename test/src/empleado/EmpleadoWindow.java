import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EmpleadoWindow extends JPanel {
    private static final long serialVersionUID = 1L;
    private EmpleadoInterface empleadoBean = empleadoGallery.getEmpleadoDao();
    
    
    public EmpleadoWindow() {
        empleadoBean.loadJDBC();
        empleadoBean.connect();
        setLayout(new FlowLayout());
        /* anotherClass = new AnotherClass(); */
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("nombre");
        model.addColumn("apellidos");
        model.addColumn("dni");
        model.addColumn("fecna");
        model.addColumn("email");
        model.addColumn("empresaid");
        model.addColumn("tiendaid");
        model.addColumn("telefono");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton button1 = new JButton("Agregar Empleado");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               empleadoBean.addEmpleado();
               empleadoBean.displayAllEmployees(model);
            }
        });
        add(button1);
        
        JButton button2 = new JButton("Modificar Empleado");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    empleadoBean.updateEmpleado(model, selectedRow);
                }
            }
        });
        add(button2);
        
        JButton button3 = new JButton("Buscar Empleado");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empleadoBean.findEmpleadoById(model);
            }
        });
        add(button3);
        
        JButton button4 = new JButton("Eliminar Empleado");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empleadoBean.deleteEmpleado(model, table.getSelectedRow());
            }
        });
        add(button4);
        
        JButton button5 = new JButton("Mostrar Todos");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
                empleadoBean.displayAllEmployees(model);
                revalidate();
                repaint();
            }
        });
        add(button5);
        scrollPane.setPreferredSize(new Dimension(980, 600));
        add(scrollPane);
        empleadoBean.displayAllEmployees(model);
        revalidate();
        repaint();
    }
}