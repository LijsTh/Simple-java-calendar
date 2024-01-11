import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class  CalendarioTest {

    @Test
    public void testCalendarioAgregaTareaDia() {
        Calendario calendario = new Calendario();
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 29,12, 0);
        Tarea tarea = new Tarea("Ver videos de Proba", "No quiero ver mas Proba", fecha, true);

        calendario.agregarTarea(tarea);
        ArrayList<Tarea> ts = calendario.tareasEnFechas(fecha, fecha);

        Assert.assertTrue("nos fijamos que este la tarea en la fecha pedida", ts.contains(tarea));
    }

    @Test
    public void testCalendarioAgregaMuchasTareaDia() {
        Calendario calendario = new Calendario();
        LocalDateTime fecha = LocalDateTime.of(2023, 4, 16,0,0);
        Tarea tarea = new Tarea("Ver videos de Proba", "Pain", fecha, true);
        Tarea tarea2 = new Tarea("Ver  videos de Proba", "Pain", fecha, true);
        Tarea tarea3 = new Tarea("Ver muchos  mas videos de Proba", "Pain", fecha, true);

        calendario.agregarTarea(tarea);
        calendario.agregarTarea(tarea2);
        calendario.agregarTarea(tarea3);
        ArrayList<Tarea> ts = calendario.tareasEnFechas(fecha, fecha);

        Assert.assertTrue("nos fijamos que este la tarea en la fecha pedida 1", ts.contains(tarea));
        Assert.assertTrue("nos fijamos que este la tarea en la fecha pedida 2", ts.contains(tarea2));
        Assert.assertTrue("nos fijamos que este la tarea en la fecha pedida 3", ts.contains(tarea3));
    }

    @Test
    public void testCalendarioAgregarRangoTareas() {
        Calendario calendario = new Calendario();
        LocalDateTime ini = LocalDateTime.of(2020, 8, 29,0,0);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 1, 10,0,0);
        LocalDateTime fin = LocalDateTime.of(2021, 7, 15,0,0);
        LocalDateTime fecha4 = LocalDateTime.of(2023, 3, 20,0,0);
        Tarea tarea1 = new Tarea("Tarea que esta", "No quiero ver mas Proba", fecha2, true);
        Tarea tarea2 = new Tarea("Tarea que no esta", "No quiero ver mas Proba", fecha4, true);

        calendario.agregarTarea(tarea1);
        calendario.agregarTarea(tarea2);
        ArrayList<Tarea> ts = calendario.tareasEnFechas(ini, fin);

        Assert.assertTrue("Nos fijamos que esta tarea esta", ts.contains(tarea1));
        Assert.assertFalse("Nos fijamos que esta tarea no esta", ts.contains(tarea2));
    }

    @Test
    public void testCalendarioBorrarTareaDia() {
        Calendario calendario = new Calendario();
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 29,0,0);
        Tarea tarea = new Tarea("Ver videos de Proba", "No quiero ver mas Proba", fecha, true);

        calendario.agregarTarea(tarea);
        calendario.eliminarTarea(tarea);
        ArrayList<Tarea> ts = calendario.tareasEnFechas(fecha, fecha);

        Assert.assertFalse("Nos fijamos que se borra", ts.contains(tarea));
    }

    @Test
    public void testCalendarioModificarTarea(){
        Calendario calendario = new Calendario();
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 7, 29,2,0);
        Tarea tarea = new Tarea("Ver videos de Proba", "No quiero ver mas Proba", fecha, false);

        calendario.agregarTarea(tarea);
        ArrayList<Tarea> tareas  = calendario.tareasEnFechas(fecha, fecha);
        tareas.get(0).setFechaLimite(fecha2);

        ArrayList<Tarea> ts = calendario.tareasEnFechas(fecha, fecha);
        ArrayList<Tarea> ts2 = calendario.tareasEnFechas(fecha2, fecha2);
        ArrayList<Tarea> ts3 = calendario.tareasEnFechas(fecha, fecha2);


        Assert.assertFalse("Nos fijamos que no este en fecha",ts.contains(tarea));
        Assert.assertTrue("Nos fijamos que este en fecha2",ts2.contains(tarea));
        Assert.assertTrue("Nos fijamos que este en fecha2",ts3.contains(tarea));
    }

    @Test
    public void testCalendarioAgregarEvento(){
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 29,2,0);
        LocalDateTime fechaNo = LocalDateTime.of(2021, 7, 30,0,0);
        LocalDateTime fechaNo2 = LocalDateTime.of(2021, 7, 30,2,0);
        Evento evento = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaFin, false);

        calendario.agregarEvento(evento);
        ArrayList<Evento> lista1 = calendario.eventosEnFechas(fechaIni, fechaFin);
        ArrayList<Evento> lista2 = calendario.eventosEnFechas(fechaNo, fechaNo2);
        ArrayList<Evento> lista3 = calendario.eventosEnFechas(fechaIni, fechaNo);
        ArrayList<Evento> lista4 = calendario.eventosEnFechas(fechaFin, fechaNo);

        Assert.assertTrue("Nos fijamos que este en el intervalo definido", lista1.contains(evento));
        Assert.assertFalse("Nos fijamos que no este en donde no tiene que estar", lista2.contains(evento));
        Assert.assertTrue("Nos fijamos que esta en un intervalo que contiene al definido", lista3.contains(evento));
        Assert.assertTrue("Nos fijamos que esta en un intervalo que contiene al definido", lista4.contains(evento));
    }

    @Test
    public void TestCalendarioAgregarEventos(){
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 29,1,0);
        LocalDateTime fechaIni2 = LocalDateTime.of(2021, 7, 29,2,0);
        LocalDateTime fechaFin2 = LocalDateTime.of(2021, 7, 29,3,0);
        LocalDateTime fechaIni3 = LocalDateTime.of(2021, 7, 29,4,0);
        LocalDateTime fechaFin3 = LocalDateTime.of(2021, 7, 29,5,0);
        Evento evento1 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaFin, false);
        Evento evento2 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni2, fechaFin2, false);
        Evento evento3 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni3, fechaFin3, false);
        Evento evento4 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, true);
        Calendario calendario = new Calendario();

        calendario.agregarEvento(evento1);
        calendario.agregarEvento(evento2);
        calendario.agregarEvento(evento3);
        calendario.agregarEvento(evento4);
        ArrayList<Evento> lista1 = calendario.eventosEnFechas(fechaIni, fechaFin);
        ArrayList<Evento> lista2 = calendario.eventosEnFechas(fechaIni2, fechaFin2);
        ArrayList<Evento> lista3 = calendario.eventosEnFechas(fechaIni3, fechaFin3);

        Assert.assertTrue("evento 1 en lista1", lista1.contains(evento1));
        Assert.assertFalse("evento 2 en lista1", lista1.contains(evento2));
        Assert.assertFalse("evento 3 en lista1", lista1.contains(evento3));
        Assert.assertTrue("evento 4 en lista1", lista1.contains(evento4));
        Assert.assertFalse("evento 1 en lista2", lista2.contains(evento1));
        Assert.assertTrue("evento 2 en lista2", lista2.contains(evento2));
        Assert.assertFalse("evento 3 en lista2", lista2.contains(evento3));
        Assert.assertTrue("evento 4 en lista2", lista2.contains(evento4));
        Assert.assertFalse("evento 1 en lista3", lista3.contains(evento1));
        Assert.assertFalse("evento 2 en lista3", lista3.contains(evento2));
        Assert.assertTrue("evento 3 en lista3", lista3.contains(evento3));
        Assert.assertTrue("evento 4 en lista3", lista3.contains(evento4));

    }

    @Test
    public void testCalendarioBorrarEvento(){
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 29,2,0);
        Evento evento = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaFin, false);

        calendario.agregarEvento(evento);
        calendario.eliminarEvento(evento);
        ArrayList<Evento> lista = calendario.eventosEnFechas(fechaIni, fechaFin);

        Assert.assertFalse("Nos fijamos que se borra", lista.contains(evento));
    }


    @Test
    public void testCalendarioModificarFechaEvento(){
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 29,2,0);
        LocalDateTime fechaIni2 = LocalDateTime.of(2021, 7, 30,0,0);
        LocalDateTime fechaFin2 = LocalDateTime.of(2021, 7, 30,2,0);
        Evento evento = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaFin, false);

        calendario.agregarEvento(evento);

        ArrayList<Evento> lista1 = calendario.eventosEnFechas(fechaIni, fechaFin);
        lista1.get(0).setFechaIni(fechaIni2);
        lista1.get(0).setFechaFin(fechaFin2);
        lista1 = calendario.eventosEnFechas(fechaIni, fechaFin);
        ArrayList<Evento> lista2 = calendario.eventosEnFechas(fechaIni2, fechaFin2);

        Assert.assertFalse("Nos fijamos que no esta en la lista1", lista1.contains(evento));
        Assert.assertTrue("Nos que si esta en la lista2", lista2.contains(evento));
    }

    @Test
    public void testCalendarioModificarEventoR() {
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2023, 2, 28, 0, 0);
        LocalDateTime fechaCalen = LocalDateTime.of(2023, 2, 28, 1, 0);
        LocalDateTime fechaCalen2 = LocalDateTime.of(2023, 3, 31, 0, 0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.MARTES);
        dias.add(DiaSemana.MIERCOLES);
        dias.add(DiaSemana.VIERNES);
        EventoRepetible eventoR = new EventoRepetible("proba time", "pain",fechaIni,fechaIni, true, Frecuencia.SEMANAL, dias, 0, 4, null, false);

        calendario.agregarEventoRepetible(eventoR);
        eventoR.setFrecuencia(Frecuencia.DIARIA, 2, null);
        ArrayList<EventoHijo> repetibles2 = eventoR.generarRepeticiones(fechaCalen, fechaCalen2);

        assertEquals("Cantidad de eventos repetibles", 4, repetibles2.size());
        assertEquals("0", LocalDateTime.of(2023,2,28,0,0), repetibles2.get(0).getFecha());
        assertEquals("1", LocalDateTime.of(2023,3,2,0,0), repetibles2.get(1).getFecha());
        assertEquals("2", LocalDateTime.of(2023,3,4,0,0), repetibles2.get(2).getFecha());
        assertEquals("3", LocalDateTime.of(2023,3,6,0,0), repetibles2.get(3).getFecha());        // Nota => el 1/4/23 a las 2 no entra porque B es hasta las 0
    }

    @Test
    public void testCalendarioAgregarEventoR() {
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2023, 3, 4,2,0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 5, 29,0,0);
        LocalDateTime A = LocalDateTime.of(2023, 3, 4,0,0);
        LocalDateTime B = LocalDateTime.of(2023, 4, 1,0,0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.LUNES);
        dias.add(DiaSemana.SABADO);
        EventoRepetible eventoR = new EventoRepetible("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, false,Frecuencia.SEMANAL, dias, 1 , 0, fechaFin, false);

        calendario.agregarEventoRepetible(eventoR);
        ArrayList<EventoHijo> eventos = calendario.eventosRepetiblesEnFechas(A,B);
        Assert.assertEquals("Nos fijamos que la cantidad de eventos aniadidos sea correcta" , 8, eventos.size());
        assertEquals("Fecha 1 " , LocalDateTime.of(2023,3,4,2,0), eventos.get(0).getFecha());
        assertEquals("Fecha 2 " , LocalDateTime.of(2023,3,6,2,0), eventos.get(1).getFecha());
        assertEquals("Fecha 3 " , LocalDateTime.of(2023,3,11,2,0), eventos.get(2).getFecha());
        assertEquals("Fecha 4 " , LocalDateTime.of(2023,3,13,2,0), eventos.get(3).getFecha());
        assertEquals("Fecha 5 " , LocalDateTime.of(2023,3,18,2,0), eventos.get(4).getFecha());
        assertEquals("Fecha 6 " , LocalDateTime.of(2023,3,20,2,0), eventos.get(5).getFecha());
        assertEquals("Fecha 7 " , LocalDateTime.of(2023,3,25,2,0), eventos.get(6).getFecha());
        assertEquals("Fecha 8 " , LocalDateTime.of(2023,3,27,2,0), eventos.get(7).getFecha());

    }

    @Test
    public void testCalendarioBorrarEventoR() {
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2023, 3, 4, 2, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 5, 29, 0, 0);
        LocalDateTime A = LocalDateTime.of(2023, 3, 4, 0, 0);
        LocalDateTime B = LocalDateTime.of(2023, 4, 1, 0, 0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.LUNES);
        dias.add(DiaSemana.SABADO);
        EventoRepetible eventoR = new EventoRepetible("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, false, Frecuencia.SEMANAL, dias, 1, 0, fechaFin, false);

        calendario.agregarEventoRepetible(eventoR);
        ArrayList<EventoHijo> eventos = calendario.eventosRepetiblesEnFechas(A, B);
        Assert.assertEquals("Nos fijamos que la cantidad de eventos aniadidos sea correcta", 8, eventos.size());
        calendario.eliminarEventoRepetibleDesdeHijo(eventos.get(0));
        eventos = calendario.eventosRepetiblesEnFechas(A, B);
        Assert.assertEquals("Nos fijamos que se haya borrado", 0, eventos.size());

    }

    @Test
    public void testCalendarioGuardar() throws Exception {
        Calendario calendario1 = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2023, 3, 4, 2, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 5, 29, 0, 0);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 1, 10,0,0);
        LocalDateTime fecha4 = LocalDateTime.of(2023, 3, 20,0,0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.LUNES);
        dias.add(DiaSemana.SABADO);
        Tarea tarea1 = new Tarea("Tarea que esta", "No quiero ver mas Proba", fecha2, true);
        Tarea tarea2 = new Tarea("Tarea que no esta", "No quiero ver mas Proba", fecha4, true);
        EventoRepetible eventoR = new EventoRepetible("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, false, Frecuencia.SEMANAL, dias, 1, 0, fechaFin, false);
        Evento evento = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaFin, false);

        calendario1.agregarEventoRepetible(eventoR);
        calendario1.agregarEvento(evento);
        calendario1.agregarTarea(tarea1);
        calendario1.agregarTarea(tarea2);
        ArrayList<Evento> eventos = calendario1.eventosEnFechas(fechaIni, fechaFin);
        ArrayList<Tarea> tareas = calendario1.tareasEnFechas(fecha2, fecha4);
        ArrayList<EventoHijo> eventosR = calendario1.eventosRepetiblesEnFechas(fechaIni, fechaFin);

        Document docEventos = calendario1.guardarEventos();
        Document docTareas = calendario1.guardarTareas();
        Document docEventosR = calendario1.guardarEventosRepetibles();
        calendario1.restaurarEventosRepetibles(docEventosR);
        calendario1.restaurarEventos(docEventos);
        calendario1.restaurarTareas(docTareas);

        ArrayList<Evento> eventosRestaurados = calendario1.eventosEnFechas(fechaIni, fechaFin);
        ArrayList<Tarea> tareasRestauradas = calendario1.tareasEnFechas(fecha2, fecha4);
        ArrayList<EventoHijo> eventosRepetiblesRestaurados = calendario1.eventosRepetiblesEnFechas(fechaIni, fechaFin);

        for (int i = 0; i < eventos.size(); i++) {
            assertEquals(eventos.get(i).getTitulo(), eventosRestaurados.get(i).getTitulo());
            assertEquals(eventos.get(i).getDescripcion(), eventosRestaurados.get(i).getDescripcion());
            assertEquals(eventos.get(i).getFecha(), eventosRestaurados.get(i).getFecha());
            assertEquals(eventos.get(i).EsDiaCompleto(), eventosRestaurados.get(i).EsDiaCompleto());
            ArrayList<Alarma> alarmas1 = eventos.get(i).getAlarmas();
            ArrayList<Alarma> alarmas2 = eventosRestaurados.get(i).getAlarmas();
            assertEquals(alarmas1.size(), alarmas2.size());
            for (int j = 0; j < eventosRestaurados.get(i).getAlarmas().size(); j++) {
                assertEquals(alarmas1.get(j).getTipo(), alarmas2.get(j).getTipo());
                assertEquals(alarmas1.get(j).getFecha(), alarmas2.get(j).getFecha());
                assertEquals(alarmas1.get(j).getMinsAntes(), alarmas2.get(j).getMinsAntes());
            }
        }

        for (int i = 0; i < tareas.size(); i++) {
            assertEquals(tareas.get(i).getTitulo(), tareasRestauradas.get(i).getTitulo());
            assertEquals(tareas.get(i).getDescripcion(), tareasRestauradas.get(i).getDescripcion());
            assertEquals(tareas.get(i).getFecha(), tareasRestauradas.get(i).getFecha());
            assertEquals(tareas.get(i).estaCompletada(), tareasRestauradas.get(i).estaCompletada());
            assertEquals(tareas.get(i).EsDiaCompleto(), tareasRestauradas.get(i).EsDiaCompleto());
        }

        for (int i = 0 ; i < eventosR.size() ; i++){
            assertEquals(eventosR.get(i).getTitulo(), eventosRepetiblesRestaurados.get(i).getTitulo());
            assertEquals(eventosR.get(i).getDescripcion(), eventosRepetiblesRestaurados.get(i).getDescripcion());
            assertEquals(eventosR.get(i).getFecha(), eventosRepetiblesRestaurados.get(i).getFecha());
            assertEquals(eventosR.get(i).getFechaFin(), eventosRepetiblesRestaurados.get(i).getFechaFin());
            assertEquals(eventosR.get(i).EsDiaCompleto(), eventosRepetiblesRestaurados.get(i).EsDiaCompleto());
            assertEquals(eventosR.get(i).getFrecuencia(), eventosRepetiblesRestaurados.get(i).getFrecuencia());
            assertEquals(eventosR.get(i).getDiasSemana(), eventosRepetiblesRestaurados.get(i).getDiasSemana());
            assertEquals(eventosR.get(i).getCantidadRepeticiones(), eventosRepetiblesRestaurados.get(i).getCantidadRepeticiones());
            assertEquals(eventosR.get(i).getIntervalo(), eventosRepetiblesRestaurados.get(i).getIntervalo());
            assertEquals(eventosR.get(i).esInfinito(), eventosRepetiblesRestaurados.get(i).esInfinito());
        }

    }



}
