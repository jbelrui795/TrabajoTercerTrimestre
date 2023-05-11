public class TestDatabase {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        if (databaseConnection.connect("jdbc:mysql://localhost/hospital?\" + \n" +
                "\t\t\"user=root&password="));
        ArrayList<Paciente> pacientes = getDatosTabla("");
    }
}
