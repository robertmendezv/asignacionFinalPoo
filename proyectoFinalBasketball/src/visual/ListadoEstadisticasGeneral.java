package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.SerieNacionaldeBasket;
import logico.Jugador;
import logico.Equipo;
import logico.Estadistica;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class ListadoEstadisticasGeneral extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private static DefaultTableModel model;
    private static Object[] row;
    private JComboBox <String> cbxJugador;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ListadoEstadisticasGeneral dialog = new ListadoEstadisticasGeneral();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ListadoEstadisticasGeneral() {
        setTitle("Listado de Estadisticas");
        setBounds(100, 100, 826, 456);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        
        {
            JPanel panel = new JPanel();
            contentPanel.add(panel, BorderLayout.CENTER);
            panel.setLayout(null);
            
            {
                JLabel lblNewLabel = new JLabel("Elija el jugador:");
                lblNewLabel.setBounds(15, 16, 144, 20);
                panel.add(lblNewLabel);
            }
            
            {
                JLabel lblNewLabel_1 = new JLabel("Todos los jugadores");
                lblNewLabel_1.setBounds(419, 16, 163, 20);
                panel.add(lblNewLabel_1);
            }
            
            {
                JCheckBox chkboxJugadores = new JCheckBox("");
                chkboxJugadores.setSelected(true);
                chkboxJugadores.setBounds(587, 12, 139, 29);
                panel.add(chkboxJugadores);
                
                JLabel lblNewLabel_2 = new JLabel("Nombre");
                lblNewLabel_2.setBounds(54, 95, 69, 20);
                panel.add(lblNewLabel_2);
                
                JLabel lblNewLabel_3 = new JLabel("Equipo");
                lblNewLabel_3.setBounds(138, 95, 69, 20);
                panel.add(lblNewLabel_3);
                
                JLabel lblNewLabel_4 = new JLabel("Promedio Puntos");
                lblNewLabel_4.setBounds(217, 95, 126, 20);
                panel.add(lblNewLabel_4);
                
                JLabel lblNewLabel_5 = new JLabel("Promedio Rebotes");
                lblNewLabel_5.setBounds(371, 95, 144, 20);
                panel.add(lblNewLabel_5);
                
                JLabel lblNewLabel_6 = new JLabel("Promedio Asistencias");
                lblNewLabel_6.setBounds(530, 95, 173, 20);
                panel.add(lblNewLabel_6);
                
                model = new DefaultTableModel(
                    new Object[][] {}, 
                    new String[] {"Nombre", "Equipo", "Promedio Puntos", "Promedio Rebotes", "Promedio Asistencias"}
                );
                
                table = new JTable(model);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(15, 130, 780, 250);
                panel.add(scrollPane);
                
                
                
                
             // Crear y configurar el JComboBox
                cbxJugador = new JComboBox<>();
                cbxJugador.setBounds(166, 13, 181, 26);
                panel.add(cbxJugador);

                // Cargar los jugadores
                cargarJugadoresEnCombo();

                // Configurar el checkbox
                chkboxJugadores.addActionListener(e -> {
                    if(chkboxJugadores.isSelected()) {
                        cbxJugador.setEnabled(false);
                        cargarDatosTodosJugadores();
                    } else {
                        cbxJugador.setEnabled(true);
                        if(cbxJugador.getSelectedIndex() > 0) {
                            cargarDatosJugadorSeleccionado();
                        } else {
                            model.setRowCount(0); // Limpiar tabla si no hay selección
                        }
                    }
                });

                // Configurar el listener del combo
                cbxJugador.addActionListener(e -> {
                    if(!chkboxJugadores.isSelected()) {
                        cargarDatosJugadorSeleccionado();
                    }
                });
            }
            
            {
                JPanel buttonPane = new JPanel();
                buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
                getContentPane().add(buttonPane, BorderLayout.SOUTH);
                
                {
                    JButton okButton = new JButton("OK");
                    okButton.setActionCommand("OK");
                    buttonPane.add(okButton);
                    getRootPane().setDefaultButton(okButton);
                }
                
                {
                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.setActionCommand("Cancel");
                    buttonPane.add(cancelButton);
                }
            }
            
            
        }
    }
    
    

    private void cargarDatosTodosJugadores() {
        model.setRowCount(0); 
        SerieNacionaldeBasket serie = SerieNacionaldeBasket.getInstance();
        if(serie != null) {
            for(Jugador jugador : serie.getTodosJugadores()) {
                agregarJugadorATabla(jugador);
            }
        }
    }

    public void cargarJugadoresEnCombo() {
        cbxJugador.removeAllItems();
        cbxJugador.addItem("");
        
        SerieNacionaldeBasket serie = SerieNacionaldeBasket.getInstance();
        if(serie != null) {
            for(Jugador jugador : serie.getTodosJugadores()) {
                String item = jugador.getNombreJugador() + " - " + jugador.getPosicionJugador();
                cbxJugador.addItem(item);
            }
        }
    }
    
    
    private void cargarDatosJugadorSeleccionado() {
        String seleccion = (String)cbxJugador.getSelectedItem();
        if(seleccion != null && !seleccion.isEmpty()) {
            String nombreJugador = seleccion.split(" - ")[0];
            Jugador jugador = SerieNacionaldeBasket.getInstance().buscarJugadorPorNombre(nombreJugador);
            if(jugador != null) {
                model.setRowCount(0);
                agregarJugadorATabla(jugador);
            }
        }
    }
    
    
    private void agregarJugadorATabla(Jugador jugador) {
        // Obtener el equipo del jugador buscando en todos los equipos
        String nombreEquipo = obtenerNombreEquipoDelJugador(jugador);
        
        Estadistica stats = jugador.getEstadistica();
        
        model.addRow(new Object[]{
            jugador.getNombreJugador(),
            nombreEquipo,
            stats != null ? stats.getPuntos() : 0,
            stats != null ? stats.getRebotes() : 0,
            stats != null ? stats.getAsistencias() : 0
        });
    }

    private String obtenerNombreEquipoDelJugador(Jugador jugador) {
        SerieNacionaldeBasket serie = SerieNacionaldeBasket.getInstance();
        for (Equipo equipo : serie.getEquipos()) {
            if (equipo.getMisjugadores().contains(jugador)) {
                return equipo.getNombreEquipo();
            }
        }
        return "Sin equipo";
    }
    
    
}