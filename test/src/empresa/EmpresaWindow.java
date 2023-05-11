package empresa;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EmpresaWindow extends JPanel {
    private static final long serialVersionUID = 1L;
    private EmpresaInterface empresaBean = EmpresaGallery.getEmpresaDao();
    public EmpresaWindow() {
        empresaBean.loadJDBC();
        empresaBean.connect();
        setLayout(new FlowLayout());
        /* anotherClass = new AnotherClass(); */

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NOMBRE");
        model.addColumn("DIRECCION");
        model.addColumn("CP");
        model.addColumn("PAIS");
        model.addColumn("EMAIL");
        model.addColumn("TELEFONO");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton button1 = new JButton("Agregar empresa");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               empresaBean.addEmpresa();
               empresaBean.displayAllEmpresas(model);
            }
        });
        add(button1);

        JButton button2 = new JButton("Modificar empresa");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    empresaBean.updateEmpresa(model, selectedRow);
                }
            }
        });
        add(button2);

        JButton button3 = new JButton("Buscar empresa");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empresaBean.findEmpresaById(model);
            }
        });
        add(button3);

        JButton button4 = new JButton("Eliminar empresa");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empresaBean.deleteEmpresa(model, table.getSelectedRow());
            }
        });
        add(button4);

        JButton button5 = new JButton("Mostrar Todos");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
                empresaBean.displayAllEmpresas(model);
                revalidate();
                repaint();
            }
        });
        add(button5);
        scrollPane.setPreferredSize(new Dimension(980, 600));
        add(scrollPane);
        empresaBean.displayAllEmpresas(model);
        revalidate();
        repaint();
    }
}