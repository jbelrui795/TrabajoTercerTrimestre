import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=root&password=");


}
