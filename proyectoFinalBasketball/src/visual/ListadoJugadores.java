package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;


import logico.SerieNacionaldeBasket;
import logico.Jugador;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logico.Equipo;
import logico.Jugador;
import logico.SerieNacionaldeBasket;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListadoJugadores extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private static DefaultTableModel model;
    private static Object[] row;
    private Jugador miJugador = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ListadoJugadores dialog = new ListadoJugadores();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ListadoJugadores() {
        setTitle("Listado de Jugadores");
        setBounds(100, 100, 900, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            JPanel panel = new JPanel();
            contentPanel.add(panel, BorderLayout.CENTER);
            panel.setLayout(new BorderLayout(0, 0));
            {
                JScrollPane scrollPane = new JScrollPane();
                panel.add(scrollPane, BorderLayout.CENTER);
                {
                    model = new DefaultTableModel();
                    String[] headers = {"Nombre", "Edad", "Posición", "Número", "Equipo", "Estatura", "Peso"};
                    model.setColumnIdentifiers(headers);
                    table = new JTable();
                    table.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int index = table.getSelectedRow();
                            if(index != -1) {
                                String nombreJugador = table.getValueAt(index, 0).toString();
                                miJugador = SerieNacionaldeBasket.getInstance().buscarJugadorPorNombre(nombreJugador);
                            }
                        }
                    });
                    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table.setModel(model);
                    scrollPane.setViewportView(table);
                }
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton btnVerEstadisticas = new JButton("Ver Estadísticas");
                btnVerEstadisticas.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (miJugador != null) {
                            // Aquí puedes abrir una ventana de estadísticas del jugador
                            JOptionPane.showMessageDialog(ListadoJugadores.this, 
                                "Estadísticas de " + miJugador.getNombreJugador(), 
                                "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(ListadoJugadores.this, 
                                "Seleccione un jugador primero", 
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
                buttonPane.add(btnVerEstadisticas);
            }
            {
                JButton btnEditar = new JButton("Editar");
                btnEditar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (miJugador != null) {
                            // Aquí puedes abrir la ventana de edición
                            JOptionPane.showMessageDialog(ListadoJugadores.this, 
                                "Editar jugador: " + miJugador.getNombreJugador(), 
                                "Editar", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(ListadoJugadores.this, 
                                "Seleccione un jugador primero", 
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
                buttonPane.add(btnEditar);
            }
            {
                JButton btnCancelar = new JButton("Cancelar");
                btnCancelar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                buttonPane.add(btnCancelar);
            }
        }
        loadJugadores();
    }

    public static void loadJugadores() {
        model.setRowCount(0);
        row = new Object[model.getColumnCount()];
        ArrayList<Jugador> jugadores = SerieNacionaldeBasket.getInstance().getTodosJugadores();
        for (Jugador jugador : jugadores) {
            row[0] = jugador.getNombreJugador();
            row[1] = jugador.getEdad();
            row[2] = jugador.getPosicionJugador();
            row[3] = jugador.getNumJugador();
            row[4] = obtenerNombreEquipo(jugador);
            row[5] = jugador.getEstatura() + " cm";
            row[6] = jugador.getPeso() + " kg";
            model.addRow(row);
        }
    }

    private static String obtenerNombreEquipo(Jugador jugador) {
        for (Equipo equipo : SerieNacionaldeBasket.getInstance().getEquipos()) {
            if (equipo.getMisjugadores().contains(jugador)) {
                return equipo.getNombreEquipo();
            }
        }
        return "Sin equipo";
    }
}