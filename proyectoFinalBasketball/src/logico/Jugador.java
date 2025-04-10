package logico;

import java.io.Serializable;

public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
	private String nombreJugador;
	private int edad;
	private String posicionJugador;
	private int numJugador;
	private Estadistica estadistica;
	private int estatura;
	private String sangre;
	private float peso;
	private String fechanacimiento;
	private String marcapromotora;
	public Jugador(String nombreJugador, int edad, String posicionJugador, int numJugador, Estadistica estadistica,
			int estatura, String sangre, float peso, String fechanacimiento, String marcapromotora) {
		super();
		this.nombreJugador = nombreJugador;
		this.edad = edad;
		this.posicionJugador = posicionJugador;
		this.numJugador = numJugador;
		this.estadistica = estadistica;
		this.estatura = estatura;
		this.sangre = sangre;
		this.peso = peso;
		this.fechanacimiento = fechanacimiento;
		this.marcapromotora = marcapromotora;
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
	public int getEstatura() {
		return estatura;
	}
	public void setEstatura(int estatura) {
		this.estatura = estatura;
	}
	public String getSangre() {
		return sangre;
	}
	public void setSangre(String sangre) {
		this.sangre = sangre;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public String getFechanacimiento() {
		return fechanacimiento;
	}
	public void setFechanacimiento(String fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}
	public String getMarcapromotora() {
		return marcapromotora;
	}
	public void setMarcapromotora(String marcapromotora) {
		this.marcapromotora = marcapromotora;
	}
	
	public String toString() {
		return nombreJugador;
		
		
	}
	
}
