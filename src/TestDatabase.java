import java.sql.SQLException;
import java.util.ArrayList;

public class TestDatabase {
    public static void main(String[] args) {
        // Creamos conexion a la base de datos
        DatabaseConnection databaseConnection = new DatabaseConnection("localhost", "hospital", "root", "");
        DatabaseManager databaseManager = null;
        try {
            // Creamos la estructura de la base de datos en caso de que no exista
            GestorEsquema.inicializarBaseDatos(databaseConnection, false);
            // Creamos instancia de databaseManager con la conexion a la base de datos hospital
            databaseManager = new DatabaseManager(databaseConnection);
        } catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        //Importamos los datos de la base de datos a partir del XML
        //databaseManager.importarXml("database.xml");
        ColumnOrder columnOrder = new ColumnOrder(1, "nombre");
        ArrayList<Object> pacientes = databaseManager.getDatosTabla(Paciente.NOMBRE_TABLA, columnOrder, "nombre","edad","telefono", "direccion");
        ArrayList<Object> medicos = databaseManager.getDatosTabla(Medico.NOMBRE_TABLA, columnOrder,"id","especialidad","nombre","telefono", "direccion");
        ArrayList<Object> cita = databaseManager.getDatosTabla(Cita.NOMBRE_TABLA, null,"id");
        // Aqui insertamos un nuevo paciente y lo imprimimos por consola
        TestDatabase testDatabase = new TestDatabase();
        testDatabase.imprimirArray(pacientes);
        //databaseManager.insertarPaciente(new Paciente(6,"Fran",31,"Calle Mexico", "656656565"));
        //pacientes = databaseManager.getDatosTabla(Paciente.NOMBRE_TABLA, columnOrder, "id","nombre","edad","telefono", "direccion");
        //System.out.println("- - - - - - - ");
        //testDatabase.imprimirArray(pacientes);

       // testDatabase.imprimirArray(medicos);
        //databaseManager.insertarMedico(new Medico(51,"Fran","Fisio","656656565","Calle Mexico" ));
        //medicos = databaseManager.getDatosTabla(Medico.NOMBRE_TABLA, columnOrder, "id","nombre","especialidad","telefono", "direccion");
        //System.out.println("- - - - - - - ");
        //testDatabase.imprimirArray(medicos);

        //testDatabase.imprimirArray(cita);
        //databaseManager.insertarCita(new Cita(52,2,3), new Cita(53, 2, 3));
        //cita = databaseManager.getDatosTabla(Cita.NOMBRE_TABLA, null, "id","id_paciente", "id_medico");
        //System.out.println("- - - - - - - ");
        //testDatabase.imprimirArray(cita);

        //testDatabase.imprimirArray(pacientes);
        //databaseManager.modificarPaciente(new Paciente(6 ,"Pablo", 31, "Calle Mexico", "656656565"));

    //databaseManager.exportarXml();

    // databaseManager.comprobarIntegridadCitas();
    }
    public void imprimirArray(ArrayList<Object> arr){
        // Recorremos el array de getDatosTabla e imprimimos su valor dependiendo de su tipo
        for (Object actualObject:arr) {
            if (actualObject instanceof Paciente) { // Si la instacia del objeto actual es Paciente se imprime
                System.out.println(actualObject);
            } else if (actualObject instanceof Medico) { // Si la instacia del objeto actual es Medico se imprime
                System.out.println(actualObject);
            } else if (actualObject instanceof Cita) { // Si la instacia del objeto actual es Cita se imprime
                System.out.println(actualObject);
            }
        }
    }
}

