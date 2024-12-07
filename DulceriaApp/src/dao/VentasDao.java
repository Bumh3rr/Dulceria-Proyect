package dao;

import dao.pool.PoolConexion;
import java.sql.Connection;
import java.util.List;

import model.Empleado;
import model.Venta;
import com.google.gson.Gson;
import model.DetalleVenta;
import lombok.Cleanup;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class VentasDao {

    public static Boolean registerSaleBD(Venta venta, List<DetalleVenta> detalles) throws Exception {
        String query = "{CALL RegisterSale( ?, ?, ?, ?, ?, ?)}";
        String detalleJson = new Gson().toJson(detalles);
        System.out.println(detalleJson);

        @Cleanup
        Connection conn = PoolConexion.getInstance().getConnection();

        try {
            // Desactivar el auto-commit para manejar las transacciones manualmente
            conn.setAutoCommit(false);

            // Definir el procedimiento almacenado
            @Cleanup
            CallableStatement stmt = conn.prepareCall(query);

            // Establecer los parámetros de entrada
            stmt.setInt(1, venta.getEmpleado().getIdEmpleado());
            stmt.setInt(2, venta.getCantidad_total_productos());
            stmt.setDouble(3, venta.getTotal_venta());
            stmt.setTimestamp(4, Timestamp.valueOf(venta.getFecha_venta()));
            stmt.setString(5, venta.getMethodPayment());
            stmt.setString(6, detalleJson);
            stmt.execute();

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw new Exception("Error al registrar la venta: " + e.getLocalizedMessage(), e);
        }
    }

    public static LinkedList<Venta> getSaleAllBD() throws Exception {
        String query = "SELECT * FROM View_Venta";

        LinkedList<Venta> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        ResultSet rs = connection.prepareStatement(query).executeQuery();

        while (rs.next()) {
            list.add(new Venta(
                    rs.getInt("id_Venta"),
                    new Empleado(rs.getInt("idEmpleado"),
                            rs.getString("nombre"),
                            rs.getString("apellidos")),
                    rs.getInt("cant_productos"),
                    rs.getDouble("total_Venta"),
                    rs.getString("metodo_pago"),
                    rs.getTimestamp("fecha_Venta").toLocalDateTime()));
        }
        return list;
    }

}
