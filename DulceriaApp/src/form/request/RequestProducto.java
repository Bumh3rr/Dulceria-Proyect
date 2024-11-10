package form.request;

import dao.PoolThreads;
import dao.ProductoDao;
import model.Producto;

import java.util.LinkedList;

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

    public static LinkedList<Producto> getAllProductos() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProductoDao.getAllProductosBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
}
