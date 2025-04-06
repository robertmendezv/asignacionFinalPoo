package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
public class Partido implements Serializable {
    private static final long serialVersionUID = 1L;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private int puntosLocal;
    private int puntosVisitante;
    private String resultado;
    public Partido(Equipo equipoLocal, Equipo equipoVisitante, LocalDate fecha, LocalTime hora) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = "Programado"; 
        this.puntosLocal = 0;
        this.puntosVisitante = 0;
        this.resultado = "0-0";
    }
    
    public Equipo getEquipoLocal() {
        return equipoLocal;
    }
    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getPuntosLocal() {
        return puntosLocal;
    }
    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }
    public int getPuntosVisitante() {
        return puntosVisitante;
    }
    public void setPuntosVisitante(int puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }
    public String getResultado() {
        return resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public void iniciarPartido() {
        this.estado = "En juego";
    }
    public void finalizarPartido(int puntosLocal, int puntosVisitante) {
        this.estado = "Finalizado";
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.resultado = puntosLocal + "-" + puntosVisitante;
    }
    public void cancelarPartido() {
        this.estado = "Cancelado";
    }
    public boolean participaJugador(Jugador jugador) {
        return equipoLocal.getMisjugadores().contains(jugador) || 
               equipoVisitante.getMisjugadores().contains(jugador);
    }
}