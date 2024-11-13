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

public class EmpleadoDao {

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
}
