package logico;

import java.util.ArrayList; 

public class Estadisticas {
    private int puntos;
    private int rebotes;
    private int asistencias;
    private int accionesdefensivas;
	private ArrayList<Jugador>misjugadores;
	private float prompuntos;
	private float promrebotes;
	private float promasistencias;
	private float promaccionesdefensivas;
	
	public Estadisticas(int puntos, int rebotes, int asistencias, int accionesdefensivas,
			ArrayList<Jugador> misjugadores, float prompuntos, float promrebotes, float promasistencias,
			float promaccionesdefensivas) {
		super();
		this.puntos = puntos;
		this.rebotes = rebotes;
		this.asistencias = asistencias;
		this.accionesdefensivas = accionesdefensivas;
		this.misjugadores = misjugadores;
		this.prompuntos = prompuntos;
		this.promrebotes = promrebotes;
		this.promasistencias = promasistencias;
		this.promaccionesdefensivas = promaccionesdefensivas;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getRebotes() {
		return rebotes;
	}

	public void setRebotes(int rebotes) {
		this.rebotes = rebotes;
	}

	public int getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}

	public int getAccionesdefensivas() {
		return accionesdefensivas;
	}

	public void setAccionesdefensivas(int accionesdefensivas) {
		this.accionesdefensivas = accionesdefensivas;
	}

	public ArrayList<Jugador> getMisjugadores() {
		return misjugadores;
	}

	public void setMisjugadores(ArrayList<Jugador> misjugadores) {
		this.misjugadores = misjugadores;
	}

	public void setPrompuntos(float prompuntos) {
		this.prompuntos = prompuntos;
	}

	public void setPromrebotes(float promrebotes) {
		this.promrebotes = promrebotes;
	}

	public void setPromasistencias(float promasistencias) {
		this.promasistencias = promasistencias;
	}

	public void setPromaccionesdefensivas(float promaccionesdefensivas) {
		this.promaccionesdefensivas = promaccionesdefensivas;
	}

}
