package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

/**
 * PoolConexion es una clase singleton que gestiona un pool de conexiones a la
 * base de datos usando HikariCP.
 */
public class PoolConexion {

    private static PoolConexion instance;
    private HikariDataSource dataSource;

    /**
     * Devuelve la instancia singleton de PoolConexion.
     *
     * @return la instancia singleton de PoolConexion
     * @throws Exception si hay un error al crear la instancia
     */
    public static PoolConexion getInstance() throws Exception {
        if (instance == null) {
            instance = new PoolConexion();
        }
        return instance;
    }

    /**
     * Construye un objeto PoolConexion e inicializa el pool de conexiones
     * HikariCP.
     *
     * @throws Exception si hay un error al inicializar el pool de conexiones
     */
    public PoolConexion() throws Exception {
        try {
            HikariConfig config = new HikariConfig();
            // Configuración de la conexión a la base de datos
//            config.setJdbcUrl("jdbc:mysql://usczjlneyqxrdref:EnUbzvDZDJ7Nqt0yHuCY@brgz2hmn59kjulcjaoig-mysql.services.clever-cloud.com:3306/brgz2hmn59kjulcjaoig");
//            config.setUsername("usczjlneyqxrdref");
//            config.setPassword("EnUbzvDZDJ7Nqt0yHuCY");

            //BD Emma MacAir
            config.setJdbcUrl("jdbc:mysql://localhost:3306/Dulceria");
            config.setUsername("root");
            config.setPassword("Manuelromero20");

            // Configuraciones opcionales
            config.setMaximumPoolSize(2); // Número máximo de conexiones
            config.setMinimumIdle(1); // Número mínimo de conexiones inactivas
            config.setIdleTimeout(Duration.ofSeconds(20).toMillis()); // Tiempo de inactividad en milisegundos
            config.setConnectionTimeout(Duration.ofSeconds(5).toMillis()); // Tiempo de espera de conexión en milisegundos
            config.setLeakDetectionThreshold(Duration.ofSeconds(15).toMillis()); // Umbral de detección de fugas en milisegundos

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new Exception("Conexión inestable, verifica tu conexión", e);
        }
    }

    /**
     * Obtiene una conexión del pool de conexiones. r
     *
     * @return un objeto Connection
     * @throws Exception si hay un error al obtener la conexión
     */
    public Connection getConnection() throws Exception {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new Exception("Conexión fallida", e);
        }
    }

}
