import java.time.LocalDateTime;
import java.util.ArrayList;
import org.w3c.dom.*;
import java.time.format.*;

public class Tarea extends Suceso {

    private boolean completada;

    public Tarea(String titulo, String descripcion, LocalDateTime fechaLimite, boolean EsDiaCompleto) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fechaLimite;
        this.diaCompleto = EsDiaCompleto;
        this.completada = false;
        this.alarmas = new ArrayList<>();
    }

    public boolean estaCompletada() {
        return this.completada;
    }

    public void actualizar() {
        this.completada = !this.completada;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fecha = fechaLimite;
    }

    public boolean estaEnFecha(LocalDateTime fechaIni, LocalDateTime fechaFin) {
        if (this.diaCompleto) {
            if (fecha.toLocalDate().equals(fechaIni.toLocalDate()) || fecha.toLocalDate().equals(fechaFin.toLocalDate())) {
                return true;
            }
        }
        if (this.fecha.equals(fechaIni) || this.fecha.equals(fechaFin)) {
            return true;
        }
        return this.fecha.isAfter(fechaIni) && this.fecha.isBefore(fechaFin);
    }

    public void Guardar(Document doc, Element raiz) {
        Element titulo = doc.createElement("titulo");
        titulo.appendChild(doc.createTextNode(this.titulo));
        raiz.appendChild(titulo);

        Element desc = doc.createElement("desc");
        desc.appendChild(doc.createTextNode(this.descripcion));
        raiz.appendChild(desc);

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Element fechaLim = doc.createElement("fechaLim");
        fechaLim.appendChild(doc.createTextNode((this.fecha).format(formateador)));
        raiz.appendChild(fechaLim);

        Element diaComp = doc.createElement("diaComp");
        diaComp.appendChild(doc.createTextNode(String.valueOf(this.diaCompleto)));
        raiz.appendChild(diaComp);

        Element estaComp = doc.createElement("estaComp");
        estaComp.appendChild(doc.createTextNode(String.valueOf(this.completada)));
        raiz.appendChild(estaComp);

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
