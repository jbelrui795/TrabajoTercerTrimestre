import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorEsquema {
    private static DatabaseConnection databaseConnection;

    /**
     * Este metodo nos crearía la base de datos y en caso de que no exista la base de datos
     * las tablas
     * @param connection instancia de DatabaseConnection
     * @param borrarBaseDatosSiExiste true borrara la base de datos en caso de que exista,
     *                                false avisara de que ha detectado una base de datos
     *                                y no creara las tablas
     * @throws SQLException
     */
    public static void inicializarBaseDatos(DatabaseConnection connection, boolean borrarBaseDatosSiExiste) throws SQLException {
        System.out.println("Inicializando base de datos");
        databaseConnection = connection;
        databaseConnection.connect(DriverManager.getConnection(connection.getMainDatabaseConnection()));
        Statement stmt = databaseConnection.getConnection().createStatement();
        if (borrarBaseDatosSiExiste){
            stmt.executeUpdate("DROP DATABASE IF EXISTS hospital");
        }
        int executeResults = stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS hospital");

        stmt.close();
        if (executeResults < 1){
            System.out.println("Se ha detectado una base de datos con el mismo nombre, no se creara la base de datos con el nombre hospital");

        }else {
            System.out.println("Se ha creado la base de datos hospital");
            databaseConnection.connect(DriverManager.getConnection(connection.getConnectionString()));
            stmt=databaseConnection.getConnection().createStatement();
            if (stmt.executeUpdate("CREATE TABLE IF NOT EXISTS pacientes ("+
                    "id INT PRIMARY KEY," +
                    "nombre VARCHAR(50)," +
                    "edad INT," +
                    "direccion VARCHAR(100)," +
                    "telefono VARCHAR(20)" +
                    ")") < 1){
                System.out.println("Se creo la tabla paciente");
            }else{
                System.out.println("No se creo la tabla paciente");
            }
            if (stmt.executeUpdate("CREATE TABLE IF NOT EXISTS medicos (id INT PRIMARY KEY, nombre VARCHAR(50), " +
                    "especialidad VARCHAR(50), telefono VARCHAR(20), direccion VARCHAR(100))")<1){
                System.out.println("Se creo la tabla medicos");
            } else {
                System.out.println("No se creo la tabla medicos");
            }
            if (stmt.executeUpdate("CREATE TABLE IF NOT EXISTS citas (id INT PRIMARY KEY, id_paciente INT, " +
                    "id_medico INT, FOREIGN KEY (id_paciente) REFERENCES pacientes(id), " +
                    "FOREIGN KEY (id_medico) REFERENCES medicos(id))") < 1){
                System.out.println("Se creo la tabla citas");
            } else {
                System.out.println("No se creo la tabla citas");
            }
            stmt.close();
        }

        System.out.println("Se ha terminado la inicialización");
    }

}
