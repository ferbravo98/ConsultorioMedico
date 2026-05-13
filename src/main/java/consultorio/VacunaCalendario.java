package consultorio;

public class VacunaCalendario {

    private int id;
    private String edadTexto;
    private int edadMeses;
    private String vacuna;
    private String dosis;
    private int orden;

    public int getId() {
        return id;
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

    public int getOrden() {
        return orden;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setOrden(int orden) {
        this.orden = orden;
    }
}