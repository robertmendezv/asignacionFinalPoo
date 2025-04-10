package visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logico.*;
import java.util.ArrayList;

public class Registrarjugador extends JFrame {
    private JPanel contentPane;
    private JTextField txtNombre, txtPosicion, txtSangre, txtFechaNac, txtMarca;
    private JSpinner spEdad, spNumJugador, spEstatura, spPeso;
    private JButton btnRegistrar, btnCancelar;
    private JComboBox<String> comboEquipos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Registrarjugador frame = new Registrarjugador();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Registrarjugador() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 550);
        setTitle("Registro de jugador");
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 2, 5, 5));

        contentPane.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        contentPane.add(txtNombre);

        JPanel panelSpinners = new JPanel(new GridLayout(1, 3, 5, 5));
        
        JPanel panelEdad = new JPanel(new BorderLayout());
        panelEdad.add(new JLabel("Edad:"), BorderLayout.NORTH);
        spEdad = new JSpinner(new SpinnerNumberModel(18, 15, 50, 1));
        ((JSpinner.DefaultEditor) spEdad.getEditor()).getTextField().setColumns(3);
        panelEdad.add(spEdad, BorderLayout.CENTER);
        panelSpinners.add(panelEdad);

        JPanel panelPeso = new JPanel(new BorderLayout());
        panelPeso.add(new JLabel("Peso:"), BorderLayout.NORTH);
        spPeso = new JSpinner(new SpinnerNumberModel(70.0, 50.0, 120.0, 0.5));
        ((JSpinner.DefaultEditor) spPeso.getEditor()).getTextField().setColumns(3);
        panelPeso.add(spPeso, BorderLayout.CENTER);
        panelSpinners.add(panelPeso);

        JPanel panelEstatura = new JPanel(new BorderLayout());
        panelEstatura.add(new JLabel("Estatura:"), BorderLayout.NORTH);
        spEstatura = new JSpinner(new SpinnerNumberModel(170, 150, 230, 1));
        ((JSpinner.DefaultEditor) spEstatura.getEditor()).getTextField().setColumns(3);
        panelEstatura.add(spEstatura, BorderLayout.CENTER);
        panelSpinners.add(panelEstatura);

        contentPane.add(new JLabel("Datos:"));
        contentPane.add(panelSpinners);

        contentPane.add(new JLabel("Posicion:"));
        txtPosicion = new JTextField();
        contentPane.add(txtPosicion);

        contentPane.add(new JLabel("Numero:"));
        spNumJugador = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        contentPane.add(spNumJugador);

        contentPane.add(new JLabel("Sangre: "));
        txtSangre = new JTextField();
        contentPane.add(txtSangre);

        contentPane.add(new JLabel("Nacimiento: (dd/mm/yy)"));
        txtFechaNac = new JTextField();
        contentPane.add(txtFechaNac);

        contentPane.add(new JLabel("Marca de calzado:"));
        txtMarca = new JTextField();
        contentPane.add(txtMarca);

        contentPane.add(new JLabel("Equipo:"));
        comboEquipos = new JComboBox<>();
        cargarEquiposEnComboBox();
        contentPane.add(comboEquipos);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarJugador());
        contentPane.add(btnRegistrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        contentPane.add(btnCancelar);
    }

    private void cargarEquiposEnComboBox() {
        comboEquipos.removeAllItems(); 
        
        ArrayList<Equipo> equipos = SerieNacionaldeBasket.getInstance().getEquipos();
     
        if (equipos == null || equipos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay equipos registrados. Por favor registre un equipo primero.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        for (Equipo equipo : equipos) {
            comboEquipos.addItem(equipo.getNombreEquipo());
        }
    }

    private void registrarJugador() {
        try {
            
            if (txtNombre.getText().isEmpty() || txtPosicion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y posicion son campos obligatorios", 
                                           "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (comboEquipos.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un equipo", 
                                           "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Jugador jugador = new Jugador(
                txtNombre.getText(),
                (int) spEdad.getValue(),
                txtPosicion.getText(),
                (int) spNumJugador.getValue(),
                null, 
                (int) spEstatura.getValue(),
                txtSangre.getText(),
                ((Double) spPeso.getValue()).floatValue(),
                txtFechaNac.getText(),
                txtMarca.getText()
            );

            
            String nombreEquipo = (String) comboEquipos.getSelectedItem();
            boolean registrado = SerieNacionaldeBasket.getInstance().agregarJugadorAEquipo(jugador, nombreEquipo);

            if (registrado) {
                JOptionPane.showMessageDialog(this, 
                    "Jugador registrado exitosamente en " + nombreEquipo + "!\n" +
                    "Nombre: " + jugador.getNombreJugador() + "\n" +
                    "Numero: " + jugador.getNumJugador(),
                    "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar el jugador. Verifique que el numero no este repetido.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        spEdad.setValue(18);
        txtPosicion.setText("");
        spNumJugador.setValue(1);
        spEstatura.setValue(170);
        txtSangre.setText("");
        spPeso.setValue(70.0);
        txtFechaNac.setText("");
        txtMarca.setText("");
    }
}