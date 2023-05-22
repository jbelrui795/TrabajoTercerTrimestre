public class Cita {
    public static final String NOMBRE_TABLA="citas";
    private int id;
    private int id_paciente;
    private int id_medico;

    /**
     * Constructor para crear instancia de cita vacia
     */
    public Cita(){

    }

    /**
     * Constructor para crear instancia de cita con todos sus datos
     * @param id id de la cita
     * @param id_paciente id del paciente
     * @param id_medico id del medico
     */
    public Cita(int id, int id_paciente, int id_medico){
        this.id=id;
        this.id_medico=id_medico;
        this.id_paciente=id_paciente;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", id_paciente=" + id_paciente +
                ", id_medico=" + id_medico +
                '}';
    }
}
