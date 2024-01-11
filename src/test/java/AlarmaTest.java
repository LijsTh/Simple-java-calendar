import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AlarmaTest {

    @Test
    public void alarmaEnTarea() {
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 1, 10, 0);
        Tarea t = new Tarea("Final Proba", "No estudie nada", fecha, false);

        t.agregarAlarmaMinsAntes(TipoDeAlarma.SONIDO,10);

        assertEquals(1, t.getAlarmas().size());
        assertEquals(TipoDeAlarma.SONIDO, t.getAlarmas().get(0).getTipo());
        assertEquals(10,t.getAlarmas().get(0).getMinsAntes());
        assertEquals(LocalDateTime.of(2021, 7, 1, 9, 50), t.getAlarmas().get(0).getFecha());
    }

    @Test
    public void alarmaEnEvento(){
        LocalDateTime fechaIni = LocalDateTime.of(2021,7,1,10,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021,7,1,12,30);
        LocalDateTime fechaFinCalen = LocalDateTime.of(2021,7,29,0,0);

        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 2, 8, null, false);
        eventoR.agregarAlarmaMinsAntes(TipoDeAlarma.SONIDO,10);
        ArrayList<EventoHijo> repeticiones = eventoR.generarRepeticiones(fechaIni, fechaFinCalen);
        for (int i = 0 ; i < repeticiones.size() ; i++){
            assertEquals(1, repeticiones.get(i).getAlarmas().size());
            assertEquals(TipoDeAlarma.SONIDO, repeticiones.get(i).getAlarmas().get(0).getTipo());
            assertEquals(10,repeticiones.get(i).getAlarmas().get(0).getMinsAntes());
            assertEquals(LocalDateTime.of(2021, 7, 1+i*2, 9, 50), repeticiones.get(i).getAlarmas().get(0).getFecha());
        }
    }

    @Test
    public void modificarAlarmaEvento(){
        LocalDateTime fechaAlarma = LocalDateTime.of(2021,7,1,2,0);
        LocalDateTime fechaIni = LocalDateTime.of(2021,7,1,10,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021,7,1,12,30);
        Evento evento = new Evento("final", "no estudie nada", fechaIni, fechaFin, false);

        evento.agregarAlarmaDefinida(TipoDeAlarma.SONIDO,fechaAlarma);
        evento.modificarAlarma(evento.getAlarmas().get(0), TipoDeAlarma.MUSICA , 50);

        assertEquals(1, evento.getAlarmas().size());
        assertEquals(TipoDeAlarma.MUSICA, evento.getAlarmas().get(0).getTipo());
        assertEquals(50,evento.getAlarmas().get(0).getMinsAntes());
        assertEquals(LocalDateTime.of(2021, 7, 1, 1, 10), evento.getAlarmas().get(0).getFecha());

    }

    @Test
    public void modificarAlarmaEventoR(){
        LocalDateTime fechaIni = LocalDateTime.of(2021,7,1,10,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021,7,1,12,30);
        LocalDateTime fechaFinCalen = LocalDateTime.of(2021,7,29,0,0);

        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 2, 8, null, false);
        eventoR.agregarAlarmaMinsAntes(TipoDeAlarma.MAIL, 10);

        eventoR.modificarAlarma(eventoR.getAlarmas().get(0), TipoDeAlarma.SONIDO, 30);

        ArrayList<EventoHijo> repeticiones = eventoR.generarRepeticiones(fechaIni, fechaFinCalen);
        for (int i = 0 ; i < repeticiones.size() ; i++){
            assertEquals(1, repeticiones.get(i).getAlarmas().size());
            assertEquals(TipoDeAlarma.SONIDO, repeticiones.get(i).getAlarmas().get(0).getTipo());
            assertEquals(30,repeticiones.get(i).getAlarmas().get(0).getMinsAntes());
            assertEquals(LocalDateTime.of(2021, 7, 1+i*2, 9, 30), repeticiones.get(i).getAlarmas().get(0).getFecha());
        }
    }

    @Test
    public void modificarAlarmaTarea() {
        LocalDateTime fecha = LocalDateTime.of(2021, 7, 1, 10, 0);
        Tarea t = new Tarea("Final Proba", "No estudie nada", fecha, false);

        t.agregarAlarmaMinsAntes(TipoDeAlarma.SONIDO,90);
        t.modificarAlarma(t.getAlarmas().get(0), TipoDeAlarma.SONIDO, 120);

        assertEquals(1, t.getAlarmas().size());
        assertEquals(TipoDeAlarma.SONIDO, t.getAlarmas().get(0).getTipo());
        assertEquals(120,t.getAlarmas().get(0).getMinsAntes());
        assertEquals(LocalDateTime.of(2021, 7, 1, 8, 0), t.getAlarmas().get(0).getFecha());
    }


    @Test
    public void eliminarAlarma() {
        LocalDateTime fechaIni = LocalDateTime.of(2021,7,1,10,0);
        LocalDateTime fechaFin = LocalDateTime.of(2021,7,1,12,30);
        LocalDateTime fechaTarea = LocalDateTime.of(2021, 7, 1, 15, 0);
        EventoRepetible eventoR = new EventoRepetible("Final Proba", "No estudie nada", fechaIni, fechaFin, false, Frecuencia.DIARIA, null, 2, 8, null, false);
        Tarea t = new Tarea("Final Proba", "No estudie nada", fechaTarea, false);

        t.agregarAlarmaMinsAntes(TipoDeAlarma.SONIDO,10);
        eventoR.agregarAlarmaMinsAntes(TipoDeAlarma.SONIDO,10);
        t.eliminarAlarma(t.getAlarmas().get(0));
        eventoR.eliminarAlarma(eventoR.getAlarmas().get(0));


        assertEquals(0, t.getAlarmas().size());
        assertEquals(0, eventoR.getAlarmas().size());
    }
}