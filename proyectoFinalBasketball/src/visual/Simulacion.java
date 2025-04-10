package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.time.LocalTime;
import logico.*;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Simulacion extends JFrame implements Serializable {
    private JPanel contentPane;
    private JLabel lblScoreLocal, lblScoreVisitante, lblTiempo, lblPeriodo;
    private JButton btnLocal1, btnLocal2, btnLocal3, btnVisitante1, btnVisitante2, btnVisitante3;
    private JButton btnStartPause, btnReset, btnFaltaLocal, btnFaltaVisitante;
    private JButton btnFinPeriodo, btnTimeoutLocal, btnTimeoutVisitante;
    private Timer timer;
    private int minutos = 12; 
    private int segundos = 0;
    private boolean enMarcha = false;
    private int scoreLocal = 0;
    private int scoreVisitante = 0;
    private int periodo = 1;
    private Partido partidoActual;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private static final long serialVersionUID = 1L;

    public Simulacion(Partido partido) {
        this.partidoActual = partido;
        this.equipoLocal = partido.getEquipoLocal();
        this.equipoVisitante = partido.getEquipoVisitante();
        
        setTitle("Simulador de Partido: " + equipoLocal.getNombreEquipo() + " vs " + equipoVisitante.getNombreEquipo());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));
        
        try {
            String projectPath = System.getProperty("user.dir");
            String imagePath = projectPath + File.separator + "Imagenes" + File.separator + "simulacion.jpg";
            File imageFile = new File(imagePath);
            
            if (!imageFile.exists()) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontr� la imagen en:\n" + imagePath, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Image img = ImageIO.read(imageFile);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                JLabel lblImagen = new JLabel(icon);
                lblImagen.setBorder(new EmptyBorder(0, 10, 0, 0)); 
                
                JPanel panelImagen = new JPanel(new BorderLayout());
                panelImagen.add(lblImagen, BorderLayout.NORTH);
                contentPane.add(panelImagen, BorderLayout.WEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar la imagen: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Iniciar el partido en el sistema
        partidoActual.iniciarPartido();
        
        initComponents();
    }

    private void initComponents() {
        // Panel superior para los marcadores
        JPanel panelMarcadores = new JPanel(new GridLayout(1, 3, 10, 10));
        panelMarcadores.setBorder(new LineBorder(Color.BLACK, 2));
        
        // Equipo local
        JPanel panelLocal = crearPanelEquipo(equipoLocal.getNombreEquipo(), Color.RED);
        lblScoreLocal = new JLabel("0", SwingConstants.CENTER);
        lblScoreLocal.setFont(new Font("Arial", Font.BOLD, 48));
        panelLocal.add(lblScoreLocal, BorderLayout.CENTER);
        
        // Tiempo y periodo
        JPanel panelCentral = new JPanel(new BorderLayout());
        lblTiempo = new JLabel("12:00", SwingConstants.CENTER);
        lblTiempo.setFont(new Font("Arial", Font.BOLD, 48));
        panelCentral.add(lblTiempo, BorderLayout.CENTER);
        
        lblPeriodo = new JLabel("PERIODO: 1", SwingConstants.CENTER);
        lblPeriodo.setFont(new Font("Arial", Font.BOLD, 16));
        panelCentral.add(lblPeriodo, BorderLayout.SOUTH);
        
        // Equipo visitante
        JPanel panelVisitante = crearPanelEquipo(equipoVisitante.getNombreEquipo(), Color.BLUE);
        lblScoreVisitante = new JLabel("0", SwingConstants.CENTER);
        lblScoreVisitante.setFont(new Font("Arial", Font.BOLD, 48));
        panelVisitante.add(lblScoreVisitante, BorderLayout.CENTER);
        
        panelMarcadores.add(panelLocal);
        panelMarcadores.add(panelCentral);
        panelMarcadores.add(panelVisitante);
        
        contentPane.add(panelMarcadores, BorderLayout.NORTH);
        
        // Panel de botones para anotaci�n
        JPanel panelBotonesAnotacion = new JPanel(new GridLayout(2, 3, 5, 5));
        
        // Botones equipo local
        btnLocal1 = new JButton("Canasta (1)");
        btnLocal2 = new JButton("Canasta (2)");
        btnLocal3 = new JButton("Triple (3)");
        
        // Botones equipo visitante
        btnVisitante1 = new JButton("Canasta (1)");
        btnVisitante2 = new JButton("Canasta (2)");
        btnVisitante3 = new JButton("Triple (3)");
        
        panelBotonesAnotacion.add(btnLocal1);
        panelBotonesAnotacion.add(btnLocal2);
        panelBotonesAnotacion.add(btnLocal3);
        panelBotonesAnotacion.add(btnVisitante1);
        panelBotonesAnotacion.add(btnVisitante2);
        panelBotonesAnotacion.add(btnVisitante3);
        
        // Panel de control
        JPanel panelControl = new JPanel(new GridLayout(1, 6, 5, 5));
        
        btnStartPause = new JButton("Iniciar/Pausar");
        btnReset = new JButton("Reiniciar Tiempo");
        btnFaltaLocal = new JButton("Falta Local");
        btnFaltaVisitante = new JButton("Falta Visitante");
        btnTimeoutLocal = new JButton("Timeout Local");
        btnTimeoutVisitante = new JButton("Timeout Visitante");
        btnFinPeriodo = new JButton("Fin Periodo");
        
        panelControl.add(btnStartPause);
        panelControl.add(btnReset);
        panelControl.add(btnFaltaLocal);
        panelControl.add(btnFaltaVisitante);
        panelControl.add(btnTimeoutLocal);
        panelControl.add(btnTimeoutVisitante);
        panelControl.add(btnFinPeriodo);
        
        // Agregar paneles al contentPane
        JPanel panelSur = new JPanel(new BorderLayout(5, 5));
        panelSur.add(panelBotonesAnotacion, BorderLayout.NORTH);
        panelSur.add(panelControl, BorderLayout.SOUTH);
        
        contentPane.add(panelSur, BorderLayout.SOUTH);
        
        // Configurar acciones de los botones
        configurarAcciones();
        
        // Configurar timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (segundos == 0) {
                    if (minutos == 0) {
                        timer.stop();
                        enMarcha = false;
                        JOptionPane.showMessageDialog(null, "�Fin del periodo!");
                    } else {
                        minutos--;
                        segundos = 59;
                    }
                } else {
                    segundos--;
                }
                actualizarTiempo();
            }
        });
    }
    
    private JPanel crearPanelEquipo(String nombre, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(color, 2));
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblNombre, BorderLayout.NORTH);
        return panel;
    }
    
    private void configurarAcciones() {
        
        btnLocal1.addActionListener(e -> {
            scoreLocal += 1;
            lblScoreLocal.setText(String.valueOf(scoreLocal));
            actualizarEstadisticasJugador(1, true);
            reiniciarCronometro();
        });
        
        btnLocal2.addActionListener(e -> {
            scoreLocal += 2;
            lblScoreLocal.setText(String.valueOf(scoreLocal));
            actualizarEstadisticasJugador(2, true);
            reiniciarCronometro();
        });
        
        btnLocal3.addActionListener(e -> {
            scoreLocal += 3;
            lblScoreLocal.setText(String.valueOf(scoreLocal));
            actualizarEstadisticasJugador(3, true);
            reiniciarCronometro();
        });
        
        
        btnVisitante1.addActionListener(e -> {
            scoreVisitante += 1;
            lblScoreVisitante.setText(String.valueOf(scoreVisitante));
            actualizarEstadisticasJugador(1, false);
            reiniciarCronometro();
        });
        
        btnVisitante2.addActionListener(e -> {
            scoreVisitante += 2;
            lblScoreVisitante.setText(String.valueOf(scoreVisitante));
            actualizarEstadisticasJugador(2, false);
            reiniciarCronometro();
        });
        
        btnVisitante3.addActionListener(e -> {
            scoreVisitante += 3;
            lblScoreVisitante.setText(String.valueOf(scoreVisitante));
            actualizarEstadisticasJugador(3, false);
            reiniciarCronometro();
        });
        
        
        btnStartPause.addActionListener(e -> {
            if (enMarcha) {
                timer.stop();
                enMarcha = false;
            } else {
                timer.start();
                enMarcha = true;
            }
        });
        
        btnReset.addActionListener(e -> {
            reiniciarCronometro();
        });
        
        btnFinPeriodo.addActionListener(e -> {
            periodo++;
            if(periodo > 4) { 
                finalizarPartido();
            } else {
                lblPeriodo.setText("PERIODO: " + periodo);
                reiniciarCronometro();
            }
        });
    }
    
    private void actualizarEstadisticasJugador(int puntos, boolean esLocal) {
        try {
            Equipo equipo = esLocal ? equipoLocal : equipoVisitante;
            
            if(equipo == null || equipo.getMisjugadores().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El equipo no tiene jugadores registrados",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            ArrayList<Jugador> jugadores = new ArrayList<>();
            for(Object obj : equipo.getMisjugadores()) {
                if(obj instanceof Jugador) {
                    jugadores.add((Jugador) obj);
                }
            }
            
            
            Jugador jugador = (Jugador) JOptionPane.showInputDialog(
                this,
                "Seleccione el jugador:",
                "Actualizar Estad�sticas",
                JOptionPane.PLAIN_MESSAGE,
                null,
                jugadores.toArray(),
                jugadores.get(0));
            
            if(jugador != null) {
                Estadistica stats = jugador.getEstadistica();
                if(stats == null) {
                    stats = new Estadistica(0, 0, 0, 0);
                    jugador.setEstadistica(stats);
                }
                stats.setPuntos(stats.getPuntos() + puntos);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al actualizar estad�sticas: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void finalizarPartido() {
        timer.stop();
        partidoActual.finalizarPartido(scoreLocal, scoreVisitante);
        JOptionPane.showMessageDialog(this, "�Partido finalizado! Resultado: " + scoreLocal + "-" + scoreVisitante);
        dispose();
    }
    
    private void reiniciarCronometro() {
        minutos = 12;
        segundos = 0;
        actualizarTiempo();
        if (enMarcha) {
            timer.restart();
        }
    }
    
    private void actualizarTiempo() {
        String tiempo = String.format("%02d:%02d", minutos, segundos);
        lblTiempo.setText(tiempo);
    }
}