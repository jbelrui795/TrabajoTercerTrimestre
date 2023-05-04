import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {
    private Connection connection=null;
    private Statement statement=null;
    private boolean drugsUpdated=false;
    private ArrayList<Drug> drugsData;
    public DatabaseManager(Connection connection){
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.drugsData = new ArrayList<Drug>();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ArrayList<>
}
