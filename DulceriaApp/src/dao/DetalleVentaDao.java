package dao;
import dao.pool.PoolConexion;
    import model.DetalleVenta;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.Statement;
    import java.util.LinkedList;
    import lombok.Cleanup;
public class DetalleVentaDao {

    public static int addDetalleVentaBD(DetalleVenta detalleVenta) throws Exception {
        String query = "INSERT INTO DETALLE_VENTA( id_Venta,id_Prod,cantidad_Prod, precio_venta, total_Venta) values(?,?,?,?,?)";
        int generatedId = -1;
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, detalleVenta.getId_venta());
        ps.setInt(2, detalleVenta.getId_producto());
        ps.setInt(3, detalleVenta.getCantidad());
        ps.setDouble(4, detalleVenta.getPrecio_venta());
        ps.setDouble(5, detalleVenta.getTotal());
        if (ps.executeUpdate() > 0) {
            @Cleanup
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }

//    public static LinkedList<DetalleVenta> getAllDetalleVentaBD() throws Exception {
//        String query = "SELECT * FROM detalle_venta";
//        LinkedList<DetalleVenta> list = new LinkedList<>();
//        @Cleanup
//        Connection connection = PoolConexion.getInstance().getConnection();
//        @Cleanup
//        Statement statement = connection.createStatement();
//        @Cleanup
//        ResultSet resultSet = statement.executeQuery(query);
//        while (resultSet.next()) {
//            list.add(new DetalleVenta(resultSet.getInt("id_detalle"), resultSet.getInt("id_venta"), resultSet.getInt("id_producto"), resultSet.getInt("cantidad"), resultSet.getDouble("precio_venta"), resultSet.getDouble("total")));
//        }
//        return list;
//    }

}