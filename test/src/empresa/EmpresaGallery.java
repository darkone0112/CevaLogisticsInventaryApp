package empresa;

public class EmpresaGallery {
    static EmpresaInterface getEmpresaDao() {
        return new EmpresaBean();
}
}