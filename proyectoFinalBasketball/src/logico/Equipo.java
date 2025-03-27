package logico;

import java.util.ArrayList;

public class Equipo {
    private String nombreEquipo;
    private String nombreEntrenador;
    private ArrayList<Jugador> misjugadores;
    
    public Equipo(String nombreEquipo, String nombreEntrenador) {
        this.nombreEquipo = nombreEquipo;
        this.nombreEntrenador = nombreEntrenador;
        this.misjugadores = new ArrayList<Jugador>();
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    public void setNombreEntrenador(String nombreEntrenador) {
        this.nombreEntrenador = nombreEntrenador;
    }

    public ArrayList<Jugador> getMisjugadores() {
        return misjugadores;
    }

    public void setMisjugadores(ArrayList<Jugador> misjugadores) {
        this.misjugadores = misjugadores;
    }
}