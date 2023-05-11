package fabricante;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FabricantaWindow extends JPanel {
    private static final long serialVersionUID = 1L;
    private FabricanteInterface fabricanteBean = FabricanteGallery.getFabricanteDao();

    public FabricantaWindow() {
        fabricanteBean.loadJDBC();
        fabricanteBean.connect();
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
        model.addColumn("PAGINA WEB");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JButton button1 = new JButton("Agregar Fabricante");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               fabricanteBean.addFabricante();
               fabricanteBean.displayAllFabricante(model);
            }
        });
        add(button1);

        JButton button2 = new JButton("Modificar Fabricante");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    fabricanteBean.updateFabricante(model, selectedRow);
                }
            }
        });
        add(button2);

        JButton button3 = new JButton("Buscar Fabricante");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fabricanteBean.findFabricanteById(model);
            }
        });
        add(button3);

        JButton button4 = new JButton("Eliminar Fabricante");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    fabricanteBean.deleteFabricante(model, selectedRow);
                }
            }
        });
        add(button4);

        JButton button5 = new JButton("Mostrar Todos");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPane.setPreferredSize(new Dimension(980, 600));
                add(scrollPane);
                fabricanteBean.displayAllFabricante(model);
                revalidate();
                repaint();
            }
        });
        add(button5);
        scrollPane.setPreferredSize(new Dimension(980, 600));
        add(scrollPane);
        fabricanteBean.displayAllFabricante(model);
        revalidate();
        repaint();
    }
}
