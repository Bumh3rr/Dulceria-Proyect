package dao;

import dao.pool.PoolConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

import lombok.Cleanup;
import model.Empleado;

/**
 * Clase EmpleadoDao que maneja las operaciones de base de datos para los empleados.
 */
public class EmpleadoDao {

    /**
     * Agrega un nuevo empleado a la base de datos.
     *
     * @param empleado El objeto Empleado que contiene los datos del nuevo empleado.
     * @return true si el empleado fue agregado exitosamente, false en caso contrario.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static Boolean addEmpleadoBD(Empleado empleado) throws Exception {
        String query = "INSERT INTO EMPLEADO(nombre,apellidos,telefono,direccion,rfc,puesto,estado,sueldo,fecha_registro) values(?,?,?,?,?,?,?,?,?)";
        int generatedId = -1;
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, empleado.getNombre());
        ps.setString(2, empleado.getApellido());
        ps.setString(3, empleado.getTelefono());
        ps.setString(4, empleado.getDireccion());
        ps.setString(5, empleado.getRfc());
        ps.setString(6, empleado.getPuesto().name());
        ps.setString(7, empleado.getEstado().name());
        ps.setDouble(8, empleado.getSueldo());
        ps.setTimestamp(9, Timestamp.valueOf(empleado.getFecha_registro()));

        if (ps.executeUpdate() > 0) {
            @Cleanup
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId > 0;
    }

    /**
     * Obtiene todos los empleados de la base de datos.
     *
     * @return Una lista enlazada de objetos Empleado que contienen los datos de todos los empleados.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static LinkedList<Empleado> getAllEmpleadosBD() throws Exception {
        String query = "SELECT * FROM EMPLEADO";
        LinkedList<Empleado> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        Statement statement = connection.createStatement();
        @Cleanup
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Empleado(resultSet.getInt("idEmpleado"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellidos"),
                    resultSet.getString("telefono"),
                    resultSet.getString("direccion"),
                    resultSet.getString("rfc"),
                    Empleado.Puesto.valueOf(resultSet.getString("puesto")),
                    Empleado.Status.valueOf(resultSet.getString("estado")),
                    resultSet.getDouble("sueldo"),
                    resultSet.getDouble("venta_semanal"),
                    resultSet.getDouble("comision"),
                    resultSet.getTimestamp("fecha_registro").toLocalDateTime(),
                    resultSet.getObject("fecha_baja")!= null ? resultSet.getTimestamp("fecha_baja").toLocalDateTime() : null));
        }
        return list;
    }

    /**
     * Obtiene todos los empleados con información simplificada de la base de datos.
     *
     * @return Una lista enlazada de objetos Empleado que contienen los datos simplificados de los empleados.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static LinkedList<Empleado> getAllEmpleadosSimpleBD() throws Exception {
        String query = "SELECT idEmpleado,nombre,apellidos FROM EMPLEADO WHERE puesto = 'VENDEDOR'";
        LinkedList<Empleado> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        Statement statement = connection.createStatement();
        @Cleanup
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            list.add(new Empleado(
                    rs.getInt("idEmpleado"),
                    rs.getString("nombre"),
                    rs.getString("apellidos")));
        }
        return list;
    }

    /**
     * Obtiene un empleado específico de la base de datos por su ID.
     *
     * @param id El ID del empleado que se desea obtener.
     * @return Un objeto Empleado que contiene los datos del empleado, o null si no se encuentra.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static Empleado getOneEmpleadosBD(int id) throws Exception {
        String query = "SELECT * FROM EMPLEADO where idEmpleado= ?";

        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);

        @Cleanup
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return new Empleado(resultSet.getInt("idEmpleado"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellidos"),
                    resultSet.getString("telefono"),
                    resultSet.getString("direccion"),
                    resultSet.getString("rfc"),
                    Empleado.Puesto.valueOf(resultSet.getString("puesto")),
                    Empleado.Status.valueOf(resultSet.getString("estado")),
                    resultSet.getDouble("sueldo"),
                    resultSet.getDouble("venta_semanal"),
                    resultSet.getDouble("comision"),
                    resultSet.getTimestamp("fecha_registro").toLocalDateTime(),
                    resultSet.getObject("fecha_baja")!= null ? resultSet.getTimestamp("fecha_baja").toLocalDateTime() : null);
        }
        return null;
    }
}