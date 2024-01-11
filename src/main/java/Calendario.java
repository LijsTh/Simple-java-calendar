import org.w3c.dom.*;
import java.util.ArrayList;
import java.time.LocalDateTime;


public class Calendario {

    private ArrayList<Evento> listaEventos;
    private ArrayList<Tarea> listaTareas;
    private ArrayList<EventoRepetible> listaEventosRepetibles;
    private final Guardador guardador;
    private final Lector lector;


    public Calendario() {
        this.listaEventos = new ArrayList<>();
        this.listaTareas = new ArrayList<>();
        this.listaEventosRepetibles = new ArrayList<>();
        this.guardador = new Guardador();
        this.lector = new Lector();
    }

    //EVENTOS
    public void agregarEvento(Evento evento) {
        listaEventos.add(evento);
    }

    public void agregarEventoRepetible(EventoRepetible eventoRepetible) {listaEventosRepetibles.add(eventoRepetible);}

    public void eliminarEvento(Evento evento) {
        listaEventos.remove(evento);
    }

    public void eliminarEventoRepetibleDesdeHijo (EventoHijo eventoHijo) {
        eventoHijo.avisarEliminarHijo(this.listaEventosRepetibles);
    }



    public ArrayList<EventoHijo> eventosRepetiblesEnFechas(LocalDateTime fechaIni , LocalDateTime fechaFin){
        ArrayList<EventoHijo> eventosQueCumplen = new ArrayList<>();

        for (EventoRepetible evento : this.listaEventosRepetibles) {
            ArrayList<EventoHijo> repeticiones = evento.generarRepeticiones(fechaIni, fechaFin);
            if (repeticiones.size() > 0) {
                eventosQueCumplen.addAll(evento.generarRepeticiones(fechaIni, fechaFin));
            }
        }
        return eventosQueCumplen;
    }

    public ArrayList<Evento> eventosEnFechas(LocalDateTime fechaIni , LocalDateTime fechaFin){
        ArrayList<Evento> eventosQueCumplen = new ArrayList<>();
        for (Evento evento : this.listaEventos){
            if (evento.estaEnFecha(fechaIni, fechaFin)) {
                eventosQueCumplen.add(evento);
            }
        }
        return eventosQueCumplen;
    }

    //TAREAS
    public void agregarTarea(Tarea tarea) {
        listaTareas.add(tarea);
    }

    public void eliminarTarea(Tarea tarea) {
        listaTareas.remove(tarea);
    }


    public ArrayList<Tarea> tareasEnFechas(LocalDateTime fechaIni, LocalDateTime fechaFin) {
        ArrayList<Tarea> tareasQueCumplen = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (tarea.estaEnFecha(fechaIni, fechaFin)) {
                tareasQueCumplen.add(tarea);
            }
        }
        return tareasQueCumplen;
    }

    protected Document guardarTareas() throws Exception {
        return guardador.GuardarTareas(this.listaTareas);
    }

    protected void restaurarTareas(Document doc) throws Exception {
        this.listaTareas = lector.RestaurarTareas(doc);
    }

    protected Document guardarEventos() throws Exception {
        return guardador.GuardarEventos(this.listaEventos);
    }

    protected void restaurarEventos(Document doc) throws Exception {
        this.listaEventos = lector.RestaurarEventos(doc);
    }

    protected Document guardarEventosRepetibles() throws Exception {
        return guardador.GuardarEventosRepetibles(this.listaEventosRepetibles);
    }

    protected void restaurarEventosRepetibles(Document doc) throws Exception {
        this.listaEventosRepetibles = lector.RestaurarEventosRepetibles(doc);
    }

    public void guardar() throws Exception {
        Document tareas = guardarTareas();
        Document eventos = guardarEventos();
        Document eventosRepetibles =  guardarEventosRepetibles();
        guardador.GuardarArchivo(tareas, "tareas.xml");
        guardador.GuardarArchivo(eventos, "eventos.xml");
        guardador.GuardarArchivo(eventosRepetibles, "eventosRepetibles.xml");
    }

    public void restaurar() throws Exception {
        Document documentoTareas = lector.agarrarDocumento("tareas.xml");
        Document documentoEventos = lector.agarrarDocumento("eventos.xml");
        Document documentoEventosRepetibles = lector.agarrarDocumento("eventosRepetibles.xml");
        restaurarTareas(documentoTareas);
        restaurarEventos(documentoEventos);
        restaurarEventosRepetibles(documentoEventosRepetibles);
    }
}
