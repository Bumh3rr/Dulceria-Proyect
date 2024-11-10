package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import lombok.Cleanup;
import model.Proveedor;

/**
 * ProveedorDao.java r
 * ProveedorDao es una clase de objeto de acceso a datos (DAO) que proporciona métodos para interactuar con la tabla PROVEEDOR en la base de datos.
 */
public class ProveedorDao {

    /**
     * Agrega un nuevo Proveedor a la base de datos.
     *
     * @param proveedor el objeto Proveedor a agregar
     * @return el ID generado del nuevo Proveedor
     * @throws Exception si hay un error durante la operación de la base de datos
     */

    public static int addProveedorBD(Proveedor proveedor) throws Exception {
        String query = "INSERT INTO PROVEEDOR(nombre,apellido,telefono,correo,estado,minicipio,calle,codigo_postal,fecha_registro) values(?,?,?,?,?,?,?,?,?)";
        int generatedId = -1;

        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, proveedor.getFirst_name());
        ps.setString(2, proveedor.getLast_name());
        ps.setString(3, proveedor.getPhone());
        ps.setString(4, proveedor.getEmail());
        ps.setString(5, proveedor.getState());
        ps.setString(6, proveedor.getMunicipality());
        ps.setString(7, proveedor.getStreet());
        ps.setString(8, proveedor.getZip());
        ps.setTimestamp(9, Timestamp.valueOf(proveedor.getDate_register()));

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
     * Recupera todos los Proveedors de la base de datos.
     *
     * @return una LinkedList de objetos Proveedor
     * @throws Exception si hay un error durante la operación de la base de datos
     */
    public static LinkedList<Proveedor> getAllProveedorsBD() throws Exception {
        String query = "SELECT * FROM PROVEEDOR";

        LinkedList<Proveedor> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        ResultSet rs = connection.prepareStatement(query).executeQuery();

        while (rs.next()) {
            list.add(new Proveedor(rs.getInt("id_Proveedor"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("estado"),
                    rs.getString("minicipio"),
                    rs.getString("calle"),
                    rs.getString("codigo_postal"),
                    rs.getObject("fecha_registro") != null ? rs.getTimestamp("fecha_registro").toLocalDateTime() : null
            ));
        }
        return list;
    }

}