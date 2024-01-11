import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void TestTareaDiaCompleto(){
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 29,0,0);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 7, 29,8,0);
        LocalDateTime fecha3 = LocalDateTime.of(2021, 7, 29,23,59);
        LocalDateTime fecha4 = LocalDateTime.of(2021,7,30,0,0);

        Tarea tarea = new Tarea("Ver videos de Proba", "No quiero ver mas Proba", fecha, true);

        assertTrue("La tarea esta en ese dia",tarea.estaEnFecha(fecha,fecha));
        assertTrue("La tarea esta en ese dia",tarea.estaEnFecha(fecha2,fecha2));
        assertTrue("La tarea esta en ese dia",tarea.estaEnFecha(fecha,fecha3));
        assertFalse("La tarea no esta en ese dia",tarea.estaEnFecha(fecha4,fecha4));
        assertTrue("La tarea esta en esos dias",tarea.estaEnFecha(fecha,fecha4));
    }

    @Test
    public void TestTareaConHora(){
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 29,12,30);
        LocalDateTime fecha2 = LocalDateTime.of(2021, 7, 29,8,0);
        LocalDateTime fecha3 = LocalDateTime.of(2021, 7, 29,23,59);
        LocalDateTime fecha4 = LocalDateTime.of(2021,7,30,0,0);

        Tarea tarea = new Tarea("Ver videos de Proba", "No quiero ver mas Proba", fecha, false);

        assertTrue("La tarea esta en ese dia",tarea.estaEnFecha(fecha,fecha));
        assertFalse("La tarea no esta en ese dia a esa hora",tarea.estaEnFecha(fecha2,fecha2));
        assertTrue("La tarea esta en esos dias",tarea.estaEnFecha(fecha,fecha3));
        assertTrue("La tarea esta en ese dia",tarea.estaEnFecha(fecha2,fecha4));
        assertFalse("La tarea no esta en esos dias",tarea.estaEnFecha(fecha3,fecha4));
    }

    @Test
    public void TestTareaModificarFecha() {
        LocalDateTime fecha1 = LocalDateTime.of(2023, 5, 2,16,30);
        LocalDateTime fecha2 = LocalDateTime.of(2023, 7, 14,16,30);
        Tarea tarea = new Tarea("Hacer Fisica II", "Ayuda no entiendo nada", fecha1, false);

        tarea.setFechaLimite(fecha2);

        assertFalse(tarea.estaEnFecha(fecha1, fecha1));
        assertTrue(tarea.estaEnFecha(fecha2, fecha2));
    }

    @Test
    public void TestTareaModificarADiaCompleto(){
        LocalDateTime fecha = LocalDateTime.of(2023, 7, 14,16,30);
        LocalDateTime fecha2 = LocalDateTime.of(2023, 7, 14,20,30);
        Tarea tarea = new Tarea("Hacer Fisica II", "Ayuda no entiendo nada", fecha, false);

        tarea.setDiaCompleto(true);

        assertTrue(tarea.estaEnFecha(fecha, fecha));
        assertTrue(tarea.estaEnFecha(fecha2, fecha2));
        assertTrue(tarea.estaEnFecha(fecha, fecha2));
    }

    @Test
    public void TestTareaModificarNoDiaCompleto() {
        LocalDateTime fecha = LocalDateTime.of(2023, 7, 14,0,0);
        Tarea tarea = new Tarea("Hacer Fisica II", "Ayuda no entiendo nada", fecha, true);

        tarea.setDiaCompleto(false);

        assertFalse(tarea.esDiaCompleto());
    }

    @Test
    public void TestTareaActualizar() {
        LocalDateTime fecha = LocalDateTime.of(2023, 7, 14,0,0);
        Tarea tarea = new Tarea("Hacer Fisica II", "Ayuda no entiendo nada", fecha, true);

        //simular que se puede marcar y desmarcar como tarea completada
        tarea.actualizar();
        assertTrue(tarea.estaCompletada());
        tarea.actualizar();
        assertFalse(tarea.estaCompletada());
    }
}