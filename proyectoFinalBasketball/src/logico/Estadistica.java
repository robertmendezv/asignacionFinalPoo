package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Estadistica implements Serializable {
    private static final long serialVersionUID = 1L;
    private int puntos;
    private int rebotes;
    private int asistencias;
    private int accionesDefensivas;
    private ArrayList<Jugador> jugadores;

    public Estadistica(int puntos, int rebotes, int asistencias, int accionesDefensivas) {
        this.puntos = puntos;
        this.rebotes = rebotes;
        this.asistencias = asistencias;
        this.accionesDefensivas = accionesDefensivas;
        this.jugadores = new ArrayList<>();
    }


    public int getPuntos() {
        return puntos;
    }

    public int getRebotes() {
        return rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public int getAccionesDefensivas() {
        return accionesDefensivas;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public void setAccionesDefensivas(int accionesDefensivas) {
        this.accionesDefensivas = accionesDefensivas;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}