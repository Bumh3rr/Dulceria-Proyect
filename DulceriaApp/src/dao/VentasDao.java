package dao;
import dao.pool.PoolConexion;
import java.sql.Connection;
import java.util.List;
import model.Ventas;
import com.google.gson.Gson;
import model.DetalleVenta;
import lombok.Cleanup;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class VentasDao {

    public static void registerSale(Ventas venta, List<DetalleVenta> detalles) throws Exception {
        String query = "{CALL RegisterSale(?, ?, ?, ?, ?)}";
        Gson gson = new Gson();
        String detalleJson = gson.toJson(detalles);

        @Cleanup
        Connection conn = PoolConexion.getInstance().getConnection();

        try {
            // Desactivar el auto-commit para manejar las transacciones manualmente
            conn.setAutoCommit(false);

            // Definir el procedimiento almacenado
            @Cleanup
            CallableStatement stmt = conn.prepareCall(query);

            // Establecer los parámetros de entrada
            stmt.setInt(1, venta.getId_Cliente()); // ID del cliente
            stmt.setInt(2, venta.getId_Trab());    // ID del empleado
            stmt.setDouble(3, venta.getTotal_venta()); // Total de la venta
            stmt.setTimestamp(4, new Timestamp(venta.getFecha_venta().getDayOfMonth())); // Fecha de la venta
            stmt.setString(5, detalleJson); // Detalle de la venta en formato JSON

            // Ejecutar el procedimiento almacenado
            stmt.execute();

            // Confirmar la transacción
            conn.commit();

            System.out.println("Venta registrada exitosamente.");

        } catch (SQLException e) {
            // En caso de error, revertir la transacción
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transacción revertida debido a un error: " + e.getMessage());
                } catch (SQLException ex) {
                    System.out.println("Error al revertir la transacción: " + ex.getMessage());
                }
            }
            e.printStackTrace();
            throw new Exception("Error al registrar la venta", e);
        }
    }
}