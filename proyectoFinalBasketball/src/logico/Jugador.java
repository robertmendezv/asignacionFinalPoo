package logico;

public class Jugador {
	private String nombreJugador;
	private int edad;
	private String posicionJugador;
	private int numJugador;
	private Estadistica estadistica;
	public Jugador(String nombreJugador, int edad, String posicionJugador, int numJugador, Estadistica estadisticas) {
		super();
		this.nombreJugador = nombreJugador;
		this.edad = edad;
		this.posicionJugador = posicionJugador;
		this.numJugador = numJugador;
		this.estadistica = estadistica;
	}
	public String getNombreJugador() {
		return nombreJugador;
	}
	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getPosicionJugador() {
		return posicionJugador;
	}
	public void setPosicionJugador(String posicionJugador) {
		this.posicionJugador = posicionJugador;
	}
	public int getNumJugador() {
		return numJugador;
	}
	public void setNumJugador(int numJugador) {
		this.numJugador = numJugador;
	}
	public Estadistica getEstadistica() {
		return estadistica;
	}
	public void setEstadistica(Estadistica estadistica) {
		this.estadistica = estadistica;
	}

	
	
	
	
	
}
