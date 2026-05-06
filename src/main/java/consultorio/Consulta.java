package consultorio;

public class Consulta {

    private int id;
    private int pacienteId;
    private String fechaConsulta;
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private String proximoControl;

    public int getId() {
        return id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getProximoControl() {
        return proximoControl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setProximoControl(String proximoControl) {
        this.proximoControl = proximoControl;
    }
}