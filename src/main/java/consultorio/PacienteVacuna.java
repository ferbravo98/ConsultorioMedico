package consultorio;

public class PacienteVacuna {

    private int id;
    private int pacienteId;
    private int calendarioVacunaId;
    private String edadTexto;
    private int edadMeses;
    private String vacuna;
    private String dosis;
    private boolean aplicada;
    private String fechaAplicacion;
    private String observaciones;
    private String estado;

    public int getId() {
        return id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public int getCalendarioVacunaId() {
        return calendarioVacunaId;
    }

    public String getEdadTexto() {
        return edadTexto;
    }

    public int getEdadMeses() {
        return edadMeses;
    }

    public String getVacuna() {
        return vacuna;
    }

    public String getDosis() {
        return dosis;
    }

    public boolean isAplicada() {
        return aplicada;
    }

    public String getFechaAplicacion() {
        return fechaAplicacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setCalendarioVacunaId(int calendarioVacunaId) {
        this.calendarioVacunaId = calendarioVacunaId;
    }

    public void setEdadTexto(String edadTexto) {
        this.edadTexto = edadTexto;
    }

    public void setEdadMeses(int edadMeses) {
        this.edadMeses = edadMeses;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public void setAplicada(boolean aplicada) {
        this.aplicada = aplicada;
    }

    public void setFechaAplicacion(String fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}