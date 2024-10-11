package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

import lombok.Cleanup;
import model.Categoria;
import model.Producto;
import model.Proveedor;

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
    public static Boolean addProductoBD(Producto producto) throws Exception {
        String query = "INSERT INTO PRODUCTO(nombre_Prod,marca,descripcion,stock_Disp,precio_Compra,precio_Venta,PROVEEDOR_id_Proveedor,CATEGORIA_id_Categoria) values(?,?,?,?,?,?,?,?)";
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
        ps.setInt(7, producto.getProveedor().getId());
        ps.setInt(8, producto.getCategoria().getId());

        if (ps.executeUpdate() > 0) {
            @Cleanup
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId > 0;
    }

    /**
     * Recupera todos los productos de la base de datos.
     *
     * @return una LinkedList de objetos producto
     * @throws Exception si hay un error durante la operación de la base de datos
     */
    public static LinkedList<Producto> getAllProductosBD() throws Exception {
        String query = "SELECT * FROM PRODUCTO AS p " +
                "JOIN CATEGORIA AS c ON c.id_Categoria = p.CATEGORIA_id_Categoria " +
                "JOIN PROVEEDOR AS prov ON prov.id_Proveedor = p.PROVEEDOR_id_Proveedor;";

        LinkedList<Producto> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        ResultSet rs = connection.prepareStatement(query).executeQuery();

        while (rs.next()) {
            list.add(new Producto(
                    rs.getInt("p.id_Prod"),
                    rs.getString("p.nombre_Prod"),
                    rs.getString("p.marca"),
                    rs.getString("p.descripcion"),
                    rs.getInt("p.stock_Disp"),
                    rs.getDouble("p.precio_Compra"),
                    rs.getDouble("p.precio_Venta"),
                    new Categoria(rs.getInt("c.id_Categoria"),
                            rs.getString("c.tipo")),
                    new Proveedor(
                            rs.getInt("prov.id_Proveedor"),
                            rs.getString("prov.nombre"),
                            rs.getString("prov.apellido"),
                            rs.getString("prov.telefono"),
                            rs.getString("prov.correo")
                    )
            ));
        }
        return list;
    }
}