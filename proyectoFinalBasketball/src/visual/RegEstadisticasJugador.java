package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import logico.SerieNacionaldeBasket;
import logico.Jugador;

public class RegEstadisticasJugador extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JComboBox<String> cbxJugador; // Cambiado a variable de clase

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            RegEstadisticasJugador dialog = new RegEstadisticasJugador();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public RegEstadisticasJugador() {
        setTitle("Registrar Estadisticas");
        setBounds(100, 100, 770, 478);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            contentPanel.add(panel, BorderLayout.CENTER);
            panel.setLayout(null);
            
            JLabel lblNewLabel = new JLabel("Elija el jugador: ");
            lblNewLabel.setBounds(15, 47, 142, 20);
            panel.add(lblNewLabel);
            
            // ComboBox modificado
            cbxJugador = new JComboBox<>();
            cbxJugador.setBounds(154, 44, 175, 26);
            panel.add(cbxJugador);
            
            // Cargar jugadores al iniciar
            cargarJugadoresEnCombo();
            
            JLabel lblNewLabel_1 = new JLabel("Puntos:");
            lblNewLabel_1.setBounds(70, 121, 69, 20);
            panel.add(lblNewLabel_1);
            
            JLabel lblNewLabel_2 = new JLabel("Rebotes:");
            lblNewLabel_2.setBounds(70, 171, 69, 20);
            panel.add(lblNewLabel_2);
            
            JLabel lblNewLabel_3 = new JLabel("Acciones defensivas:");
            lblNewLabel_3.setBounds(395, 104, 158, 55);
            panel.add(lblNewLabel_3);
            
            JSpinner spnPuntos = new JSpinner();
            spnPuntos.setBounds(154, 118, 32, 26);
            panel.add(spnPuntos);
            
            JSpinner spnRebotes = new JSpinner();
            spnRebotes.setBounds(154, 168, 32, 26);
            panel.add(spnRebotes);
            
            JSpinner spnAcciones = new JSpinner();
            spnAcciones.setBounds(568, 118, 32, 26);
            panel.add(spnAcciones);
            
            JLabel lblNewLabel_4 = new JLabel("Asistencias:");
            lblNewLabel_4.setBounds(395, 171, 107, 20);
            panel.add(lblNewLabel_4);
            
            JSpinner spnAsistencias = new JSpinner();
            spnAsistencias.setBounds(568, 168, 32, 26);
            panel.add(spnAsistencias);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
            fl_buttonPane.setVgap(2);
            buttonPane.setLayout(fl_buttonPane);
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Registrar");
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
    
    // Método para cargar jugadores en el ComboBox
    public void cargarJugadoresEnCombo() {
        cbxJugador.removeAllItems();
        
        SerieNacionaldeBasket serie = SerieNacionaldeBasket.getInstance();
        if(serie != null) {
            for(Jugador jugador : serie.getTodosJugadores()) {
                String item = jugador.getNombreJugador() + " - " + jugador.getPosicionJugador();
                cbxJugador.addItem(item);
            }
        }
    }
    
    // Método para obtener el jugador seleccionado
    public Jugador getJugadorSeleccionado() {
        String seleccion = (String)cbxJugador.getSelectedItem();
        if(seleccion != null && !seleccion.isEmpty()) {
            String nombreJugador = seleccion.split(" - ")[0];
            return SerieNacionaldeBasket.getInstance().buscarJugadorPorNombre(nombreJugador);
        }
        return null;
    }
}