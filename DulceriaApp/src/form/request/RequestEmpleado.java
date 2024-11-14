package form.request;

import dao.EmpleadoDao;
import dao.ProveedorDao;
import dao.pool.PoolThreads;
import java.sql.Timestamp;
import java.util.LinkedList;
import model.Empleado;

public class RequestEmpleado {

    public static Boolean addEmpleado(Empleado empleado) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.addEmpleadoBD(empleado);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
    
    public static Empleado getOneProducto(int idEmpleado) throws Exception {
//        return PoolThreads.getInstance().getExecutorService().submit(() -> {
//            try {
//                return ProveedorDao.addProveedorBD(proveedor);
//            } catch (Exception e) {
//                throw new Exception(e);
//            }
//        }).get();
        return null;
    }
    
    public static Boolean setEmpleado(Empleado empleado) throws Exception {
//        return PoolThreads.getInstance().getExecutorService().submit(() -> {
//            try {
//                return ProveedorDao.addProveedorBD(proveedor);
//            } catch (Exception e) {
//                throw new Exception(e);
//            }
//        }).get();
        return false;
    }
    
    public static Boolean setDateLowEmpleado(int idEmpleado,Timestamp dateTime) throws Exception {
//        return PoolThreads.getInstance().getExecutorService().submit(() -> {
//            try {
//                return ProveedorDao.addProveedorBD(proveedor);
//            } catch (Exception e) {
//                throw new Exception(e);
//            }
//        }).get();
        return false;
    }

    public static LinkedList<Empleado> getAllEmpleados()throws Exception{
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.getAllEmpleadosBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
}
