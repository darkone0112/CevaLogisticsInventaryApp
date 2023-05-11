/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producto;

/**
 *
 * @author osboxes
 */
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

/**
 *
 * @author osboxes
 */
public class ProductoWindow extends JPanel{
   private static final long serialVersionUID = 1L;
    ProductoBean productoBean = new ProductoBean();
    
    public ProductoWindow() {
        productoBean.loadJDBC();
        productoBean.connect();
        setLayout(new FlowLayout());
        /* anotherClass = new AnotherClass(); */
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("nombre");
        model.addColumn("discripcion");
        model.addColumn("precio");
        model.addColumn("fabricanteid");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton button1 = new JButton("Agregar Producto");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               productoBean.addProducto();
               productoBean.displayAllProducto(model);
            }
        });
        add(button1);
        
        JButton button2 = new JButton("Modificar Producto");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    productoBean.updateProducto(model, selectedRow);
                }
            }
        });
        add(button2);
        
        JButton button3 = new JButton("Buscar Producto");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
               productoBean.findProductoById(model);
               revalidate();
               repaint();
            }
        });
        add(button3);
        
        JButton button4 = new JButton("Eliminar Producto");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoBean.deleteProducto(model, table.getSelectedRow());
            }
        });
        add(button4);
        
        JButton button5 = new JButton("Mostrar Todos");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
                productoBean.displayAllProducto(model);
                revalidate();
                repaint();
            }
        });
        add(button5);
        scrollPane.setPreferredSize(new Dimension(980, 600));
        add(scrollPane);
        productoBean.displayAllProducto(model);
        revalidate();
        repaint();
    } 
}
