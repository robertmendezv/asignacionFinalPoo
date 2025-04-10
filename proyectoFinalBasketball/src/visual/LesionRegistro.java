package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import logico.Jugador;
import logico.Lesion;
import logico.SerieNacionaldeBasket;

public class LesionRegistro extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField txtDuracion;
    private JComboBox<String> cbxTipoLesion;
    private Jugador jugador;
    private ImageIcon nurseIcon;
    private final Dimension imageSize = new Dimension(318, 159);

    public LesionRegistro(Jugador jugador) {
        this.jugador = jugador;
        setTitle("Registrar Lesión");
        setBounds(100, 100, 430, 400);
        setLocationRelativeTo(null);
        
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        loadNurseImage();
        
        setupFormComponents();
        
        // Configurar panel de imagen
        setupImagePanel();
        
        setupButtonPanel();
    }

    private void loadNurseImage() {
        try {
            String projectPath = System.getProperty("user.dir");
            String imagePath = projectPath + File.separator + "Imagenes" + File.separator + "nurse.jpg";
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                Image img = new ImageIcon(imagePath).getImage();
                nurseIcon = new ImageIcon(img.getScaledInstance(
                    imageSize.width, imageSize.height, Image.SCALE_SMOOTH));
            } else {
                nurseIcon = new ImageIcon(getClass().getResource("/Imagenes/nurse.jpg"));
                if (nurseIcon != null) {
                    Image img = nurseIcon.getImage();
                    nurseIcon = new ImageIcon(img.getScaledInstance(
                        imageSize.width, imageSize.height, Image.SCALE_SMOOTH));
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "No se pudo cargar la imagen de la enfermera", 
                "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setupFormComponents() {
        JLabel lblTipoLesion = new JLabel("Tipo de Lesión:");
        lblTipoLesion.setBounds(30, 30, 120, 25);
        contentPanel.add(lblTipoLesion);

        cbxTipoLesion = new JComboBox<>();
        cbxTipoLesion.setModel(new DefaultComboBoxModel<>(new String[]{
            "Esguince", "Fractura", "Desgarro", "Tendinitis", "Luxación"
        }));
        cbxTipoLesion.setBounds(160, 30, 220, 25);
        contentPanel.add(cbxTipoLesion);

        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setBounds(30, 80, 120, 25);
        contentPanel.add(lblDuracion);

        txtDuracion = new JTextField();
        txtDuracion.setBounds(160, 80, 80, 25);
        contentPanel.add(txtDuracion);
        txtDuracion.setColumns(10);
    }

    private void setupImagePanel() {
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (nurseIcon != null) {
                    int x = (getWidth() - nurseIcon.getIconWidth()) / 2;
                    int y = (getHeight() - nurseIcon.getIconHeight()) / 2;
                    nurseIcon.paintIcon(this, g, x, y);
                } else {
                    g.setColor(Color.RED);
                    g.drawString("Imagen no encontrada (318x159)", 10, 20);
                }
            }
        };
        imagePanel.setBorder(null);
        
        imagePanel.setBounds(30, 120, imageSize.width, imageSize.height);
        imagePanel.setBackground(Color.WHITE);
        contentPanel.add(imagePanel);
    }

    private void setupButtonPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.setBackground(new Color(220, 220, 220));
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