package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import lombok.Cleanup;
import model.Trabajador;
public class TrabajadorDao {
    public static int addTrabajadorBD(Trabajador trabajador) throws Exception {
        String query = "INSERT INTO TRABAJADOR(nombre_Trab,puesto_Trab,sueldo,venta_semanal,estacomisiondo) values(?,?,?,?,?)";
        int generatedId = -1;
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, trabajador.getNombre_Trab());
        ps.setString(2, trabajador.getPuesto_Trab());
        ps.setDouble(3, trabajador.getSueldo());
        ps.setDouble(4, trabajador.getVenta_semanal());
        ps.setDouble(5, trabajador.getComision());

        if (ps.executeUpdate() > 0) {
            @Cleanup
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }
    public static LinkedList<Trabajador> getAllTrabajadoresBD() throws Exception {
        String query = "SELECT * FROM trabajador";
        LinkedList<Trabajador> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        Statement statement = connection.createStatement();
        @Cleanup
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Trabajador(resultSet.getInt("id_Trabajador"), resultSet.getString("nombre_Trab"),
                    resultSet.getString("puesto_Trab"), resultSet.getDouble("sueldo"),
                    resultSet.getDouble("venta_semanal"), resultSet.getDouble("comision")));
        }
        return list;
    }
}
