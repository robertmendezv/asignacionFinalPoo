package logico;

import java.io.Serializable;
import java.util.Date;

public class Lesion implements Serializable {
    private static final long serialVersionUID = 1L;
	private String tipoLesion;
	private int duracion;
	private Jugador jugadorLesionado;
	private Date fechaInicio;
	
	public Lesion(String tipoLesion, int duracion, Jugador jugadorLesionado) {
		super();
		this.tipoLesion = tipoLesion;
		this.duracion = duracion;
		this.jugadorLesionado = jugadorLesionado;
		this.fechaInicio = new Date();
	}
	public String getTipoLesion() {
		return tipoLesion;
	}
	public void setTipoLesion(String tipoLesion) {
		this.tipoLesion = tipoLesion;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public Jugador getJugadorLesionado() {
		return jugadorLesionado;
	}
	public void setJugadorLesionado(Jugador jugadorLesionado) {
		this.jugadorLesionado = jugadorLesionado;
	}
	
	public Date getFechaInicio() {
        return fechaInicio;
    }
	
}
