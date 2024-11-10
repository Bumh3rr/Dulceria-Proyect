package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import lombok.Cleanup;
import model.Proveedor;

public class ProveedorDao {

    public static int addProveedorBD(Proveedor proveedor) throws Exception {
        String query = "INSERT INTO PROVEEDOR(first_name,last_name,phone,email,state,municipality,street,zip,date_register) values(?,?,?,?,?,?,?,?,?)";
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
    
    
        public static LinkedList<Proveedor> getAllProveedorsBD() throws Exception {
        String query = "SELECT * FROM PROVEEDOR";

        LinkedList<Proveedor> list = new LinkedList<>();
        @Cleanup
        Connection connection = PoolConexion.getInstance().getConnection();
        @Cleanup
        ResultSet rs = connection.prepareStatement(query).executeQuery();

        while (rs.next()) {
            list.add(new Proveedor(rs.getInt("id_Proveedor"), 
                    rs.getString("first_name"), 
                    rs.getString("last_name"), 
                    rs.getString("phone"), 
                    rs.getString("email"), 
                    rs.getString("state"), 
                    rs.getString("municipality"), 
                    rs.getString("street"), 
                    rs.getString("zip"), 
                    rs.getObject("date_register") != null ? rs.getTimestamp("date_register").toLocalDateTime() : null
            ));
        }
        return list;
    }

}
