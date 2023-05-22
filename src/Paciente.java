public class Paciente {
    public static final String NOMBRE_TABLA="pacientes";
    private int id;
    private String nombre;
    private int edad;
    private String direccion;
    private String telefono;

    /**
     * Constructor para crear instancia de cita vacia
     */
    public Paciente(){

    }

    /**
     * Constructor para crear instancia de paciente con todos sus datos
     * @param id id paciente
     * @param nombre nombre paciente
     * @param edad edad paciente
     * @param direccion direccion paciente
     * @param telefono telefono paciente
     */
    public Paciente(int id, String nombre, int edad, String direccion, String telefono){

        this.id=id;
        this.nombre=nombre;
        this.edad=edad;
        this.direccion=direccion;
        this.telefono=telefono;
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
    public int getEdad(){
        return edad;
    }
    public void setEdad(int edad){
        this.edad=edad;
    }
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono=telefono;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
