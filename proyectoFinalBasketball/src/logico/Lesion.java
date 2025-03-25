package logico;

public class Lesion {
	private String tipoLesion;
	private int duracion;
	private Jugador jugadorLesionado;
	public Lesion(String tipoLesion, int duracion, Jugador jugadorLesionado) {
		super();
		this.tipoLesion = tipoLesion;
		this.duracion = duracion;
		this.jugadorLesionado = jugadorLesionado;
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
	
	
}
