import java.sql.*;
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
    public ArrayList<Paciente> getPacientes(){
        ArrayList<Paciente> pacientes=null;
        try {
            PreparedStatement ps = this.connection.prepareStatement("SELECT id,nombre,edad,direccion,telefono FROM pacientes");
            ResultSet rs = ps.executeQuery();
            pacientes= new ArrayList<Paciente>();
            while(rs.next()){
                pacientes.add(new Paciente(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pacientes;
    }
    public ArrayList<Paciente> getPacientes(String nombre){
        ArrayList<Paciente> pacientes=null;
        try {
            PreparedStatement ps=this.connection.prepareStatement("SELECT id,nombre,edad,direccion,telefono FROM pacientes WHERE nombre=?");
            ps.setString(1,nombre);
            ResultSet rs = ps.executeQuery();
            pacientes = new ArrayList<Paciente>();
            while (rs.next()){
                pacientes.add(new Paciente(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pacientes;
    }
}
