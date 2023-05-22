import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private Connection connection;
    private String connectionString;
    private String mainDatabaseConnection;
    private static DatabaseConnection databaseConnection;

    /**
     * Crea una instancia de DatabaseConnection en el que hay metodos para
     * gestionar en la base de datos
     * @param url Direccion del servidor y puerto. Ejemplo: localhost:3306
     * @param database Nombre de la base de datos
     * @param usuario Usuario con el que accedemos a la base de datos
     * @param clave Contraseña con la que accedemos a la base de datos
     */
    public DatabaseConnection(String url, String database, String usuario, String clave) {
        String baseUrl = "jdbc:mysql://%s/%s?user=%s&password=%s";
        // guarda los datos de la conexion
        this.connectionString = String.format(baseUrl, url, database, usuario, clave);
        this.mainDatabaseConnection = String.format(baseUrl, url, "mysql", usuario, clave);
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch ( SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Guarda el objeto de conexion
     * @param connection
     * @return Si es distinto de null devuelve true
     */
    public boolean connect(Connection connection){
        this.connection = connection;
        return connection==null?false:true;
    }

    /**
     * Obtiene la conexion actual
     * @return
     */
    public Connection getConnection(){

        return this.connection;
    }

    /**
     * Devuelve la url para conectarte a la base de datos deseada
     * @return
     */
    public String getConnectionString(){
        return this.connectionString;
    }

    /**
     * Devuelve la url para conectarte a la base de datos principal de mysql
     * @return
     */
    public String getMainDatabaseConnection(){
        return this.mainDatabaseConnection;
    }
}
