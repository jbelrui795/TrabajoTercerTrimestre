import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private Connection connection;
    public boolean connect( String connectionString){
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            this.connection = DriverManager.getConnection("mysql://localhost/hospital?user=root&password=");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection==null?false:true;
    }
    public boolean disconnect(){
        try{
            this.connection.close();
            return true;
        } catch (SQLException e){
            return false;
        }
    }
    public boolean isConnected(){
        try {
            return  !this.connection.isClosed();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
