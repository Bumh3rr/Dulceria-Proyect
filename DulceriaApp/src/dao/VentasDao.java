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
/*
ProductoDao.java:
----Contiene métodos para interactuar con la base de datos de productos, como obtener productos por ID y actualizar productos.
---DetalleVenta.java:
Define la clase DetalleVenta que representa los detalles de una venta, incluyendo el ID del producto, cantidad, precio de venta y total.
----VentasDao.java:
Contiene el método registerSale que registra una venta y sus detalles en la base de datos utilizando un procedimiento almacenado.
----console.sql:
Define el procedimiento almacenado RegisterSale que inserta una venta en la tabla VENTAS y sus detalles en la tabla DETALLE_VENTAS.
--------------------------------------Registrar una Venta con Varios Productos----------------------------------------------
Para registrar una venta con varios productos, el método registerSale en VentasDao.java ya está configurado para aceptar una lista de DetalleVenta. Aquí está el flujo general:
--------------Crear una Venta:
Crear una instancia de Ventas con los detalles del cliente, empleado, total de la venta y fecha de la venta.
---------------Crear Detalles de Venta:
Crear una lista de DetalleVenta con los productos que el cliente desea comprar.
----------------Registrar la Venta:
Llamar al método registerSale con la venta y la lista de detalles.
* import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear una venta
            Ventas venta = new Ventas(1, 2, 100.0, LocalDateTime.now());

            // Crear detalles de venta
            List<DetalleVenta> detalles = new ArrayList<>();
            detalles.add(new DetalleVenta(1, 2, 50.0, 100.0));
            detalles.add(new DetalleVenta(2, 1, 50.0, 50.0));

            // Registrar la venta
            VentasDao.registerSale(venta, detalles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
* */