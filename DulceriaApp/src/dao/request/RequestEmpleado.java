package dao.request;

import dao.EmpleadoDao;
import dao.pool.PoolThreads;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import model.Empleado;

/**
 * Clase RequestEmpleado que maneja las solicitudes relacionadas con los empleados utilizando un pool de hilos.
 */
public class RequestEmpleado {

    /**
     * Agrega un nuevo empleado utilizando un pool de hilos.
     *
     * @param empleado El objeto Empleado que contiene los datos del nuevo empleado.
     * @return true si el empleado fue agregado exitosamente, false en caso contrario.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static Boolean addEmpleado(Empleado empleado) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.addEmpleadoBD(empleado);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    /**
     * Actualiza un empleado existente utilizando un pool de hilos.
     *
     * @param empleado El objeto Empleado que contiene los datos actualizados del empleado.
     * @return true si el empleado fue actualizado exitosamente, false en caso contrario.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static Boolean updateEmpleado(Empleado empleado,int id) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.updateEmpleadoBD(empleado,id);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }


    public static LocalDateTime updateStatusEmpleado(Boolean status, int id) throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.updateStatus(status,id);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    /**
     * Obtiene todos los empleados utilizando un pool de hilos.
     *
     * @return Una lista enlazada de objetos Empleado que contienen los datos de todos los empleados.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static List<Empleado> getAllEmpleados() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.getAllEmpleadosBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }

    /**
     * Obtiene todos los empleados con información simplificada utilizando un pool de hilos.
     *
     * @return Una lista enlazada de objetos Empleado que contienen los datos simplificados de los empleados.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static LinkedList<Empleado> getAllEmpleadosSimple() throws Exception {
        return PoolThreads.getInstance().getExecutorService().submit(() -> {
            try {
                return EmpleadoDao.getAllEmpleadosSimpleBD();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }).get();
    }
}