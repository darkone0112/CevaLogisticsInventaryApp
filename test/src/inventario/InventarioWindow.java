package inventario;

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

public class InventarioWindow extends JPanel {
    private static final long serialVersionUID = 1L;
    InventarioBean inventarioBean = new InventarioBean();

    public InventarioWindow() {
        inventarioBean.loadJDBC();
        inventarioBean.connect();
        setLayout(new FlowLayout());
        /* anotherClass = new AnotherClass(); */

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("producto");
        model.addColumn("precio");
        model.addColumn("tienda");
        model.addColumn("cantidad");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton button1 = new JButton("Agregar Inventario");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               inventarioBean.addInventario();
               inventarioBean.displayAllInventario(model);
            }
        });
        add(button1);

        JButton button2 = new JButton("Modificar Inventario");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    inventarioBean.updateInventario(model, selectedRow);
                }
            }
        });
        add(button2);

        JButton button3 = new JButton("Buscar Inventario");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
               inventarioBean.findInventarioById(model);
               revalidate();
               repaint();
            }
        });
        add(button3);

        JButton button4 = new JButton("Eliminar Inventario");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventarioBean.deleteInventario(model, table.getSelectedRow());
            }
        });
        add(button4);

        JButton button5 = new JButton("Mostrar Todos");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
                inventarioBean.displayAllInventario(model);
                revalidate();
                repaint();
            }
        });
        add(button5);
        scrollPane.setPreferredSize(new Dimension(980, 600));
        add(scrollPane);
        inventarioBean.displayAllInventario(model);
        revalidate();
        repaint();
    }
}