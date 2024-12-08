package dao.request;

import dao.DetalleVentaDao;
import dao.pool.PoolThreads;
import model.DetalleVenta;
import java.util.LinkedList;

/**
 * Clase RequestDetalleVenta que maneja las solicitudes relacionadas con los detalles de venta.
 */
public class RequestDetalleVenta {

    /**
     * Obtiene todos los detalles de venta para un ID de venta específico utilizando un pool de hilos.
     *
     * @param id_venta El ID de la venta para la cual se desean obtener los detalles.
     * @return Una lista enlazada de objetos DetalleVentaSub que contienen los detalles de la venta.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static LinkedList<DetalleVenta.DetalleVentaSub> getDetallesVentaAll(int id_venta) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return DetalleVentaDao.getDetallesVentaAllBD(id_venta);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

}