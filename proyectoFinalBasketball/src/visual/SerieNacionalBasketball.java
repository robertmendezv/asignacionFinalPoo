package visual;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.sun.glass.events.WindowEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import logico.Equipo;
import logico.SerieNacionaldeBasket;
import java.util.ArrayList;
import javax.swing.JDialog;
import logico.Partido;  

public class SerieNacionalBasketball {

    private JFrame frmSerieNacionalDe;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }

    public SerieNacionalBasketball() {
        initialize();
    }

    private void initialize() {
        frmSerieNacionalDe = new JFrame();
        frmSerieNacionalDe.setTitle("Serie Nacional de Basketball");
        frmSerieNacionalDe.setBounds(100, 100, 800, 600);
        frmSerieNacionalDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSerieNacionalDe.getContentPane().setLayout(new BorderLayout(0, 0));
        
        frmSerieNacionalDe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                guardarDatos();
            }
        });
        
        JPanel panel = new JPanel();
        frmSerieNacionalDe.getContentPane().add(panel, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        frmSerieNacionalDe.setJMenuBar(menuBar);
        
        // Menú Equipos
        JMenu mnEquipos = new JMenu("Equipos");
        menuBar.add(mnEquipos);
        
        JMenuItem mntmRegistrarEquipo = new JMenuItem("Registrar Equipo");
        mntmRegistrarEquipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrarEquipo dialog = new RegistrarEquipo(null);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnEquipos.add(mntmRegistrarEquipo);
        
        JMenuItem mntmListarEquipos = new JMenuItem("Listar Equipos");
        mntmListarEquipos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListadoEquipos dialog = new ListadoEquipos();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnEquipos.add(mntmListarEquipos);
        
        JMenuItem mntmModificarEquipo = new JMenuItem("Modificar Equipo");
        mntmModificarEquipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Equipo> equipos = SerieNacionaldeBasket.getInstance().getEquipos();
                ModificarEquipo dialog = new ModificarEquipo(equipos);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnEquipos.add(mntmModificarEquipo);
        
        // Menú Jugadores
        JMenu mnJugadores = new JMenu("Jugadores");
        menuBar.add(mnJugadores);
        
        JMenuItem mntmRegistrarJugador = new JMenuItem("Registrar Jugador");
        mntmRegistrarJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Registrarjugador dialog = new Registrarjugador();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnJugadores.add(mntmRegistrarJugador);
        
        JMenuItem mntmListarJugadores = new JMenuItem("Lista de Jugadores");
        mntmListarJugadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListadoJugadores dialog = new ListadoJugadores();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnJugadores.add(mntmListarJugadores);
        
        // Menú Estadísticas
        JMenu mnEstadisticas = new JMenu("Estadísticas");
        menuBar.add(mnEstadisticas);
        
        JMenuItem mntmRegistrarEstadisticas = new JMenuItem("Registrar Estadísticas");
        mntmRegistrarEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegEstadisticasJugador dialog = new RegEstadisticasJugador();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnEstadisticas.add(mntmRegistrarEstadisticas);
        
        JMenuItem mntmTablaEstadisticas = new JMenuItem("Tabla de Estadisticas");
        mntmTablaEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListadoEstadisticasGeneral dialog = new ListadoEstadisticasGeneral();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnEstadisticas.add(mntmTablaEstadisticas);
        
        // Menú Lesiones
        JMenu mnLesiones = new JMenu("Lesiones");
        menuBar.add(mnLesiones);
        
        // Menú Partidos (optimizado)
        JMenu mnPartidos = new JMenu("Partidos");
        menuBar.add(mnPartidos);
        
        JMenuItem mntmCalendario = new JMenuItem("Calendario de Partidos");
        mntmCalendario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCalendario();
            }
        });
        mnPartidos.add(mntmCalendario);
        
        JMenuItem mntmSimularPartido = new JMenuItem("Simular Partido");
        mntmSimularPartido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simularPartido();
            }
        });
        mnPartidos.add(mntmSimularPartido);
    }
    
    private void mostrarCalendario() {
        ArrayList<Equipo> equipos = SerieNacionaldeBasket.getInstance().getEquipos();
        if (equipos.size() < 2) {
            JOptionPane.showMessageDialog(frmSerieNacionalDe, 
                "Debe haber al menos 2 equipos registrados",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Calendario calendario = new Calendario();
        calendario.setVisible(true);
    }
    
    
    private void simularPartido() {
        
        Calendario calendario = new Calendario();
        calendario.setVisible(true);
        
        JOptionPane.showMessageDialog(frmSerieNacionalDe,
            "Seleccione un partido del calendario para simular",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    private void guardarDatos() {
        try (ObjectOutputStream out = new ObjectOutputStream(
             new FileOutputStream("serie_nacional.dat"))) {
            out.writeObject(SerieNacionaldeBasket.getInstance());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frmSerieNacionalDe, 
                "Error al guardar los datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setVisible(boolean visible) {
        frmSerieNacionalDe.setVisible(visible);
    }
}