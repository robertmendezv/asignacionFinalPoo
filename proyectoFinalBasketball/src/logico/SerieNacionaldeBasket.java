package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class SerieNacionaldeBasket {
    
    private static SerieNacionaldeBasket instance = null;
    private ArrayList<Equipo> equipos;
    private ArrayList<Partido> partidos;
    private ArrayList<Lesion> lesiones;

    public SerieNacionaldeBasket() {
        this.equipos = new ArrayList<Equipo>();
        this.partidos = new ArrayList<Partido>();
        this.lesiones = new ArrayList<Lesion>();
    }
    
    

    public static SerieNacionaldeBasket getInstance() {
        if (instance == null) {
            instance = new SerieNacionaldeBasket();
        }
        return instance;
    }

    public ArrayList<Equipo> getEquipos() {
        return new ArrayList<Equipo>(equipos);
    }

    public ArrayList<Partido> getPartidos() {
        return new ArrayList<Partido>(partidos);
    }

    public ArrayList<Lesion> getLesiones() {
        return new ArrayList<Lesion>(lesiones);
    }

    public boolean registrarEquipo(Equipo equipo) {
        if(equipo != null && !existeEquipo(equipo.getNombreEquipo())) {
            return equipos.add(equipo);
        }
        return false;
    }
    
    public void agregarEquipo(Equipo equipo) {
        if (equipo != null && !equipos.contains(equipo)) {
            equipos.add(equipo);
        }
    }

    public boolean eliminarEquipo(String nombreEquipo) {
        Equipo equipo = buscarEquipoPorNombre(nombreEquipo);
        return equipo != null && equipos.remove(equipo);
    }

    public Equipo buscarEquipoPorNombre(String nombreBuscado) {
        for (Equipo equipo : equipos) {
            if (equipo.getNombreEquipo().equalsIgnoreCase(nombreBuscado)) {
                return equipo;
            }
        }
        return null;
    }

    private boolean existeEquipo(String nombreEquipo) {
        return buscarEquipoPorNombre(nombreEquipo) != null;
    }

    public boolean agregarJugadorAEquipo(Jugador jugador, String nombreEquipo) {
        Equipo equipo = buscarEquipoPorNombre(nombreEquipo);
        if(equipo != null && !equipo.getMisjugadores().contains(jugador)) {
            return equipo.getMisjugadores().add(jugador);
        }
        return false;
    }

    public ArrayList<Partido> getPartidosPorFecha(LocalDate fecha) {
        ArrayList<Partido> partidosFecha = new ArrayList<>();
        if(fecha == null) return partidosFecha; 

        for(Partido p : partidos) {
            if(fecha.equals(p.getFecha())) { 
                partidosFecha.add(p);
            }
        }
        return partidosFecha;
    }
    
    public boolean registrarResultado(String nombreEquipoLocal, String nombreEquipoVisitante, 
            int puntosLocal, int puntosVisitante) {
    	Partido partido = buscarPartido(nombreEquipoLocal, nombreEquipoVisitante);
    	if (partido != null) {
    		partido.setPuntosLocal(puntosLocal);
    		partido.setPuntosVisitante(puntosVisitante);
    		partido.setResultado(puntosLocal + "-" + puntosVisitante);
    		partido.setEstado("Finalizado");
    		return true;
    	}
    	return false;
    }
    
    public boolean programarPartido(Partido partido) {
        if(partido != null && !partidos.contains(partido)) {
            return partidos.add(partido);
        }
        return false;
    }

    public boolean eliminarPartido(Partido partido) {
        return partidos.remove(partido);
    }


    public Partido buscarPartido(String equipoLocal, String equipoVisitante) {
        for (Partido partido : partidos) {
            if (partido.getEquipoLocal().getNombreEquipo().equalsIgnoreCase(equipoLocal) && 
                partido.getEquipoVisitante().getNombreEquipo().equalsIgnoreCase(equipoVisitante)) {
                return partido;
            }
        }
        return null;
    }
    
    public ArrayList<Partido> getPartidosPorEquipo(String nombreEquipo) {
        ArrayList<Partido> partidosEquipo = new ArrayList<>();
        for(Partido p : partidos) {
            if(p.getEquipoLocal().getNombreEquipo().equalsIgnoreCase(nombreEquipo) || 
               p.getEquipoVisitante().getNombreEquipo().equalsIgnoreCase(nombreEquipo)) {
                partidosEquipo.add(p);
            }
        }
        return partidosEquipo;
    }
    
    private boolean existePartido(Partido partido) {
        for(Partido p : partidos) {
            if(p.getEquipoLocal().equals(partido.getEquipoLocal()) && 
               p.getEquipoVisitante().equals(partido.getEquipoVisitante()) &&
               p.getFecha().equals(partido.getFecha())) {
                return true;
            }
        }
        return false;
    }
    
    public void agregarPartido(Partido partido) {
        if (partido != null) {
            
            if (equipos.contains(partido.getEquipoLocal()) && 
                equipos.contains(partido.getEquipoVisitante())) {
                if (!partidos.contains(partido)) {
                    partidos.add(partido);
                } else {
                    throw new IllegalArgumentException("El partido ya está registrado");
                }
            } else {
                throw new IllegalArgumentException("Uno o ambos equipos no están registrados");
            }
        }
    }
    
    
  
    private int contarPartidosJugados(Jugador jugador) {
        int contador = 0;
        for(Partido p : partidos) {
            if(p.participaJugador(jugador)) {
                contador++;
            }
        }
        return contador;
    }

    public boolean registrarLesion(Lesion lesion) {
        return lesion != null && lesiones.add(lesion);
    }
}