package fabricante;
/**
 *
 * @author osboxes
 */
public class FabricanteGallery {
    static FabricanteInterface getFabricanteDao() {
           return new FabricanteBean();
    }
    
    
}