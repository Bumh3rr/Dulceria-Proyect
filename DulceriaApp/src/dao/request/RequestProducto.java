package dao.request;

import dao.pool.PoolThreads;
import dao.ProductoDao;
import model.Producto;

import java.util.LinkedList;
import model.Categoria;

/**
 * Clase RequestProducto que maneja las solicitudes relacionadas con los productos.
 */
public class  RequestProducto {

    /**
     * Agrega un producto a la base de datos.
     *
     * @param producto El objeto Producto que se desea agregar.
     * @return Booleano que indica si el producto fue agregado exitosamente.
     * @throws Exception Si ocurre un error durante la operación.
     */
    public static Boolean addProducto(Producto producto) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProductoDao.addProductoBD(producto);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    /**
     * Obtiene una lista de todos los productos de la base de datos.
     *
     * @return LinkedList de objetos Producto.
     * @throws Exception Si ocurre un error durante la operación.
     */
    public static LinkedList<Producto> getAllProductos() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProductoDao.getAllProductosBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    public static LinkedList<Producto> getAllProductosSimple() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProductoDao.getAllProductosSimpleBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }


    public static Boolean setProducto(Producto producto) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {

                // Aquí deberías implementar la lógica para actualizar el producto
                return ProductoDao.updateProductoBD(producto);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    public static Producto getOneProducto(int idProducto) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProductoDao.getProductoById(idProducto);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
    
    public static LinkedList<Producto> getProductsByCategoriaAndEstado(Categoria categoria,Producto.Status estado) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProductoDao.getProductsByCategoriaAndEstadoBD(categoria,estado);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
}