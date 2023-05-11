package producto;

public class ProductoGallery {
    static ProductoInterface getProductoDao() {
           return new ProductoBean();
    }
}
