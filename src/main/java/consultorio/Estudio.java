package consultorio;

public class Estudio {
    private int id;
    private int pacienteId;
    private Integer consultaId;
    private String fecha;
    private String tipo;
    private String nombre;
    private String indicacion;
    private String resultado;
    private String estado;
    private String rutaArchivo;

    public int getId() { return id; }
    public int getPacienteId() { return pacienteId; }
    public Integer getConsultaId() { return consultaId; }
    public String getFecha() { return fecha; }
    public String getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public String getIndicacion() { return indicacion; }
    public String getResultado() { return resultado; }
    public String getEstado() { return estado; }
    public String getRutaArchivo() { return rutaArchivo; }

    public void setId(int id) { this.id = id; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }
    public void setConsultaId(Integer consultaId) { this.consultaId = consultaId; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setIndicacion(String indicacion) { this.indicacion = indicacion; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }
}