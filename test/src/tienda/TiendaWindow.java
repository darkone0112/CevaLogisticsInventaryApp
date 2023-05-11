/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TiendaWindow extends JPanel {
       private static final long serialVersionUID = 1L;
    TiendaInterface tiendaBean = TiendaGallery.getTiendaDao();
    
    public TiendaWindow() {
        tiendaBean.loadJDBC();
        tiendaBean.connect();
        setLayout(new FlowLayout());
        /* anotherClass = new AnotherClass(); */
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("nombre");
        model.addColumn("direccion");
        model.addColumn("cp");
        model.addColumn("pais");
        model.addColumn("provincia");
        model.addColumn("email");
        model.addColumn("telefono");
        model.addColumn("empresaid");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton button1 = new JButton("Agregar Tienda");
        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               tiendaBean.addTienda();
               tiendaBean.displayAllTienda(model);
            }
        });
        add(button1);
        
        JButton button2 = new JButton("Modificar Tienda");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tiendaBean.updateTienda(model, selectedRow);
                }
            }
        });
        add(button2);
        
        JButton button3 = new JButton("Buscar Tienda");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiendaBean.findTiendaById(model);
            }
        });
        add(button3);
        
        JButton button4 = new JButton("Eliminar Tienda");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiendaBean.deleteTienda(model, table.getSelectedRow());
            }
        });
        add(button4);
        
        JButton button5 = new JButton("Mostrar Todos");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
                tiendaBean.displayAllTienda(model);
                revalidate();
                repaint();
            }
        });
        add(button5);
        scrollPane.setPreferredSize(new Dimension(980, 600));
        add(scrollPane);
        tiendaBean.displayAllTienda(model);
        revalidate();
        repaint();
    }
}