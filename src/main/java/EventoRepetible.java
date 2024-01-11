import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

public class EventoRepetible extends Evento implements ObserverHijoModificado,ObserverBorradoHijo {

    protected Frecuencia frecuencia;
    protected SortedSet<DiaSemana> diasSemana; //Solo para frecuencia semanal
    protected int intervalo;
    protected LocalDateTime fechaFinRepeticiones;
    protected LocalDateTime fechaCreacion;
    protected boolean infinito;
    protected int cantidadRepeticiones;

    public EventoRepetible(String titulo, String descripcion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, boolean diaCompleto, Frecuencia frecuencia, SortedSet<DiaSemana> diasSemana, int intervalo, int cantidadRepeticiones, LocalDateTime fechaFinRepeticiones, boolean infinito) {
        super(titulo, descripcion, fechaHoraInicio, fechaHoraFin, diaCompleto);
        this.frecuencia = frecuencia;
        this.diasSemana = diasSemana;
        this.intervalo = intervalo;
        this.cantidadRepeticiones = cantidadRepeticiones;
        if (cantidadRepeticiones != 0) {
            this.fechaFinRepeticiones = this.definirFechaFin(cantidadRepeticiones);
        } else {
            this.fechaFinRepeticiones = fechaFinRepeticiones;
        }
        this.fechaCreacion = fechaHoraInicio;
        this.infinito = infinito;
    }

    private static LocalDateTime calcularFechaFinalSemanales(LocalDateTime fechaInicio , SortedSet<DiaSemana> dias , int cantidadRepeticiones) {
        Iterator<DiaSemana> iter = dias.iterator();
        DayOfWeek diaWeek = fechaInicio.getDayOfWeek();
        DiaSemana diaInicial = DiaSemana.from(diaWeek);
        DiaSemana actual = null;
        while (iter.hasNext() && actual != diaInicial) {
            actual = iter.next();
        }
        for (int contador = 0; contador < cantidadRepeticiones-1 ; contador++) {
            if (!iter.hasNext()){
                iter = dias.iterator(); // vuelve al principio del set
            }
            actual = iter.next();
            DayOfWeek dayOfWeek = DiaSemana.from(actual);
            fechaInicio = fechaInicio.with(TemporalAdjusters.next(dayOfWeek));
        }
        return fechaInicio;
    }

    protected LocalDateTime definirFechaFin(int cantidadRepeticiones) {
        LocalDateTime fechaFinRepeticiones = this.fecha;
        switch (this.frecuencia) {
            case DIARIA -> fechaFinRepeticiones = fechaFinRepeticiones.plusDays((long) (cantidadRepeticiones - 1) * this.intervalo);
            case SEMANAL -> fechaFinRepeticiones = calcularFechaFinalSemanales(this.fecha, this.diasSemana, cantidadRepeticiones);
            case MENSUAL -> fechaFinRepeticiones = fechaFinRepeticiones.plusMonths((cantidadRepeticiones - 1));
            case ANUAL -> fechaFinRepeticiones = fechaFinRepeticiones.plusYears((cantidadRepeticiones - 1));
        }
        return fechaFinRepeticiones;
    }

