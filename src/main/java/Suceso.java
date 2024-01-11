import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Suceso {

    protected String titulo;
    protected String descripcion;
    protected boolean diaCompleto;
    protected ArrayList<Alarma> alarmas;
    protected LocalDateTime fecha;


    public void setTitulo(String nombre) {
        this.titulo = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDiaCompleto(boolean esDiaCompleto) {
        this.diaCompleto = esDiaCompleto;
    }

    public void agregarAlarmaDefinida(TipoDeAlarma tipo ,LocalDateTime fecha){
        Alarma alarma = new Alarma(tipo, fecha);
        this.alarmas.add(alarma);
    }

    public void agregarAlarmaMinsAntes(TipoDeAlarma tipo, int minsAntes) {
        Alarma alarma = new Alarma(tipo, this.fecha, minsAntes);
        this.alarmas.add(alarma);
    }

    public void agregarAlarmaLector(TipoDeAlarma tipo, LocalDateTime fecha, int minsAntes){
        Alarma alarma = new Alarma(tipo, fecha);
        alarma.setMinsAntesEstatico(minsAntes);
        this.alarmas.add(alarma);
    }

    public void modificarAlarma(Alarma alarma, TipoDeAlarma tipo, int minsAntes) {
        alarma.setTipo(tipo);
        alarma.setMinsAntes(minsAntes);
    }

    public void eliminarAlarma(Alarma alarma) {
        this.alarmas.remove(alarma);
    }


// PARA TESTS
    public boolean esDiaCompleto() {
        return diaCompleto;
    }

    public ArrayList<Alarma> getAlarmas() {
        return alarmas;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }


    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean EsDiaCompleto() {
        return diaCompleto;
    }
}
