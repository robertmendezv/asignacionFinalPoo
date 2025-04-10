package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import logico.*;
import java.util.ArrayList;
import java.io.Serializable;

public class Simulacion extends JFrame implements Serializable {
    private JPanel contentPane;
    private JLabel lblScoreLocal, lblScoreVisitante;
    private int scoreLocal = 0;
    private int scoreVisitante = 0;
    private Partido partidoActual;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private static final long serialVersionUID = 1L;
    private JSpinner spinnerPuntos, spinnerAsistencias, spinnerRebotes, spinnerDefensas;
    private JButton btnLocal, btnVisitante, btnFinalizar;

    public Simulacion(Partido partido) {
        this.partidoActual = partido;
        this.equipoLocal = partido.getEquipoLocal();
        this.equipoVisitante = partido.getEquipoVisitante();
        
        setTitle("Simulador de Partido: " + equipoLocal.getNombreEquipo() + " vs " + equipoVisitante.getNombreEquipo());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));
        
        partidoActual.iniciarPartido();
        initComponents();
    }

    private void initComponents() {
   
        JPanel panelMarcadores = new JPanel(new GridLayout(1, 2, 10, 10));
        panelMarcadores.setBorder(new LineBorder(Color.BLACK, 2));
        

        JPanel panelLocal = crearPanelEquipo(equipoLocal.getNombreEquipo(), Color.RED);
        lblScoreLocal = new JLabel("0", SwingConstants.CENTER);
        lblScoreLocal.setFont(new Font("Arial", Font.BOLD, 48));
        panelLocal.add(lblScoreLocal, BorderLayout.CENTER);
        
   
        JPanel panelVisitante = crearPanelEquipo(equipoVisitante.getNombreEquipo(), Color.BLUE);
        lblScoreVisitante = new JLabel("0", SwingConstants.CENTER);
        lblScoreVisitante.setFont(new Font("Arial", Font.BOLD, 48));
        panelVisitante.add(lblScoreVisitante, BorderLayout.CENTER);
        
        panelMarcadores.add(panelLocal);
        panelMarcadores.add(panelVisitante);
        contentPane.add(panelMarcadores, BorderLayout.NORTH);
        
    
        JPanel panelEstadisticas = new JPanel(new GridLayout(4, 3, 10, 10));
        panelEstadisticas.setBorder(BorderFactory.createTitledBorder("Registrar Estadísticas por Jugador"));
        
      
        SpinnerNumberModel modelPuntos = new SpinnerNumberModel(1, 1, 3, 1);
        SpinnerNumberModel modelEstadisticas = new SpinnerNumberModel(0, 0, 100, 1);
     
        panelEstadisticas.add(new JLabel("Puntos:"));
        spinnerPuntos = new JSpinner(modelPuntos);
        panelEstadisticas.add(spinnerPuntos);
        JButton btnSelPuntos = new JButton("Seleccionar Jugador");
        panelEstadisticas.add(btnSelPuntos);
        
 
        panelEstadisticas.add(new JLabel("Asistencias:"));
        spinnerAsistencias = new JSpinner(modelEstadisticas);
        panelEstadisticas.add(spinnerAsistencias);
        JButton btnSelAsistencias = new JButton("Seleccionar Jugador");
        panelEstadisticas.add(btnSelAsistencias);
        
      
        panelEstadisticas.add(new JLabel("Rebotes:"));
        spinnerRebotes = new JSpinner(modelEstadisticas);
        panelEstadisticas.add(spinnerRebotes);
        JButton btnSelRebotes = new JButton("Seleccionar Jugador");
        panelEstadisticas.add(btnSelRebotes);
        
    
        panelEstadisticas.add(new JLabel("Acciones Defensivas:"));
        spinnerDefensas = new JSpinner(modelEstadisticas);
        panelEstadisticas.add(spinnerDefensas);
        JButton btnSelDefensas = new JButton("Seleccionar Jugador");
        panelEstadisticas.add(btnSelDefensas);
        
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 10));
        btnLocal = new JButton("Registrar para Local");
        btnVisitante = new JButton("Registrar para Visitante");
        btnFinalizar = new JButton("Finalizar Partido");
        panelBotones.add(btnLocal);
        panelBotones.add(btnVisitante);
        panelBotones.add(btnFinalizar);

        contentPane.add(panelEstadisticas, BorderLayout.CENTER);
        contentPane.add(panelBotones, BorderLayout.SOUTH);
        
     
        configurarAcciones(btnSelPuntos, btnSelAsistencias, btnSelRebotes, btnSelDefensas);
    }
    
    private JPanel crearPanelEquipo(String nombre, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(color, 2));
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblNombre, BorderLayout.NORTH);
        return panel;
    }
    
    private void configurarAcciones(JButton btnSelPuntos, JButton btnSelAsistencias, 
                                  JButton btnSelRebotes, JButton btnSelDefensas) {
  
        Jugador[] jugadoresSeleccionados = new Jugador[4]; 
        
        btnSelPuntos.addActionListener(e -> {
            jugadoresSeleccionados[0] = seleccionarJugador("Seleccione el jugador que anotó:");
        });
        
        
        btnSelAsistencias.addActionListener(e -> {
            jugadoresSeleccionados[1] = seleccionarJugador("Seleccione el jugador que dio la asistencia:");
        });
        
      
        btnSelRebotes.addActionListener(e -> {
            jugadoresSeleccionados[2] = seleccionarJugador("Seleccione el jugador que hizo el rebote:");
        });
        
  
        btnSelDefensas.addActionListener(e -> {
            jugadoresSeleccionados[3] = seleccionarJugador("Seleccione el jugador que hizo la acción defensiva:");
        });
        
      
        btnLocal.addActionListener(e -> {
            registrarEstadisticasEquipo(true, jugadoresSeleccionados);
        });

        btnVisitante.addActionListener(e -> {
            registrarEstadisticasEquipo(false, jugadoresSeleccionados);
        });
        

        btnFinalizar.addActionListener(e -> {
            finalizarPartido();
        });
    }
    
    private Jugador seleccionarJugador(String mensaje) {
        try {
            ArrayList<Jugador> jugadores = new ArrayList<>();
            for(Object obj : partidoActual.getEquipoLocal().getMisjugadores()) {
                if(obj instanceof Jugador) {
                    jugadores.add((Jugador) obj);
                }
            }
            for(Object obj : partidoActual.getEquipoVisitante().getMisjugadores()) {
                if(obj instanceof Jugador) {
                    jugadores.add((Jugador) obj);
                }
            }
            
            return (Jugador) JOptionPane.showInputDialog(
                this,
                mensaje,
                "Selección de Jugador",
                JOptionPane.PLAIN_MESSAGE,
                null,
                jugadores.toArray(),
                null);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al seleccionar jugador: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    private void registrarEstadisticasEquipo(boolean esLocal, Jugador[] jugadores) {
        Equipo equipo = esLocal ? equipoLocal : equipoVisitante;
        int puntos = (int) spinnerPuntos.getValue();
        
    
        if (esLocal) {
            scoreLocal += puntos;
            lblScoreLocal.setText(String.valueOf(scoreLocal));
        } else {
            scoreVisitante += puntos;
            lblScoreVisitante.setText(String.valueOf(scoreVisitante));
        }
        
   
        if (jugadores[0] != null) { 
            actualizarEstadisticas(jugadores[0], puntos, 0, 0, 0);
        }
        if (jugadores[1] != null) { 
            actualizarEstadisticas(jugadores[1], 0, (int)spinnerAsistencias.getValue(), 0, 0);
        }
        if (jugadores[2] != null) { 
            actualizarEstadisticas(jugadores[2], 0, 0, (int)spinnerRebotes.getValue(), 0);
        }
        if (jugadores[3] != null) { 
            actualizarEstadisticas(jugadores[3], 0, 0, 0, (int)spinnerDefensas.getValue());
        }
        
       
        spinnerPuntos.setValue(1);
        spinnerAsistencias.setValue(0);
        spinnerRebotes.setValue(0);
        spinnerDefensas.setValue(0);
    }
    
    private void actualizarEstadisticas(Jugador jugador, int puntos, int asistencias, int rebotes, int defensas) {
        Estadistica stats = jugador.getEstadistica();
        if(stats == null) {
            stats = new Estadistica(0, 0, 0, 0);
            jugador.setEstadistica(stats);
        }
        stats.setPuntos(stats.getPuntos() + puntos);
        stats.setAsistencias(stats.getAsistencias() + asistencias);
        stats.setRebotes(stats.getRebotes() + rebotes);
        stats.setAccionesDefensivas(stats.getAccionesDefensivas() + defensas);
    }
    
    private void finalizarPartido() {
        partidoActual.finalizarPartido(scoreLocal, scoreVisitante);
        JOptionPane.showMessageDialog(this, "¡Partido finalizado! Resultado: " + scoreLocal + "-" + scoreVisitante);
        dispose();
    }
}