    public void setFrecuencia(Frecuencia frecuencia, int intervalo, SortedSet<DiaSemana> diasSemana) {
        this.frecuencia = frecuencia;
        this.setIntervalo(intervalo);
        this.setDiasSemana(diasSemana);
        if (this.fechaFinRepeticiones != null) {
            this.fechaFinRepeticiones = definirFechaFin(this.cantidadRepeticiones);
        }
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public void setCantidadRepeticiones(int cantidadRepeticiones) {
        this.cantidadRepeticiones = cantidadRepeticiones;
        this.fechaFinRepeticiones = this.definirFechaFin(cantidadRepeticiones);
        this.infinito = false;
    }

    public void setFechaFinRepeticiones(LocalDateTime fechaFinRepeticiones) {
        this.fechaFinRepeticiones = fechaFinRepeticiones;
        this.infinito = false;
        this.cantidadRepeticiones = 0;
    }

    public void setDiasSemana(SortedSet<DiaSemana> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setInfinito(boolean infinito) {
        this.cantidadRepeticiones = 0;
        this.fechaFinRepeticiones = null;
        this.infinito = infinito;
    }

    public void setFechaIni(LocalDateTime fecha){
        this.fecha = fecha;
        if (fecha.isBefore(this.fechaCreacion)) {
            this.fechaCreacion = fecha;
        }
    }

    @Override
    public boolean estaEnFecha(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        return !this.generarRepeticiones(fechaInicial, fechaFinal).isEmpty();
    }


    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public SortedSet<DiaSemana> getDiasSemana() {
        return diasSemana;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public LocalDateTime getFechaFinRepeticiones() {
        return fechaFinRepeticiones;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public boolean esInfinito() {
        return infinito;
    }

    public int getCantidadRepeticiones() {
        return cantidadRepeticiones;
    }

    public ArrayList<EventoHijo> generarRepeticiones(LocalDateTime fecha, LocalDateTime fechaFin) {
        if (this.diaCompleto) {
            fecha = LocalDateTime.of(fecha.getYear(), fecha.getMonth(), fecha.getDayOfMonth(), 0, 0, 0);
        }
        ArrayList<EventoHijo> repeticiones = new ArrayList<>();
        LocalDateTime fechaFinAux = this.fechaFin; //chequear si modifica el original o no
        if (this.infinito) {
            fechaFinRepeticiones = fechaFin;
        }
        if (fecha.isBefore(fechaCreacion)) {
            if (fechaFin.isBefore(fechaCreacion)) {
                return repeticiones;
            }
            fecha = fechaCreacion;
        }
        LocalDateTime fechaFinRepAux = fechaFinRepeticiones.plusSeconds(1);
        fechaFin = fechaFin.plusSeconds(1); //para que sea incluyente en el intervalo

        switch (frecuencia) {
            case DIARIA -> {
                while (fecha.isBefore(fechaFin) && fecha.isBefore(fechaFinRepAux)) {
                    this.generarRepesAux(repeticiones, fecha, fechaFinAux);
                    fecha = fecha.plusDays(intervalo);
                    fechaFinAux = fechaFinAux.plusDays(intervalo);
                }
            }
            case SEMANAL -> {
                while (fecha.isBefore(fechaFin) && fecha.isBefore(fechaFinRepAux)) {
                    if (this.diasSemana.contains(DiaSemana.from(fecha.getDayOfWeek()))) {
                        this.generarRepesAux(repeticiones, fecha, fechaFinAux);
                    }
                    fecha = fecha.plusDays(1);
                    fechaFinAux = fechaFinAux.plusDays(1);
                }
            }
            case MENSUAL -> {
                while (fecha.isBefore(fechaFin) && fecha.isBefore(fechaFinRepAux)) {
                    this.generarRepesAux(repeticiones, fecha, fechaFinAux);
                    fecha = fecha.plusMonths(1);
                    fechaFinAux = fechaFinAux.plusMonths(1);
                }
            }
            case ANUAL -> {
                while (fecha.isBefore(fechaFin) && fecha.isBefore(fechaFinRepAux)) {
                    this.generarRepesAux(repeticiones, fecha, fechaFinAux);
                    fecha = fecha.plusYears(1);
                    fechaFinAux = fechaFinAux.plusYears(1);
                }
            }
        }
        return repeticiones;
    }

    private void generarRepesAux(ArrayList<EventoHijo> repeticiones, LocalDateTime fecha, LocalDateTime fechaFinAux) {
        EventoHijo eventoHijo = new EventoHijo(this.titulo, this.descripcion, fecha, fechaFinAux, this.diaCompleto, this.frecuencia, this.diasSemana, this.intervalo, this.cantidadRepeticiones, this.fechaFinRepeticiones, this.infinito, this, this.fecha, this.fechaFin, this.alarmas);
        eventoHijo.copiarAlarmas(this.alarmas);
        repeticiones.add(eventoHijo);
    }

    public void actualizar(EventoHijo hijo) {
        this.setTitulo(hijo.getTitulo());
        this.setDescripcion(hijo.getDescripcion());
        this.setDiaCompleto(hijo.esDiaCompleto());
        this.setFechaIni(hijo.getFechaIniPadre());
        this.alarmas = hijo.getAlarmasPadre();
        this.setFechaFin(hijo.getFechaFinPadre());
        this.setFrecuencia(hijo.getFrecuencia(), hijo.getIntervalo(), hijo.getDiasSemana());
        this.setDiasSemana(hijo.getDiasSemana());
        this.setIntervalo(hijo.getIntervalo());
        this.setFechaFinRepeticiones(hijo.getFechaFinRepeticiones());
        this.setFechaCreacion(hijo.getFechaCreacion());
        this.setInfinito(hijo.esInfinito());
        this.setCantidadRepeticiones(hijo.getCantidadRepeticiones());
    }

    public void eliminarPadre(ArrayList<EventoRepetible> listaEventosRepetiblesCalendario){
        listaEventosRepetiblesCalendario.remove(this);
    }

    @Override
    public void Guardar(Document doc, Element raiz) {
        super.Guardar(doc, raiz);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Element fechaFinRepeticiones = doc.createElement("fechaFinRepeticiones");
        if (this.fechaFinRepeticiones != null) {
            fechaFinRepeticiones.appendChild(doc.createTextNode((this.fechaFinRepeticiones).format(formateador)));
        } else {
            fechaFinRepeticiones.appendChild(doc.createTextNode("null"));
        }
        raiz.appendChild(fechaFinRepeticiones);

        Element infinito = doc.createElement("infinito");
        infinito.appendChild(doc.createTextNode(String.valueOf(this.infinito)));
        raiz.appendChild(infinito);

        Element intervalo = doc.createElement("intervalo");
        intervalo.appendChild(doc.createTextNode(String.valueOf(this.intervalo)));
        raiz.appendChild(intervalo);

        Element cantRepeticiones = doc.createElement("cantRepeticiones");
        cantRepeticiones.appendChild(doc.createTextNode(String.valueOf(this.cantidadRepeticiones)));
        raiz.appendChild(cantRepeticiones);

        Element frecuencia = doc.createElement("frecuencia");
        frecuencia.appendChild(doc.createTextNode(String.valueOf(this.frecuencia)));
        raiz.appendChild(frecuencia);

        Element diasSemana = doc.createElement("diasSemana");
        raiz.appendChild(diasSemana);
        if (this.diasSemana != null) {
            for (DiaSemana dia : this.diasSemana) {
                Element diaSemana = doc.createElement("diaSemana");
                diaSemana.appendChild(doc.createTextNode(String.valueOf(dia)));
                diasSemana.appendChild(diaSemana);
            }
        } else {
            diasSemana.appendChild(doc.createTextNode("null"));
        }
    }
}