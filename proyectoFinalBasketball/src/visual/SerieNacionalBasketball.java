package visual;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import logico.Equipo;
import logico.SerieNacionaldeBasket;
import java.util.ArrayList;
import javax.swing.JDialog;

public class SerieNacionalBasketball {

    private JFrame frmSerieNacionalDe;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SerieNacionalBasketball window = new SerieNacionalBasketball();
                    window.frmSerieNacionalDe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SerieNacionalBasketball() {
        initialize();
    }

    private void initialize() {
        frmSerieNacionalDe = new JFrame();
        frmSerieNacionalDe.setTitle("Serie Nacional de Basketball");
        frmSerieNacionalDe.setBounds(100, 100, 743, 460);
        frmSerieNacionalDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSerieNacionalDe.getContentPane().setLayout(new BorderLayout(0, 0));
        
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
                RegistrarEquipo dialog = new RegistrarEquipo();
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
        

        JMenu mnLesiones = new JMenu("Lesiones");
        menuBar.add(mnLesiones);
        

        JMenu mnPartidos = new JMenu("Partidos");
        menuBar.add(mnPartidos);
    }
}