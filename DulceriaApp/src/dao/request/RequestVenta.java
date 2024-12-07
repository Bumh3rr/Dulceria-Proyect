package dao.request;

import dao.VentasDao;
import dao.pool.PoolThreads;
import model.DetalleVenta;
import model.Venta;

import java.util.List;

public class RequestVenta {

    public static Boolean registerSale(Venta venta, List<DetalleVenta> detalles) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return VentasDao.registerSaleBD(venta, detalles);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }


}
