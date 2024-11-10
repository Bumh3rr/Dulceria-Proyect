package form.request;

import dao.PoolThreads;
import dao.ProductoDao;
import model.Producto;

public class RequestProducto {

    public static Boolean addProducto(Producto producto) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProductoDao.addProductoBD(producto);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

}
