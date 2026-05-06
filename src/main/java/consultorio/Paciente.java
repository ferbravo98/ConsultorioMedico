package consultorio;

public class Paciente {

    private int id;
    private String dni;
    private String apellido;
    private String nombre;
    private String fechaNacimiento;
    private String sexo;
    private String telefono;
    private String domicilio;
    private String obraSocial;
    private String numeroAfiliado;
    private String observaciones;

    public Paciente() {
    }

    public Paciente(int id, String dni, String apellido, String nombre, String fechaNacimiento,
                    String sexo, String telefono, String domicilio, String obraSocial,
                    String numeroAfiliado, String observaciones) {
        this.id = id;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public void setNumeroAfiliado(String numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombreCompleto() {
        return apellido + ", " + nombre;
    }
}