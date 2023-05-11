package inventario;

public class InventarioGallery {
    static InventarioInterface getInventarioDao() {
        return new InventarioBean();
 }
}