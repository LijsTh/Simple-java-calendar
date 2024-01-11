import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class LectorTest {

    @Test
    public void TestrestaurarTareas() throws Exception {
        Calendario calendario = new Calendario();
        LocalDateTime fecha = LocalDateTime.of(2023, 4, 16, 0, 0);
        Tarea tarea = new Tarea("Ver videos de Proba", "Pain", fecha, true);
        Tarea tarea2 = new Tarea("Ver aburridos videos de Proba", "Pain", fecha, true);
        Tarea tarea3 = new Tarea("Ver muchos  mas videos de Proba", "Pain", fecha, false);


        calendario.agregarTarea(tarea);
        calendario.agregarTarea(tarea2);
        tarea2.actualizar();
        calendario.agregarTarea(tarea3);
        calendario.guardar();

        ArrayList<Tarea> tareas1 = calendario.tareasEnFechas(fecha, fecha);
        assertEquals(tareas1.size(), 3);

        Calendario calendario2 = new Calendario();
        calendario2.restaurar();
        ArrayList<Tarea> tareas2 = calendario2.tareasEnFechas(fecha, fecha);

        assertEquals(3, tareas2.size());
        for (int i = 0; i < tareas1.size(); i++) {
            assertEquals(tareas1.get(i).getTitulo(), tareas2.get(i).getTitulo());
            assertEquals(tareas1.get(i).getDescripcion(), tareas2.get(i).getDescripcion());
            assertEquals(tareas1.get(i).getFecha(), tareas2.get(i).getFecha());
            assertEquals(tareas1.get(i).estaCompletada(), tareas2.get(i).estaCompletada());
            assertEquals(tareas1.get(i).EsDiaCompleto(), tareas2.get(i).EsDiaCompleto());
        }
    }

    @Test
    public void TestrestaurarTareasConAlarmas() throws Exception {
        Calendario calendario = new Calendario();
        LocalDateTime fecha = LocalDateTime.of(2023, 4, 16, 0, 0);
        LocalDateTime fechaAlarma = LocalDateTime.of(2023, 4, 15, 0, 0);
        Tarea tarea = new Tarea("Ver videos de Proba", "Pain", fecha, true);

        tarea.agregarAlarmaDefinida(TipoDeAlarma.SONIDO, fechaAlarma);
        tarea.agregarAlarmaMinsAntes(TipoDeAlarma.MAIL, 20);

        calendario.agregarTarea(tarea);

        ArrayList<Tarea> tareas1 = calendario.tareasEnFechas(fecha, fecha);
        assertEquals(2, tareas1.get(0).getAlarmas().size());

        calendario.guardar();

        Calendario calendario2 = new Calendario();
        calendario2.restaurar();
        ArrayList<Tarea> tareas2 = calendario2.tareasEnFechas(fecha, fecha);
        ArrayList<Alarma> alarmasEnTarea = tareas2.get(0).getAlarmas();
        assertEquals(2, alarmasEnTarea.size());
        assertEquals(TipoDeAlarma.SONIDO, alarmasEnTarea.get(0).getTipo());
        assertEquals(fechaAlarma, alarmasEnTarea.get(0).getFecha());
        assertEquals(TipoDeAlarma.MAIL, alarmasEnTarea.get(1).getTipo());
        assertEquals(fecha.minusMinutes(20), alarmasEnTarea.get(1).getFecha());

    }

    @Test
    public void TestrestaurarEventos() throws Exception {
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 29, 1, 0);
        LocalDateTime fechaIni2 = LocalDateTime.of(2021, 7, 29, 2, 0);
        LocalDateTime fechaFin2 = LocalDateTime.of(2021, 7, 29, 3, 0);
        LocalDateTime fechaIni3 = LocalDateTime.of(2021, 7, 29, 4, 0);
        LocalDateTime fechaFin3 = LocalDateTime.of(2021, 7, 29, 5, 0);
        Evento evento1 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaFin, false);
        Evento evento2 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni2, fechaFin2, false);
        Evento evento3 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni3, fechaFin3, false);

        calendario.agregarEvento(evento1);
        calendario.agregarEvento(evento2);
        calendario.agregarEvento(evento3);
        ArrayList<Evento> eventos1 = calendario.eventosEnFechas(fechaIni, fechaFin3);

        calendario.guardar();

        Calendario calendario2 = new Calendario();
        calendario2.restaurar();
        ArrayList<Evento> eventos2 = calendario2.eventosEnFechas(fechaIni, fechaFin3);

        assertEquals(3, eventos2.size());
        for (int i = 0; i < eventos1.size(); i++) {
            assertEquals(eventos1.get(i).getTitulo(), eventos2.get(i).getTitulo());
            assertEquals(eventos1.get(i).getDescripcion(), eventos2.get(i).getDescripcion());
            assertEquals(eventos1.get(i).getFecha(), eventos2.get(i).getFecha());
            assertEquals(eventos1.get(i).getFechaFin(), eventos2.get(i).getFechaFin());
            assertEquals(eventos1.get(i).EsDiaCompleto(), eventos2.get(i).EsDiaCompleto());
        }
    }

    @Test
    public void TestRestuararEventosConAlarmas() throws Exception {
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 29, 1, 0);
        LocalDateTime fechaIni2 = LocalDateTime.of(2021, 7, 29, 2, 0);
        LocalDateTime fechaFin2 = LocalDateTime.of(2021, 7, 29, 3, 0);
        LocalDateTime fechaAlarma = LocalDateTime.of(2021, 7, 28, 0, 0);
        Evento evento1 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaFin, false);
        Evento evento2 = new Evento("Ver videos de Proba", "No quiero ver mas Proba", fechaIni2, fechaFin2, false);

        evento1.agregarAlarmaDefinida(TipoDeAlarma.SONIDO, fechaAlarma);
        evento1.agregarAlarmaMinsAntes(TipoDeAlarma.MAIL, 20);
        evento2.agregarAlarmaDefinida(TipoDeAlarma.SONIDO, fechaAlarma);

        calendario.agregarEvento(evento1);
        calendario.agregarEvento(evento2);
        ArrayList<Evento> eventos1 = calendario.eventosEnFechas(fechaIni, fechaFin2);
        calendario.guardar();


        Calendario calendario2 = new Calendario();
        calendario2.restaurar();
        ArrayList<Evento> eventos2 = calendario2.eventosEnFechas(fechaIni, fechaFin2);

        for (int i = 0; i < eventos1.size(); i++) {
            ArrayList<Alarma> alarmas1 = eventos1.get(i).getAlarmas();
            ArrayList<Alarma> alarmas2 = eventos2.get(i).getAlarmas();
            assertEquals(alarmas1.size(), alarmas2.size());
            for (int j = 0; j < eventos1.get(i).getAlarmas().size(); j++) {
                assertEquals(alarmas1.get(j).getTipo(), alarmas2.get(j).getTipo());
                assertEquals(alarmas1.get(j).getFecha(), alarmas2.get(j).getFecha());
                assertEquals(alarmas1.get(j).getMinsAntes(), alarmas2.get(j).getMinsAntes());
            }
        }
    }

    @Test
    public void TestRestaurarEventosRepetibles() throws Exception {
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2023, 3, 1,2,0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 3, 29,0,0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.LUNES);
        dias.add(DiaSemana.SABADO);
        EventoRepetible eventoR = new EventoRepetible("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, true,Frecuencia.SEMANAL, dias, 1 , 0, fechaFin, false);
        EventoRepetible eventoR2 = new EventoRepetible("ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, true,Frecuencia.DIARIA, null, 2 , 4, fechaFin, false);
        EventoRepetible eventoR3 = new EventoRepetible("ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, true,Frecuencia.MENSUAL, null, 0 , 0, null, true);
        calendario.agregarEventoRepetible(eventoR);
        calendario.agregarEventoRepetible(eventoR2);
        calendario.agregarEventoRepetible(eventoR3);
        calendario.guardar();

        Calendario calendario2 = new Calendario();
        calendario2.restaurar();

        ArrayList<EventoHijo> eventosR1 = calendario.eventosRepetiblesEnFechas(fechaIni, fechaFin);
        ArrayList<EventoHijo> eventosR2 = calendario2.eventosRepetiblesEnFechas(fechaIni, fechaFin);

        assertEquals(eventosR1.size(), eventosR2.size());
        for (int i = 0 ; i < eventosR1.size() ; i++){
            assertEquals(eventosR1.get(i).getTitulo(), eventosR2.get(i).getTitulo());
            assertEquals(eventosR1.get(i).getDescripcion(), eventosR2.get(i).getDescripcion());
            assertEquals(eventosR1.get(i).getFecha(), eventosR2.get(i).getFecha());
            assertEquals(eventosR1.get(i).getFechaFin(), eventosR2.get(i).getFechaFin());
            assertEquals(eventosR1.get(i).EsDiaCompleto(), eventosR2.get(i).EsDiaCompleto());
            assertEquals(eventosR1.get(i).getFrecuencia(), eventosR2.get(i).getFrecuencia());
            assertEquals(eventosR1.get(i).getDiasSemana(), eventosR2.get(i).getDiasSemana());
            assertEquals(eventosR1.get(i).getCantidadRepeticiones(), eventosR2.get(i).getCantidadRepeticiones());
            assertEquals(eventosR1.get(i).getIntervalo(), eventosR2.get(i).getIntervalo());
            assertEquals(eventosR1.get(i).esInfinito(), eventosR2.get(i).esInfinito());
        }
    }

    @Test
    public void TestRestaurarEventosRepetiblesConAlarmas() throws Exception {
        Calendario calendario = new Calendario();
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 29, 1, 0);
        LocalDateTime fechaAlarma = LocalDateTime.of(2021, 7, 28, 0, 0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.LUNES);
        dias.add(DiaSemana.SABADO);
        EventoRepetible eventoR1 = new EventoRepetible("Ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, true,Frecuencia.SEMANAL, dias, 1 , 0, fechaFin, false);
        EventoRepetible eventoR2 = new EventoRepetible("ver videos de Proba", "No quiero ver mas Proba", fechaIni, fechaIni, true,Frecuencia.DIARIA, null, 2 , 4, fechaFin, false);
        calendario.agregarEventoRepetible(eventoR1);
        calendario.agregarEventoRepetible(eventoR2);
        eventoR1.agregarAlarmaDefinida(TipoDeAlarma.SONIDO, fechaAlarma);
        eventoR2.agregarAlarmaDefinida(TipoDeAlarma.SONIDO, fechaAlarma);
        eventoR2.agregarAlarmaMinsAntes(TipoDeAlarma.MAIL, 20);

        calendario.guardar();

        Calendario calendario2 = new Calendario();
        calendario2.restaurar();

        ArrayList<EventoHijo> eventosR1 = calendario.eventosRepetiblesEnFechas(fechaIni, fechaFin);
        ArrayList<EventoHijo> eventosR2 = calendario2.eventosRepetiblesEnFechas(fechaIni, fechaFin);

        for (int i = 0; i < eventosR1.size(); i++) {
            ArrayList<Alarma> alarmas1 = eventosR1.get(i).getAlarmas();
            ArrayList<Alarma> alarmas2 = eventosR2.get(i).getAlarmas();
            assertEquals(alarmas1.size(), alarmas2.size());
            for (int j = 0; j < eventosR2.get(i).getAlarmas().size(); j++) {
                assertEquals(alarmas1.get(j).getTipo(), alarmas2.get(j).getTipo());
                assertEquals(alarmas1.get(j).getFecha(), alarmas2.get(j).getFecha());
                assertEquals(alarmas1.get(j).getMinsAntes(), alarmas2.get(j).getMinsAntes());
            }
        }
    }

}
