import empresa.EmpresaWindow;
import fabricante.FabricantaWindow;
import inventario.InventarioWindow;
/* import empleado.EmpleadoWindow; */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import producto.ProductoWindow;
import tienda.TiendaWindow;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel currentPanel = null;

    public MainWindow() {
        setTitle("Main Class");
        setSize(1024, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem menuItem1 = new JMenuItem("Empleados");
        menuItem1.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                /* Si el panel actual no es null, se remueve del JFrame */
                /*para poder cargar el nuevo*/
              if (currentPanel != null) {
                  remove(currentPanel);
              }
              EmpleadoWindow empleadoWindow = new EmpleadoWindow();
              currentPanel = empleadoWindow;
              add(empleadoWindow, BorderLayout.CENTER);
              validate();
          }
      });
        menu.add(menuItem1);

        JMenuItem menuItem2 = new JMenuItem("Empresas");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Si el panel actual no es null, se remueve del JFrame */
                /*para poder cargar el nuevo*/
                if (currentPanel != null) {
                    remove(currentPanel);
                }
                EmpresaWindow empresaWindow = new EmpresaWindow();
                currentPanel = empresaWindow;
                add(empresaWindow, BorderLayout.CENTER);
                validate();
            }
        });
        menu.add(menuItem2);

        JMenuItem menuItem3 = new JMenuItem("Fabricantes");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Si el panel actual no es null, se remueve del JFrame */
                /*para poder cargar el nuevo*/
                if (currentPanel != null) {
                    remove(currentPanel);
                }
                FabricantaWindow fabricanteWindow = new FabricantaWindow();
                currentPanel = fabricanteWindow;
                add(fabricanteWindow, BorderLayout.CENTER);
                validate();
            }
        });
        menu.add(menuItem3);

        JMenuItem menuItem4 = new JMenuItem("Tienda");
        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Si el panel actual no es null, se remueve del JFrame */
                /*para poder cargar el nuevo*/
                if (currentPanel != null) {
                    remove(currentPanel);
                }
                TiendaWindow tiendaWindow = new TiendaWindow();
                currentPanel = tiendaWindow;
                add(tiendaWindow, BorderLayout.CENTER);
                validate();
            }
        });
        menu.add(menuItem4);

        JMenuItem menuItem5 = new JMenuItem("Producto");
        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Si el panel actual no es null, se remueve del JFrame */
                /*para poder cargar el nuevo*/
                if (currentPanel != null) {
                    remove(currentPanel);
                }
                ProductoWindow prodcutoWindow = new ProductoWindow();
                currentPanel = prodcutoWindow;
                add(prodcutoWindow, BorderLayout.CENTER);
                validate();
            }
        });

        menu.add(menuItem5);

        JMenuItem menuItem6 = new JMenuItem("Inventario");
        menuItem6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Si el panel actual no es null, se remueve del JFrame */
                /*para poder cargar el nuevo*/
                if (currentPanel != null) {
                    remove(currentPanel);
                }
                InventarioWindow inventarioWindow = new InventarioWindow();
                currentPanel = inventarioWindow;
                add(inventarioWindow, BorderLayout.CENTER);
                validate();
            }
        });
        menu.add(menuItem6);

    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}