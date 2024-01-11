import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.util.ArrayList;

public class Guardador {

    public void GuardarArchivo(Document doc, String path) throws Exception {
        TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
        Transformer transformador = fabricaTransformador.newTransformer();

        transformador.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult resultado = new StreamResult(new File(path));

        transformador.transform(source, resultado);
    }

    public Document GuardarTareas(ArrayList<Tarea> Lista) throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element raiz = doc.createElement("Tareas");
        doc.appendChild(raiz);

        for (Tarea t : Lista) {
            Element tarea = doc.createElement("Tarea");
            raiz.appendChild(tarea);
            t.Guardar(doc, tarea);
        }

        return doc;
    }

    public Document GuardarEventos(ArrayList<Evento> Lista) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element raiz = doc.createElement("Eventos");
        doc.appendChild(raiz);

        for (Evento e : Lista) {
            Element evento = doc.createElement("Evento");
            raiz.appendChild(evento);
            e.Guardar(doc, evento);
        }

        return doc;
    }

    public Document GuardarEventosRepetibles(ArrayList<EventoRepetible> Lista) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element raiz = doc.createElement("EventosRepetibles");
        doc.appendChild(raiz);

        for (Evento e : Lista) {
            Element evento = doc.createElement("EventoRepetible");
            raiz.appendChild(evento);
            e.Guardar(doc, evento);
        }

        return doc;
    }
}
