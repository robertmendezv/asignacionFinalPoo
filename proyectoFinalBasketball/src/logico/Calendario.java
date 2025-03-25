package logico;

import java.util.ArrayList;
import java.util.Date;

public class Calendario {
	
	private Date fechaInicio;
	private Date fechaFin;
	private ArrayList<Partido> partidos;
	
	public Calendario(Date fechaInicio, Date fechaFin, ArrayList<Partido> partidos) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.partidos = new ArrayList<Partido>();
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public ArrayList<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(ArrayList<Partido> partidos) {
		this.partidos = partidos;
	}

}
