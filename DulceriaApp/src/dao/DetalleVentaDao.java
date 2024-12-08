package dao;

import dao.pool.PoolConexion;
import lombok.Cleanup;
import model.DetalleVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 * Clase DetalleVentaDao que maneja las operaciones de base de datos para los detalles de venta.
 */
public class DetalleVentaDao {

    /**
     * Obtiene todos los detalles de venta de la base de datos para un ID de venta específico.
     *
     * @param id_venta El ID de la venta para la cual se desean obtener los detalles.
     * @return Una lista enlazada de objetos DetalleVentaSub que contienen los detalles de la venta.
     * @throws Exception Si ocurre un error durante la operación de base de datos.
     */
    public static LinkedList<DetalleVenta.DetalleVentaSub> getDetallesVentaAllBD(int id_venta) throws Exception {
        String query = "SELECT * FROM View_DetalleVenta WHERE id_Venta = ?";

        LinkedList<DetalleVenta.DetalleVentaSub> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id_venta);

        @Cleanup
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            list.add(new DetalleVenta.DetalleVentaSub(
                    rs.getInt("cantidad_Prod"),
                    rs.getString("nombre_Prod"),
                    rs.getDouble("precio_Venta"),
                    rs.getDouble("total_Venta")
            ));
        }
        return list;
    }
}