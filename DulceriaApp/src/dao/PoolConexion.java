package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

public class PoolConexion {

    private static PoolConexion instance;
    private HikariDataSource dataSource;

    public static PoolConexion getInstance() throws Exception {
        if (instance == null) {
            instance = new PoolConexion();
        }
        return instance;
    }

    public PoolConexion() throws Exception {
        try {

            HikariConfig config = new HikariConfig();
            /*BD Emma*/
            config.setJdbcUrl("jdbc:mysql://localhost:3306/dulceria_1");
            config.setUsername("root");
            config.setPassword("Manuelromero20@");

            // Configuraciones opcionales
            config.setMaximumPoolSize(2);//Maximas de Conexiones
            config.setMinimumIdle(1);//Minimo de conexiones abiertas
            config.setIdleTimeout(Duration.ofSeconds(20).toMillis());//Tiempo que la conexion estara disponible
            config.setConnectionTimeout(Duration.ofSeconds(5).toMillis()); //Tiempo antes de lanzar Exepcion
            config.setLeakDetectionThreshold(Duration.ofSeconds(15).toMillis());  // Detección de conexiones con fugas

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new Exception("Conexión inestable, verifica tu conexión", e);
        }
    }

    public Connection getConnection() throws Exception {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new Exception("Conexión fallida", e);
        }
    }

}
