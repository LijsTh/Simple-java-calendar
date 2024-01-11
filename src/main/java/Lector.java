import java.util.ArrayList;

import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedSet;
import java.util.TreeSet;

public class Lector {

    public Document agarrarDocumento(String path) throws Exception {
        File archivoXML = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(archivoXML);
        return doc;
    }

    public  ArrayList<Tarea> RestaurarTareas(Document doc) throws Exception{
        ArrayList<Tarea> resultado = new ArrayList<>();

        Element raiz = doc.getDocumentElement();

        NodeList listaTareas = raiz.getElementsByTagName("Tarea");
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < listaTareas.getLength(); i++) {
            Element tareaElement = (Element) listaTareas.item(i);
            String titulo = tareaElement.getElementsByTagName("titulo").item(0).getTextContent();
            String desc = tareaElement.getElementsByTagName("desc").item(0).getTextContent();
            String fecha = tareaElement.getElementsByTagName("fechaLim").item(0).getTextContent();
            boolean diaCompleto = Boolean.parseBoolean(tareaElement.getElementsByTagName("diaComp").item(0).getTextContent());
            boolean estaComp = Boolean.parseBoolean(tareaElement.getElementsByTagName("estaComp").item(0).getTextContent());

            LocalDateTime fechaLimite = LocalDateTime.parse(fecha, formateador);

            Tarea tarea = new Tarea(titulo, desc, fechaLimite, diaCompleto);
            if (estaComp) {
                tarea.actualizar();
            }

            NodeList listaAlarmas = tareaElement.getElementsByTagName("alarma");
            restaurarAlarmasDeSuceso(tarea, listaAlarmas, formateador);
            resultado.add(tarea);
        }
        return resultado;
    }


    public ArrayList<Evento> RestaurarEventos(Document doc) throws Exception{
        ArrayList<Evento> resultado = new ArrayList<>();

        Element raiz = doc.getDocumentElement();

        NodeList listaEventos = raiz.getElementsByTagName("Evento");
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < listaEventos.getLength(); i++) {
            Element ElementoElement = (Element) listaEventos.item(i);
            String titulo = ElementoElement.getElementsByTagName("titulo").item(0).getTextContent();
            String desc = ElementoElement.getElementsByTagName("desc").item(0).getTextContent();
            String fechaIniString = ElementoElement.getElementsByTagName("fechaIni").item(0).getTextContent();
            boolean diaCompleto = Boolean.parseBoolean(ElementoElement.getElementsByTagName("diaComp").item(0).getTextContent());
            String fechaFinString = ElementoElement.getElementsByTagName("fechaFin").item(0).getTextContent();

            LocalDateTime fechaIni = LocalDateTime.parse(fechaIniString, formateador);
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinString, formateador);

            Evento evento = new Evento(titulo, desc, fechaIni, fechaFin, diaCompleto);

            NodeList listaAlarmas = ElementoElement.getElementsByTagName("alarma");
            restaurarAlarmasDeSuceso(evento, listaAlarmas, formateador);

            resultado.add(evento);
        }


        return resultado;
    }

    public ArrayList<EventoRepetible> RestaurarEventosRepetibles(Document doc) throws Exception {
        ArrayList<EventoRepetible> resultado = new ArrayList<>();

        Element raiz = doc.getDocumentElement();

        NodeList listaEventosRepetibles = raiz.getElementsByTagName("EventoRepetible");
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < listaEventosRepetibles.getLength(); i++) {
            Element ElementoElement = (Element) listaEventosRepetibles.item(i);
            // Titulo y descripcion:
            String titulo = ElementoElement.getElementsByTagName("titulo").item(0).getTextContent();
            String desc = ElementoElement.getElementsByTagName("desc").item(0).getTextContent();

            // las fechas:
            String fechaIniString = ElementoElement.getElementsByTagName("fechaIni").item(0).getTextContent();
            String fechaFinString = ElementoElement.getElementsByTagName("fechaFin").item(0).getTextContent();
            String fechaFinRepeticiones = ElementoElement.getElementsByTagName("fechaFinRepeticiones").item(0).getTextContent();

            // los booleans:
            boolean diaCompleto = Boolean.parseBoolean(ElementoElement.getElementsByTagName("diaComp").item(0).getTextContent());
            boolean infinito = Boolean.parseBoolean(ElementoElement.getElementsByTagName("infinito").item(0).getTextContent());

            // los ints:
            int intervalo = Integer.parseInt(ElementoElement.getElementsByTagName("intervalo").item(0).getTextContent());
            int cantRepeticiones = Integer.parseInt(ElementoElement.getElementsByTagName("cantRepeticiones").item(0).getTextContent());

            // los enums: FRECUENCIA, DiaSemana (SortedSet)
            Frecuencia frecuencia = Frecuencia.valueOf(ElementoElement.getElementsByTagName("frecuencia").item(0).getTextContent());


            // <DiasSemana>
            // SortedSet<DiaSemana> diasSemana;
            String diasSemanasTexto = ElementoElement.getElementsByTagName("diasSemana").item(0).getTextContent();
            SortedSet<DiaSemana> diasDeSemanas = null;
            if (!diasSemanasTexto.equals("null")) {
                diasDeSemanas = new TreeSet<>();
                NodeList dias = ElementoElement.getElementsByTagName("diaSemana");
                for (int j = 0; j < dias.getLength(); j++) {
                    DiaSemana dia = DiaSemana.valueOf(dias.item(j).getTextContent());
                    diasDeSemanas.add(dia);
                }
            }

            LocalDateTime fechaIni = LocalDateTime.parse(fechaIniString, formateador);
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinString, formateador);

            LocalDateTime fechaFinRepes = null;
            if (!fechaFinRepeticiones.equals("null")) {
                fechaFinRepes = LocalDateTime.parse(fechaFinRepeticiones, formateador);
            }

            EventoRepetible eventoR = new EventoRepetible(titulo, desc, fechaIni, fechaFin, diaCompleto, frecuencia, diasDeSemanas, intervalo, cantRepeticiones, fechaFinRepes, infinito);

            NodeList listaAlarmas = ElementoElement.getElementsByTagName("alarma");
            restaurarAlarmasDeSuceso(eventoR, listaAlarmas, formateador);

            resultado.add(eventoR);
        }

        return resultado;
    }

    public void restaurarAlarmasDeSuceso(Suceso suceso , NodeList listaAlarmas , DateTimeFormatter formateador){
        for (int a = 0; a < listaAlarmas.getLength(); a++) {
            Element alarmaElement = (Element) listaAlarmas.item(a);
            TipoDeAlarma tipo = TipoDeAlarma.valueOf(alarmaElement.getElementsByTagName("tipo").item(0).getTextContent());
            String fechaA = alarmaElement.getElementsByTagName("fechLim").item(0).getTextContent();
            int minAnt = Integer.parseInt(alarmaElement.getElementsByTagName("minAntes").item(0).getTextContent());

            LocalDateTime fechaAlarma = LocalDateTime.parse(fechaA, formateador);

            suceso.agregarAlarmaLector(tipo, fechaAlarma, minAnt);
        }
    }


}
