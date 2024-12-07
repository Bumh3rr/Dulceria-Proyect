package dao.request;

import dao.ProveedorDao;
import dao.pool.PoolThreads;
import java.util.LinkedList;
import model.Proveedor;

/**
 * ProveedorRequest es una clase interna que gestiona las solicitudes de
 * proveedores.
 */
public class RequestProveedor {

    /**
     * Agrega un nuevo proveedor.
     *
     * @param proveedor el objeto Proveedor a agregar
     * @return el ID generado del nuevo proveedor
     * @throws Exception si hay un error durante la operación
     */
    public static int addProveedor(Proveedor proveedor) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProveedorDao.addProveedorBD(proveedor);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    /**
     * Recupera todos los proveedores.
     *
     * @return una LinkedList de objetos Proveedor
     * @throws Exception si hay un error durante la operación
     */
    public static LinkedList<Proveedor> getAllProveedors() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return ProveedorDao.getAllProveedorsBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
}
