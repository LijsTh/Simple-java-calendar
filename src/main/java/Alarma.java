import java.time.LocalDateTime;
import org.w3c.dom.*;
import java.time.format.*;

public class Alarma {

    private TipoDeAlarma tipo;
    private LocalDateTime fecha;
    private int minsAntes;

    public Alarma(TipoDeAlarma tipo , LocalDateTime fecha){
        this.tipo = tipo;
        this.fecha = fecha;
        this.minsAntes = 0;
    }

    public Alarma(TipoDeAlarma tipo, LocalDateTime fecha,  int minutos) {
        this.tipo = tipo;
        this.fecha = fecha.minusMinutes(minutos);
        this.minsAntes = minutos;
    }

    public TipoDeAlarma getTipo() {
        return tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getMinsAntes() {
        return minsAntes;
    }

    public void setTipo(TipoDeAlarma tipo) {
        this.tipo = tipo;
    }

    public void setMinsAntes(int minsAntes) {
        this.fecha = fecha.plusMinutes(this.minsAntes);
        this.minsAntes = minsAntes;
        this.fecha = fecha.minusMinutes(minsAntes);
    }

    public void setMinsAntesEstatico(int minsAntes){
        this.minsAntes = minsAntes;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void Guardar(Document doc, Element raiz, DateTimeFormatter formateador) {
        Element tipo = doc.createElement("tipo");
        tipo.appendChild(doc.createTextNode(String.valueOf(this.tipo)));
        raiz.appendChild(tipo);

        Element fechLim = doc.createElement("fechLim");
        fechLim.appendChild(doc.createTextNode(this.fecha.format(formateador)));
        raiz.appendChild(fechLim);

        Element minAntes = doc.createElement("minAntes");
        minAntes.appendChild(doc.createTextNode(String.valueOf(this.minsAntes)));
        raiz.appendChild(minAntes);
    }
}
