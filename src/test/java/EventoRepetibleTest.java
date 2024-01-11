import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class EventoRepetibleTest {

    @Test
    public void testEventoRepetibleDiaIntervalo() {
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 1, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 1, 12, 30);
        LocalDateTime fechaFinCalen = LocalDateTime.of(2021, 7, 29, 0, 0);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 2, 10, null, false);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaIni, fechaFinCalen);

        assertEquals("Cantidad de eventos repetibles", 10, repetibles.size());
        for (int i = 0; i < repetibles.size(); i++) {
            assertEquals("Fecha inicio", fechaIni.plusDays(i * 2L), repetibles.get(i).getFecha()); //chequeo que esten bien las fechas
            assertEquals("Fecha fin", fechaFin.plusDays(i * 2L), repetibles.get(i).getFechaFin()); // Que este bien la fecha final
        }
    }

    @Test
    public void testEventoRepetibleDiaFechaLim() {
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 1, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 1, 12, 30);
        LocalDateTime fechaLim = LocalDateTime.of(2021, 7, 10, 12, 30);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 1, 0, fechaLim, false);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaIni, fechaLim);

        assertEquals("Cantidad de eventos repetibles", 10, repetibles.size());
        for (int i = 0; i < repetibles.size(); i++) {
            assertEquals("Fecha inicio", fechaIni.plusDays(i), repetibles.get(i).getFecha()); //chequeo que esten bien las fechas
            assertEquals("Fecha fin", fechaFin.plusDays(i), repetibles.get(i).getFechaFin()); // Que este bien la fecha final
        }
    }

    @Test
    public void testEventoRepetibleInfinito() {
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 1, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 1, 12, 30);
        LocalDateTime fechaCalen = LocalDateTime.of(2021, 7, 29, 0, 0);
        LocalDateTime fechaCalen2 = LocalDateTime.of(2021, 8, 29, 0, 0);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 1, 0, null, true);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaIni, fechaCalen);
        ArrayList<EventoHijo> repetibles2 = eventoR.generarRepeticiones(fechaIni, fechaCalen2);

        assertEquals("Cantidad de eventos repetibles", 28, repetibles.size());
        assertEquals("Cantidad de eventos repetibles", 59, repetibles2.size());
        for (int i = 0; i < repetibles2.size(); i++) {
            if (i < repetibles.size()) {
                assertEquals("Fecha inicio", fechaIni.plusDays(i), repetibles.get(i).getFecha()); //chequeo que esten bien las fechas
                assertEquals("Fecha fin", fechaFin.plusDays(i), repetibles.get(i).getFechaFin()); // Que este bien la fecha final
            }
            assertEquals("Fecha inicio", fechaIni.plusDays(i), repetibles2.get(i).getFecha()); //chequeo que esten bien las fechas
            assertEquals("Fecha fin", fechaFin.plusDays(i), repetibles2.get(i).getFechaFin()); // Que este bien la fecha final
        }
    }

    @Test
    public void testEventoModificarRepetibleInfinito() {
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 1, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 1, 12, 30);
        LocalDateTime fechaCalen = LocalDateTime.of(2021, 7, 29, 0, 0);
        LocalDateTime fechaCalen2 = LocalDateTime.of(2021, 8, 29, 0, 0);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 1, 3, null, false);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaIni, fechaCalen);
        eventoR.setInfinito(true);
        ArrayList<EventoHijo> repetibles2 = eventoR.generarRepeticiones(fechaIni, fechaCalen2);

        assertEquals("Cantidad de eventos repetibles", 3, repetibles.size());
        assertEquals("Cantidad de eventos repetibles", 59, repetibles2.size());
        for (int i = 0; i < repetibles2.size(); i++) {
            if (i < repetibles.size()) {
                assertEquals("Fecha inicio", fechaIni.plusDays(i), repetibles.get(i).getFecha()); //chequeo que esten bien las fechas
                assertEquals("Fecha fin", fechaFin.plusDays(i), repetibles.get(i).getFechaFin()); // Que este bien la fecha final
            }
            assertEquals("Fecha inicio", fechaIni.plusDays(i), repetibles2.get(i).getFecha()); //chequeo que esten bien las fechas
            assertEquals("Fecha fin", fechaFin.plusDays(i), repetibles2.get(i).getFechaFin()); // Que este bien la fecha final
        }
    }

    @Test
    public void testEventoRepetibleSemanal() {
        LocalDateTime fechaIni = LocalDateTime.of(2023, 2, 28, 0, 0);
        LocalDateTime fechaCalen = LocalDateTime.of(2023, 2, 28, 1, 0);
        LocalDateTime fechaCalen2 = LocalDateTime.of(2023, 3, 31, 0, 0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.MARTES);
        dias.add(DiaSemana.MIERCOLES);
        dias.add(DiaSemana.VIERNES);
        EventoRepetible eventoR = new EventoRepetible("Idk", "idk",fechaIni,fechaIni, true, Frecuencia.SEMANAL, dias, 1, 30, null, false);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaCalen, fechaCalen2);
        assertEquals("Cantidad de eventos repetibles", 15, repetibles.size());
        assertEquals("0", LocalDateTime.of(2023,2,28,0,0), repetibles.get(0).getFecha());
        assertEquals("1", LocalDateTime.of(2023,3,1,0,0), repetibles.get(1).getFecha());
        assertEquals("2", LocalDateTime.of(2023,3,3,0,0), repetibles.get(2).getFecha());
        assertEquals("3", LocalDateTime.of(2023,3,7,0,0), repetibles.get(3).getFecha());
        assertEquals("4", LocalDateTime.of(2023,3,8,0,0), repetibles.get(4).getFecha());
        assertEquals("5", LocalDateTime.of(2023,3,10,0,0), repetibles.get(5).getFecha());
        assertEquals("6", LocalDateTime.of(2023,3,14,0,0), repetibles.get(6).getFecha());
        assertEquals("7", LocalDateTime.of(2023,3,15,0,0), repetibles.get(7).getFecha());
        assertEquals("8", LocalDateTime.of(2023,3,17,0,0), repetibles.get(8).getFecha());
        assertEquals("9", LocalDateTime.of(2023,3,21,0,0), repetibles.get(9).getFecha());
        assertEquals("10", LocalDateTime.of(2023,3,22,0,0), repetibles.get(10).getFecha());
        assertEquals("11", LocalDateTime.of(2023,3,24,0,0), repetibles.get(11).getFecha());
        assertEquals("12", LocalDateTime.of(2023,3,28,0,0), repetibles.get(12).getFecha());
        assertEquals("13", LocalDateTime.of(2023,3,29,0,0), repetibles.get(13).getFecha());
        assertEquals("14", LocalDateTime.of(2023,3,31,0,0), repetibles.get(14).getFecha());
    }

    @Test
    public void testEventoRepetibleMensual() {
        LocalDateTime fechaIniCalen = LocalDateTime.of(2021, 7, 1, 0, 0);
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 1, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 1, 12, 30);
        LocalDateTime fechaCalen = LocalDateTime.of(2021, 10, 29, 0, 0);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.MENSUAL, null, 1, 0, null, true);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaIniCalen, fechaCalen);

        assertEquals("Cantidad de eventos repetibles", 4, repetibles.size());
        for (int i = 0; i < repetibles.size(); i++) {
            assertEquals("Fecha inicio", fechaIni.plusMonths(i), repetibles.get(i).getFecha()); //chequeo que esten bien las fechas
            assertEquals("Fecha fin", fechaFin.plusMonths(i), repetibles.get(i).getFechaFin()); // Que este bien la fecha final
        }

    }

    @Test
    public void testEventoRepetibleAnual() {
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 1, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 1, 12, 30);
        LocalDateTime fechaCalen = LocalDateTime.of(2024, 7, 29, 0, 0);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.ANUAL, null, 1, 0, null, true);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaIni, fechaCalen);

        assertEquals("Cantidad de eventos repetibles", 4, repetibles.size());

        for (int i = 0; i < repetibles.size(); i++) {
            assertEquals("Fecha inicio", fechaIni.plusYears(i), repetibles.get(i).getFecha()); //chequeo que esten bien las fechas
            assertEquals("Fecha fin", fechaFin.plusYears(i), repetibles.get(i).getFechaFin()); // Que este bien la fecha final
        }
    }

    @Test
    public void testEventoRepetibleCambiarDiaCompleto() {
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 1, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 1, 12, 30);
        LocalDateTime fechaLim = LocalDateTime.of(2021, 7, 10, 12, 30);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 1, 0, fechaLim, false);

        eventoR.setDiaCompleto(true);
        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaIni, fechaLim);

        LocalDateTime fechaAuxIni = LocalDateTime.of(2021, 7, 1, 0, 0);
        LocalDateTime fechaAuxFin = LocalDateTime.of(2021, 7, 1, 23, 59);
        for (Evento repetible : repetibles) {
            assertTrue(repetible.esDiaCompleto());
            assertTrue(repetible.estaEnFecha(fechaAuxIni, fechaAuxFin));
            fechaAuxIni = fechaAuxIni.plusDays(1);
            fechaAuxFin = fechaAuxFin.plusDays(1);
        }
    }

    @Test
    public void testEventoRepetibleCasoBordeSemanal() {
        LocalDateTime fechaIni = LocalDateTime.of(2023, 3, 1, 0, 0);
        LocalDateTime fechaCalen = LocalDateTime.of(2023, 2, 28, 1, 0);
        LocalDateTime fechaCalen2 = LocalDateTime.of(2023, 3, 31, 0, 0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.MARTES);
        dias.add(DiaSemana.MIERCOLES);
        dias.add(DiaSemana.VIERNES);
        EventoRepetible eventoR = new EventoRepetible("proba time", "pain",fechaIni,fechaIni, true, Frecuencia.SEMANAL, dias, 0, 4, null, false);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaCalen, fechaCalen2);
        assertEquals("Cantidad de eventos repetibles", 4, repetibles.size());
        assertEquals("0", LocalDateTime.of(2023,3,1,0,0), repetibles.get(0).getFecha());
        assertEquals("1", LocalDateTime.of(2023,3,3,0,0), repetibles.get(1).getFecha());
        assertEquals("2", LocalDateTime.of(2023,3,7,0,0), repetibles.get(2).getFecha());
        assertEquals("3", LocalDateTime.of(2023,3,8,0,0), repetibles.get(3).getFecha());
    }


    @Test
    public void testEventoRepetibleModificarCantRepes() {
        LocalDateTime fechaIni = LocalDateTime.of(2023, 2, 28, 0, 0);
        LocalDateTime fechaCalen = LocalDateTime.of(2023, 2, 28, 1, 0);
        LocalDateTime fechaCalen2 = LocalDateTime.of(2023, 3, 31, 0, 0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.MARTES);
        dias.add(DiaSemana.MIERCOLES);
        dias.add(DiaSemana.VIERNES);
        // 28 1 3 7
        EventoRepetible eventoR = new EventoRepetible("proba time", "pain",fechaIni,fechaIni, true, Frecuencia.SEMANAL, dias, 0, 4, null, false);

        ArrayList<EventoHijo> repetibles = eventoR.generarRepeticiones(fechaCalen, fechaCalen2);
        assertEquals("Cantidad de eventos repetibles", 4, repetibles.size());
        assertEquals("0", LocalDateTime.of(2023,2,28,0,0), repetibles.get(0).getFecha());
        assertEquals("1", LocalDateTime.of(2023,3,1,0,0), repetibles.get(1).getFecha());
        assertEquals("2", LocalDateTime.of(2023,3,3,0,0), repetibles.get(2).getFecha());
        assertEquals("3", LocalDateTime.of(2023,3,7,0,0), repetibles.get(3).getFecha());


        eventoR.setCantidadRepeticiones(2);
        ArrayList<EventoHijo> repetibles2 = eventoR.generarRepeticiones(fechaCalen, fechaCalen2);
        assertEquals("Cantidad de eventos repetibles", 2, repetibles2.size());
        assertEquals("0", LocalDateTime.of(2023,2,28,0,0), repetibles2.get(0).getFecha());
        assertEquals("1", LocalDateTime.of(2023,3,1,0,0), repetibles2.get(1).getFecha());
    }

    @Test
    public void testEventoRepetibleModificarFrecuencia() {
        LocalDateTime fechaIni = LocalDateTime.of(2023, 2, 28, 0, 0);
        LocalDateTime fechaCalen = LocalDateTime.of(2023, 2, 28, 1, 0);
        LocalDateTime fechaCalen2 = LocalDateTime.of(2023, 3, 31, 0, 0);
        SortedSet<DiaSemana> dias = new TreeSet<>();
        dias.add(DiaSemana.MARTES);
        dias.add(DiaSemana.MIERCOLES);
        dias.add(DiaSemana.VIERNES);
        EventoRepetible eventoR = new EventoRepetible("proba time", "pain",fechaIni,fechaIni, true, Frecuencia.SEMANAL, dias, 0, 4, null, false);

        eventoR.setFrecuencia(Frecuencia.DIARIA, 2, null);
        ArrayList<EventoHijo> repetibles2 = eventoR.generarRepeticiones(fechaCalen, fechaCalen2);

        assertEquals("Cantidad de eventos repetibles", 4, repetibles2.size());
        assertEquals("0", LocalDateTime.of(2023,2,28,0,0), repetibles2.get(0).getFecha());
        assertEquals("1", LocalDateTime.of(2023,3,2,0,0), repetibles2.get(1).getFecha());
        assertEquals("2", LocalDateTime.of(2023,3,4,0,0), repetibles2.get(2).getFecha());
        assertEquals("3", LocalDateTime.of(2023,3,6,0,0), repetibles2.get(3).getFecha());

    }
}