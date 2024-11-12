package dao;
import dao.pool.PoolConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import model.Ventas;
import lombok.Cleanup;
public class VentasDao {
    public static int addVentaBD(Ventas venta) throws Exception {
        String query = "INSERT INTO VENTAS(id_Cliente,id_Trab,total_venta,fecha_venta) values(?,?,?,?)";
        int generatedId = -1;
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, venta.getId_Cliente());
        ps.setInt(2, venta.getId_Trab());
        ps.setDouble(3, venta.getTotal_venta());
        ps.setObject(4, venta.getFecha_venta());
        if (ps.executeUpdate() > 0) {
            @Cleanup
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }
    public static LinkedList<Ventas> getAllVentasBD() throws Exception {
        String query = "SELECT * FROM ventas";
        LinkedList<Ventas> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        Statement statement = connection.createStatement();
        @Cleanup
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Ventas(resultSet.getInt("id_venta"), resultSet.getInt("id_Cliente"), resultSet.getInt("id_Trab"), resultSet.getDouble("total_venta"), resultSet.getObject("fecha_venta", java.time.LocalDateTime.class)));
        }
        return list;
    }
}
