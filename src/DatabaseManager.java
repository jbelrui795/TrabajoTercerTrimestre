import org.w3c.dom.*;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private DatabaseConnection databaseConnection;

    private Connection con;

    /**
     * Crea una instancia databasManager con la que trabajaremos en la base de datos
     * @param connection
     * @throws SQLException
     */
    public DatabaseManager(DatabaseConnection connection) throws SQLException {
        this.databaseConnection = connection;
        this.databaseConnection.connect(DriverManager.getConnection(connection.getConnectionString()));
        this.con = this.databaseConnection.getConnection();
    }

    /**
     * Con este metodo obtenemos los datos de la tabla que nosotros indiquemos.
     * @param nombreTabla Nombre de la tabla. Usar las constantes estaticas de las clase Cita,
     *                    Medico y Paciente
     * @param columnOrder Podemos añadir un objeto ColumnOrder para poder ordenar los resultados
     *                    segun este objeto. También se puede dejar en null si no se quiere ordenar
     * @param campos Campos a mostrar
     * @return array de Object en el que habra las instancias de las clase Cita, Medico, Paciente
     */
    public ArrayList<Object> getDatosTabla(String nombreTabla, ColumnOrder columnOrder, String... campos) {
        ArrayList<Object> resultados = new ArrayList<>();
        try {
            String select = "SELECT ";
            String from = "FROM";
            for (int i = 0; i < campos.length; i++) {
                select += campos[i] + ",";
            }
            select = select.substring(0, select.length() - 1);// Quita la última coma
            select += " " + from + " " + nombreTabla;

            if (columnOrder != null) {
                select += " ORDER BY " + columnOrder.getOrder();
            }

            Statement stmt = this.databaseConnection.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery(select);


            if (nombreTabla.equals(Paciente.NOMBRE_TABLA)) {

                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    resultados.add(paciente);
                    try {
                        for (int i = 0; i < campos.length; i++) {
                            switch (campos[i]) {
                                case "id":
                                    paciente.setId(rs.getInt(i + 1));
                                    break;
                                case "nombre":
                                    paciente.setNombre(rs.getString(i + 1));
                                    break;
                                case "edad":
                                    paciente.setEdad(rs.getInt(i + 1));
                                    break;
                                case "direccion":
                                    paciente.setDireccion(rs.getString(i + 1));
                                    break;
                                case "telefono":
                                    paciente.setTelefono(rs.getString(i + 1));
                                    break;
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            } else if (nombreTabla.equals(Cita.NOMBRE_TABLA)) {
                while (rs.next()) {
                    Cita cita = new Cita();
                    resultados.add(cita);
                    try {
                        for (String campoActual : campos) {
                            switch (campoActual) {
                                case "id":
                                    cita.setId(rs.getInt(campoActual));
                                    break;
                                case "id_paciente":
                                    cita.setId_paciente(rs.getInt(campoActual));
                                    break;
                                case "id_medico":
                                    cita.setId_medico(rs.getInt(campoActual));
                                    break;
                            }

                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else if (nombreTabla.equals(Medico.NOMBRE_TABLA)) {
                while (rs.next()) {
                    Medico medico = new Medico();


                    for (int i = 0; i < campos.length; i++) {

                        switch (campos[i]) {
                            case "id":
                                medico.setId(rs.getInt(i + 1));
                                break;
                            case "nombre":
                                medico.setNombre(rs.getString(i + 1));
                                break;
                            case "especialidad":
                                medico.setEspecialidad(rs.getString(i + 1));
                                break;
                            case "telefono":
                                medico.setTelefono(rs.getString(i + 1));
                                break;
                            case "direccion":
                                medico.setDireccion(rs.getString(i + 1));
                                break;
                            default:
                                System.out.println(campos[i]);

                        }
                    }
                    resultados.add(medico);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    /**
     * Añade uno o mas pacientes
     * @param paciente
     */
    public void insertarPaciente(Paciente... paciente) {
        if(paciente.length < 1) throw new IllegalArgumentException("No has pasado ningun paciente");
        PreparedStatement ps;
        String sql;
        try {
            sql = "INSERT INTO pacientes(id, nombre, edad, direccion, telefono) VALUES (?, ?, ?, ?, ?)";
            ps = this.con.prepareStatement(sql);
            for (Paciente pacienteActual : paciente) {
                ps.setInt(1, pacienteActual.getId());
                ps.setString(2, pacienteActual.getNombre());
                ps.setInt(3, pacienteActual.getEdad());
                ps.setString(4, pacienteActual.getDireccion());
                ps.setString(5, pacienteActual.getTelefono());
                if (ps.executeUpdate() < 1) {
                    throw new SQLException("No se inserto el paciente con id " + pacienteActual.getId());
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade uno o mas medicos
     * @param medico
     */
    public void insertarMedico(Medico... medico) {
        if(medico.length < 1) throw new IllegalArgumentException("No has pasado ningun medico");
        PreparedStatement ps;
        String sql;
        try {
            sql = "INSERT INTO medicos(id, nombre, especialidad, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
            ps = this.con.prepareStatement(sql);
            for (Medico medicoActual : medico) {

                ps.setInt(1, medicoActual.getId());
                ps.setString(2, medicoActual.getNombre());
                ps.setString(3, medicoActual.getEspecialidad());
                ps.setString(4, medicoActual.getTelefono());
                ps.setString(5, medicoActual.getDireccion());
                if (ps.executeUpdate() < 1) {
                    throw new SQLException("No se inserto el medico con id " + medicoActual.getId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade uno o mas citas
     * @param cita
     */
    public void insertarCita(Cita... cita) {
        if(cita.length < 1) throw new IllegalArgumentException("No has pasado ninguna cita");
        PreparedStatement ps;
        String sql;
        try {
            sql = "INSERT INTO citas(id, id_paciente, id_medico) VALUES (?, ?, ?)";
            ps = this.con.prepareStatement(sql);
            for (Cita citaActual : cita) {
                ps.setInt(1, citaActual.getId());
                ps.setInt(2, citaActual.getId_paciente());
                ps.setInt(3, citaActual.getId_medico());
                if (ps.executeUpdate() < 1) {
                    throw new SQLException("No se inserto la cita con id " + citaActual.getId());
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica un paciente ya existente
     * @param paciente Pasar un objeto con todos sus datos y cambiamos el que nos interese antes de
     *                 usar este metodo
     */
    public void modificarPaciente(Paciente paciente) {
        PreparedStatement ps;
        String sql;
        try {
            sql = "UPDATE pacientes SET nombre=?, edad=?, direccion=?, telefono=? WHERE id=?";
            ps = this.con.prepareStatement(sql);
            ps.setString(1, paciente.getNombre());
            ps.setInt(2, paciente.getEdad());
            ps.setString(3, paciente.getDireccion());
            ps.setString(4, paciente.getTelefono());
            ps.setInt(5, paciente.getId());
            if (ps.executeUpdate() < 1) {
                throw new SQLException("No se modifico ningun paciente");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Modifica un medico ya existente
     * @param medico Pasar un objeto con todos sus datos y cambiamos el que nos interese antes de
     *                 usar este metodo
     */
    public void modificarMedico(Medico medico) {
        PreparedStatement ps;
        String sql;
        try {
            sql = "UPDATE medicos SET nombre=?, especialidad=?, telefono=?, direccion=? WHERE id=?";
            ps = this.con.prepareStatement(sql);
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getEspecialidad());
            ps.setString(3, medico.getTelefono());
            ps.setString(4, medico.getDireccion());
            ps.setInt(5, medico.getId());
            if (ps.executeUpdate() < 1) {
                throw new SQLException("No se modifico medico");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Modifica una cita ya existente
     * @param cita Pasar un objeto con todos sus datos y cambiamos el que nos interese antes de
     *                 usar este metodo
     */
    public void modificarCita(Cita cita) {
        PreparedStatement ps;
        String sql;
        try {
            sql = "UPDATE medicos SET id_medico=?, id_paciente=? WHERE id=?";
            ps = this.con.prepareStatement(sql);
            ps.setInt(1, cita.getId_medico());
            ps.setInt(2, cita.getId_paciente());
            ps.setInt(3, cita.getId());

            if (ps.executeUpdate() < 1) {
                throw new SQLException("No se modifico la cita");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un paciente segun su id
     * @param id id paciente
     * @return
     */
    public boolean eliminarPaciente(int id) {
        boolean deleted = false;
        PreparedStatement ps;
        String sql;

        try {
            sql = "DELETE FROM pacientes WHERE id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new SQLException("No se elimino ningun paciente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    /**
     * Eliminamos un medico segun su id
     * @param id id medico
     * @return
     */
    public boolean eliminarMedico(int id) {
        boolean deleted = false;
        PreparedStatement ps;
        String sql;

        try {
            sql = "DELETE FROM medicos WHERE id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new SQLException("No se elimino ningun medico");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    /**
     * Eliminamos una cita segun su id
     * @param id id cita
     * @return
     */
    public boolean eliminarCita(int id) {
        boolean deleted = false;
        PreparedStatement ps;
        String sql;

        try {
            sql = "DELETE FROM medicos WHERE id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new SQLException("No se elimino ningun medico");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    /**
     * Metodo que exportará todos los datos actuales de la base de datos
     */
    public void exportarXml(){
        try {
            // 1º Creamos una instancia de la clase File para acceder al archivo donde guardaremos el XML.
            File f=new File("database.xml");
            //2º Creamos una nueva instancia del transformador a través de la fábrica de transformadores.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //3º Establecemos algunas opciones de salida, como por ejemplo, la codificación de salida.
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //4º Creamos el StreamResult, que intermediará entre el transformador y el archivo de destino.
            StreamResult result = new StreamResult(f);
            // Creamos documento vacio
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            // Se crean las etiquetas padres de las diferentes tablas, donde
            // se guardaran los datos que se obtengan
            Element pacientes = doc.createElement("pacientes");
            Element citas = doc.createElement("citas");
            Element medicos = doc.createElement("medicos");
            Element raiz = doc.createElement("data");
            doc.appendChild(raiz);
            // Añadimos al final del documento vacio los elementos padres
            raiz.appendChild(pacientes);
            raiz.appendChild(citas);
            raiz.appendChild(medicos);
            ColumnOrder columnOrder = new ColumnOrder(1, "id");
            // Obtenemos los datos de la base de datos
            ArrayList<Object> datosPacientes = this.getDatosTabla(Paciente.NOMBRE_TABLA,columnOrder, "id", "nombre", "edad", "direccion", "telefono");
            ArrayList<Object> datosMedicos = this.getDatosTabla(Medico.NOMBRE_TABLA,columnOrder, "id", "nombre", "especialidad", "telefono", "direccion");
            ArrayList<Object> datosCitas = this.getDatosTabla(Cita.NOMBRE_TABLA,columnOrder, "id", "id_paciente", "id_medico");
            for (Object pacienteActual:datosPacientes) {
                Paciente paciente = (Paciente)pacienteActual;
                Element elementoPaciente = doc.createElement("paciente");
                elementoPaciente.setAttribute("id", String.valueOf(paciente.getId()));
                Element nombrePaciente = doc.createElement("nombre");
                nombrePaciente.appendChild(doc.createTextNode(paciente.getNombre()));
                Element edadPaciente = doc.createElement("edad");
                edadPaciente.appendChild(doc.createTextNode(String.valueOf(paciente.getEdad())));
                Element direccionPaciente = doc.createElement("direccion");
                direccionPaciente.appendChild(doc.createTextNode(paciente.getDireccion()));
                Element telefonoPaciente = doc.createElement("telefono");
                telefonoPaciente.appendChild(doc.createTextNode(paciente.getTelefono()));
                elementoPaciente.appendChild(nombrePaciente);
                elementoPaciente.appendChild(edadPaciente);
                elementoPaciente.appendChild(direccionPaciente);
                elementoPaciente.appendChild(telefonoPaciente);
                pacientes.appendChild(elementoPaciente);
                System.out.println(paciente);
            }
            for (Object medicoActual:datosMedicos) {
                Medico medico = (Medico) medicoActual;
                Element elementoMedico = doc.createElement("medico");
                elementoMedico.setAttribute("id", String.valueOf(medico.getId()));
                Element nombreMedico = doc.createElement("nombre");
                nombreMedico.appendChild(doc.createTextNode(medico.getNombre()));
                Element especialidadMedico = doc.createElement("especialidad");
                especialidadMedico.appendChild(doc.createTextNode(medico.getEspecialidad()));
                Element telefonoMedico = doc.createElement("telefono");
                telefonoMedico.appendChild(doc.createTextNode(medico.getTelefono()));
                Element direccionMedico = doc.createElement("direccion");
                direccionMedico.appendChild(doc.createTextNode(medico.getDireccion()));
                elementoMedico.appendChild(nombreMedico);
                elementoMedico.appendChild(especialidadMedico);
                elementoMedico.appendChild(telefonoMedico);
                elementoMedico.appendChild(direccionMedico);
                medicos.appendChild(elementoMedico);
            }
            for (Object citaActual:datosCitas) {
                Cita cita = (Cita) citaActual;
                Element elementoCita = doc.createElement("cita");
                elementoCita.setAttribute("id", String.valueOf(cita.getId()));
                Element elementoIdPaciente = doc.createElement("id_paciente");
                elementoIdPaciente.appendChild(doc.createTextNode(String.valueOf(cita.getId_paciente())));
                Element elementoIdMedico = doc.createElement("id_medico");
                elementoIdMedico.appendChild(doc.createTextNode(String.valueOf(cita.getId_medico())));
                elementoCita.appendChild(elementoIdPaciente);
                elementoCita.appendChild(elementoIdMedico);
                citas.appendChild(elementoCita);
            }
            //5º Creamos el DOMSource, que intermediará entre el transformador y el árbol DOM.
            DOMSource source = new DOMSource(doc);
            //6º Realizamos la transformación.
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println("¡Error! No se ha podido llevar a cabo la transformación.");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que importa todos los datos de la base de datos
     * @param urlXml direccion archivo XML
     */
    public void importarXml(String urlXml){
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(urlXml);
            NodeList pacientes = doc.getElementsByTagName("pacientes").item(0).getChildNodes();
            NodeList medicos = doc.getElementsByTagName("medicos").item(0).getChildNodes();
            NodeList citas = doc.getElementsByTagName("citas").item(0).getChildNodes();


            // bucle para recorrer los hijos de la etiqueta paciente
            for (int i = 0; i < pacientes.getLength(); i++) {
                Node nodoActual = pacientes.item(i);
                if (nodoActual.getNodeType() == Document.ELEMENT_NODE){
                    Element pacienteActual = (Element) nodoActual;
                    NodeList datosPaciente = pacienteActual.getChildNodes();
                    Paciente paciente = new Paciente();
                    paciente.setId(Integer.parseInt(pacienteActual.getAttribute("id")));
                    for (int j = 0; j < datosPaciente.getLength(); j++) {
                        Node datoActual = datosPaciente.item(j);


                        if (datoActual.getNodeType() == Document.ELEMENT_NODE){
                            Element elementoDato = (Element) datoActual;
                            switch (elementoDato.getFirstChild().getNodeType()){


                                case Document.TEXT_NODE ->
                                {
                                    Text valor = (Text) elementoDato.getFirstChild();
                                    if (elementoDato.getTagName().equals("nombre")){
                                        paciente.setNombre(valor.getWholeText());
                                    } else if (elementoDato.getTagName().equals("edad")) {
                                        paciente.setEdad(Integer.parseInt(valor.getWholeText()));
                                    } else if (elementoDato.getTagName().equals("direccion")) {
                                        paciente.setDireccion(valor.getWholeText());
                                    } else if (elementoDato.getTagName().equals("telefono")) {
                                        paciente.setTelefono(valor.getWholeText());
                                    }
                                }


                            }
                        } else if (datoActual.getNodeType() == Document.TEXT_NODE) {


                        }
                    }
                    System.out.println(paciente);
                    this.insertarPaciente(paciente);
                }
            }
            // bucle para recorrer los hijos de la etiqueta medicos
            for (int i = 0; i < medicos.getLength(); i++) {
                Node nodoActual = medicos.item(i);
                if (nodoActual.getNodeType() == Document.ELEMENT_NODE){
                    Element medicoActual = (Element) nodoActual;
                    NodeList datosMedico = medicoActual.getChildNodes();
                    Medico medico = new Medico();
                    medico.setId(Integer.parseInt(medicoActual.getAttribute("id")));
                    for (int j = 0; j < datosMedico.getLength(); j++) {
                        Node datoActual = datosMedico.item(j);


                        if (datoActual.getNodeType() == Document.ELEMENT_NODE){
                            Element elementoDato = (Element) datoActual;
                            switch (elementoDato.getFirstChild().getNodeType()){


                                case Document.TEXT_NODE ->
                                {
                                    Text valor = (Text) elementoDato.getFirstChild();
                                    if (elementoDato.getTagName().equals("nombre")){
                                        medico.setNombre(valor.getWholeText());
                                    } else if (elementoDato.getTagName().equals("especialidad")) {
                                        medico.setEspecialidad(valor.getWholeText());
                                    } else if (elementoDato.getTagName().equals("telefono")) {
                                        medico.setTelefono(valor.getWholeText());
                                    } else if (elementoDato.getTagName().equals("direccion")) {
                                        medico.setDireccion(valor.getWholeText());
                                    }
                                }


                            }
                        } else if (datoActual.getNodeType() == Document.TEXT_NODE) {


                        }
                    }
                    System.out.println(medico);
                    this.insertarMedico(medico);

                }
            }
            // bucle para recorrer los hijos de la etiqueta medicos
            for (int i = 0; i < citas.getLength(); i++) {
                Node nodoActual = citas.item(i);
                if (nodoActual.getNodeType() == Document.ELEMENT_NODE){
                    Element citasActual = (Element) nodoActual;
                    NodeList datoscitas = citasActual.getChildNodes();
                    Cita cita = new Cita();
                    cita.setId(Integer.parseInt(citasActual.getAttribute("id")));
                    for (int j = 0; j < datoscitas.getLength(); j++) {
                        Node datoActual = datoscitas.item(j);


                        if (datoActual.getNodeType() == Document.ELEMENT_NODE){
                            Element elementoDato = (Element) datoActual;
                            switch (elementoDato.getFirstChild().getNodeType()){


                                case Document.TEXT_NODE ->
                                {
                                    Text valor = (Text) elementoDato.getFirstChild();
                                    if (elementoDato.getTagName().equals("id_medico")){
                                        cita.setId_medico(Integer.parseInt(valor.getWholeText()));
                                    } else if (elementoDato.getTagName().equals("id_paciente")) {
                                        cita.setId_paciente(Integer.parseInt(valor.getWholeText()));
                                    }
                                }


                            }
                        } else if (datoActual.getNodeType() == Document.TEXT_NODE) {
                        }
                    }
                    System.out.println(cita);
                    this.insertarCita(cita);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Comprueba la integridad referencial de las citas ya que es la unica tabla con relaciones
     */
    public void comprobarIntegridadCitas() {
        try {
            Statement stmtCitas = con.createStatement();
            ResultSet citas = stmtCitas.executeQuery("SELECT * FROM citas");
            PreparedStatement stmtMedico = con.prepareStatement("SELECT COUNT(id) FROM medicos WHERE id=?");
            PreparedStatement stmtPaciente = con.prepareStatement("SELECT COUNT(id) FROM pacientes WHERE id=?");
            while (citas.next()) {
                Cita citaActual = new Cita(citas.getInt(1), citas.getInt(2), citas.getInt(3));
                System.out.println("Cita " + citaActual.getId());
                System.out.println("Relación a médico \n -------------");
                if (citaActual.getId_medico() > 0) {
                    stmtMedico.setInt(1, citaActual.getId_medico());
                    ResultSet rsMedico = stmtMedico.executeQuery();
                    if (rsMedico.next() && rsMedico.getInt(1) > 0) {
                        System.out.println("La relacion es la correcta");
                    } else {
                        System.out.println("La celda apunta a un id eliminado");
                    }
                } else {
                    System.out.println("No hay un medico asignado a la cita");
                }
                System.out.println("Relación a paciente \n -------------");
                if (citaActual.getId_paciente() > 0) {
                    stmtPaciente.setInt(1, citaActual.getId_paciente());
                    ResultSet rsPaciente = stmtPaciente.executeQuery();
                    if (rsPaciente.next() && rsPaciente.getInt(1) > 0) {
                        System.out.println("La relacion es la correcta");
                    } else {
                        System.out.println("La celda apunta a un id eliminado");
                    }
                } else {
                    System.out.println("No hay un paciente asignado a la cita");
                }
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}