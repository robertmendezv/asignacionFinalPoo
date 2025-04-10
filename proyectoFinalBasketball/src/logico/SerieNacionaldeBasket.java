package logico;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class SerieNacionaldeBasket implements Serializable{

	private static SerieNacionaldeBasket instance = null;


	private ArrayList<Equipo> equipos;
	private ArrayList<Partido> partidos;
	private ArrayList<Lesion> lesiones;


	private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_DATOS = "SerieNacionalBasketball.dat";
	private static SerieNacionaldeBasket serie;
	private ArrayList<User> misUsers;
	private ArrayList<Jugador> misJugadores;
	private static User loginUser;
	




	public SerieNacionaldeBasket() {
		this.equipos = new ArrayList<Equipo>();
		this.partidos = new ArrayList<Partido>();
		this.lesiones = new ArrayList<Lesion>();
		this.misJugadores = new ArrayList<Jugador>();
		this.misUsers = new ArrayList<User>();
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

	
	public boolean agregarPartido(Partido partido) {
	    if (!partidos.contains(partido)) {
	        partidos.add(partido);
	        return true;
	    }
	    return false; 
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



	public void setMisUsers(ArrayList<User> misUsers) {
		this.misUsers = misUsers;
	}


	public static User getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(User loginUser) {
		SerieNacionaldeBasket.loginUser = loginUser;
	}


	public boolean confirmLogin(String username, String password) {
	    if(misUsers == null) { // Protección adicional
	        misUsers = new ArrayList<>();
	        return false;
	    }
	    
	    for (User user : misUsers) {
	        if(user.getUserName().equals(username) && user.getPassw().equals(password)){
	            loginUser = user;
	            return true;
	        }
	    }
	    return false;
	}

	public static SerieNacionaldeBasket getSerie() {
		return serie;
	}

	public static void setSerie(SerieNacionaldeBasket serie) {
		SerieNacionaldeBasket.serie = serie;
	}

	public User buscarUsuarioPorNombre(String nombreBuscadoUsuario) {
		for (User user : misUsers) {
			String nombreUser = user.getUserName();
			if (nombreUser.equalsIgnoreCase(nombreBuscadoUsuario)) {
				return user;
			}
		}
		return null;
	}


    public void regUser(User user) {
        if(misUsers == null) {
            misUsers = new ArrayList<>();
        }
        if(user != null && !existeUser(user.getUserName())) {
            misUsers.add(user);
            guardarDatos(); 
            System.out.println("Usuario registrado: " + user.getUserName());
        }
    }


	private boolean existeUser(String nombreUser) {
		return buscarUsuarioPorNombre(nombreUser) != null;
	}


	public Jugador buscarJugadorPorNombre(String nombreBuscado) {
		for (Equipo equipo : equipos) {
			for (Jugador jugador : equipo.getMisjugadores()) {
				if (jugador.getNombreJugador().equalsIgnoreCase(nombreBuscado)) {
					return jugador;
				}
			}
		}
		return null;
	}

	public ArrayList<Jugador> getTodosJugadores() {
		ArrayList<Jugador> todosJugadores = new ArrayList<>();
		for (Equipo equipo : equipos) {
			todosJugadores.addAll(equipo.getMisjugadores());
		}
		return todosJugadores;
	}


	public boolean registrarLesion(Lesion lesion) {
		if (lesion != null && !existeLesionActiva(lesion.getJugadorLesionado())) {
			boolean resultado = lesiones.add(lesion);
			guardarDatos();
			return resultado;
		}
		return false;
	}

	public boolean eliminarLesion(Lesion lesion) {
		boolean resultado = lesiones.remove(lesion);
		if (resultado) {
			guardarDatos();
		}
		return resultado;
	}

	public boolean tieneLesionActiva(Jugador jugador) {
		return getLesionActiva(jugador) != null;
	}

	public Lesion getLesionActiva(Jugador jugador) {
		for (Lesion lesion : lesiones) {
			if (lesion.getJugadorLesionado().equals(jugador)) {
				long diasTranscurridos = (new Date().getTime() - lesion.getFechaInicio().getTime()) 
						/ (1000 * 60 * 60 * 24);
				if (diasTranscurridos < lesion.getDuracion()) {
					return lesion;
				}
			}
		}
		return null;
	}

	private boolean existeLesionActiva(Jugador jugador) {
		return getLesionActiva(jugador) != null;
	}

	public void limpiarLesionesExpiradas() {
		ArrayList<Lesion> paraEliminar = new ArrayList<>();
		Date ahora = new Date();

		for(Lesion lesion : lesiones) {
			long diasTranscurridos = (ahora.getTime() - lesion.getFechaInicio().getTime()) 
					/ (1000 * 60 * 60 * 24);
			if(diasTranscurridos >= lesion.getDuracion()) {
				paraEliminar.add(lesion);
			}
		}

		if (!paraEliminar.isEmpty()) {
			lesiones.removeAll(paraEliminar);
			guardarDatos();
		}
	}


	public void guardarDatos() {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(ARCHIVO_DATOS))) {

			
			limpiarLesionesExpiradas();

			DatosGuardados datos = new DatosGuardados(equipos, partidos, lesiones, misUsers);
			oos.writeObject(datos);
	        System.out.println("Datos guardados. Usuarios: " + misUsers.size());

		} catch (IOException e) {
			System.err.println("Error al guardar datos: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void cargarDatos() {
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(ARCHIVO_DATOS))) {

			DatosGuardados datos = (DatosGuardados) ois.readObject();
			this.equipos = datos.getEquipos();
			this.partidos = datos.getPartidos();
			this.lesiones = datos.getLesiones();
			this.misUsers = datos.getMisUsers();

			
			verificarIntegridadDatos();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Iniciando con datos nuevos. Raz�n: " + e.getMessage());
			this.equipos = new ArrayList<>();
			this.partidos = new ArrayList<>();
			this.lesiones = new ArrayList<>();
	        this.misUsers = new ArrayList<>(); 

		}
	}

	private void verificarIntegridadDatos() {
		
		lesiones.removeIf(lesion -> lesion.getJugadorLesionado() == null);

	
		ArrayList<Lesion> lesionesValidas = new ArrayList<>();
		for (Lesion lesion : lesiones) {
			boolean jugadorEncontrado = false;
			for (Equipo equipo : equipos) {
				if (equipo.getMisjugadores().contains(lesion.getJugadorLesionado())) {
					jugadorEncontrado = true;
					break;
				}
			}
			if (jugadorEncontrado) {
				lesionesValidas.add(lesion);
			}
		}
		lesiones = lesionesValidas;
	}

	private static class DatosGuardados implements Serializable {
        private static final long serialVersionUID = 1L;
        private ArrayList<Equipo> equipos;
        private ArrayList<Partido> partidos;
        private ArrayList<Lesion> lesiones;
        private ArrayList<User> misUsers; 

        public DatosGuardados(ArrayList<Equipo> equipos, 
                            ArrayList<Partido> partidos, 
                            ArrayList<Lesion> lesiones,
                            ArrayList<User>misUsers) {
            this.equipos = new ArrayList<>(equipos);
            this.partidos = new ArrayList<>(partidos);
            this.lesiones = new ArrayList<>(lesiones);
            this.misUsers = new ArrayList<>(misUsers); 

            
        }
        
        public ArrayList<Partido> getPartidosPendientes() {
            ArrayList<Partido> pendientes = new ArrayList<>();
            for (Partido partido : partidos) { 
                if ("Pendiente".equals(partido.getEstado())) {
                    pendientes.add(partido);
                }
            }
            return pendientes;
        }

        public ArrayList<Equipo> getEquipos() { return new ArrayList<>(equipos); }
        public ArrayList<Partido> getPartidos() { return new ArrayList<>(partidos); }
        public ArrayList<Lesion> getLesiones() { return new ArrayList<>(lesiones); }
        public ArrayList<User> getMisUsers() {return new ArrayList<>(misUsers);}
        
        
        
    }







}