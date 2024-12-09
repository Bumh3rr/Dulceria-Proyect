package dao.request;

import dao.VentasDao;
import dao.pool.PoolThreads;
import java.util.LinkedList;
import model.DetalleVenta;
import model.Venta;

import java.util.List;

/**
 * Clase RequestVenta que maneja las solicitudes relacionadas con las ventas utilizando un pool de hilos.
 */
public class RequestVenta {

    /**
     * Registra una nueva venta y sus detalles utilizando un pool de hilos.
     *
     * @param venta    El objeto Venta que contiene los datos de la venta.
     * @param detalles Una lista de objetos DetalleVenta que contiene los detalles de la venta.
     * @return true si la venta fue registrada exitosamente, false en caso contrario.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static Boolean registerSale(Venta venta, List<DetalleVenta> detalles) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return VentasDao.registerSaleBD(venta, detalles);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    /**
     * Obtiene todas las ventas utilizando un pool de hilos.
     *
     * @return Una lista enlazada de objetos Venta que contienen los datos de todas las ventas.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static LinkedList<Venta> getSaleAll() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return VentasDao.getSaleAllBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

}