public class Paciente {
    private int id;
    private String nombre;
    private String edad;
    private String direccion;
    private String telefono;

    public Paciente(int id, String nombre, String edad, String direccion, String telefono){
        super();
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
    public String getEdad(){
        return edad;
    }
    public void setEdad(String edad){
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
}
