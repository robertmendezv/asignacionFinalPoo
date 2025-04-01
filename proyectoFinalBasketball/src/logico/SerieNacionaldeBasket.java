package logico;

import java.util.ArrayList;

public class SerieNacionaldeBasket {
    
    private static SerieNacionaldeBasket instance = null;
    
    
    private ArrayList<Equipo> equipos;
    private ArrayList<Partido> partidos;
    private ArrayList<Lesion> lesiones;

    public ArrayList<Equipo> getEquipos() {
        return new ArrayList<Equipo>(equipos);
    }

    public ArrayList<Partido> getPartidos() {
        return new ArrayList<Partido>(partidos);
    }

    public ArrayList<Lesion> getLesiones() {
        return new ArrayList<Lesion>(lesiones);
    }
    
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


    public boolean registrarEquipo(Equipo equipo) {
        if(equipo != null && !existeEquipo(equipo.getNombreEquipo())) {
            return equipos.add(equipo);
        }
        return false;
    }

    public boolean eliminarEquipo(String nombreEquipo) {
        Equipo equipo = buscarEquipoPorNombre(nombreEquipo);
        return equipo != null && equipos.remove(equipo);
    }

    public Equipo buscarEquipoPorNombre(String nombreBuscado) {
        for (Equipo equipo : equipos) {
            String nombreEquipo = equipo.getNombreEquipo();
            if (nombreEquipo.equalsIgnoreCase(nombreBuscado)) {
                return equipo;
            }
        }
        return null;
    }

    private boolean existeEquipo(String nombreEquipo) {
        return buscarEquipoPorNombre(nombreEquipo) != null;
    }

    public boolean agregarJugadorAEquipo(Jugador jugador, String nombreEquipo) {
        if(jugador != null && nombreEquipo != null) {
            Equipo equipo = buscarEquipoPorNombre(nombreEquipo);
            if(equipo != null && equipo.getMisjugadores() != null) {
                return equipo.getMisjugadores().add(jugador);
            }
        }
        return false;
    }


    public boolean programarPartido(Partido partido) {
        if(partido != null) {
            return partidos.add(partido);
        }
        return false;
    }

    public boolean registrarResultadoPartido(String equipoLocal, String equipoVisitante, 
                                           int puntosLocal, int puntosVisitante) {
        Partido partido = buscarPartido(equipoLocal, equipoVisitante);
        if(partido != null) {
            partido.setPuntosLocal(puntosLocal);
            partido.setPuntosVisitante(puntosVisitante);
            partido.setResultado(puntosLocal + "-" + puntosVisitante);
            return true;
        }
        return false;
    }

    private Partido buscarPartido(String equipoLocal, String equipoVisitante) {
        for (Partido partido : partidos) {
            String local = partido.getEquipoLocal().getNombreEquipo();
            String visitante = partido.getEquipoVisitante().getNombreEquipo();
            
            if (local.equalsIgnoreCase(equipoLocal) && 
                visitante.equalsIgnoreCase(equipoVisitante)) {
                return partido;
            }
        }
        return null;
    }

    public float calcularPromedioPuntos(Jugador jugador) {
        int partidosJugados = contarPartidosJugados(jugador);
        return partidosJugados > 0 ? jugador.getEstadistica().getPuntos() / (float)partidosJugados : 0;
    }

    public float calcularPromedioRebotes(Jugador jugador) {
        int partidosJugados = contarPartidosJugados(jugador);
        return partidosJugados > 0 ? jugador.getEstadistica().getRebotes() / (float)partidosJugados : 0;
    }

    public float calcularPromedioAsistencias(Jugador jugador) {
        int partidosJugados = contarPartidosJugados(jugador);
        return partidosJugados > 0 ? jugador.getEstadistica().getAsistencias() / (float)partidosJugados : 0;
    }
    
    public float calcularPromedioAccionesDefensivas(Jugador jugador) {
        int partidosJugados = contarPartidosJugados(jugador);
        return partidosJugados > 0 ? jugador.getEstadistica().getAccionesDefensivas() / (float)partidosJugados : 0;
    }

    private int contarPartidosJugados(Jugador jugador) {
        int contador = 0;
        for(Partido p : partidos) {
            if(p.getEquipoLocal().getMisjugadores().contains(jugador) || 
               p.getEquipoVisitante().getMisjugadores().contains(jugador)) {
                contador++;
            }
        }
        return contador;
    }

    public Jugador MVP() {
        Jugador mvp = null;
        float maxPuntaje = -1;

        for(Equipo equipo : this.equipos) { 
            if(equipo != null) {
                ArrayList<Jugador> jugadores = equipo.getMisjugadores();
                if(jugadores != null) {
                    for(Jugador jugador : jugadores) {
                        if(jugador != null && jugador.getEstadistica() != null) {
                            float puntajeTotal = calcularPromedioPuntos(jugador) 
                                              + calcularPromedioRebotes(jugador)
                                              + calcularPromedioAsistencias(jugador)
                                              + calcularPromedioAccionesDefensivas(jugador);
                            
                            if(puntajeTotal > maxPuntaje) {
                                maxPuntaje = puntajeTotal;
                                mvp = jugador;
                            }
                        }
                    }
                }
            }
        }
        return mvp;
    }


    
    
}