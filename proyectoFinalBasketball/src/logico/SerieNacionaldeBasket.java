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

	//robert
	private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_DATOS = "SerieNacionalBasketball.dat";
	private static SerieNacionaldeBasket serie;
	private ArrayList<User> misUsers;
	private ArrayList<Jugador> misJugadores;
	private static User loginUser;
	//



	public SerieNacionaldeBasket() {
		this.equipos = new ArrayList<Equipo>();
		this.partidos = new ArrayList<Partido>();
		this.lesiones = new ArrayList<Lesion>();
		this.misJugadores = new ArrayList<Jugador>();

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
					throw new IllegalArgumentException("El partido ya estï¿½ registrado");
				}
			} else {
				throw new IllegalArgumentException("Uno o ambos equipos no estï¿½n registrados");
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






	// 	3/4/25 1:59pm -robert
	public void regUser(User user) {
		misUsers.add(user);

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


	public boolean confirmLogin(String text, String text2) {
		boolean login = false;
		for (User user : misUsers) {
			if(user.getUserName().equals(text) && user.getPassw().equals(text2)){
				loginUser = user;
				login = true;
			}
		}
		return login;
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


	public boolean registrarUser(User user) {
		if(user != null && !existeUser( user.getUserName() ) ) {
			return misUsers.add(user);
		}
		return false;
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




	//


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

	/* Métodos para persistencia */
	public void guardarDatos() {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(ARCHIVO_DATOS))) {

			// Limpiar lesiones expiradas antes de guardar
			limpiarLesionesExpiradas();

			DatosGuardados datos = new DatosGuardados(equipos, partidos, lesiones);
			oos.writeObject(datos);
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

			// Verificar integridad de datos cargados
			verificarIntegridadDatos();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Iniciando con datos nuevos. Razón: " + e.getMessage());
			this.equipos = new ArrayList<>();
			this.partidos = new ArrayList<>();
			this.lesiones = new ArrayList<>();
		}
	}

	private void verificarIntegridadDatos() {
		// Eliminar referencias inválidas
		lesiones.removeIf(lesion -> lesion.getJugadorLesionado() == null);

		// Verificar que los jugadores en lesiones existan en equipos
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

        public DatosGuardados(ArrayList<Equipo> equipos, 
                            ArrayList<Partido> partidos, 
                            ArrayList<Lesion> lesiones) {
            this.equipos = new ArrayList<>(equipos);
            this.partidos = new ArrayList<>(partidos);
            this.lesiones = new ArrayList<>(lesiones);
        }

        public ArrayList<Equipo> getEquipos() { return new ArrayList<>(equipos); }
        public ArrayList<Partido> getPartidos() { return new ArrayList<>(partidos); }
        public ArrayList<Lesion> getLesiones() { return new ArrayList<>(lesiones); }
    }







}