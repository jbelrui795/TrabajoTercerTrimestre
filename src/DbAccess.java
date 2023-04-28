import java.sql.*;
public class DbAccess {
    public DbAccess() throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@WIN01:" +
                "1521:oracleBD","user","passwd");
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery();
    }

}
