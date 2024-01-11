import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Evento extends Suceso  {
    protected LocalDateTime fechaFin;

    public Evento(String titulo, String descripcion, LocalDateTime fechaIni, LocalDateTime fechaFin, boolean diaCompleto) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fechaIni;
        this.fechaFin = fechaFin;
        this.diaCompleto = diaCompleto;
        this.alarmas = new ArrayList<>();
    }

    @Override
    public void agregarAlarmaDefinida(TipoDeAlarma tipo, LocalDateTime fecha) {
        Alarma alarma = new Alarma(tipo, fecha);
        this.alarmas.add(alarma);
    }

    @Override
    public void agregarAlarmaMinsAntes(TipoDeAlarma tipo, int minsAntes) {
        Alarma alarma = new Alarma(tipo, this.fecha, minsAntes);
        this.alarmas.add(alarma);
    }

    public void eliminarAlarma(Alarma alarma){
        this.alarmas.remove(alarma);
    }

    public void setFechaIni(LocalDateTime fechaIni) {
        this.fecha = fechaIni;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }


    public boolean estaEnFecha(LocalDateTime fechaInicial , LocalDateTime fechaFinal) {
        if (this.diaCompleto){
            if (this.fechaFin.toLocalDate().equals(fechaInicial.toLocalDate()) || this.fechaFin.toLocalDate().equals(fechaFinal.toLocalDate()) || this.fecha.toLocalDate().equals(fechaInicial.toLocalDate()) || this.fecha.toLocalDate().equals(fechaFinal.toLocalDate())) {
                return true;
            }
        }
        if (this.fecha.equals(fechaInicial) || this.fecha.equals(fechaFinal) || this.fechaFin.equals(fechaFinal) || this.fechaFin.equals(fechaInicial)) {
            return true;
        }
        return (this.fecha.isAfter(fechaInicial) && this.fecha.isBefore(fechaFinal)) || (this.fechaFin.isAfter(fechaInicial) && this.fechaFin.isBefore(fechaFinal)) || (this.fecha.isBefore(fechaInicial) && this.fechaFin.isAfter(fechaFinal));
    }

    public void Guardar(Document doc, Element raiz) {
        Element titulo = doc.createElement("titulo");
        titulo.appendChild(doc.createTextNode(this.titulo));
        raiz.appendChild(titulo);

        Element desc = doc.createElement("desc");
        desc.appendChild(doc.createTextNode(this.descripcion));
        raiz.appendChild(desc);

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Element fechIni = doc.createElement("fechaIni");
        fechIni.appendChild(doc.createTextNode(this.fecha.format(formateador)));
        raiz.appendChild(fechIni);

        Element fechFin = doc.createElement("fechaFin");
        fechFin.appendChild(doc.createTextNode(this.fechaFin.format(formateador)));
        raiz.appendChild(fechFin);

        Element diaComp = doc.createElement("diaComp");
        diaComp.appendChild(doc.createTextNode(String.valueOf(this.diaCompleto)));
        raiz.appendChild(diaComp);

        if (this.alarmas.size() > 0) {
            Element listaAlarmas = doc.createElement("listaAlarmas");
            raiz.appendChild(listaAlarmas);

            for (Alarma a : this.alarmas) {
                Element alarma = doc.createElement("alarma");
                listaAlarmas.appendChild(alarma);
                a.Guardar(doc, alarma, formateador);
            }
        }
    }
}
