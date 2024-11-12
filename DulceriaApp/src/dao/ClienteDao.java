package dao;

import dao.pool.PoolConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

import lombok.Cleanup;
import model.Cliente;

public class ClienteDao {
    public static int addClienteBD(Cliente cliente) throws Exception {
        String query = "INSERT INTO CLIENTE(nom_Cliente) values(?)";
        int generatedId = -1;
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, cliente.getNom_cliente());
        if (ps.executeUpdate() > 0) {
            @Cleanup
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }
    public static LinkedList<Cliente> getAllClientesBD() throws Exception {
        String query = "SELECT * FROM cliente";
        LinkedList<Cliente> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        Statement statement = connection.createStatement();
        @Cleanup
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            list.add(new Cliente(resultSet.getInt("id_Cliente"), resultSet.getString("nom_Cliente")));
        }
        return list;
    }
}
