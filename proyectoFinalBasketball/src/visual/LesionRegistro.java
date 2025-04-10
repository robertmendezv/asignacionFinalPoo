package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import logico.Jugador;
import logico.Lesion;
import logico.SerieNacionaldeBasket;
import javax.swing.DefaultComboBoxModel;

public class LesionRegistro extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField txtDuracion;
    private JComboBox<String> cbxTipoLesion;
    private Jugador jugador;
    private static final long serialVersionUID = 1L;
    

    public LesionRegistro(Jugador jugador) {
        this.jugador = jugador;
        setTitle("Registrar Lesion");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

       
        try {
        
        	String projectPath = System.getProperty("user.dir");
         
            String imagePath = projectPath + File.separator + "Imagenes" + File.separator + "nurse.jpg";
            File imageFile = new File(imagePath);

            
            if (!imageFile.exists()) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró la imagen en:\n" + imagePath, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                
                Image img = ImageIO.read(imageFile);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                JLabel lblImagen = new JLabel(icon);
                lblImagen.setBounds(10, 10, 100, 100);
                contentPanel.add(lblImagen);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar la imagen: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }

      
        JLabel lblTipoLesion = new JLabel("Tipo de Lesión:");
        lblTipoLesion.setBounds(120, 25, 100, 16);
        contentPanel.add(lblTipoLesion);

        cbxTipoLesion = new JComboBox<>();
        cbxTipoLesion.setModel(new DefaultComboBoxModel<>(new String[]{
            "Esguince", "Fractura", "Desgarro", "Tendinitis", "Luxación"
        }));
        cbxTipoLesion.setBounds(230, 22, 200, 22);
        contentPanel.add(cbxTipoLesion);

        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setBounds(120, 60, 100, 16);
        contentPanel.add(lblDuracion);

        txtDuracion = new JTextField();
        txtDuracion.setBounds(230, 57, 100, 22);
        contentPanel.add(txtDuracion);
        txtDuracion.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Registrar");
        okButton.addActionListener(e -> registrarLesion());
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        buttonPane.add(cancelButton);
    }

    private void registrarLesion() {
        try {
            if (!txtDuracion.getText().isEmpty()) {
                String tipo = cbxTipoLesion.getSelectedItem().toString();
                int duracion = Integer.parseInt(txtDuracion.getText());
                
                if (duracion <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "La duración debe ser mayor a 0 días", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Lesion lesion = new Lesion(tipo, duracion, jugador);
                boolean registrado = SerieNacionaldeBasket.getInstance().registrarLesion(lesion);
                
                if (registrado) {
                    JOptionPane.showMessageDialog(this, "Lesión registrada exitosamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "El jugador ya tiene una lesión activa", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "La duración debe ser un número válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}