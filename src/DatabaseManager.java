import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private DatabaseConnection databaseConnection;
    private Connection con;
    private Paciente paciente;

    public DatabaseManager(){
        this.databaseConnection = DatabaseConnection.getInstance();

    }
    public ArrayList<Paciente> getDatosTabla(String nombreTabla, String[] campos){
        ArrayList<Paciente> pacientes=null;
        try {
            String select = "SELECT ";
            String from = "FROM";
            for (int i = 0; i < campos.length; i++) {
                select += campos[i] + from + nombreTabla +",";
            }
            select = select.substring(0, select.length()-1); // Quita la última coma

            PreparedStatement ps = this.databaseConnection.getConnection().prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            pacientes= new ArrayList<Paciente>();
            while(rs.next()){
                pacientes.add(new Paciente(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pacientes;
    }
    // 2.2 2.3
    public ArrayList<Paciente> getPacientes(String nombre, ColumnOrder... columnOrder){
        ArrayList<Paciente> pacientes=null;
        try {
            PreparedStatement ps=this.databaseConnection.getConnection().prepareStatement("SELECT id,nombre,edad,direccion,telefono " +
                    "FROM pacientes WHERE nombre=? ORDER BY " + columnOrder[0].getOrder());
            ps.setString(1,nombre);
            ResultSet rs = ps.executeQuery();
            pacientes = new ArrayList<Paciente>();
            while (rs.next()){
                pacientes.add(new Paciente(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pacientes;
    }
    /**
     * 2.5
     * Añade un nuevo paciente
     */
    public void insertarPaciente(int id, String nombre, int edad, String direccion, String telefono){
        PreparedStatement ps;
        String sql;
        paciente.setId(id);
        paciente.setNombre(nombre);
        paciente.setEdad(edad);
        paciente.setDireccion(direccion);
        paciente.setTelefono(telefono);
        try {
            con = databaseConnection.getConnection();
            sql = "INSERT INTO pacientes(id, nombre, edad, direccion, telefono) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, paciente.getId());
            ps.setString(2, paciente.getNombre());
            ps.setInt(3, paciente.getEdad());
            ps.setString(4, paciente.getDireccion());
            ps.setString(5, paciente.getTelefono());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión");
        }
    }
    public boolean eliminarPaciente(int id){
        PreparedStatement ps;
        String sql;
        try {
        ps = databaseConnection.getConnection("DELETE FROM paciente WHERE id=?");
        ps.setInt(1,id);
        } catch (SQLException e){

        }
        return
    }


}
