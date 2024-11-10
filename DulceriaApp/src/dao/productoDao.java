package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import lombok.Cleanup;
import model.producto;

/**
 * productoDao es una clase de objeto de acceso a datos (DAO) que proporciona métodos para interactuar con la tabla PRODUCTO en la base de datos.
 */
public class productoDao {

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto el objeto producto a agregar
     * @return el ID generado del nuevo producto
     * @throws Exception si hay un error durante la operación de la base de datos
     */
    public static int addProductoBD(producto producto) throws Exception {
        String query = "INSERT INTO PRODUCTO(nombre_Prod, id_Prov, precio_Compra, precio_Venta, id_Categoria, stock_Disp, descripcion) values(?,?,?,?,?,?,?)";
        int generatedId = -1;

        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, producto.getNombre_Prod());
        ps.setInt(3, producto.getId_Prov());
        ps.setDouble(5, producto.getPrecio_Compra());
        ps.setDouble(6, producto.getPrecio_Venta());
        ps.setInt(2, producto.getId_Categoria());
        ps.setInt(4, producto.getStock_Disp());
        ps.setString(7, producto.getDescripcion());

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
     * Recupera todos los productos de la base de datos.
     *
     * @return una LinkedList de objetos producto
     * @throws Exception si hay un error durante la operación de la base de datos
     */
    public static LinkedList<producto> getAllProductosBD() throws Exception {
        String query = "SELECT * FROM PRODUCTO";

        LinkedList<producto> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        ResultSet rs = connection.prepareStatement(query).executeQuery();

        while (rs.next()) {
            list.add(new producto(rs.getInt("id_Prod"),
                    rs.getString("nombre_Prod"),
                    rs.getInt("id_Prov"),
                    rs.getInt("precio_Compra"),
                    rs.getInt("precio_Venta"),
                    rs.getInt("id_Categoria"),
                    rs.getInt("stock_Disp"),
                    rs.getString("descripcion")
            ));
        }
        return list;
    }
}