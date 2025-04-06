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

public class SerieNacionalBasketball {

    private JFrame frmSerieNacionalDe;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Solo mostrar el Login inicial
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
        frmSerieNacionalDe.setBounds(100, 100, 743, 460);
        frmSerieNacionalDe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSerieNacionalDe.getContentPane().setLayout(new BorderLayout(0, 0));
        
        
        frmSerieNacionalDe.addWindowListener(new WindowAdapter() {
           // @Override
            public void windowClosing(WindowEvent e) {
                guardarDatos();
            }
        });
        
        JPanel panel = new JPanel();
        frmSerieNacionalDe.getContentPane().add(panel, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        frmSerieNacionalDe.setJMenuBar(menuBar);
        
        // Men� Equipos
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
        
        // Men� Jugadores
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
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Lista de Jugadores");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListadoJugadores dialog = new ListadoJugadores();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnJugadores.add(mntmNewMenuItem_1);
        

        JMenu mnEstadisticas = new JMenu("Estad�sticas");
        menuBar.add(mnEstadisticas);
        
        JMenuItem mntmRegistrarEstadisticas = new JMenuItem("Registrar Estad�sticas");
        mntmRegistrarEstadisticas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegEstadisticasJugador dialog = new RegEstadisticasJugador();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnEstadisticas.add(mntmRegistrarEstadisticas);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Tabla de Estadisticas");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListadoEstadisticasGeneral dialog = new ListadoEstadisticasGeneral();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        mnEstadisticas.add(mntmNewMenuItem);
        

        JMenu mnLesiones = new JMenu("Lesiones");
        menuBar.add(mnLesiones);
        

        JMenu mnPartidos = new JMenu("Partidos");
        menuBar.add(mnPartidos);
    }
    
    private void guardarDatos() {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try {
            fileOut = new FileOutputStream("serie_nacional.dat");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(SerieNacionaldeBasket.getInstance());
            System.out.println("Datos guardados exitosamente");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frmSerieNacionalDe, 
                "Error al guardar los datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (fileOut != null) fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setVisible(boolean visible) {
        frmSerieNacionalDe.setVisible(visible);
    }
}