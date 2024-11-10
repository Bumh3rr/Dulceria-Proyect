package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import lombok.Cleanup;
import model.Producto;

/**
 * productoDao es una clase de objeto de acceso a datos (DAO) que proporciona métodos para interactuar con la tabla PRODUCTO en la base de datos.
 */
public class ProductoDao {

    /**
     * Agrega un nuevo producto a la base de datos.
     *
     * @param producto el objeto producto a agregar
     * @return el ID generado del nuevo producto
     * @throws Exception si hay un error durante la operación de la base de datos
     */
    public static int addProductoBD(Producto producto) throws Exception {
        String query = "INSERT INTO PRODUCTO(nombre_Prod, marca, descripcion, stock_Disp, precio_Compra, precio_Venta, PROVEEDOR_id_Proveedor,CATEGORIA_id_Categoria) values(?,?,?,?,?,?,?,?)";
        int generatedId = -1;

        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, producto.getNombre());
        ps.setString(2, producto.getMarca());
        ps.setString(3, producto.getDescripcion());
        ps.setInt(4, producto.getStock());
        ps.setDouble(5, producto.getPrecio_Compra());
        ps.setDouble(6, producto.getPrecio_Venta());
        ps.setInt(7, producto.getId_Categoria());
        ps.setInt(8, producto.getId_Prov());

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
    public static LinkedList<Producto> getAllProductosBD() throws Exception {
        String query = "SELECT * FROM PRODUCTO";

        LinkedList<Producto> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        ResultSet rs = connection.prepareStatement(query).executeQuery();

        while (rs.next()) {
            list.add(new Producto(
                    rs.getInt("id_Prod"),
                    rs.getString("nombre_Prod"),
                    rs.getString("marca"),
                    rs.getString("descripcion"),
                    rs.getInt("stock_Disp"),
                    rs.getDouble("precio_Compra"),
                    rs.getDouble("precio_Venta"),
                    rs.getInt("CATEGORIA_id_Categoria"),
                    rs.getInt("PROVEEDOR_id_Proveedor")
            ));
        }
        return list;
    }
}