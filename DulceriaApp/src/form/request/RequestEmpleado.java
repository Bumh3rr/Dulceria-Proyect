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
    
    public static Empleado getOneEmpledo(int idEmpleado) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.getOneEmpleadosBD(idEmpleado);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
       
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
    public static LinkedList<Empleado> getAllEmpleadosSimple()throws Exception{
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.getAllEmpleadosSimpleBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
}
