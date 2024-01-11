import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.SortedSet;

public class EventoHijo extends EventoRepetible {

    ObserverBorradoHijo observerBorradoHijo;
    ObserverHijoModificado observerHijoModificado;
    LocalDateTime fechaIniPadre;
    LocalDateTime fechaFinPadre;
    ArrayList<Alarma> alarmasPadre;

    public EventoHijo(String titulo, String descripcion, LocalDateTime fechaIni, LocalDateTime fechaFin, boolean diaCompleto, Frecuencia frecuencia, SortedSet<DiaSemana> diasSemana, int intervalo, int cantidadRepeticiones, LocalDateTime fechaFinRepeticiones, boolean infinito, EventoRepetible suscriptor, LocalDateTime fechaIniPadre, LocalDateTime fechaFinPadre, ArrayList<Alarma> alarmasPadre) {
        super(titulo,descripcion, fechaIni, fechaFin, diaCompleto, frecuencia, diasSemana, intervalo, cantidadRepeticiones, fechaFinRepeticiones, infinito);
        this.fechaIniPadre = fechaIniPadre;
        this.fechaFinPadre = fechaFinPadre;
        this.alarmasPadre = alarmasPadre;
        this.observerHijoModificado = suscriptor;
        this.observerBorradoHijo = suscriptor;
    }

    public void avisarEliminarHijo(ArrayList<EventoRepetible> listaEventosRepetiblesCalendario){
        this.observerBorradoHijo.eliminarPadre(listaEventosRepetiblesCalendario);
    }
    public void avisar(){
        this.observerHijoModificado.actualizar(this);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.avisar();
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
        this.avisar();
    }


    public void setFechaIni(LocalDateTime fecha){
        this.fechaIniPadre = fecha;
        this.avisar();
    }

    public void setDiaCompleto(boolean diaCompleto){
        this.diaCompleto = diaCompleto;
        this.avisar();
    }

    public void setNuevaAlarmaMinsAntes(TipoDeAlarma tipo, int minsAntes){
        this.alarmasPadre.add(new Alarma(tipo, this.fechaCreacion, minsAntes));
        this.avisar();
    }

    public void setNuevaAlarma(TipoDeAlarma tipo, LocalDateTime fecha){
        this.alarmasPadre.add(new Alarma(tipo, fecha));
        this.avisar();
    }

    public void setModificarAlarma(Alarma alarmaVieja, TipoDeAlarma tipo, LocalDateTime fecha, int minsAntes){
        int posDeAlarmaEnElHijo = this.alarmas.indexOf(alarmaVieja);
        alarmaVieja = this.alarmasPadre.get(posDeAlarmaEnElHijo);
        if (fecha != null) {
            alarmaVieja.setFecha(fecha);
            alarmaVieja.setMinsAntes(0);
        } else {
            alarmaVieja.setFecha(this.fecha);
            alarmaVieja.setMinsAntes(minsAntes);
        }
        alarmaVieja.setTipo(tipo);
        this.avisar();
    }

    public void setEliminarAlarma(Alarma alarma){
        this.alarmas.remove(alarma);
        this.avisar();
    }

    public void setFechaFin( LocalDateTime fechafin){
        this.fechaFinPadre = fechafin;
        this.avisar();
    }

    public void setFechaCreacion (LocalDateTime fechaCreacion){
        this.fechaCreacion = fechaCreacion;
        this.avisar();
    }

    public void setFrecuencia(Frecuencia frecuencia, int intervalo, SortedSet<DiaSemana> diasSemana){
        this.frecuencia = frecuencia;
        this.intervalo = intervalo;
        this.diasSemana = diasSemana;
        this.avisar();
    }

    public void setInfinito(boolean inf) {
        this.infinito = inf;
        this.avisar();
    }

    public void setCantRepes(int cantRepes) {
        this.cantidadRepeticiones = cantRepes;
        this.avisar();
    }

    public void setFinRepeticiones(LocalDateTime fechaFinRepeticiones){
        this.fechaFinRepeticiones = fechaFinRepeticiones;
        this.avisar();
    }

    public void setDiasSemana (SortedSet<DiaSemana> diasRepeticion) {
        this.diasSemana = diasRepeticion;
        this.avisar();
    }

    public void setIntervalo (int intervalo) {
        this.intervalo = intervalo;
        this.avisar();
    }


    protected void copiarAlarmas(ArrayList<Alarma> alarmas) {
        for (Alarma alarma : alarmas) {
            alarma = new Alarma(alarma.getTipo(), this.fecha, alarma.getMinsAntes());
            this.alarmas.add(alarma);
        }
    }

    public LocalDateTime getFechaIniPadre() {
        return fechaIniPadre;
    }

    public LocalDateTime getFechaFinPadre() {
        return fechaFinPadre;
    }

    public ArrayList<Alarma> getAlarmasPadre() {
        return alarmasPadre;
    }


}

