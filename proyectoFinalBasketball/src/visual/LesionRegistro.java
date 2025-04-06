package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logico.Lesion;
import logico.Jugador;
import logico.SerieNacionaldeBasket;

public class LesionRegistro extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField txtDuracion;
    private JComboBox<String> cbxTipoLesion;
    private Jugador jugador;

    public LesionRegistro(Jugador jugador) {
        this.jugador = jugador;
        setTitle("Registrar Lesión");
        setBounds(100, 100, 350, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblTipoLesion = new JLabel("Tipo de Lesión:");
        lblTipoLesion.setBounds(12, 25, 100, 16);
        contentPanel.add(lblTipoLesion);

        cbxTipoLesion = new JComboBox<>();
        cbxTipoLesion.setModel(new DefaultComboBoxModel<>(new String[]{
            "Esguince", "Fractura", "Desgarro", "Tendinitis", "Luxación"
        }));
        cbxTipoLesion.setBounds(124, 22, 200, 22);
        contentPanel.add(cbxTipoLesion);

        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setBounds(12, 60, 100, 16);
        contentPanel.add(lblDuracion);

        txtDuracion = new JTextField();
        txtDuracion.setBounds(124, 57, 100, 22);
        contentPanel.add(txtDuracion);
        txtDuracion.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarLesion();
            }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);
    }

    private void registrarLesion() {
        try {
            if(!txtDuracion.getText().isEmpty()) {
                String tipo = cbxTipoLesion.getSelectedItem().toString();
                int duracion = Integer.parseInt(txtDuracion.getText());
                
                if(duracion <= 0) {
                    JOptionPane.showMessageDialog(this, "La duración debe ser mayor a 0 días", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Lesion lesion = new Lesion(tipo, duracion, jugador);
                boolean registrado = SerieNacionaldeBasket.getInstance().registrarLesion(lesion);
                
                if(registrado) {
                    JOptionPane.showMessageDialog(this, "Lesión registrada exitosamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "El jugador ya tiene una lesión activa", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número válido", 
                                      "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}