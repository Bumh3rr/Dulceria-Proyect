package dao.request;

import dao.DetalleVentaDao;
import dao.pool.PoolThreads;
import model.DetalleVenta;
import java.util.LinkedList;

public class RequestDetalleVenta {


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
