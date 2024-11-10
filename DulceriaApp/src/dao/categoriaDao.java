package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import lombok.Cleanup;
import model.categoria;

/**
 * categoriaDao es una clase de objeto de acceso a datos (DAO) que proporciona métodos para interactuar con la tabla CATEGORIA en la base de datos.
 */
public class categoriaDao {

    /**
     * Agrega una nueva categoria a la base de datos.
     *
     * @param categoria el objeto categoria a agregar
     * @return el ID generado de la nueva categoria
     * @throws Exception si hay un error durante la operación de la base de datos
     */
    public static int addCategoriaBD(categoria categoria) throws Exception {
        String query = "INSERT INTO CATEGORIA(tipo) values(?)";
        int generatedId = -1;

        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, categoria.getTipo());
        if (ps.executeUpdate() > 0) {
            @Cleanup
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }

    /**
     * Recupera todas las categorias de la base de datos.
     *
     * @return una LinkedList de objetos categoria
     * @throws Exception si hay un error durante la operación de la base de datos
     */
    public static LinkedList<categoria> getCategoriasBD() throws Exception {
        String query = "SELECT * FROM CATEGORIA";
        LinkedList<categoria> categorias = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query);
        @Cleanup
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            categorias.add(new categoria(rs.getInt("id"), rs.getString("tipo")));
        }
        return categorias;
    }
}