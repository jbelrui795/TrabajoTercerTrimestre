public class Medico {
    public static final String NOMBRE_TABLA="medicos";
    private int id;
    private String nombre;
    private String especialidad;
    private String telefono;
    private String direccion;

    /**
     * Constructor para crear instancia de cita vacia
     */
    public Medico(){

    }

    /**
     * Constructor para crear instancia de medico con todos sus datos
     * @param id id medico
     * @param nombre nombre medico
     * @param especialidad especialidad medico
     * @param telefono telefono medico
     * @param direccion direccion medico
     */
    public Medico(int id, String nombre, String especialidad, String telefono, String direccion){

        this.id=id;
        this.nombre=nombre;
        this.especialidad=especialidad;
        this.telefono=telefono;
        this.direccion=direccion;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public String getEspecialidad(){
        return especialidad;
    }
    public void setEspecialidad(String especialidad){
        this.especialidad=especialidad;
    }
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono=telefono;
    }
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }

    @Override
    public String toString() {
        return "Medicos{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
