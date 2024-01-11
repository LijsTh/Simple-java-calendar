import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventoTest {

    @Test
    public void TestEventoDiaCompleto() {
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 7, 29,8,0);
        LocalDateTime fecha3 = LocalDateTime.of(2021, 7, 29,23,59);
        LocalDateTime fecha4 = LocalDateTime.of(2021,7,30,0,0);

        Evento evento = new Evento("Feriado Nacional", "No hay clase de Proba :)", fecha2, fecha3, true);

        assertTrue("El evento es de todo el dia", evento.estaEnFecha(fecha, fecha2));
        assertTrue("El evento está en ese horario al ser de todo el día", evento.estaEnFecha(fecha2, fecha2));
        assertTrue("El evento está en ese horario al ser de todo el día", evento.estaEnFecha(fecha3, fecha3));
        assertTrue("El evento no esta en ese dia",evento.estaEnFecha(fecha,fecha4));
    }

    @Test
    public void TestEventoMuyLargo() {
        LocalDateTime fecha = LocalDateTime.of(2021, 3, 13,0,0);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 5, 1,0,0);
        LocalDateTime fecha3 = LocalDateTime.of(2021, 5, 31,23,59);
        LocalDateTime fecha4 = LocalDateTime.of(2021,7,30,23,59);

        Evento evento = new Evento("Rendir Proba", "No quiero rendir proba", fecha, fecha4, false);

        assertTrue("El evento esta en Mayo, a pesar de empezar antes y terminar despues", evento.estaEnFecha(fecha2,fecha3));
    }

    @Test
    public void TestEventoConHora(){
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 7, 29,8,0);
        LocalDateTime fecha3 = LocalDateTime.of(2021, 7, 29,23,59);

        Evento evento = new Evento("Feriado Nacional", "No hay clase de Proba :)", fecha, fecha2, false);

        assertTrue("El evento esta en ese horario", evento.estaEnFecha(fecha, fecha2));
        assertFalse("El evento no esta en ese horario", evento.estaEnFecha(fecha3, fecha3));
        assertTrue("El evento esta en ese horario", evento.estaEnFecha(fecha, fecha3));
    }

    @Test
    public void TestEventoConHoraDias() {
        LocalDateTime fechaIni = LocalDateTime.of(2021, 7, 29,22,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021, 7, 30,8,0);
        LocalDateTime fechaAntes = LocalDateTime.of(2021,7,29,21,59);
        LocalDateTime fechaDespues = LocalDateTime.of(2021,7,30,8,1);

        Evento evento = new Evento("Feriado Nacional", "No hay clase de Proba :)", fechaIni, fechaFin, false);

        assertTrue("El evento esta en ese horario", evento.estaEnFecha(fechaIni, fechaFin));
        assertFalse("El evento no esta en ese horario", evento.estaEnFecha(fechaAntes, fechaAntes));
        assertFalse("El evento no esta en ese horario", evento.estaEnFecha(fechaDespues, fechaDespues));
        assertTrue("El evento esta en ese horario", evento.estaEnFecha(fechaIni, fechaDespues));
        assertTrue("El evento esta en ese horario", evento.estaEnFecha(fechaAntes, fechaDespues));

    }

    @Test
    public void TestEventoModificarADiaCompleto() {
        LocalDateTime fechaIni = LocalDateTime.of(2023, 7, 14, 16, 30);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 7, 14, 20, 30);
        Evento evento = new Evento("Feriado Nacional", "No hay clase de Proba :)", fechaIni, fechaFin, false);

        evento.setDiaCompleto(true);

        assertTrue(evento.estaEnFecha(fechaIni, fechaFin));
        assertTrue(evento.estaEnFecha(fechaIni, fechaFin));
        assertTrue(evento.estaEnFecha(fechaIni, fechaFin));
    }

    @Test
    public void TestEventoModificarADiaNoCompleto() {
        LocalDateTime fechaIni = LocalDateTime.of(2023, 7, 14, 16, 30);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 7, 14, 20, 30);
        Evento evento = new Evento("Feriado Nacional", "No hay clase de Proba :)", fechaIni, fechaFin, true);

        evento.setDiaCompleto(false);

        assertTrue(evento.estaEnFecha(fechaIni, fechaFin));
        assertTrue(evento.estaEnFecha(fechaIni, fechaFin));
        assertTrue(evento.estaEnFecha(fechaIni, fechaFin));
    }



}