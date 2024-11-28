package dao;

import dao.pool.PoolConexion;

import java.sql.Connection;
import java.util.List;

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
        String query = "{CALL RegisterSale( ?, ?, ?, ?, ?)}";
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
            stmt.setInt(1, venta.getId_Empleado());    // ID del empleado
            stmt.setDouble(2, venta.getTotal_venta()); // Total de la venta
            stmt.setTimestamp(3, Timestamp.valueOf(venta.getFecha_venta())); // Fecha de la venta
            stmt.setString(4, venta.getMethodPayment()); // Detalle de la venta en formato JSON
            stmt.setString(5, detalleJson); // Detalle de la venta en formato JSON

            stmt.execute();

            // Confirmar la transacción
            conn.commit();
            
            return true;
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
    
//    public LinkedList<Venta> getSaleAll(){
//        
//    }
    
    //hola
    
    
    
    
}
